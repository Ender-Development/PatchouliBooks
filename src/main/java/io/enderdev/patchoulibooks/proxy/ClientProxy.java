package io.enderdev.patchoulibooks.proxy;

import io.enderdev.patchoulibooks.pages.PageBase;
import io.enderdev.patchoulibooks.pages.PageRegister;
import io.enderdev.patchoulibooks.util.AnnotationUtil;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import vazkii.patchouli.client.book.ClientBookRegistry;

import java.util.Objects;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        Objects.requireNonNull(AnnotationUtil.validateAnnotations(PageRegister.class, PageBase.class)).forEach(clazz -> {
            PageRegister annotation = clazz.getAnnotation(PageRegister.class);
            ClientBookRegistry.INSTANCE.pageTypes.put(annotation.value(), clazz.asSubclass(PageBase.class));
        });
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
