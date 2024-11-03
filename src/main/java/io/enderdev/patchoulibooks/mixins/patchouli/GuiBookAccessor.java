package io.enderdev.patchoulibooks.mixins.patchouli;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import vazkii.patchouli.client.book.gui.GuiBook;

@Mixin(value = GuiBook.class, remap = false)
public interface GuiBookAccessor {
    @Accessor("tooltipStack")
    ItemStack getTooltipStack();
}
