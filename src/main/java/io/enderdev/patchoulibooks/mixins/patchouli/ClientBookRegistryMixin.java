package io.enderdev.patchoulibooks.mixins.patchouli;

import io.enderdev.patchoulibooks.pages.PagePillar;
import io.enderdev.patchoulibooks.pages.PageTextE;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.patchouli.client.book.BookPage;
import vazkii.patchouli.client.book.ClientBookRegistry;

import java.util.Map;

@Mixin(value = ClientBookRegistry.class, remap = false)
public class ClientBookRegistryMixin {
    @Shadow
    @Final
    public Map<String, Class<? extends BookPage>> pageTypes;

    @Inject(method = "addPageTypes", at = @At("TAIL"))
    private void addPageTypes(CallbackInfo ci) {
        pageTypes.put("text+", PageTextE.class);
        pageTypes.put("pillar", PagePillar.class);
    }
}
