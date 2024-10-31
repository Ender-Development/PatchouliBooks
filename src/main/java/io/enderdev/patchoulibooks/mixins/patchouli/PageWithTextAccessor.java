package io.enderdev.patchoulibooks.mixins.patchouli;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import vazkii.patchouli.client.book.page.abstr.PageWithText;

@Mixin(value = PageWithText.class, remap = false)
public interface PageWithTextAccessor {
    @Accessor("text")
    String getText();
}
