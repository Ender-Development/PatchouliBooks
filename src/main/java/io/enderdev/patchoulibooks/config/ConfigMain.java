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
    public static class ConfigDebug {
        @Config.Name("Enable Debug")
        @Config.Comment("Enable debug mode.")
        @Config.RequiresMcRestart
        public static boolean enableDebug = false;
    }

    @Config(modid = Tags.MOD_ID, category = "general")
    @Config.LangKey("config.patchoulibooks.general")
    public static class ConfigGeneral {
        @Config.Name("Enable Recipes")
        @Config.Comment("Add default recipes for Patchouli Books books.")
        @Config.RequiresMcRestart
        public static boolean enableRecipes = true;

        @Config.Name("Enable Pamphlets for everyone")
        @Config.Comment("Enable Pamphlets for all books if there is only one category, which has less than 15 entries.")
        public static boolean enablePamphlets = false;

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

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Tags.MOD_ID)) {
            ConfigManager.sync(Tags.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
