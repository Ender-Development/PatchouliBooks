package io.enderdev.patchoulibooks.pages;

import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.gui.BookTextRenderer;
import vazkii.patchouli.client.book.gui.GuiBookEntry;

@PageDecorator("text+")
public class PageText extends PageBase {
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
        textRenderer1 = new BookTextRenderer(parent, text, 0, title != null || pageNum == 0 ? DIST_SEP_TEXT : DIST_SEP_TEXT - 2 * TEXT_LINE_HEIGHT);
        if (text2 == null) {
            text2 = "";
        }
        textRenderer2 = new BookTextRenderer(parent, text2, 0, PAGE_CENTER_VERTICAL + (title2 != null ? DIST_SEP_TEXT : 0));
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
            drawHeading(title2, PAGE_CENTER_VERTICAL - 2, true);
        }
        if (text2 != null && !text2.isEmpty()) {
            textRenderer2.render(mouseX, mouseY);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (textRenderer1 != null) {
            textRenderer1.click(mouseX, mouseY, mouseButton);
        }
        if (textRenderer2 != null) {
            textRenderer2.click(mouseX, mouseY, mouseButton);
        }
    }
}
