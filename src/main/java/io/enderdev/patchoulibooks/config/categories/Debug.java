package io.enderdev.patchoulibooks.config.categories;

import net.minecraftforge.common.config.Config;

public class Debug {
    @Config.Name("Enable Debug")
    @Config.Comment("Enable debug mode.")
    @Config.RequiresMcRestart
    public final boolean enableDebug = false;
}
