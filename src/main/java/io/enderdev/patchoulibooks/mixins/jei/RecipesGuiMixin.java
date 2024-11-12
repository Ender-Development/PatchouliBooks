package io.enderdev.patchoulibooks.mixins.jei;

import io.enderdev.patchoulibooks.PatchouliBooks;
import io.enderdev.patchoulibooks.integration.jei.IButtonAccessor;
import io.enderdev.patchoulibooks.integration.jei.PatchouliButton;
import mezz.jei.gui.recipes.RecipeLayout;
import mezz.jei.gui.recipes.RecipesGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(value = RecipesGui.class, remap = false)
public class RecipesGuiMixin extends GuiScreen {

    @Inject(method = "addRecipeTransferButtons", at = @At(value = "INVOKE", target = "Lmezz/jei/gui/recipes/RecipeLayout;getRecipeTransferButton()Lmezz/jei/gui/recipes/RecipeTransferButton;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void addRecipeTransferButtons(Minecraft minecraft, List<RecipeLayout> recipeLayouts, CallbackInfo ci, EntityPlayer player, Container container, Iterator var5, RecipeLayout recipeLayout) {
        IButtonAccessor accessor = (IButtonAccessor) recipeLayout;
        List<PatchouliButton> listPB = accessor.patchouliBooks$getPatchouliButton();
        if (!listPB.isEmpty()) {
            listPB.forEach(button -> {
                if (button.isUnlocked()) {
                    this.addButton(button);
                }
            });
        }
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"))
    private void actionPerformed(GuiButton button, CallbackInfo ci) {
        if (button instanceof PatchouliButton) {
            PatchouliButton patchouliButton = (PatchouliButton) button;
            patchouliButton.openBook();
        }
    }
}
