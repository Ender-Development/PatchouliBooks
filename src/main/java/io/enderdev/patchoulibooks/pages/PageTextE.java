package io.enderdev.patchoulibooks.pages;

import io.enderdev.patchoulibooks.mixins.patchouli.PageWithTextAccessor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.client.book.gui.BookTextRenderer;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.client.book.page.abstr.PageWithText;

public class PageTextE extends PageWithText {
    String title;
    String title2;
    String text2;

    transient String text;
    transient BookTextRenderer textRenderer1, textRenderer2;

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
        text = ((PageWithTextAccessor) this).getText();
        if (text == null) text = "";
        textRenderer1 = new BookTextRenderer(parent, text, 0, title != null ? 22 : -4);
        if (text2 == null) text2 = "";
        textRenderer2 = new BookTextRenderer(parent, text2, 0, (GuiBook.PAGE_HEIGHT / 2) + 22);
    }

    @Override
    public int getTextHeight() {
        return title != null ? 22 : -4;
    }

    @Override
    public void render(int mouseX, int mouseY, float pticks) {
        boolean renderedSmol = false;
        String smolText = "";

        if (mc.gameSettings.advancedItemTooltips) {
            ResourceLocation res = parent.getEntry().getResource();
            smolText = res.toString();
        } else if (entry.isExtension()) {
            String name = entry.getTrueProvider().getOwnerName();
            smolText = I18n.format("patchouli.gui.lexicon.added_by", name);
        }

        if (!smolText.isEmpty()) {
            GlStateManager.scale(0.5F, 0.5F, 1F);
            parent.drawCenteredStringNoShadow(smolText, GuiBook.PAGE_WIDTH, 12, book.headerColor);
            GlStateManager.scale(2F, 2F, 1F);
            renderedSmol = true;
        }

        if (pageNum == 0 && title == null) {
            parent.drawCenteredStringNoShadow(parent.getEntry().getName(), GuiBook.PAGE_WIDTH / 2, renderedSmol ? -3 : 0, book.headerColor);
        }
        if (title != null && !title.isEmpty()) {
            parent.drawCenteredStringNoShadow(title, GuiBook.PAGE_WIDTH / 2, renderedSmol ? -3 : 0, book.headerColor);
            GuiBook.drawSeparator(book, 0, 12);
        }
        if (text != null && !text.isEmpty()) {
            textRenderer1.render(mouseX, mouseY);
        }
        if (title2 != null && !title2.isEmpty()) {
            parent.drawCenteredStringNoShadow(title2, GuiBook.PAGE_WIDTH / 2, GuiBook.PAGE_HEIGHT / 2, book.headerColor);
            GuiBook.drawSeparator(book, 0, (GuiBook.PAGE_HEIGHT / 2) + 12);
        }
        if (text2 != null && !text2.isEmpty()) {
            textRenderer2.render(mouseX, mouseY);
        }
    }
}
