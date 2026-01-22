package org.ender_development.patchoulibooks;

import org.ender_development.patchoulibooks.Tags;
import org.ender_development.patchoulibooks.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = Tags.DEPENDENCIES)
public class PatchouliBooks {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    public static final String COMMON_PROXY = "org.ender_development.patchoulibooks.proxy.CommonProxy";
	public static final String CLIENT_PROXY = "org.ender_development.patchoulibooks.proxy.ClientProxy";

	@Mod.Instance(value = Tags.MOD_ID)
	public static PatchouliBooks instance;

	@SidedProxy(modId = Tags.MOD_ID, clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

}
