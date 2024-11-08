package io.enderdev.patchoulibooks.mixins.patchouli;

import io.enderdev.patchoulibooks.Tags;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import vazkii.patchouli.client.book.BookContents;
import vazkii.patchouli.common.book.Book;

import java.nio.file.Path;
import java.util.List;
import java.util.function.BiFunction;

@Mixin(value = BookContents.class, remap = false)
public abstract class BookContentsMixin {

    @Final
    @Shadow
    public Book book;

    @Shadow
    protected abstract BiFunction<Path, Path, Boolean> pred(String modId, List<ResourceLocation> list);

    @SideOnly(Side.CLIENT)
    @Inject(method = "getSubtitle", at = @At("HEAD"), cancellable = true)
    private void getSubtitle(CallbackInfoReturnable<String> cir) {
        BookContents contents = (BookContents) (Object) this;
        if (patchouliBooks$isVersion(contents.book.version)) {
            cir.setReturnValue(I18n.format("patchoulibooks.gui.lexicon.version_str", contents.book.version));
        }
    }

    @Inject(method = "findFiles", at = @At("TAIL"))
    private void globalTemplate(String dir, List<ResourceLocation> list, CallbackInfo ci) {
        ModContainer mod = book.owner;
        String id = mod.getModId();
        if (mod.getModId().equals(Tags.MOD_ID) && dir.equals("templates")) {
            CraftingHelper.findFiles(mod, String.format("assets/%s/%s", id, dir), null, pred(id, list), false, false);
        }
    }

    @ModifyArgs(method = "loadLocalizedJson", at = @At(value = "INVOKE", target = "Lvazkii/patchouli/client/book/BookContents;loadJson(Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/util/ResourceLocation;)Ljava/io/InputStream;"))
    private void loadJson(Args args) {
        ResourceLocation loc = args.get(0);
        ResourceLocation res = args.get(1);
        if (res.getNamespace().equals(Tags.MOD_ID) && res.getPath().split("/")[3].equals("templates")) {
            ResourceLocation redirect = new ResourceLocation(Tags.MOD_ID, "templates/" + res.getPath().split("templates/")[1]);
            args.set(0, redirect);
            args.set(1, redirect);
        }
    }

    @Unique
    private static boolean patchouliBooks$isVersion(String version) {
        // Define the regular expression pattern matching the version format (e.g. 1.0.0)
        String pattern = "\\d+\\.\\d+\\.\\d+";
        return version.matches(pattern);
    }
}
