package io.enderdev.patchoulibooks.mixins.patchouli;

import com.google.gson.Gson;
import io.enderdev.patchoulibooks.PatchouliBooks;
import io.enderdev.patchoulibooks.Tags;
import io.enderdev.patchoulibooks.config.ConfigMain;
import io.enderdev.patchoulibooks.integration.patchouli.BookExtension;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Mixin(value = BookRegistry.class, remap = false)
public class BookRegistryMixin {

    @Final
    @Shadow
    public Map<ResourceLocation, Book> books;

    @Shadow
    public Gson gson;

    @Inject(method = "loadBook", at = @At("HEAD"), cancellable = true)
    private void loadBook(ModContainer mod, ResourceLocation res, InputStream stream, boolean external, CallbackInfo ci) {
        PatchouliBooks.LOGGER.debug("Loading {}: [Mod]<{}> || [Book]<{}>", external ? "EXTERNAL" : "INTERNAL", mod.getModId(), res.getPath());
        if (!mod.getModId().equals(Tags.MOD_ID) && !external) {
            return;
        }
        if (!Loader.isModLoaded(res.getPath()) && !ConfigMain.DEBUG.enableDebug && !external) {
            PatchouliBooks.LOGGER.debug("Cancelling: [Book]<{}>, because the mod is not present.", res.getPath());
            ci.cancel();
            return;
        }
        Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        BookExtension book = gson.fromJson(reader, BookExtension.class);
        if (book == null) {
            PatchouliBooks.LOGGER.error("Failed to load [Book]<{}> from [Mod]<{}>", res, mod.getModId());
            PatchouliBooks.LOGGER.error("Returning to normal loading...");
            return;
        }
        if (!book.bookPlus && !mod.getModId().equals(Tags.MOD_ID)) {
            return;
        }
        if (!book.bookDisabled) {
            books.put(res, book);
            book.build(mod, res, external);
        }
        ci.cancel();
    }
}
