package io.enderdev.patchoulibooks.mixins.patchouli;

import com.google.gson.annotations.SerializedName;
import io.enderdev.patchoulibooks.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.EntryDisplayState;
import vazkii.patchouli.common.book.Book;

@Mixin(value = BookEntry.class, remap = false)
public class BookEntryMixin {

    @Shadow
    Book book;

    @Shadow
    boolean locked;

    @Unique
    @SerializedName("force_locked")
    public boolean force_locked;

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

    @Inject(method = "isLocked", at = @At("HEAD"), cancellable = true)
    private void isLocked(CallbackInfoReturnable<Boolean> cir) {
        if (force_locked) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(method = "updateLockStatus", at = @At("HEAD"))
    private void updateLockStatus(CallbackInfo ci) {
        if (force_locked) {
            locked = true;
        }
    }
}
