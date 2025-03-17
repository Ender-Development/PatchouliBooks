package io.enderdev.patchoulibooks.integration.patchouli;

import io.enderdev.patchoulibooks.Tags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.client.book.BookContents;
import vazkii.patchouli.common.book.Book;

import java.io.IOException;
import java.util.*;

public class ButtonScreen extends GuiScreen {

    private static final ResourceLocation PATCHOULI_BUTTON = new ResourceLocation(Tags.MOD_ID, "textures/gui/patchouli_button.png");
    private static final int BOOKS_PER_PAGE = 6;
    private final List<Book> bookList = new ArrayList<>();
    private int page = 0;

    public ButtonScreen(List<Book> list) {
        list.stream().sorted(Comparator.comparing(a -> a.name)).filter(a -> !a.name.isEmpty()).forEach(bookList::add);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.mc.getTextureManager().bindTexture(PATCHOULI_BUTTON);
        drawTexturedModalRect(this.width / 2 - 73, this.height / 2 - 90, 0, 0, 146, 180);

        String title = I18n.format("patchoulibooks.gui.inventory_button.title");
        this.fontRenderer.drawString(title, (this.width / 2 - this.fontRenderer.getStringWidth(title) / 2), this.height / 2 - 75, 0x55AA00);

        int maxPageOffset = this.bookList.size() % BOOKS_PER_PAGE == 0 ? 0 : 1;

        String pageText = I18n.format("patchoulibooks.gui.inventory_button.page", this.page + 1, this.bookList.size() / BOOKS_PER_PAGE + maxPageOffset);
        this.fontRenderer.drawString(pageText, (this.width / 2 - this.fontRenderer.getStringWidth(pageText) / 2), this.height / 2 + 65, 0x999999);

        if (this.bookList.size() > BOOKS_PER_PAGE) {
            GlStateManager.enableBlend();
            GlStateManager.color(1F, 1F, 1F, 1F);
            this.mc.getTextureManager().bindTexture(PATCHOULI_BUTTON);
            if (this.page > 0) {
                if (this.isPointWithinBounds(this.width / 2 - 55, this.height / 2 + 65, 18, 10, mouseX, mouseY)) {
                    drawTexturedModalRect(this.width / 2 - 55, this.height / 2 + 65, 18, 190, 18, 10);
                } else {
                    drawTexturedModalRect(this.width / 2 - 55, this.height / 2 + 65, 0, 190, 18, 10);
                }
            }
            if (this.bookList.size() > this.page * BOOKS_PER_PAGE + BOOKS_PER_PAGE) {
                if (this.isPointWithinBounds(this.width / 2 + 30, this.height / 2 + 65, 18, 10, mouseX, mouseY)) {
                    drawTexturedModalRect(this.width / 2 + 30, this.height / 2 + 65, 18, 180, 18, 10);
                } else {
                    drawTexturedModalRect(this.width / 2 + 30, this.height / 2 + 65, 0, 180, 18, 10);
                }
            }
        }

        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
        for (Book book : this.bookList) {
            int index = this.bookList.indexOf(book);
            int page = index / BOOKS_PER_PAGE;
            int offset = index % BOOKS_PER_PAGE;

            if (page != this.page) {
                continue;
            }

            int x = this.width / 2 - 62;
            int y = this.height / 2 - 60 + offset * 20;

            RenderHelper.enableGUIStandardItemLighting();
            itemRenderer.renderItemAndEffectIntoGUI(book.getBookItem(), x + 2, y + 2);
            RenderHelper.disableStandardItemLighting();

            String text = I18n.format(book.name);
            if (this.fontRenderer.getStringWidth(text) > 90) {
                text = text.substring(0, 14) + "...";
            }

            if (isPointWithinBounds(x, y, 110, 18, mouseX, mouseY)) {
                this.fontRenderer.drawString(text, x + 26, y + 6, book.linkHoverColor);
                drawHoveringText(I18n.format("patchoulibooks.gui.inventory_button.tooltip", book.name), mouseX, mouseY);
            } else {
                this.fontRenderer.drawString(text, x + 26, y + 6, book.headerColor);
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (this.bookList.size() > BOOKS_PER_PAGE) {
            if (this.page > 0 && isPointWithinBounds(this.width / 2 - 55, this.height / 2 + 65, 18, 10, mouseX, mouseY)) {
                this.page--;
                return;
            }
            if (this.bookList.size() > this.page * BOOKS_PER_PAGE + BOOKS_PER_PAGE && isPointWithinBounds(this.width / 2 + 30, this.height / 2 + 65, 18, 10, mouseX, mouseY)) {
                this.page++;
                return;
            }
        }

        for (Book book : this.bookList) {
            int index = this.bookList.indexOf(book);
            int page = index / BOOKS_PER_PAGE;
            int offset = index % BOOKS_PER_PAGE;

            if (page != this.page) {
                continue;
            }

            int x = this.width / 2 - 62;
            int y = this.height / 2 - 60 + offset * 20;

            if (isPointWithinBounds(x, y, 110, 18, mouseX, mouseY)) {
                openBook(book);
                return;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void openBook(Book book) {
        BookContents contents = book.contents;
        contents.openLexiconGui(contents.getCurrentGui(), false);
    }

    private boolean isPointWithinBounds(int x, int y, int width, int height, int pointX, int pointY) {
        return pointX >= x && pointX < x + width && pointY >= y && pointY < y + height;
    }
}
