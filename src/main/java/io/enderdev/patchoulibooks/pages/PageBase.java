package io.enderdev.patchoulibooks.pages;

import io.enderdev.patchoulibooks.Tags;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IShapedRecipe;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.BookPage;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.client.book.gui.GuiBookEntry;

import java.util.HashMap;
import java.util.Map;

public abstract class PageBase extends BookPage {

    public static final int FULL_WIDTH = GuiBook.FULL_WIDTH;
    public static final int FULL_HEIGHT = GuiBook.FULL_HEIGHT;
    public static final int PAGE_WIDTH = GuiBook.PAGE_WIDTH;
    public static final int PAGE_HEIGHT = GuiBook.PAGE_HEIGHT;
    public static final int TOP_PADDING = GuiBook.TOP_PADDING;
    public static final int LEFT_PAGE_X = GuiBook.LEFT_PAGE_X;
    public static final int RIGHT_PAGE_X = GuiBook.RIGHT_PAGE_X;
    public static final int TEXT_LINE_HEIGHT = GuiBook.TEXT_LINE_HEIGHT;
    public static final int MAX_BOOKMARKS = GuiBook.MAX_BOOKMARKS;
    public static final int ITEM_WIDTH = 16;
    public static final int ITEM_HEIGHT = 16;
    public static final int DIST_HEADER_SEP = 10;
    public static final int DIST_SEP_TEXT = 12;
    public static final int PAGE_CENTER_HORIZONTAL = PAGE_WIDTH / 2;
    public static final int PAGE_CENTER_VERTICAL = DIST_SEP_TEXT + 7 * TEXT_LINE_HEIGHT;

    protected static final Map<String, ResourceLocation> TEXTURES = new HashMap<String, ResourceLocation>() {
        {
            put("hourglass", new ResourceLocation(Tags.MOD_ID, "textures/templates/pedestal_crafting/hourglass.png"));
            put("pillar_middle", new ResourceLocation(Tags.MOD_ID, "textures/templates/pedestal_crafting/pedestal_core.png"));
            put("pillar", new ResourceLocation(Tags.MOD_ID, "textures/templates/pedestal_crafting/pedestal.png"));
        }
    };

    /**
     * Is called when the page is built, can be used to load resources and convert data into ItemStacks.
     *
     * @param entry   The entry the page is in, this gives access to the book's data
     * @param pageNum The page number of the entry in the book
     */
    @Override
    public void build(BookEntry entry, int pageNum) {
        super.build(entry, pageNum);
    }

    /**
     * Is called when the page is displayed, must be used to create text renderers.
     *
     * @param parent The parent GuiBookEntry
     * @param left   The left position of the page, relative to the page of the book
     * @param top    The top position of the page, relative to the page of the book
     */
    @Override
    public void onDisplayed(GuiBookEntry parent, int left, int top) {
        super.onDisplayed(parent, left, top);
    }

    /**
     * Is called when the page is rendered, must be used to render the page.
     *
     * @param mouseX The x position of the mouse
     * @param mouseY The y position of the mouse
     * @param pticks The partial ticks (unused)
     */
    @Override
    public abstract void render(int mouseX, int mouseY, float pticks);

    /**
     * Draws the title at the top of the page with a separator at y=12.
     * It also draws a small text below the title if the advanced tooltips are
     * enabled that shows the resource location of the entry.
     *
     * @param title The title to draw
     */
    protected void drawTitle(String title) {
        boolean renderedSmol = false;
        String smolText = "";

        if (mc.gameSettings.advancedItemTooltips) {
            if (pageNum == 0) {
                smolText = parent.getEntry().getResource().toString();
            } else {
                smolText = I18n.format("patchoulibooks.gui.lexicon.page_type", type);
            }
        } else if (entry.isExtension()) {
            String name = entry.getTrueProvider().getOwnerName();
            smolText = I18n.format("patchouli.gui.lexicon.added_by", name);
        }

        if (!smolText.isEmpty()) {
            GlStateManager.scale(0.5F, 0.5F, 1F);
            parent.drawCenteredStringNoShadow(smolText, PAGE_WIDTH, 5, book.headerColor);
            GlStateManager.scale(2F, 2F, 1F);
            renderedSmol = true;
        }
        drawHeading(title, renderedSmol ? -6 : -3, true, renderedSmol ? 3 : 0);
    }

    /**
     * Draws a heading at the given position.
     *
     * @param title     The title to draw
     * @param posY      The y position to draw the title
     * @param separator If a separator should be drawn below the title
     */
    protected void drawHeading(String title, int posY, boolean separator) {
        drawHeading(title, posY, separator, 0);
    }

    protected void drawHeading(String title, int posY, boolean separator, int spacing) {
        parent.drawCenteredStringNoShadow(title, PAGE_CENTER_HORIZONTAL, posY, book.headerColor);
        if (separator) {
            GuiBook.drawSeparator(book, 0, posY + DIST_HEADER_SEP + spacing);
        }
    }

    /**
     * Draws an item at the given position.
     *
     * @param drawFrame If a frame should be drawn around the item
     * @param item      The item to draw can be an ItemStack or an Ingredient
     * @param posX      The x position to draw the item
     * @param posY      The y position to draw the item
     * @param mouseX    The x position of the mouse
     * @param mouseY    The y position of the mouse
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
     *
     * @param texture       The texture to draw
     * @param posX          The x position to draw the texture
     * @param posY          The y position to draw the texture
     * @param posU          The x position of the texture to start drawing from
     * @param posV          The y position of the texture to start drawing from
     * @param width         The width of the texture to draw
     * @param height        The height of the texture to draw
     * @param textureWidth  The width of the texture
     * @param textureHeight The height of the texture
     */
    protected void drawTexture(ResourceLocation texture, int posX, int posY, int posU, int posV, int width, int height, int textureWidth, int textureHeight) {
        mc.renderEngine.bindTexture(texture);
        GlStateManager.enableBlend();
        Gui.drawModalRectWithCustomSizedTexture(posX, posY, posU, posV, width, height, textureWidth, textureHeight);
    }

    /**
     * Draws a full texture at the given position.
     *
     * @param texture The texture to draw
     * @param posX    The x position to draw the texture
     * @param posY    The y position to draw the texture
     * @param width   The width of the given texture
     * @param height  The height of the given texture
     */
    protected void drawFullTexture(ResourceLocation texture, int posX, int posY, int width, int height) {
        drawTexture(texture, posX, posY, 0, 0, width, height, width, height);
    }

    /**
     * Draws the item spotlight texture at the given y position.
     * It will always draw the texture at the horizontal center of the page.
     *
     * @param item   The item to draw can be an ItemStack or an Ingredient
     * @param posY   The y position to draw the texture
     * @param mouseX The x position of the mouse
     * @param mouseY The y position of the mouse
     */
    protected void drawHighlightItem(Object item, int posY, int mouseX, int mouseY) {
        int w = 66;
        int h = 26;
        drawTexture(book.craftingResource, centerHorizontal(w), posY, 0, 128 - h, w, h, 128, 128);
        drawItem(false, item, centerHorizontal(ITEM_WIDTH), posY + 5, mouseX, mouseY);
    }

    /**
     * Draws the crafting recipe at the given position.
     *
     * @param recipe  The recipe to draw
     * @param recipeY The y position to draw the recipe
     * @param mouseX  The x position of the mouse
     * @param mouseY  The y position of the mouse
     */
    protected void drawCraftingRecipe(IRecipe recipe, int recipeY, int mouseX, int mouseY) {
        if (recipe == null) {
            return;
        }
        int recipeX = GuiBook.PAGE_WIDTH / 2 - 49;
        mc.renderEngine.bindTexture(book.craftingResource);
        GlStateManager.enableBlend();
        Gui.drawModalRectWithCustomSizedTexture(recipeX - 2, recipeY - 2, 0, 0, 100, 62, 128, 128);

        boolean shaped = recipe instanceof IShapedRecipe;
        if (!shaped) {
            int iconX = recipeX + 62;
            int iconY = recipeY + 2;
            Gui.drawModalRectWithCustomSizedTexture(iconX, iconY, 0, 64, 11, 11, 128, 128);
            if (parent.isMouseInRelativeRange(mouseX, mouseY, iconX, iconY, 11, 11)) {
                parent.setTooltip(I18n.format("patchouli.gui.lexicon.shapeless"));
            }
        }

        parent.renderItemStack(recipeX + 79, recipeY + 22, mouseX, mouseY, recipe.getRecipeOutput());

        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        int wrap = 3;
        if (shaped) {
            wrap = ((IShapedRecipe) recipe).getRecipeWidth();
        }

        for (int i = 0; i < ingredients.size(); i++) {
            parent.renderIngredient(recipeX + (i % wrap) * 19 + 3, recipeY + (i / wrap) * 19 + 3, mouseX, mouseY, ingredients.get(i));
        }
    }

    protected int centerHorizontal(int width) {
        return PAGE_CENTER_HORIZONTAL - width / 2;
    }

    protected int centerVertical(int height) {
        return PAGE_CENTER_VERTICAL - height / 2;
    }
}
