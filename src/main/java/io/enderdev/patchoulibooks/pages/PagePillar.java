package io.enderdev.patchoulibooks.pages;

import com.google.gson.annotations.SerializedName;
import io.enderdev.patchoulibooks.PatchouliBooks;
import io.enderdev.patchoulibooks.Tags;
import io.enderdev.patchoulibooks.util.MathUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.BookPage;
import vazkii.patchouli.client.book.gui.BookTextRenderer;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.common.util.ItemStackUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagePillar extends BookPage {
    String title = "";
    @SerializedName("output")
    String output;
    @SerializedName("center")
    String middle;
    @SerializedName("inputs")
    String[] inputs;
    @SerializedName("time")
    int time = 0;

    @SerializedName("link_recipe")
    boolean linkRecipe;

    transient ItemStack itemOutput;
    transient Ingredient itemMiddle;
    transient Ingredient[] itemInputs;
    transient BookTextRenderer textRenderer;

    transient ResourceLocation hourglass = new ResourceLocation(Tags.MOD_ID, "textures/templates/pedestal_crafting/hourglass.png");
    transient ResourceLocation pillar_middle = new ResourceLocation(Tags.MOD_ID, "textures/templates/pedestal_crafting/pedestal_core.png");
    transient ResourceLocation pillar = new ResourceLocation(Tags.MOD_ID, "textures/templates/pedestal_crafting/pedestal.png");

    @Override
    public void build(BookEntry entry, int pageNum) {
        itemOutput = ItemStackUtil.loadStackFromString(output);
        itemMiddle = ItemStackUtil.loadIngredientFromString(middle);

        itemInputs = new Ingredient[Math.min(inputs.length, 32)];
        for (int i = 0; i < itemInputs.length; i++) {
            itemInputs[i] = ItemStackUtil.loadIngredientFromString(inputs[i]);
        }

        if (linkRecipe) {
            entry.addRelevantStack(itemOutput, pageNum);
        }
    }

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
        textRenderer = new BookTextRenderer(parent, "$(l)" + time + "t$()", 20, 22);
    }

    @Override
    public void render(int mouseX, int mouseY, float pticks) {
        int w = 32;
        int h = 32;

        parent.drawCenteredStringNoShadow(!title.isEmpty() ? title : itemOutput.getDisplayName(), GuiBook.PAGE_WIDTH / 2, 0, book.headerColor);
        GuiBook.drawSeparator(book, 0, 12);

        parent.renderItemStack(50, 20, mouseX, mouseY, itemOutput);
        GlStateManager.enableBlend();
        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(book.craftingResource);
        Gui.drawModalRectWithCustomSizedTexture(50 - 4, 20 - 4, 83, 71, 24, 24, 128, 128);

        mc.renderEngine.bindTexture(pillar_middle);
        GlStateManager.enableBlend();
        Gui.drawModalRectWithCustomSizedTexture(50, 90, 0, 0, w / 2, h / 2, 16, 16);
        parent.renderIngredient(50, 78, mouseX, mouseY, itemMiddle);

        if (time > 0) {
            mc.renderEngine.bindTexture(hourglass);
            GlStateManager.enableBlend();
            Gui.drawModalRectWithCustomSizedTexture(0, 20, 0, 0, w / 2, h / 2, 16, 16);
            textRenderer.render(mouseX, mouseY);
        }

        List<MathUtil.Point> points = MathUtil.arrangeOnCircle(itemInputs.length, 40, 50, 78);
        for (int i = 0; i < itemInputs.length; i++) {
            Ingredient ingr = itemInputs[i];
            if (ingr == null) {
                PatchouliBooks.LOGGER.debug("Ingredient is null at index {} in entry {}", i, entry.getResource().getPath());
                continue;
            }
            int x = (int) points.get(i).getX();
            int y = (int) points.get(i).getY();
            parent.renderIngredient(x, y, mouseX, mouseY, ingr);

            mc.renderEngine.bindTexture(pillar);
            GlStateManager.enableBlend();
            Gui.drawModalRectWithCustomSizedTexture(x, y + 12, 0, 0, w / 2, h / 2, 16, 16);
        }
    }
}
