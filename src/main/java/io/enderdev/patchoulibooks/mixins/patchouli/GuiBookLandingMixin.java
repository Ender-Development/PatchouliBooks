package io.enderdev.patchoulibooks.mixins.patchouli;

import io.enderdev.patchoulibooks.extension.BookExtension;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.client.book.gui.GuiBookLanding;

@Mixin(value = GuiBookLanding.class, remap = false)
public class GuiBookLandingMixin {
    @ModifyArg(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/I18n;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"))
    private String modifyLandingText(String title) {
        GuiBook gui = (GuiBook) (Object) this;
        Book book = gui.book;
        if (book instanceof BookExtension) {
            BookExtension bookExtension = (BookExtension) book;
            return I18n.format("patchoulibooks.gui.lexicon.landing", bookExtension.linkCurseforge, bookExtension.linkGitHub, bookExtension.linkWiki);
        }
        return title;
    }
}
