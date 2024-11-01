package io.enderdev.patchoulibooks.pages;

import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.gui.BookTextRenderer;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.client.book.gui.GuiBookEntry;

public class PageTextE extends PageBase {
    String title;
    String title2;
    String text;
    String text2;

    transient BookTextRenderer textRenderer1, textRenderer2;

    @Override
    public void build(BookEntry entry, int pageNum) {
        super.build(entry, pageNum);
    }

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
        if (text == null) {
            text = "";
        }
        textRenderer1 = new BookTextRenderer(parent, text, 0, title != null ? 22 : -4);
        if (text2 == null) {
            text2 = "";
        }
        textRenderer2 = new BookTextRenderer(parent, text2, 0, (GuiBook.PAGE_HEIGHT / 2) + 22);
    }

    @Override
    public void render(int mouseX, int mouseY, float pticks) {

        if (pageNum == 0 && title == null) {
            drawTitle(parent.getEntry().getName());
        }
        if (title != null && !title.isEmpty()) {
            drawTitle(title);
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
