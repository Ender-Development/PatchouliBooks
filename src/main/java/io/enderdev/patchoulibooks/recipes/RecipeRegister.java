package io.enderdev.patchoulibooks.recipes;

import io.enderdev.patchoulibooks.PatchouliBooks;
import io.enderdev.patchoulibooks.Tags;
import io.enderdev.patchoulibooks.PBConfig;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

import java.util.*;

public class RecipeRegister {
    private final List<ResourceLocation> RECIPES = new ArrayList<>();

    public RecipeRegister() {
        addBookRecipe("gendustry", "gene_sample_blank");
        addBookRecipe("thermallogistics","crafter");
        addBookRecipe("moarboats", "oars");
        addBookRecipe("pressure", "pipe");
        // addBookRecipe("ic2", "blockrubsapling");
    }

    @SubscribeEvent
    public void init(RegistryEvent.Register<IRecipe> event) {
        if (!PBConfig.GENERAL.enableRecipes) {
            return;
        }
        RECIPES.forEach((resourceLocation) -> {
            String mod_id = resourceLocation.getNamespace();
            if (Loader.isModLoaded(mod_id)) {
                Item input = Item.getByNameOrId(resourceLocation.toString());
                if (input == null) {
                    PatchouliBooks.LOGGER.error("Unable to find item at {}", resourceLocation.toString());
                    return;
                }
                ResourceLocation id = new ResourceLocation(Tags.MOD_ID, mod_id);
                ShapelessOreRecipe recipe = generateRecipe(id, input);
                if (recipe == null) {
                    PatchouliBooks.LOGGER.error("Unable to generate recipe for {}", id);
                    return;
                }
                event.getRegistry().register(recipe.setRegistryName(id));
            }
        });
    }

    private ShapelessOreRecipe generateRecipe(ResourceLocation id, Item input) {
        ItemStack book = BookRegistry.INSTANCE.books.values().stream().filter(b -> b.resourceLoc.getPath().equals(id.getPath())).map(Book::getBookItem).findFirst().orElse(ItemStack.EMPTY);
        if (book.isEmpty()) {
            PatchouliBooks.LOGGER.warn("Book not found for mod id: {}", id.getPath());
            return null;
        }
        return new ShapelessOreRecipe(id, book, Items.BOOK, Ingredient.fromItem(input));
    }

    private void addBookRecipe(String mod_id, String item_name) {
        RECIPES.add(new ResourceLocation(mod_id, item_name));
    }
}
