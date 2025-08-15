package io.enderdev.patchoulibooks.unlock;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.Loader;

import java.util.Locale;

public class Requirement {
    private final String type;
    private final String trigger;

    transient boolean fullfilled = false;

    public Requirement(String type, String trigger) {
        this.type = type;
        this.trigger = trigger;
    }

    public UnlockTypes getType() {
        return UnlockTypes.fromString(type);
    }

    public String getTrigger() {
        return trigger;
    }

    public boolean unlocked() {
        switch (getType()) {
            case GAMEMODE:
                return checkGamemode();
            case DIMENSION:
                return checkDimension();
            case GAMESTAGE:
                return checkGamestage();
            default:
                return false;
        }
    }

    private boolean checkGamemode() {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player == null) return false;
        boolean creative = player.isCreative() && getTrigger().toLowerCase(Locale.ROOT).equals("creative");
        boolean spectator = player.isSpectator() && getTrigger().toLowerCase(Locale.ROOT).equals("spectator");
        boolean survival = !player.isCreative() && !player.isSpectator() && (getTrigger().toLowerCase(Locale.ROOT).equals("survival") || getTrigger().toLowerCase(Locale.ROOT).equals("adventure"));
        fullfilled = creative || survival || spectator;
        return fullfilled;
    }

    private boolean checkDimension() {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player == null) return false;
        String currentDimension = player.world.provider.getDimensionType().getName().toLowerCase(Locale.ROOT);
        fullfilled = currentDimension.equals(getTrigger().toLowerCase(Locale.ROOT));
        return fullfilled;
    }

    private boolean checkGamestage() {
        if (!Loader.isModLoaded("gamestages")) return true;
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        fullfilled = player != null && GameStageHelper.hasStage(player, getTrigger());
        return fullfilled;
    }
}
