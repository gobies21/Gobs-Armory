package net.gobies.gobsarmory;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = GobsArmory.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Integer> MALICIOUS_SCYTHE_HIT_AMOUNT;
    public static int malicious_scythe_hit_amount;

    public Config() {
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading configEvent) {
        malicious_scythe_hit_amount = MALICIOUS_SCYTHE_HIT_AMOUNT.get();

    }

    static {
        BUILDER.push("Malicious Scythe");
        MALICIOUS_SCYTHE_HIT_AMOUNT = BUILDER.comment("Amount of hits to activate the devastating attack").define("Amount", 8);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}