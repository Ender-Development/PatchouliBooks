package io.enderdev.patchoulibooks.config.categories;

import net.minecraftforge.common.config.Config;

public class General {
    @Config.Name("Enable Recipes")
    @Config.Comment("Add default recipes for Patchouli Books books.")
    @Config.RequiresMcRestart
    public final boolean enableRecipes = true;

    @Config.Name("Enable Pamphlets for everyone")
    @Config.Comment({
            "Enable Pamphlets for all books if there is only one category, which has less than 15 entries.",
            "You can still create pamphlets manually by adding the 'pamphlet' tag to a book.json."
    })
    public final boolean enablePamphlets = false;

    @Config.Name("Improve Recipe Lookup")
    @Config.Comment({
            "Improve recipe lookup by checking for output instead of recipe id.",
            "This is untested with books added by other mods. User added books work fine."
    })
    public final boolean improveRecipeLookup = false;
}
