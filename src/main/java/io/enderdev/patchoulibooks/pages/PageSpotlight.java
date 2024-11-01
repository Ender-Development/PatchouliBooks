package io.enderdev.patchoulibooks.pages;

import com.google.gson.annotations.SerializedName;
import net.minecraft.item.ItemStack;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.gui.BookTextRenderer;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.common.util.ItemStackUtil;

@PageRegister("spotlight+")
public class PageSpotlight extends PageBase {
    String item;
    String title;
    String text;

    transient BookTextRenderer textRenderer;

    @SerializedName("link_recipe")
    boolean linkRecipe;

    transient ItemStack itemStack;

    @Override
    public void build(BookEntry entry, int pageNum) {
        super.build(entry, pageNum);
        itemStack = ItemStackUtil.loadStackFromString(item);

        if (linkRecipe) {
            entry.addRelevantStack(itemStack, pageNum);
        }
    }

    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
        textRenderer = new BookTextRenderer(parent, text, 0, DIST_SEP_TEXT + 3 * TEXT_LINE_HEIGHT);
    }

    @Override
    public void render(int mouseX, int mouseY, float pticks) {
        drawTitle(title != null && !title.isEmpty() ? title : itemStack.getDisplayName());
        drawHighlightItem(itemStack, DIST_SEP_TEXT, mouseX, mouseY);
        textRenderer.render(mouseX, mouseY);
    }
}
