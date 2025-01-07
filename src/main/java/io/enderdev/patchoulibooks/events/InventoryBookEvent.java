package io.enderdev.patchoulibooks.events;


import io.enderdev.patchoulibooks.PatchouliBooks;
import io.enderdev.patchoulibooks.Tags;
import io.enderdev.patchoulibooks.PBConfig;
import io.enderdev.patchoulibooks.integration.patchouli.ButtonScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

import java.util.Arrays;

/*
Source: https://github.com/KatatsumuriPan/Nutrition-Unofficial-Extended-Life/blob/1.12/src/main/java/ca/wescook/nutrition/events/EventNutritionButton.java
License: MIT
Date retrieved: 06/January/2025
 */
public class InventoryBookEvent {

    private static final int BUTTON_ID = 801;
    private static final ResourceLocation BUTTON_ICON = new ResourceLocation(Tags.MOD_ID, "textures/gui/button.png");
    private GuiButtonImage buttonBook;
    private int[] pos;

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void guiOpen(GuiScreenEvent.InitGuiEvent.Post event) {
        // If any inventory except player inventory is opened, get out
        GuiScreen gui = event.getGui();
        if (!(gui instanceof GuiInventory )|| !PBConfig.INVENTORY_BUTTON.addUniqueInventoryButton) {
            return;
        }

        // Get button position
        pos = calculateButtonPosition(gui);

        // Create button
        buttonBook = new GuiButtonImage(BUTTON_ID, pos[0], pos[1], 20, 18, 14, 0, 19, BUTTON_ICON);
        PatchouliBooks.LOGGER.debug("Button created at x: {} y: {}", pos[0], pos[1]);
        event.getButtonList().add(buttonBook);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void guiButtonClick(GuiScreenEvent.ActionPerformedEvent.Post event) {
        // Only run on GuiInventory
        if (!(event.getGui() instanceof GuiInventory)) {
            return;
        }

        // If Book button is clicked
        if (event.getButton().equals(buttonBook)) {
            Minecraft.getMinecraft().displayGuiScreen(new ButtonScreen(Arrays.asList(BookRegistry.INSTANCE.books.values().toArray(new Book[0]))));
            buttonBook.playPressSound(Minecraft.getMinecraft().getSoundHandler());
        } else {
            // Presumably recipe book button was clicked - recalculate nutrition button position
            pos = calculateButtonPosition(event.getGui());
            int xPosition = pos[0];
            int yPosition = pos[1];
            buttonBook.setPosition(xPosition, yPosition);
        }
    }

    // Return array [x,y] of button coordinates
    @SideOnly(Side.CLIENT)
    private int[] calculateButtonPosition(GuiScreen gui) {
        int x = 0;
        int y = 0;

        // Get bounding box of origin
        int width = ((GuiInventory) gui).getXSize();
        int height = ((GuiInventory) gui).getYSize();
        // Calculate anchor position from origin (e.g. x/y pixels at right side of gui)
        // The x/y is still relative to the top/left corner of the screen at this point
        switch (PBConfig.INVENTORY_BUTTON.buttonAnchor) {
            case TOP:
                x = width / 2;
                y = 0;
                break;
            case RIGHT:
                x = width;
                y = height / 2;
                break;
            case BOTTOM:
                x = width / 2;
                y = height;
                break;
            case LEFT:
                x = 0;
                y = height / 2;
                break;
            case TOP_LEFT:
                x = 0;
                y = 0;
                break;
            case TOP_RIGHT:
                x = width;
                y = 0;
                break;
            case BOTTOM_RIGHT:
                x = width;
                y = height;
                break;
            case BOTTOM_LEFT:
                x = 0;
                y = height;
                break;
            case CENTER:
                x = width / 2;
                y = height / 2;
                break;
        }


        x += ((GuiInventory) gui).getGuiLeft();
        y += ((GuiInventory) gui).getGuiTop();


        // Then add the offset as defined in the config file
        x += PBConfig.INVENTORY_BUTTON.buttonXPosition;
        y += PBConfig.INVENTORY_BUTTON.buttonYPosition;

        return new int[]{x, y};
    }
}
