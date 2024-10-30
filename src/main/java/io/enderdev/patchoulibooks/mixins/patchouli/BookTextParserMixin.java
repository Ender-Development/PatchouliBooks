package io.enderdev.patchoulibooks.mixins.patchouli;

import io.enderdev.patchoulibooks.PatchouliBooks;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.text.BookTextParser;
import vazkii.patchouli.client.book.text.SpanState;

@Mixin(value = BookTextParser.class, remap = false)
public class BookTextParserMixin {
    @Inject(method = "lambda$static$14", at = @At(value = "RETURN"))
    private static void addLog(String parameter, SpanState state, CallbackInfoReturnable<String> cir) {
        ResourceLocation href = new ResourceLocation(state.book.getModNamespace(), parameter);
        BookEntry entry = state.book.contents.entries.get(href);
        if (state.tooltip.contains("BAD") || state.tooltip.contains("INVALID")) {
            PatchouliBooks.LOGGER.warn("Found a broken [LINK]<{}> to [ENTRY]<{}>. [HINT]<{}>", parameter, entry.getName(), state.tooltip);
        }
    }
}
