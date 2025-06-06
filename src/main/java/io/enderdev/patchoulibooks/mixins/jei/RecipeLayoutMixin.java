package io.enderdev.patchoulibooks.mixins.jei;

import io.enderdev.patchoulibooks.PBConfig;
import io.enderdev.patchoulibooks.integration.jei.IButtonAccessor;
import io.enderdev.patchoulibooks.integration.jei.PatchouliButton;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.gui.recipes.RecipeLayout;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Mixin(value = RecipeLayout.class, remap = false)
public abstract class RecipeLayoutMixin implements IButtonAccessor {

    @Unique
    private List<PatchouliButton> patchouliBooks$patchouliButton = new ArrayList<>();

    @Override
    public List<PatchouliButton> patchouliBooks$getPatchouliButton() {
        return patchouliBooks$patchouliButton;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(int index, IRecipeCategory recipeCategory, IRecipeWrapper recipeWrapper, IFocus focus, int posX, int posY, CallbackInfo ci) {
        if (focus != null && focus.getValue() instanceof ItemStack && PBConfig.JEI.enableBooksInJEI) {
            ItemStack itemStack = (ItemStack) focus.getValue();
            List<Book> bookList = BookRegistry.INSTANCE.books.values().stream()
                    .filter(book -> !Arrays.asList(PBConfig.JEI.blacklistBook).contains(book.resourceLoc.toString()))
                    .filter(book1 -> book1.contents.entries.values().stream().anyMatch(bookEntry -> bookEntry.isStackRelevant(itemStack) && !bookEntry.isLocked()))
                    .collect(Collectors.toList());
            if (!bookList.isEmpty() && index >= 0) {
                int width = recipeCategory.getBackground().getWidth();
                int height = recipeCategory.getBackground().getHeight();
                AtomicInteger c = new AtomicInteger();
                bookList.forEach(book -> {
                    if (book.name.isEmpty()) return;
                    patchouliBooks$patchouliButton.add(new PatchouliButton(90 + index + c.get(), 0, 0, 16, 16, book, itemStack));
                    patchouliBooks$patchouliButton.get(c.get()).x = c.get() <= 1 ? posX + width + 6 : posX + width + 6 + 18;
                    patchouliBooks$patchouliButton.get(c.get()).y = c.get() <= 1 ? posY + height - 31 - c.get() * 18 : posY + height - 31 - (c.get() - 2) * 18;
                    c.getAndIncrement();
                });
            }
        }
    }

    @Inject(method = "drawRecipe", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;disableBlend()V", remap = true), remap = false)
    private void drawRecipe(Minecraft minecraft, int mouseX, int mouseY, CallbackInfo ci) {
        if (!patchouliBooks$patchouliButton.isEmpty()) {
            float partialTicks = minecraft.getRenderPartialTicks();
            patchouliBooks$patchouliButton.forEach(patchouliButton -> patchouliButton.drawButton(minecraft, mouseX, mouseY, partialTicks));
        }
    }

    @Inject(method = "drawOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;disableBlend()V", remap = true), remap = false)
    private void drawOverlays(Minecraft minecraft, int mouseX, int mouseY, CallbackInfo ci) {
        if (!patchouliBooks$patchouliButton.isEmpty()) {
            patchouliBooks$patchouliButton.forEach(patchouliButton -> patchouliButton.drawToolTip(minecraft, mouseX, mouseY));
        }
    }

    @Inject(method = "isMouseOver", at = @At("RETURN"), cancellable = true)
    private void isMouseOver(int mouseX, int mouseY, CallbackInfoReturnable<Boolean> cir) {
        if (!patchouliBooks$patchouliButton.isEmpty()) {
            cir.setReturnValue(cir.getReturnValue() || patchouliBooks$patchouliButton.stream().anyMatch(PatchouliButton::isMouseOver));
        }
    }
}
