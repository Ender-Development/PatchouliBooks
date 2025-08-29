package io.enderdev.patchoulibooks.unlock;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.GameRules;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nonnull;
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
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player == null) return false;
        switch (getType()) {
            case GAMEMODE:
                return checkGamemode(player);
            case DIMENSION:
                return checkDimension(player);
            case GAMESTAGE:
                return checkGamestage(player);
            case GAMERULE:
                return checkGamerule(player);
            default:
                return false;
        }
    }

    private boolean checkGamemode(@Nonnull EntityPlayer player) {
        boolean creative = player.isCreative() && getTrigger().toLowerCase(Locale.ROOT).equals("creative");
        boolean spectator = player.isSpectator() && getTrigger().toLowerCase(Locale.ROOT).equals("spectator");
        boolean survival = !player.isCreative() && !player.isSpectator() && (getTrigger().toLowerCase(Locale.ROOT).equals("survival") || getTrigger().toLowerCase(Locale.ROOT).equals("adventure"));
        fullfilled = creative || survival || spectator;
        return fullfilled;
    }

    private boolean checkDimension(@Nonnull EntityPlayer player) {
        String currentDimension = player.world.provider.getDimensionType().getName().toLowerCase(Locale.ROOT);
        fullfilled = currentDimension.equals(getTrigger().toLowerCase(Locale.ROOT));
        return fullfilled;
    }

    private boolean checkGamestage(@Nonnull EntityPlayer player) {
        if (!Loader.isModLoaded("gamestages")) return true;
        fullfilled = GameStageHelper.hasStage(player, getTrigger());
        return fullfilled;
    }

    private boolean checkGamerule(@Nonnull EntityPlayer player) {
        GameRules rules = player.world.getGameRules();
        return rules.hasRule(getTrigger()) && rules.getBoolean(getTrigger());
    }
}
