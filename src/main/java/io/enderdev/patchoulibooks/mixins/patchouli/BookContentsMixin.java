package io.enderdev.patchoulibooks.mixins.patchouli;

import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.patchouli.client.book.BookContents;

@Mixin(value = BookContents.class, remap = false)
public class BookContentsMixin {
    @Inject(method = "getSubtitle", at = @At("HEAD"), cancellable = true)
    private void getSubtitle(CallbackInfoReturnable<String> cir) {
        BookContents contents = (BookContents) (Object) this;
        if (patchouliBooks$isVersion(contents.book.version)) {
            cir.setReturnValue(I18n.format("patchoulibooks.gui.lexicon.version_str", contents.book.version));
        }
    }

    @Unique
    private static boolean patchouliBooks$isVersion(String version) {
        // Define the regular expression pattern matching the version format (e.g. 1.0.0)
        String pattern = "\\d+\\.\\d+\\.\\d+";
        return version.matches(pattern);
    }
}
