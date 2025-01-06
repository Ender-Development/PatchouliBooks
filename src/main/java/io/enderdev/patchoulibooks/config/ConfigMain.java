package io.enderdev.patchoulibooks.config;

import io.enderdev.patchoulibooks.Tags;
import io.enderdev.patchoulibooks.config.categories.Debug;
import io.enderdev.patchoulibooks.config.categories.General;
import io.enderdev.patchoulibooks.config.categories.InventoryButton;
import io.enderdev.patchoulibooks.config.categories.JustEnoughItems;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class ConfigMain {

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

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Tags.MOD_ID)) {
            ConfigManager.sync(Tags.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
