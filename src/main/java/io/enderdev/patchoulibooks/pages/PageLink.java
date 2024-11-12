package io.enderdev.patchoulibooks.pages;

import com.google.gson.annotations.SerializedName;
import net.minecraft.client.gui.GuiButton;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.gui.BookTextRenderer;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.client.book.gui.GuiBookEntry;

@PageDecorator("link+")
public class PageLink extends PageBase {

    String title;
    String text;
    String url;
    String link;
    String icon;
    @SerializedName("link_text")
    String linkText;
    @SerializedName("button_text")
    String buttonText;

    transient GuiButton linkButton;
    transient BookTextRenderer textRenderer;
    transient RenderObject iconObj;

    @Override
    public void build(BookEntry entry, int pageNum) {
        super.build(entry, pageNum);
        iconObj = icon != null ? new RenderObject(icon) : null;
        if (url == null) {
            url = link;
        }
        if (linkText == null && buttonText == null) {
            linkText = "Open Link";
        }
        if (buttonText != null) {
            linkText = buttonText;
        }
        if (title == null) {
            if (pageNum == 0) {
                title = entry.getName();
            } else {
                title = "";
            }
        }
    }

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
        int start;
        if (iconObj != null) {
            if (title.isEmpty()) {
                start = DIST_SEP_TEXT + TEXT_LINE_HEIGHT;
            } else {
                start = DIST_SEP_TEXT + 3 * TEXT_LINE_HEIGHT;
            }
        } else {
            if (title.isEmpty()) {
                start = DIST_SEP_TEXT - 2 * TEXT_LINE_HEIGHT;
            } else {
                start = DIST_SEP_TEXT;
            }
        }
        textRenderer = new BookTextRenderer(parent, text, 0, start);
        addButton(linkButton = new GuiButton(0, PAGE_WIDTH / 2 - 50, PAGE_HEIGHT - 35, 100, 20, linkText));
    }

    @Override
    public void render(int mouseX, int mouseY, float pticks) {
        if (!title.isEmpty()) {
            drawTitle(title);
        }
        if (iconObj != null) {
            drawHighlightItem(iconObj, !title.isEmpty() ? DIST_SEP_TEXT : DIST_SEP_TEXT - 2 * TEXT_LINE_HEIGHT, mouseX, mouseY);
        }
        textRenderer.render(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    protected void onButtonClicked(GuiButton button) {
        super.onButtonClicked(button);
        if (button == linkButton) {
            GuiBook.openWebLink(url);
        }
    }
}
