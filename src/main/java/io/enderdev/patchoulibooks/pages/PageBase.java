package io.enderdev.patchoulibooks.pages;

import io.enderdev.patchoulibooks.Tags;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.BookPage;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.client.book.gui.GuiBookEntry;

import java.util.HashMap;
import java.util.Map;

public abstract class PageBase extends BookPage {
    protected static final Map<String, ResourceLocation> TEXTURES = new HashMap<String, ResourceLocation>() {
        {
            put("hourglass", new ResourceLocation(Tags.MOD_ID, "textures/templates/pedestal_crafting/hourglass.png"));
            put("pillar_middle", new ResourceLocation(Tags.MOD_ID, "textures/templates/pedestal_crafting/pedestal_core.png"));
            put("pillar", new ResourceLocation(Tags.MOD_ID, "textures/templates/pedestal_crafting/pedestal.png"));
        }
    };

    /**
     * Is called when the page is built, can be used to load resources convert data into ItemStacks.
     * @param entry The entry the page is in, this gives access to the book's data
     * @param pageNum The page number of the entry in the book
     */
    @Override
    public void build(BookEntry entry, int pageNum) {
        super.build(entry, pageNum);
    }

    /**
     * Is called when the page is displayed, must be used to create text renderers.
     * @param parent The parent GuiBookEntry
     * @param left The left position of the page, relative to the page of the book
     * @param top The top position of the page, relative to the page of the book
     */
    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
    }

    /**
     * Is called when the page is rendered, must be used to render the page.
     * @param mouseX The x position of the mouse
     * @param mouseY The y position of the mouse
     * @param pticks The partial ticks (unused)
     */
    @Override
    public abstract void render(int mouseX, int mouseY, float pticks);

    /**
     * Draws the title at the top of the page with a separator.
     * It also draws a small text below the title if the advanced tooltips are
     * enabled that shows the resource location of the entry.
     * @param title The title to draw
     */
    protected void drawTitle(String title) {
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
        parent.drawCenteredStringNoShadow(title, GuiBook.PAGE_WIDTH / 2, renderedSmol ? -3 : 0, book.headerColor);
        GuiBook.drawSeparator(book, 0, 12);
    }

    /**
     * Draws an item at the given position.
     * @param drawFrame If a frame should be drawn around the item
     * @param item The item to draw can be an ItemStack or an Ingredient
     * @param posX The x position to draw the item
     * @param posY The y position to draw the item
     * @param mouseX The x position of the mouse
     * @param mouseY The y position of the mouse
     */
    protected void drawItem(boolean drawFrame, Object item, int posX, int posY, int mouseX, int mouseY) {
        if (item instanceof ItemStack) {
            parent.renderItemStack(posX, posY, mouseX, mouseY, (ItemStack) item);
        } else {
            parent.renderIngredient(posX, posY, mouseX, mouseY, (Ingredient) item);
        }
        if (drawFrame) {
            GlStateManager.enableBlend();
            GlStateManager.color(1F, 1F, 1F, 1F);
            mc.renderEngine.bindTexture(book.craftingResource);
            Gui.drawModalRectWithCustomSizedTexture(posX - 4, posY - 4, 83, 71, 24, 24, 128, 128);
        }
    }

    /**
     * Draws a texture at the given position.
     * @param texture The texture to draw
     * @param posX The x position to draw the texture
     * @param posY The y position to draw the texture
     * @param posU The x position of the texture to start drawing from
     * @param posV The y position of the texture to start drawing from
     * @param width The width of the texture to draw
     * @param height The height of the texture to draw
     * @param textureWidth The width of the texture
     * @param textureHeight The height of the texture
     */
    protected void drawTexture(ResourceLocation texture, int posX, int posY, int posU, int posV, int width, int height, int textureWidth, int textureHeight) {
        mc.renderEngine.bindTexture(texture);
        GlStateManager.enableBlend();
        Gui.drawModalRectWithCustomSizedTexture(posX, posY, posU, posV, width, height, textureWidth, textureHeight);
    }

    /**
     * Draws a full texture at the given position.
     * @param texture The texture to draw
     * @param posX The x position to draw the texture
     * @param posY The y position to draw the texture
     * @param width The width of the given texture
     * @param height The height of the given texture
     */
    protected void drawFullTexture(ResourceLocation texture, int posX, int posY, int width, int height) {
        drawTexture(texture, posX, posY, 0, 0, width, height, width, height);
    }
}
