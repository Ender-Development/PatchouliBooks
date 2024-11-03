package io.enderdev.patchoulibooks.integration.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.IFocus;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@JEIPlugin
public class JEICustomPlugin implements IModPlugin {
    private static IJeiRuntime jeiRuntime;

    public JEICustomPlugin() {
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
        JEICustomPlugin.jeiRuntime = jeiRuntime;
    }

    public static boolean showRecipesForItemStack(ItemStack itemStack, boolean isUses) {
        jeiRuntime.getRecipesGui().show(jeiRuntime.getRecipeRegistry().createFocus(isUses ? IFocus.Mode.INPUT : IFocus.Mode.OUTPUT, itemStack));
        Minecraft mc = Minecraft.getMinecraft();
        return mc.currentScreen instanceof IRecipesGui;
    }
}
