package org.ender_development.patchoulibooks.proxy;

import org.ender_development.patchoulibooks.PBConfig;
import org.ender_development.patchoulibooks.events.InventoryBookEvent;
import io.enderdev.patchoulibooks.pages.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.ender_development.patchoulibooks.pages.*;
import vazkii.patchouli.client.book.ClientBookRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        ClientBookRegistry.INSTANCE.pageTypes.put("pillar", PagePillar.class);
        ClientBookRegistry.INSTANCE.pageTypes.put("spotlight+", PageSpotlight.class);
        ClientBookRegistry.INSTANCE.pageTypes.put("text+", PageText.class);
        ClientBookRegistry.INSTANCE.pageTypes.put("crafting+", PageCrafting.class);
        ClientBookRegistry.INSTANCE.pageTypes.put("link+", PageLink.class);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        if (PBConfig.INVENTORY_BUTTON.enableInventoryButton && PBConfig.INVENTORY_BUTTON.addUniqueInventoryButton) {
            MinecraftForge.EVENT_BUS.register(new InventoryBookEvent());
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
