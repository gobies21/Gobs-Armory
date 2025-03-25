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
    public static ForgeConfigSpec.ConfigValue<Integer> MALICIOUS_SCYTHE_DEFAULT_RADIUS;
    public static int malicious_scythe_default_radius;
    public static ForgeConfigSpec.ConfigValue<Integer> MALICIOUS_SCYTHE_DEVASTATING_RADIUS;
    public static int malicious_scythe_devastating_radius;

    public Config() {
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading configEvent) {
        malicious_scythe_hit_amount = MALICIOUS_SCYTHE_HIT_AMOUNT.get();
        malicious_scythe_default_radius = MALICIOUS_SCYTHE_DEFAULT_RADIUS.get();
        malicious_scythe_devastating_radius = MALICIOUS_SCYTHE_DEVASTATING_RADIUS.get();

    }

    static {
        BUILDER.push("Malicious Scythe");
        MALICIOUS_SCYTHE_HIT_AMOUNT = BUILDER.comment("Amount of hits to activate the devastating attack").define("Amount", 8);
        MALICIOUS_SCYTHE_DEFAULT_RADIUS = BUILDER.comment("Max radius of default sweep damage").define("Default Radius", 4);
        MALICIOUS_SCYTHE_DEVASTATING_RADIUS = BUILDER.comment("Max radius of devastating sweep damage").define("Devastating Radius", 8);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}