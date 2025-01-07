package io.enderdev.patchoulibooks;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Tags.MOD_ID, name = Tags.MOD_ID, category = Tags.MOD_ID)
@Config.LangKey("config.patchoulibooks")
public class PBConfig {
    public enum Anchor {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        CENTER
    }

    @Config.Name("Debug")
    @Config.LangKey("config.patchoulibooks.debug")
    public static final Debug DEBUG = new Debug();

    @Config.Name("General")
    @Config.LangKey("config.patchoulibooks.general")
    public static final General GENERAL = new General();

    @Config.Name("Just Enough Items / Had Enough Items")
    @Config.LangKey("config.patchoulibooks.jei")
    public static final JustEnoughItems JEI = new JustEnoughItems();

    @Config.Name("Inventory Button")
    @Config.LangKey("config.patchoulibooks.inventory_button")
    public static final InventoryButton INVENTORY_BUTTON = new InventoryButton();

    public static class Debug {
        @Config.Name("Enable Debug")
        @Config.Comment("Enable debug mode.")
        @Config.RequiresMcRestart
        public boolean enableDebug = false;
    }

    public static class General {
        @Config.Name("[1] Enable Recipes")
        @Config.Comment("Add default recipes for Patchouli Books books.")
        @Config.RequiresMcRestart
        public boolean enableRecipes = true;

        @Config.Name("[2] Enable Pamphlets for everyone")
        @Config.Comment({
                "Enable Pamphlets for all books if there is only one category, which has less than 15 entries.",
                "You can still create pamphlets manually by adding the 'pamphlet' tag to a book.json."
        })
        public boolean enablePamphlets = false;

        @Config.Name("[3] Improve Recipe Lookup")
        @Config.Comment({
                "Improve recipe lookup by checking for output instead of recipe id.",
                "This is untested with books added by other mods. User added books work fine."
        })
        public boolean improveRecipeLookup = false;
    }

    public static class InventoryButton {

        @Config.Name("[1] Enable Inventory Button")
        @Config.Comment({
                "Enable the inventory button for Patchouli books.",
                "On click it will open a list of all loaded books.",
                "The button will be displayed only if the is no book specified in the patchouli config and 'addUniqueInventoryButton' is set to false."
        })
        @Config.RequiresMcRestart
        public boolean enableInventoryButton = true;

        @Config.Name("[2] Add unique inventory button")
        @Config.Comment({
                "Instead of overriding the patchouli inventory button, add a new button.",
                "This requires the inventory button to be enabled."
        })
        public boolean addUniqueInventoryButton = true;

        @Config.Name("[3] Button Anchor")
        @Config.Comment("Anchor position of the inventory button.")
        public Anchor buttonAnchor = Anchor.TOP_LEFT;

        @Config.Name("[4] Button Offset X")
        @Config.Comment("Offset of the button from the anchor position on the x-axis.")
        public int buttonXPosition = 134;

        @Config.Name("[5] Button Offset Y")
        @Config.Comment("Offset of the button from the anchor position on the y-axis.")
        public int buttonYPosition = 61;
    }

    public static class JustEnoughItems {
        @Config.Name("Open JEI from Books")
        @Config.Comment("Enable opening JEI for items in Patchouli books.")
        public boolean enableJEIinBooks = true;

        @Config.Name("Open Patchouli Book from JEI")
        @Config.Comment({
                "Enable opening Patchouli books from JEI.",
                "Each book with a matching item will be displayed next to the recipe.",
                "If there is more than one book, the books will be displayed in a grid."
        })
        public boolean enableBooksInJEI = true;
    }


    @Mod.EventBusSubscriber(modid = Tags.MOD_ID)
    public static class ConfigEventHandler {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Tags.MOD_ID)) {
                ConfigManager.sync(Tags.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
