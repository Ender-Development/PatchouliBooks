package io.enderdev.patchoulibooks.integration.jei;

import io.enderdev.patchoulibooks.PatchouliBooks;
import mezz.jei.gui.TooltipRenderer;
import mezz.jei.gui.elements.DrawableIngredient;
import mezz.jei.gui.elements.GuiIconButtonSmall;
import mezz.jei.plugins.vanilla.ingredients.item.ItemStackRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.client.book.gui.GuiBookEntry;
import vazkii.patchouli.client.book.gui.GuiBookLanding;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.util.ItemStackUtil;

public class PatchouliButton extends GuiIconButtonSmall {

    private final Book book;
    private final ItemStack itemStack;
    private final String book_name;

    public PatchouliButton(int buttonId, int x, int y, int widthIn, int heightIn, Book book, ItemStack itemStack) {
        super(buttonId, x, y, widthIn, heightIn, new DrawableIngredient<>(book.getBookItem(), new ItemStackRenderer()));
        this.book = book;
        this.itemStack = itemStack;
        this.book_name = book.getBookItem().getDisplayName();
    }

    public void drawToolTip(Minecraft mc, int mouseX, int mouseY) {
        String tooltipTransfer = I18n.format("patchoulibooks.jei.openbook", this.book_name);
        if (this.hovered && this.visible) {
            TooltipRenderer.drawHoveringText(mc, tooltipTransfer, mouseX, mouseY);
        }
    }

    public void openBook() {
        Pair<BookEntry, Integer> hover = getMapping();
        if (hover != null) {
            BookEntry entry = hover.getLeft();

            if (!entry.isLocked()) {
                int page = hover.getRight();
                GuiBook curr = book.contents.getCurrentGui();
                book.contents.currentGui = new GuiBookEntry(book, entry, page);
                book.contents.guiStack.push(curr);
            }
            if (!book.contents.getCurrentGui().canBeOpened()) {
                book.contents.currentGui = null;
                book.contents.guiStack.clear();
            }

            book.contents.openLexiconGui(book.contents.getCurrentGui(), false);
        }
    }

    public Book getBook() {
        return this.book;
    }

    private BookEntry getEntry() {
        return this.book.contents.entries.values().stream().filter(bookEntry -> bookEntry.isStackRelevant(this.itemStack)).findFirst().orElse(null);
    }

    private Pair<BookEntry, Integer> getMapping() {
        return this.book.contents.recipeMappings.get(ItemStackUtil.wrapStack(this.itemStack));
    }

    public boolean isUnlocked() {
        return this.getEntry() != null && !this.getEntry().isLocked();
    }
}
