package org.ender_development.patchoulibooks.mixins;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.fml.common.Loader;
import org.ender_development.patchoulibooks.Tags;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.List;
import java.util.stream.Collectors;

public class LateMixin implements ILateMixinLoader {
    public static final List<String> modMixins = ImmutableList.of(
            "jei",
            "patchouli"
    );

    @Override
    public List<String> getMixinConfigs() {
        return modMixins.stream().filter(Loader::isModLoaded).map(mod -> "mixins." + Tags.MOD_ID + "." + mod + ".json").collect(Collectors.toList());
    }
}
