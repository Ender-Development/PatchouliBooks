package io.enderdev.patchoulibooks.mixins.patchouli;

import com.google.gson.Gson;
import io.enderdev.patchoulibooks.PatchouliBooks;
import io.enderdev.patchoulibooks.Tags;
import io.enderdev.patchoulibooks.PBConfig;
import io.enderdev.patchoulibooks.integration.patchouli.BookExtension;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

import java.io.*;
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
    private void loadBook(ModContainer mod, ResourceLocation res, InputStream stream, boolean external, CallbackInfo ci) throws IOException {
        PatchouliBooks.LOGGER.debug("Loading {}: [Mod]<{}> || [Book]<{}>", external ? "EXTERNAL" : "INTERNAL", mod.getModId(), res.getPath());
        // return if the book is added by another mod
        if (!mod.getModId().equals(Tags.MOD_ID) && !external) {
            return;
        }
        // return if the required mod for one of my books isn't loaded
        if (!Loader.isModLoaded(res.getPath()) && !PBConfig.DEBUG.enableDebug && !external) {
            PatchouliBooks.LOGGER.debug("Cancelling: [Book]<{}>, because the mod is not present.", res.getPath());
            ci.cancel();
            return;
        }

        // No return after this point as it consumes the InputStream
        // The book.json file should be small enough to load it in memory
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        patchouliBooks$copyStream(stream, buffer);
        byte[] data = buffer.toByteArray();

        // duplicate the Reader so I can read it multiple times
        Reader reader1 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data), StandardCharsets.UTF_8));
        Reader reader2 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data), StandardCharsets.UTF_8));

        BookExtension book = gson.fromJson(reader1, BookExtension.class);
        if (book == null) {
            PatchouliBooks.LOGGER.error("Failed to load [Book]<{}> from [Mod]<{}>", res, mod.getModId());
            ci.cancel();
            return;
        }
        // implement default book building
        if (!book.bookPlus && !mod.getModId().equals(Tags.MOD_ID)) {
            Book pbook = this.gson.fromJson(reader2, Book.class);
            this.books.put(res, pbook);
            pbook.build(mod, res, external);
        }
        // build my BookExtension
        if (!book.bookDisabled && !books.containsKey(res)) {
            books.put(res, book);
            book.build(mod, res, external);
        }
        ci.cancel();
    }

    @Unique
    private static void patchouliBooks$copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
}
