package io.enderdev.patchoulibooks.config.categories;

import net.minecraftforge.common.config.Config;

public class InventoryButton {
    public enum Anchor {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        CENTER
    }

    @Config.Name("Enable Inventory Button")
    @Config.Comment({
            "Enable the inventory button for Patchouli books.",
            "The button will be displayed only if the is no book specified in the patchouli config."
    })
    @Config.RequiresMcRestart
    public final boolean enableInventoryButton = true;

    @Config.Name("Add unique inventory button")
    @Config.Comment({
            "Instead of overriding the patchouli inventory button, add a new button.",
            "This requires the inventory button to be enabled."
    })
    public final boolean addUniqueInventoryButton = true;

    @Config.Name("Button Anchor")
    @Config.Comment("Anchor position of the inventory button.")
    public final Anchor buttonAnchor = Anchor.TOP_LEFT;

    @Config.Name("Button Offset X")
    @Config.Comment("Offset of the button from the anchor position on the x-axis.")
    public final int buttonXPosition = 134;

    @Config.Name("Button Offset Y")
    @Config.Comment("Offset of the button from the anchor position on the y-axis.")
    public final int buttonYPosition = 61;
}
