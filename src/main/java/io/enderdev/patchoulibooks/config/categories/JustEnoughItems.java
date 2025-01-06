package io.enderdev.patchoulibooks.config.categories;

import net.minecraftforge.common.config.Config;

public class JustEnoughItems {
    @Config.Name("Open JEI from Books")
    @Config.Comment("Enable opening JEI for items in Patchouli books.")
    public final boolean enableJEIinBooks = true;

    @Config.Name("Open Patchouli Book from JEI")
    @Config.Comment({
            "Enable opening Patchouli books from JEI.",
            "Each book with a matching item will be displayed next to the recipe.",
            "If there is more than one book, the books will be displayed in a grid."
    })
    public final boolean enableBooksInJEI = true;
}
