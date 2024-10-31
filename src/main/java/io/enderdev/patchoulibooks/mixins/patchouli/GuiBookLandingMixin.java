package io.enderdev.patchoulibooks.mixins.patchouli;

import io.enderdev.patchoulibooks.extension.BookExtension;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.client.book.gui.button.GuiButtonEntry;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.client.book.gui.GuiBookLanding;

import java.util.ArrayList;
import java.util.Collections;

@Mixin(value = GuiBookLanding.class, remap = false)
public abstract class GuiBookLandingMixin extends GuiBook {

    @Shadow
    int loadedCategories;

    @Shadow
    abstract void makeErrorTooltip();

    private GuiBookLandingMixin(Book book) {
        super(book);
    }

    @ModifyArg(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/I18n;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"))
    private String modifyLandingText(String title) {
        Book book = this.book;
        if (book instanceof BookExtension) {
            BookExtension bookExtension = (BookExtension) book;
            return I18n.format("patchoulibooks.gui.lexicon.landing", bookExtension.linkCurseforge, bookExtension.linkGitHub, bookExtension.linkWiki);
        }
        return title;
    }

    @Inject(method = "initGui", at = @At(value = "INVOKE", target = "Ljava/util/Collections;sort(Ljava/util/List;)V", remap = false), cancellable = true)
    private void renderPamphlet(CallbackInfo ci) {
        Book book = this.book;
        if (book instanceof BookExtension && book.contents.categories.size() <= 1 && book.contents.entries.size() <= 12) {
            BookExtension bookExtension = (BookExtension) book;
            ArrayList<BookEntry> entriesInPamphlet = new ArrayList<>(bookExtension.contents.entries.values());
            entriesInPamphlet.removeIf(BookEntry::shouldHide);
            Collections.sort(entriesInPamphlet);
            int i = 0;
            for (BookEntry entry : entriesInPamphlet) {
                buttonList.add(new GuiButtonEntry(this, bookLeft + RIGHT_PAGE_X, bookTop + TOP_PADDING + 18 + (i * 11), entry, i));
                i++;
            }
            loadedCategories = i;
            ci.cancel();
        }
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"))
    private void actionPerformed(GuiButton button, CallbackInfo ci) {
        if (button instanceof GuiButtonEntry) {
            displayLexiconGui(new GuiBookEntry(book, ((GuiButtonEntry) button).getEntry()), true);
        }
    }

    @Inject(method = "drawForegroundElements", at = @At(value = "INVOKE", target = "Lvazkii/patchouli/client/book/gui/GuiBookLanding;drawCenteredStringNoShadow(Ljava/lang/String;III)V"), cancellable = true)
    private void drawForegroundElements(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        Book book = this.book;
        if (book.contents.categories.size() <= 1 && book.contents.entries.size() <= 12 && book instanceof BookExtension) {
            BookExtension bookExtension = (BookExtension) book;
            drawCenteredStringNoShadow(I18n.format("patchoulibooks.gui.lexicon.pamphlet"), RIGHT_PAGE_X + PAGE_WIDTH / 2, TOP_PADDING, book.headerColor);
            int topSeparator = TOP_PADDING + 12;
            int bottomSeparator = topSeparator + 8 + 11 * loadedCategories;
            patchouliBooks$drawHeader();
            drawSeparator(book, RIGHT_PAGE_X, topSeparator);
            if (loadedCategories <= 12) {
                drawSeparator(book, RIGHT_PAGE_X, bottomSeparator);
            }
            if (book.contents.isErrored()) {
                int x = RIGHT_PAGE_X + PAGE_WIDTH / 2;
                int y = bottomSeparator + 12;
                drawCenteredStringNoShadow(I18n.format("patchouli.gui.lexicon.loading_error"), x, y, 0xFF0000);
                drawCenteredStringNoShadow(I18n.format("patchouli.gui.lexicon.loading_error_hover"), x, y + 10, 0x777777);
                x -= PAGE_WIDTH / 2;
                y -= 4;
                if (isMouseInRelativeRange(mouseX, mouseY, x, y, PAGE_WIDTH, 20))
                    makeErrorTooltip();
            }
            ci.cancel();
        }
    }

    @Unique
    private void patchouliBooks$drawHeader() {
        GlStateManager.color(1F, 1F, 1F, 1F);
		drawFromTexture(book, -8, 12, 0, 180, 140, 31);

		int color = book.nameplateColor;
		boolean unicode = fontRenderer.getUnicodeFlag();
        String displayName = book.getBookItem().getDisplayName();
		fontRenderer.drawString(displayName.substring(0,displayName.length() - 6), 13, 16, color);

		if(!book.useBlockyFont)
			fontRenderer.setUnicodeFlag(true);
		fontRenderer.drawString(book.contents.getSubtitle(), 24, 24, color);
		fontRenderer.setUnicodeFlag(unicode);
    }
}
