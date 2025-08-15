package io.enderdev.patchoulibooks.mixins.patchouli;

import com.google.gson.annotations.SerializedName;
import io.enderdev.patchoulibooks.Tags;
import io.enderdev.patchoulibooks.unlock.Requirement;
import net.minecraft.util.ResourceLocation;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(value = BookEntry.class, remap = false)
public class BookEntryMixin {

    @Shadow
    Book book;

    @Shadow
    boolean locked;

    @Shadow
    boolean built;

    @Unique
    @SerializedName("force_locked")
    public boolean force_locked;

    @Unique
    @SerializedName("requirements")
    public Requirement[] patchouliBooks$requirements;

    @Unique
    public List<Requirement> patchouliBooks$realRequirements = new ArrayList<>();

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
        if (force_locked || patchouliBooks$checkRequirements()) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(method = "updateLockStatus", at = @At("HEAD"))
    private void updateLockStatus(CallbackInfo ci) {
        if (force_locked || patchouliBooks$checkRequirements()) {
            locked = true;
        }
    }

    @Inject(method = "build", at = @At("HEAD"))
    private void build(ResourceLocation resource, CallbackInfo ci) {
        if (!built) {
            patchouliBooks$realRequirements.addAll(Arrays.asList(patchouliBooks$requirements));
        }
    }

    @Unique
    private boolean patchouliBooks$checkRequirements() {
        return patchouliBooks$requirements != null && patchouliBooks$requirements.length != 0 && !patchouliBooks$realRequirements.stream().allMatch(Requirement::unlocked);
    }
}
