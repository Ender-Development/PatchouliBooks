package io.enderdev.patchoulibooks.proxy;

import io.enderdev.patchoulibooks.pages.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
