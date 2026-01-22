package org.ender_development.patchoulibooks.mixins.patchouli;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import org.ender_development.patchoulibooks.PBConfig;
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
    private int patchouliBooks$lastSoundTick = 0;

    @Unique
    private int patchouliBooks$lastToastTick = 0;

    @Inject(method = "drawButton", at = @At(value = "INVOKE", target = "Lvazkii/patchouli/client/book/gui/GuiBook;drawMarking(Lvazkii/patchouli/common/book/Book;IIILvazkii/patchouli/client/book/EntryDisplayState;)V", remap = false), remap = true)
    private void pingSound(Minecraft mc, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (!PBConfig.INVENTORY_BUTTON.annoyPlayer) return;
        if (!PBConfig.INVENTORY_BUTTON.annoySound.isEmpty() && ClientTicker.ticksInGame - patchouliBooks$lastSoundTick > PBConfig.INVENTORY_BUTTON.annoySoundDelay * 20) {
            SoundEvent sfx = SoundEvent.REGISTRY.getObject(new ResourceLocation(PBConfig.INVENTORY_BUTTON.annoySound));
            if (sfx == null) {
                sfx = SoundEvent.REGISTRY.getRandomObject(new Random());
            }
            assert sfx != null;
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(sfx, (float)(0.7 + Math.random() * 0.3)));
            patchouliBooks$lastSoundTick = ClientTicker.ticksInGame;
        }
        if (PBConfig.INVENTORY_BUTTON.annoyToast && ClientTicker.ticksInGame - patchouliBooks$lastToastTick > PBConfig.INVENTORY_BUTTON.annoyToastDelay * 20) {
            SystemToast toast = new SystemToast(SystemToast.Type.TUTORIAL_HINT, new TextComponentTranslation("patchoulibooks.toast.title"), new TextComponentTranslation("patchoulibooks.toast.sub"));
            Minecraft.getMinecraft().getToastGui().add(toast);
            patchouliBooks$lastToastTick = ClientTicker.ticksInGame;
        }
    }
}
