package io.enderdev.patchoulibooks.mixins.jei;

import io.enderdev.patchoulibooks.integration.jei.IButtonAccessor;
import io.enderdev.patchoulibooks.integration.jei.PatchouliButton;
import mezz.jei.gui.recipes.RecipeLayout;
import mezz.jei.gui.recipes.RecipesGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = RecipesGui.class, remap = false)
public class RecipesGuiMixin extends GuiScreen {

    @Inject(method = "addRecipeTransferButtons", at = @At(value = "INVOKE", target = "Lmezz/jei/gui/recipes/RecipesGui;getParentContainer()Lnet/minecraft/inventory/Container;", remap = true), remap = false)
    private void addRecipeTransferButtons(Minecraft minecraft, List<RecipeLayout> recipeLayouts, CallbackInfo ci) {
        for (RecipeLayout recipeLayout : recipeLayouts) {
            IButtonAccessor accessor = (IButtonAccessor) recipeLayout;
            List<PatchouliButton> listPB = accessor.patchouliBooks$getPatchouliButton();
            if (listPB != null && !listPB.isEmpty()) {
                listPB.forEach(this::addButton);
            }
        }
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), remap = true)
    private void actionPerformed(GuiButton guibutton, CallbackInfo ci) {
        if (guibutton instanceof PatchouliButton) {
            PatchouliButton patchouliButton = (PatchouliButton) guibutton;
            patchouliButton.openBook();
        }
    }

}
