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
        public static boolean enableDebug = false;
    }

    @Config(modid = Tags.MOD_ID, category = "recipes")
    @Config.LangKey("config.patchoulibooks.general")
    public static class ConfigRecipes {
        @Config.Name("Enable Recipes")
        @Config.Comment("Add default recipes for Patchouli books.")
        public static boolean enableRecipes = true;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Tags.MOD_ID)) {
            ConfigManager.sync(Tags.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
