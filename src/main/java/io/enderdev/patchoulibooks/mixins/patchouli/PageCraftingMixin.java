package io.enderdev.patchoulibooks.mixins.patchouli;

import io.enderdev.patchoulibooks.PatchouliBooks;
import io.enderdev.patchoulibooks.PBConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.BookPage;
import vazkii.patchouli.client.book.page.PageCrafting;

import java.util.Objects;

@Mixin(value = PageCrafting.class, remap = false)
public abstract class PageCraftingMixin extends BookPage {

    @Inject(method = "loadRecipe(Lvazkii/patchouli/client/book/BookEntry;Ljava/lang/String;)Lnet/minecraft/item/crafting/IRecipe;", at = @At("HEAD"), cancellable = true)
    protected void loadRecipeMixin(BookEntry entry, String loc, CallbackInfoReturnable<IRecipe> cir) {
        if (PBConfig.GENERAL.improveRecipeLookup) {
            if (loc == null) {
                cir.setReturnValue(null);
                cir.cancel();
                return;
            }
            ResourceLocation res = new ResourceLocation(loc);
            IRecipe tempRecipe = CraftingManager.getRecipe(res);
            if (tempRecipe == null) {
                String raw = loc.split(":")[0] + ":" + loc.split(":")[1];
                int meta = loc.split(":").length == 3 ? Integer.parseInt(loc.split(":")[2]) : 0;
                Item item = Item.getByNameOrId(raw);
                if (item != null) {
                    ItemStack stack = new ItemStack(item, 1, meta);
                    ResourceLocation origin = CraftingManager.REGISTRY.getKeys().stream().filter((resourceLocation) -> Objects.requireNonNull(CraftingManager.REGISTRY.getObject(resourceLocation)).getRecipeOutput().isItemEqual(stack)).findFirst().orElse(null);
                    if (origin != null) {
                        tempRecipe = CraftingManager.REGISTRY.getObject(origin);
                    } else {
                        PatchouliBooks.LOGGER.debug("Could not find a recipe for item: {}", loc);
                    }
                }
            }

            if (tempRecipe != null)
                entry.addRelevantStack(tempRecipe.getRecipeOutput(), pageNum);
            cir.setReturnValue(tempRecipe);
            cir.cancel();
        }
    }
}
