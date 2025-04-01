package net.gobies.gobsarmory.init;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;

public class GobsArmoryRarities {

    public static Rarity CYBER;

    public GobsArmoryRarities() {
    }

    static {
        CYBER = Rarity.create("CYBER", ChatFormatting.DARK_GREEN);
    }
}