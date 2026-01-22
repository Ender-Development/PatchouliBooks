package io.enderdev.patchoulibooks.mixins.patchouli;

import io.enderdev.patchoulibooks.PBConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.patchouli.client.base.ClientTicker;
import vazkii.patchouli.client.gui.GuiButtonInventoryBook;
import vazkii.patchouli.common.book.Book;

import java.util.Random;

@Mixin(value = GuiButtonInventoryBook.class, remap = false)
public class GuiButtonInventoryBookMixin {
    @Shadow
    Book book;

    @Unique
    private int patchouliBooks$lastTick = 0;

    @Inject(method = "drawButton", at = @At(value = "INVOKE", target = "Lvazkii/patchouli/client/book/gui/GuiBook;drawMarking(Lvazkii/patchouli/common/book/Book;IIILvazkii/patchouli/client/book/EntryDisplayState;)V", remap = false), remap = true)
    private void pingSound(Minecraft mc, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (PBConfig.INVENTORY_BUTTON.annoyPlayer && ClientTicker.ticksInGame - patchouliBooks$lastTick > 60) {
            SoundEvent sfx = SoundEvent.REGISTRY.getObject(new ResourceLocation(PBConfig.INVENTORY_BUTTON.annoySound));
            if (sfx == null) {
                sfx = SoundEvent.REGISTRY.getRandomObject(new Random());
            }
            assert sfx != null;
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(sfx, (float)(0.7 + Math.random() * 0.3)));
            patchouliBooks$lastTick = ClientTicker.ticksInGame;
        }
    }
}
