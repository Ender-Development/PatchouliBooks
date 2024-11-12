package io.enderdev.patchoulibooks.pages;

import com.google.gson.annotations.SerializedName;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.gui.BookTextRenderer;
import vazkii.patchouli.client.book.gui.GuiBookEntry;

@PageDecorator("spotlight+")
public class PageSpotlight extends PageBase {
    @SerializedName("item")
    String objRaw;
    String title;
    String text;

    transient BookTextRenderer textRenderer;

    @SerializedName("link_recipe")
    boolean linkRecipe;

    transient RenderObject obj;

    @Override
    public void build(BookEntry entry, int pageNum) {
        super.build(entry, pageNum);
        obj = new RenderObject(objRaw);
        if (obj.isItem() && linkRecipe) {
            entry.addRelevantStack(obj.getItemStack(), pageNum);
        }
        if (title == null) {
            title = obj.isItem() ? obj.getItemStack().getDisplayName() : "";
            if (title.isEmpty() && pageNum == 0) {
                title = entry.getName();
            }
        }
    }

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
        textRenderer = new BookTextRenderer(parent, text, 0, !title.isEmpty() ? DIST_SEP_TEXT + 3 * TEXT_LINE_HEIGHT : DIST_SEP_TEXT + TEXT_LINE_HEIGHT);
    }

    @Override
    public void render(int mouseX, int mouseY, float pticks) {
        if (!title.isEmpty()) {
            drawTitle(title);
        }
        drawHighlightItem(obj, !title.isEmpty() ? DIST_SEP_TEXT : DIST_SEP_TEXT - 2 * TEXT_LINE_HEIGHT, mouseX, mouseY);
        textRenderer.render(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (textRenderer != null) {
            textRenderer.click(mouseX, mouseY, mouseButton);
        }
    }
}
