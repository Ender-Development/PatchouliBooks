package io.enderdev.patchoulibooks.pages;

import com.google.gson.annotations.SerializedName;
import io.enderdev.patchoulibooks.PatchouliBooks;
import io.enderdev.patchoulibooks.util.MathUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.gui.BookTextRenderer;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.common.util.ItemStackUtil;

import java.util.List;

@PageRegister("pillar")
public class PagePillar extends PageBase {
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

    @Override
    public void build(BookEntry entry, int pageNum) {
        super.build(entry, pageNum);
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
        textRenderer = new BookTextRenderer(parent, String.format("$(l)%.1fs$()", ((double)time)/20), 20, 22);
    }

    @Override
    public void render(int mouseX, int mouseY, float pticks) {
        drawTitle(!title.isEmpty() ? title : itemOutput.getDisplayName());
        drawItem(true, itemOutput, 50, 20, mouseX, mouseY);
        drawItemWithPillar(itemMiddle, true, 50, 78, mouseX, mouseY);

        if (time > 0) {
            drawFullTexture(TEXTURES.get("hourglass"), 0, 20, 16, 16);
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
            drawItemWithPillar(ingr, false, x, y, mouseX, mouseY);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (textRenderer != null) {
            textRenderer.click(mouseX, mouseY, mouseButton);
        }
    }

    private void drawItemWithPillar(Object item, boolean core, int posX, int posY, int mouseX, int mouseY) {
        drawItem(false, item, posX, posY, mouseX, mouseY);
        drawFullTexture(core ? TEXTURES.get("pillar_middle") : TEXTURES.get("pillar"), posX, posY + 12, 16, 16);
    }
}
