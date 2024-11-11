package io.enderdev.patchoulibooks.mixins.jei;

import io.enderdev.patchoulibooks.integration.jei.IButtonAccessor;
import io.enderdev.patchoulibooks.integration.jei.PatchouliButton;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IIngredientType;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.gui.ingredients.GuiIngredientGroup;
import mezz.jei.gui.ingredients.GuiItemStackGroup;
import mezz.jei.gui.recipes.RecipeLayout;
import mezz.jei.plugins.vanilla.crafting.ShapedRecipesWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

import java.util.Map;


@Mixin(value = RecipeLayout.class, remap = false)
public abstract class RecipeLayoutMixin implements IButtonAccessor {
    @Shadow
    public abstract IGuiItemStackGroup getItemStacks();

    @Shadow
    @Final
    private Map<IIngredientType, GuiIngredientGroup> guiIngredientGroups;
    @Shadow
    @Final
    private GuiItemStackGroup guiItemStackGroup;
    @Shadow @Final private IRecipeWrapper recipeWrapper;
    @Unique
    private PatchouliButton patchouliBooks$patchouliButton;

    @Override
    public PatchouliButton patchouliBooks$getPatchouliButton() {
        return patchouliBooks$patchouliButton;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(int index, IRecipeCategory recipeCategory, IRecipeWrapper recipeWrapper, IFocus focus, int posX, int posY, CallbackInfo ci) {
        if (focus != null && focus.getValue() instanceof ItemStack) {
            ItemStack itemStack = (ItemStack) focus.getValue();
            Book book = BookRegistry.INSTANCE.books.values().stream().filter(book1 -> book1.contents.entries.values().stream().anyMatch(bookEntry -> bookEntry.isStackRelevant(itemStack))).findFirst().orElse(null);
            if (book != null && index >= 0) {
                patchouliBooks$patchouliButton = new PatchouliButton(90 + index, 0, 0, 16, 16, book, itemStack);
            }
            if (patchouliBooks$patchouliButton != null) {
                int width = recipeCategory.getBackground().getWidth();
                int height = recipeCategory.getBackground().getHeight();
                patchouliBooks$patchouliButton.x = posX + width + 4 + 2;
                patchouliBooks$patchouliButton.y = posY + height - 13 - 20;
            }
        }
    }

    @Inject(method = "drawRecipe", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;disableBlend()V"))
    private void drawRecipe(Minecraft minecraft, int mouseX, int mouseY, CallbackInfo ci) {
        if (patchouliBooks$patchouliButton != null && patchouliBooks$patchouliButton.isUnlocked()) {
            float partialTicks = minecraft.getRenderPartialTicks();
            patchouliBooks$patchouliButton.drawButton(minecraft, mouseX, mouseY, partialTicks);
        }
    }

    @Inject(method = "drawOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;disableBlend()V"))
    private void drawOverlays(Minecraft minecraft, int mouseX, int mouseY, CallbackInfo ci) {
        if (patchouliBooks$patchouliButton != null && patchouliBooks$patchouliButton.isUnlocked()) {
            patchouliBooks$patchouliButton.drawToolTip(minecraft, mouseX, mouseY);
        }
    }

    @Inject(method = "isMouseOver", at = @At("RETURN"), cancellable = true)
    private void isMouseOver(int mouseX, int mouseY, CallbackInfoReturnable<Boolean> cir) {
        if (patchouliBooks$patchouliButton != null && patchouliBooks$patchouliButton.isUnlocked()) {
            cir.setReturnValue(cir.getReturnValue() || patchouliBooks$patchouliButton.isMouseOver());
        }
    }
}
