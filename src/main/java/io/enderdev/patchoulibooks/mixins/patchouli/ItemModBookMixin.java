package io.enderdev.patchoulibooks.mixins.patchouli;

import io.enderdev.patchoulibooks.PatchouliBooks;
import io.enderdev.patchoulibooks.Tags;
import io.enderdev.patchoulibooks.integration.patchouli.BookExtension;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.item.ItemModBook;

import java.util.List;

import static vazkii.patchouli.common.item.ItemModBook.getBook;

@Mixin(value = ItemModBook.class, remap = false)
public class ItemModBookMixin {
    @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
    @Inject(method = "addInformation", at = @At(value = "HEAD"), cancellable = true)
    private void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag, CallbackInfo ci) {
        Book book = getBook(stack);
        if (book != null && book.owner.getModId().equals(Tags.MOD_ID)) {
            tooltip.add(I18n.format("patchoulibooks.item.book.tooltip", ((BookExtension) book).bookAuthor));
            ci.cancel();
        } else {
            PatchouliBooks.LOGGER.debug("Book is null or not from PatchouliBooks");
        }
    }
}
