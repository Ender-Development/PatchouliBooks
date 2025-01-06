package io.enderdev.patchoulibooks.config;

import io.enderdev.patchoulibooks.Tags;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class ConfigMain {

    @Config(modid = Tags.MOD_ID, category = "debug")
    @Config.LangKey("config.patchoulibooks.debug")
    public static class Debug {
        @Config.Name("Enable Debug")
        @Config.Comment("Enable debug mode.")
        @Config.RequiresMcRestart
        public static boolean enableDebug = false;
    }

    @Config(modid = Tags.MOD_ID, category = "general")
    @Config.LangKey("config.patchoulibooks.general")
    public static class General {
        @Config.Name("Enable Recipes")
        @Config.Comment("Add default recipes for Patchouli Books books.")
        @Config.RequiresMcRestart
        public static boolean enableRecipes = true;

        @Config.Name("Enable Pamphlets for everyone")
        @Config.Comment("Enable Pamphlets for all books if there is only one category, which has less than 15 entries.")
        public static boolean enablePamphlets = false;

        @Config.Name("Enable Inventory Button")
        @Config.Comment({
                "Enable the inventory button for Patchouli books.",
                "The button will be displayed only if the is no book specified in the patchouli config."
        })
        @Config.RequiresMcRestart
        public static boolean enableInventoryButton = true;

        @Config.Name("Add unique inventory button")
        @Config.Comment({
                "Instead of overriding the patchouli inventory button, add a new button.",
                "This requires the inventory button to be enabled."
        })
        public static boolean addUniqueInventoryButton = false;

        @Config.Name("Improve Recipe Lookup")
        @Config.Comment({
                "Improve recipe lookup by checking for output instead of recipe id.",
                "This is untested with books added by other mods. User added books work fine."
        })
        public static boolean improveRecipeLookup = false;

        @Config.Name("Open JEI from Books")
        @Config.Comment("Enable opening JEI for items in Patchouli books.")
        public static boolean enableJEIinBooks = true;

        @Config.Name("Open Patchouli Book from JEI")
        @Config.Comment({
                "Enable opening Patchouli books from JEI.",
                "Each book with a matching item will be displayed next to the recipe.",
                "If there is more than one book, the books will be displayed in a grid."
        })
        public static boolean enableBooksInJEI = true;
    }

    @Config(modid = Tags.MOD_ID, category = "inventory_button")
    @Config.LangKey("config.patchoulibooks.inventory_button")
    public static class InventoryButton {
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

        @Config.Name("Button Anchor")
        @Config.Comment("Anchor position of the inventory button.")
        public static Anchor buttonAnchor = Anchor.TOP_LEFT;

        @Config.Name("Button Offset X")
        @Config.Comment("Offset of the button from the anchor position on the x-axis.")
        public static int buttonXPosition = 134;

        @Config.Name("Button Offset Y")
        @Config.Comment("Offset of the button from the anchor position on the y-axis.")
        public static int buttonYPosition = 61;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Tags.MOD_ID)) {
            ConfigManager.sync(Tags.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
