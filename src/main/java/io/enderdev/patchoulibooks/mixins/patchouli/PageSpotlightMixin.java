package io.enderdev.patchoulibooks.mixins.patchouli;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.patchouli.client.book.page.PageSpotlight;

@Mixin(value = PageSpotlight.class, remap = false)
public class PageSpotlightMixin {
    /**
     * Set the text height to 35 instead of 40 to decrease the space between the text and the image
     */
    @Inject(method = "getTextHeight", at = @At("HEAD"), cancellable = true)
    private void getTextHeight(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(35);
    }
}
