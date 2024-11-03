package io.enderdev.patchoulibooks.integration.jei;

import io.enderdev.patchoulibooks.Tags;
import io.enderdev.patchoulibooks.mixins.patchouli.GuiBookAccessor;
import mezz.jei.config.KeyBindings;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import vazkii.patchouli.client.book.gui.GuiBook;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class InputHandler {

    private static boolean showRecipe;
    private static boolean showUses;

    public InputHandler() {
    }

    @SubscribeEvent
    public static void keyInputEvent(GuiScreenEvent.KeyboardInputEvent.Pre event) {
        if (handleKeyEvent() && event.getGui() instanceof GuiBook) {
            GuiBook guiBook = (GuiBook) event.getGui();
            ItemStack itemStack = ((GuiBookAccessor) guiBook).getTooltipStack();
            if (itemStack != null) {
                JEICustomPlugin.showRecipesForItemStack(itemStack, showUses);
            }
        }
    }

    private static boolean handleKeyEvent() {
        char typedChar = Keyboard.getEventCharacter();
        int eventKey = Keyboard.getEventKey();
        return (eventKey == 0 && typedChar >= ' ' || Keyboard.getEventKeyState()) && handleKeyDown(typedChar, eventKey);
    }

    private static boolean handleKeyDown(char typedChar, int eventKey) {
        if (!Loader.isModLoaded("jei")) {
            return false;
        }
        showRecipe = KeyBindings.showRecipe.isActiveAndMatches(eventKey);
        showUses = KeyBindings.showUses.isActiveAndMatches(eventKey);
        return showRecipe || showUses;
    }
}

