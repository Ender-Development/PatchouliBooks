package io.enderdev.patchoulibooks.mixins.patchouli;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.patchouli.client.book.gui.GuiBook;

@Mixin(value = GuiBook.class, remap = false)
public class GuiBookMixin {
    @Inject(method = "updateScreen", at = @At("TAIL"))
    private void patchouliBooks$updateScreen(CallbackInfo ci) {
        GuiBook guiBook = (GuiBook) (Object) this;
        guiBook.book.reloadLocks(false);
    }
}
