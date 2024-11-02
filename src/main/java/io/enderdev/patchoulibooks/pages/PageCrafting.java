package io.enderdev.patchoulibooks.pages;

import com.google.gson.annotations.SerializedName;
import io.enderdev.patchoulibooks.PatchouliBooks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.gui.BookTextRenderer;
import vazkii.patchouli.client.book.gui.GuiBookEntry;

import java.util.Objects;

@PageRegister("crafting+")
public class PageCrafting extends PageBase {

    String title;
    @SerializedName("recipe")
    String recipeRaw;
    String text;

    String title2;
    @SerializedName("recipe2")
    String recipe2Raw;
    String text2;

    protected transient IRecipe recipe1, recipe2;
    protected transient BookTextRenderer textRenderer1, textRenderer2;

    @Override
    public void build(BookEntry entry, int pageNum) {
        super.build(entry, pageNum);

        recipe1 = loadRecipe(entry, recipeRaw);
        recipe2 = loadRecipe(entry, recipe2Raw);

        if (recipe1 != null && recipe1.equals(recipe2)) {
            if (text != null && !text.isEmpty()) {
                recipe1 = null;
            } else if (text2 != null && !text2.isEmpty()) {
                recipe2 = null;
            } else {
                recipe1 = null;
                recipe2 = null;
            }
        }
        if (title == null && recipe1 == null && pageNum == 0) {
            title = entry.getName();
        }

        text = text == null ? "" : text;
        text2 = text2 == null ? "" : text2;
        if (!text.isEmpty() && text2.isEmpty()) {
            text2 = text;
        }
    }

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
        textRenderer1 = new BookTextRenderer(parent, text, 0, ((title != null && !title.isEmpty() || recipeRaw != null && pageNum != 0) ? DIST_SEP_TEXT : DIST_SEP_TEXT - 2 * TEXT_LINE_HEIGHT));
        textRenderer2 = new BookTextRenderer(parent, text2, 0, PAGE_CENTER_VERTICAL + ((title2 != null && !title2.isEmpty() || recipe2Raw != null) ? DIST_SEP_TEXT : 0));
    }

    public void render(int mouseX, int mouseY, float pticks) {
        if (title != null) {
            if (!title.isEmpty()) {
                drawTitle(title);
            }
        } else if (recipeRaw != null) {
            drawTitle(getRecipeOutput(recipe1).getDisplayName());
        }
        int recipeY = 12;
        if (recipe1 != null) {
            drawCraftingRecipe(recipe1, recipeY, mouseX, mouseY);
        } else {
            textRenderer1.render(mouseX, mouseY);
        }
        if (title2 != null) {
            if (!title2.isEmpty()) {
                drawHeading(title2, PAGE_CENTER_VERTICAL - 2, true);
            }
        } else if (recipe2Raw != null) {
            drawHeading(getRecipeOutput(recipe2).getDisplayName(), PAGE_CENTER_VERTICAL - 2, true);
        }
        if (recipe2Raw != null) {
            drawCraftingRecipe(recipe2, recipeY + 76, mouseX, mouseY);
        } else {
            textRenderer2.render(mouseX, mouseY);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (textRenderer1 != null) {
            textRenderer1.click(mouseX, mouseY, mouseButton);
        }
        if (textRenderer2 != null) {
            textRenderer2.click(mouseX, mouseY, mouseButton);
        }
    }

    protected IRecipe loadRecipe(BookEntry entry, String loc) {
        // this now uses my improved method to load the recipe
        if (loc == null) {
            return null;
        }
        ResourceLocation res = new ResourceLocation(loc);
        IRecipe tempRecipe = CraftingManager.getRecipe(res);
        if (tempRecipe == null) {
            String raw = loc.split(":")[0] + ":" + loc.split(":")[1];
            int meta = loc.split(":").length == 3 ? Integer.parseInt(loc.split(":")[2]) : 0;
            Item item = Item.getByNameOrId(raw);
            if (item != null) {
                ItemStack stack = new ItemStack(item, 1, meta);
                ResourceLocation origin = CraftingManager.REGISTRY.getKeys().stream().filter((resourceLocation) -> Objects.requireNonNull(CraftingManager.REGISTRY.getObject(resourceLocation)).getRecipeOutput().isItemEqual(stack)).findFirst().orElse(null);
                if (origin != null) {
                    tempRecipe = CraftingManager.REGISTRY.getObject(origin);
                } else {
                    PatchouliBooks.LOGGER.debug("Could not find a recipe for item: {}", loc);
                }
            }
        }

        if (tempRecipe != null) {
            entry.addRelevantStack(tempRecipe.getRecipeOutput(), pageNum);
        }
        return tempRecipe;
    }

    protected ItemStack getRecipeOutput(IRecipe recipe) {
        return recipe == null ? ItemStack.EMPTY : recipe.getRecipeOutput();
    }
}
