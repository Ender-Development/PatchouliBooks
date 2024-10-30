package io.enderdev.patchoulibooks.mixins.patchouli;

import io.enderdev.patchoulibooks.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.EntryDisplayState;
import vazkii.patchouli.common.book.Book;

@Mixin(value = BookEntry.class, remap = false)
public class BookEntryMixin {

    @Shadow
    Book book;

    /**
     * Mark all my entries as read by default instead of having to set this for each entry
     */
    @Inject(method = "computeReadState", at = @At("HEAD"), cancellable = true)
    private void computeReadState(CallbackInfoReturnable<EntryDisplayState> cir) {
        if (book.owner.getModId().equals(Tags.MOD_ID)) {
            cir.setReturnValue(EntryDisplayState.NEUTRAL);
            cir.cancel();
        }
    }
}
