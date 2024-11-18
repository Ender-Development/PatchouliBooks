package io.enderdev.patchoulibooks.mixins.patchouli;

import io.enderdev.patchoulibooks.config.ConfigMain;
import io.enderdev.patchoulibooks.integration.patchouli.ButtonScreen;
import io.enderdev.patchoulibooks.integration.patchouli.GuiButtonInventoryBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiScreenEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.patchouli.client.base.InventoryBookButtonHandler;
import vazkii.patchouli.common.base.PatchouliConfig;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

import java.util.Arrays;
import java.util.List;

@Mixin(value = InventoryBookButtonHandler.class, remap = false)
public class InventoryBookButtonHandlerMixin {
    @Inject(method = "onActionPressed", at = @At(value = "HEAD"), cancellable = true)
    private static void displayButtonScreen(GuiScreenEvent.ActionPerformedEvent.Pre event, CallbackInfo ci) {
        GuiButton button = event.getButton();
        if (button instanceof GuiButtonInventoryBook) {
            Minecraft.getMinecraft().displayGuiScreen(new ButtonScreen(Arrays.asList(BookRegistry.INSTANCE.books.values().toArray(new Book[0]))));
            button.playPressSound(Minecraft.getMinecraft().getSoundHandler());
            event.setCanceled(true);
            ci.cancel();
        }
    }

    @Inject(method = "onGuiInitPost", at = @At(value = "HEAD"), cancellable = true)
    private static void createButton(GuiScreenEvent.InitGuiEvent.Post event, CallbackInfo ci) {
        String bookID = PatchouliConfig.inventoryButtonBook;
        if (!bookID.isEmpty() || !(event.getGui() instanceof GuiInventory) || !ConfigMain.ConfigGeneral.enableInventoryButton) {
            return;
        }

        List<GuiButton> buttons = event.getButtonList();

        for (int i = 0; i < buttons.size(); i++) {
            GuiButton button = buttons.get(i);
            if (button.id == 10) {
                GuiButton newButton = new GuiButtonInventoryBook(button.id, button.x, button.y - 1);
                buttons.set(i, newButton);
                ci.cancel();
                return;
            }
        }
    }
}
