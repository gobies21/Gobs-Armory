package net.gobies.gobsarmory;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = GobsArmory.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Integer> MALICIOUS_SCYTHE_ATTACK_DAMAGE;
    public static int malicious_scythe_attack_damage;
    public static ForgeConfigSpec.ConfigValue<Double> MALICIOUS_SCYTHE_ATTACK_SPEED;
    public static float malicious_scythe_attack_speed;
    public static ForgeConfigSpec.ConfigValue<Integer> MALICIOUS_SCYTHE_HIT_AMOUNT;
    public static int malicious_scythe_hit_amount;
    public static ForgeConfigSpec.ConfigValue<Integer> MALICIOUS_SCYTHE_DEFAULT_RADIUS;
    public static int malicious_scythe_default_radius;
    public static ForgeConfigSpec.ConfigValue<Double> MALICIOUS_SCYTHE_DEFAULT_AREA_DAMAGE;
    public static float malicious_scythe_default_area_damage;
    public static ForgeConfigSpec.ConfigValue<Integer> MALICIOUS_SCYTHE_DEVASTATING_RADIUS;
    public static int malicious_scythe_devastating_radius;
    public static ForgeConfigSpec.ConfigValue<Double> MALICIOUS_SCYTHE_DEVASTATING_AREA_DAMAGE;
    public static float malicious_scythe_devastating_area_damage;

    public Config() {
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading configEvent) {
        malicious_scythe_attack_damage = MALICIOUS_SCYTHE_ATTACK_DAMAGE.get();
        malicious_scythe_attack_speed = (float) ((Double) MALICIOUS_SCYTHE_ATTACK_SPEED.get() * (double) 1.0F);
        malicious_scythe_hit_amount = MALICIOUS_SCYTHE_HIT_AMOUNT.get();
        malicious_scythe_default_radius = MALICIOUS_SCYTHE_DEFAULT_RADIUS.get();
        malicious_scythe_default_area_damage = (float) ((Double) MALICIOUS_SCYTHE_DEFAULT_AREA_DAMAGE.get() * (double) 1.0F);
        malicious_scythe_devastating_radius = MALICIOUS_SCYTHE_DEVASTATING_RADIUS.get();
        malicious_scythe_devastating_area_damage = (float) ((Double) MALICIOUS_SCYTHE_DEVASTATING_AREA_DAMAGE.get() * (double) 1.0F);

    }
//cannot get base stats to be configurable
    static {
        BUILDER.push("Malicious Scythe");
        MALICIOUS_SCYTHE_ATTACK_DAMAGE = BUILDER.comment("Attack damage of malicious scythe").defineInRange("Damage", 27, 1, 100);
        MALICIOUS_SCYTHE_ATTACK_SPEED = BUILDER.comment("Attack speed of malicious scythe").defineInRange("Speed", 0.7, 0.1, 5);
        MALICIOUS_SCYTHE_HIT_AMOUNT = BUILDER.comment("Amount of hits to activate the devastating attack").defineInRange("Amount of Hits", 8, 1, 100);
        MALICIOUS_SCYTHE_DEFAULT_RADIUS = BUILDER.comment("Max radius of default area damage").defineInRange("Default Radius", 4, 1, 10);
        MALICIOUS_SCYTHE_DEFAULT_AREA_DAMAGE = BUILDER.comment("Damage of default area damage (4 = 25% damage)").defineInRange("Default Area Damage", 4.0, 1.0, 4.0);
        MALICIOUS_SCYTHE_DEVASTATING_RADIUS = BUILDER.comment("Max radius of devastating area damage").defineInRange("Devastating Radius", 8, 1, 20);
        MALICIOUS_SCYTHE_DEVASTATING_AREA_DAMAGE = BUILDER.comment("Damage of devastating area damage (1 = 100% damage)").defineInRange("Devastating Area Damage", 1.0, 1.0, 4.0);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}