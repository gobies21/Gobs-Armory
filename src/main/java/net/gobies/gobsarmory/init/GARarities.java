package net.gobies.gobsarmory.init;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;

public class GARarities {

    public static Rarity CYBER;
    public static Rarity ENDER;

    public GARarities() {
    }

    static {
        CYBER = Rarity.create("CYBER", ChatFormatting.DARK_GREEN);
        ENDER = Rarity.create("ENDER", ChatFormatting.DARK_PURPLE);
    }
}