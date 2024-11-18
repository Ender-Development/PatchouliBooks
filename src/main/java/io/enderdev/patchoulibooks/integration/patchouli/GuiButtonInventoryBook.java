package io.enderdev.patchoulibooks.integration.patchouli;

import io.enderdev.patchoulibooks.Tags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.common.base.Patchouli;

public class GuiButtonInventoryBook extends GuiButton {

    public GuiButtonInventoryBook(int buttonId, int x, int y) {
        super(buttonId, x, y, 20, 20, "");
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        mc.renderEngine.bindTexture(new ResourceLocation(Patchouli.MOD_ID, "textures/gui/inventory_button.png"));
        GlStateManager.color(1F, 1F, 1F);
        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
        Gui.drawModalRectWithCustomSizedTexture(x, y, (hovered ? 20 : 0), 0, width, height, 64, 64);
        mc.renderEngine.bindTexture(new ResourceLocation(Tags.MOD_ID, "textures/gui/icons/akashic_tome.png"));
        Gui.drawModalRectWithCustomSizedTexture(x + 2, y + 2, 0, hovered ? 16 : 0, 16, 16, 16, 32);
    }
}
