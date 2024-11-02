package io.enderdev.patchoulibooks.pages;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.common.util.ItemStackUtil;

public class RenderObject {
    private final IconType type;
    private final ItemStack stack;
    private final Ingredient ingredient;
    private final ResourceLocation res;

    public RenderObject(String str) {
        if (str.endsWith(".png")) {
            this.type = IconType.RESOURCE;
            this.stack = null;
            this.ingredient = null;
            this.res = new ResourceLocation(str);
        } else {
            this.ingredient = ItemStackUtil.loadIngredientFromString(str);
            this.stack = ItemStackUtil.loadStackFromString(str);
            this.res = null;
            if (this.stack != null && !this.stack.isEmpty()) {
                this.type = IconType.STACK;
            } else {
                this.type = IconType.INGREDIENT;
            }
        }
    }

    public RenderObject(ItemStack stack) {
        this.type = IconType.STACK;
        this.stack = stack;
        this.ingredient = null;
        this.res = null;
    }

    public RenderObject(ResourceLocation res) {
        this.type = IconType.RESOURCE;
        this.stack = null;
        this.ingredient = null;
        this.res = res;
    }

    public RenderObject(Ingredient ingredient) {
        this.type = IconType.INGREDIENT;
        this.stack = null;
        this.ingredient = ingredient;
        this.res = null;
    }

    public void render(GuiBookEntry parent, int posX, int posY, int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getMinecraft();
        if (isItem()) {
            parent.renderItemStack(posX, posY, mouseX, mouseY, getItemStack());
        } else if (isIngredient()) {
            parent.renderIngredient(posX, posY, mouseX, mouseY, getIngredient());
        } else if (isTexture()) {
            GlStateManager.color(1F, 1F, 1F, 1F);
            mc.renderEngine.bindTexture(getResourceLocation());
            Gui.drawScaledCustomSizeModalRect(posX, posY, 0, 0, 16, 16, 16, 16, 16, 16);
        }
    }

    public boolean isTexture() {
        return type == IconType.RESOURCE;
    }

    public boolean isItem() {
        return type == IconType.STACK;
    }

    public boolean isIngredient() {
        return type == IconType.INGREDIENT;
    }

    public ItemStack getItemStack() {
        return stack;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public ResourceLocation getResourceLocation() {
        return res;
    }

    private enum IconType {
        STACK, RESOURCE, INGREDIENT
    }
}
