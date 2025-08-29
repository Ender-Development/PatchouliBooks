package io.enderdev.patchoulibooks.unlock;

import io.enderdev.patchoulibooks.PatchouliBooks;

public enum UnlockTypes {
    ERROR("error"),
    GAMESTAGE("gamestage"),
    GAMEMODE("gamemode"),
    DIMENSION("dimension"),
    GAMERULE("gamerule");

    private final String type;

    UnlockTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static UnlockTypes fromString(String type) {
        for (UnlockTypes unlockType : values()) {
            if (unlockType.getType().equalsIgnoreCase(type)) {
                return unlockType;
            }
        }
        PatchouliBooks.LOGGER.error("Unknown unlock type: {}, defaulting to ERROR.", type);
        return ERROR; // Default to ERROR if no match found
    }
}
