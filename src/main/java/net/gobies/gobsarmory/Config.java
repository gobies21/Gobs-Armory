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

    public static ForgeConfigSpec.ConfigValue<Integer> CYBERNETIC_CLEAVER_ATTACK_DAMAGE;
    public static int cybernetic_cleaver_attack_damage;
    public static ForgeConfigSpec.ConfigValue<Double> CYBERNETIC_CLEAVER_ATTACK_SPEED;
    public static float cybernetic_cleaver_attack_speed;
    public static ForgeConfigSpec.ConfigValue<Integer> CYBERNETIC_CLEAVER_BUGGED_MAX_STACK;
    public static int cybernetic_cleaver_bugged_max_stack;
    public static ForgeConfigSpec.ConfigValue<Double> CYBERNETIC_CLEAVER_BUGGED_CHANCE;
    public static float cybernetic_cleaver_bugged_chance;
    public static ForgeConfigSpec.ConfigValue<Integer> CYBERNETIC_CLEAVER_BUGGED_MIN_DURATION;
    public static int cybernetic_cleaver_bugged_min_duration;
    public static ForgeConfigSpec.ConfigValue<Integer> CYBERNETIC_CLEAVER_BUGGED_MAX_DURATION;
    public static int cybernetic_cleaver_bugged_max_duration;

    public static ForgeConfigSpec.ConfigValue<Integer> QUANTUM_AXE_ATTACK_DAMAGE;
    public static int quantum_axe_attack_damage;
    public static ForgeConfigSpec.ConfigValue<Double> QUANTUM_AXE_ATTACK_SPEED;
    public static float quantum_axe_attack_speed;

    public Config() {
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading configEvent) {
        malicious_scythe_attack_damage = MALICIOUS_SCYTHE_ATTACK_DAMAGE.get();
        malicious_scythe_attack_speed = (float) (MALICIOUS_SCYTHE_ATTACK_SPEED.get() * (double) 1.0F);
        malicious_scythe_hit_amount = MALICIOUS_SCYTHE_HIT_AMOUNT.get();
        malicious_scythe_default_radius = MALICIOUS_SCYTHE_DEFAULT_RADIUS.get();
        malicious_scythe_default_area_damage = (float) (MALICIOUS_SCYTHE_DEFAULT_AREA_DAMAGE.get() * (double) 1.0F);
        malicious_scythe_devastating_radius = MALICIOUS_SCYTHE_DEVASTATING_RADIUS.get();
        malicious_scythe_devastating_area_damage = (float) (MALICIOUS_SCYTHE_DEVASTATING_AREA_DAMAGE.get() * (double) 1.0F);
        cybernetic_cleaver_attack_damage = CYBERNETIC_CLEAVER_ATTACK_DAMAGE.get();
        cybernetic_cleaver_attack_speed = (float) (CYBERNETIC_CLEAVER_ATTACK_SPEED.get() * (double) 1.0F);
        cybernetic_cleaver_bugged_max_stack = CYBERNETIC_CLEAVER_BUGGED_MAX_STACK.get();
        cybernetic_cleaver_bugged_chance = (float) (CYBERNETIC_CLEAVER_BUGGED_CHANCE.get() * (double) 1.0F);
        cybernetic_cleaver_bugged_min_duration = CYBERNETIC_CLEAVER_BUGGED_MIN_DURATION.get();
        cybernetic_cleaver_bugged_max_duration = CYBERNETIC_CLEAVER_BUGGED_MAX_DURATION.get();
        quantum_axe_attack_damage = QUANTUM_AXE_ATTACK_DAMAGE.get();
        quantum_axe_attack_speed = (float) (QUANTUM_AXE_ATTACK_SPEED.get() * (double) 1.0F);

    }

    static {
        BUILDER.push("Malicious Scythe");
        MALICIOUS_SCYTHE_ATTACK_DAMAGE = BUILDER.comment("Attack damage of malicious scythe").defineInRange("Attack Damage", 20, 1, 100);
        MALICIOUS_SCYTHE_ATTACK_SPEED = BUILDER.comment("Attack speed of malicious scythe").defineInRange("Attack Speed", 0.7, 0.1, 5);
        MALICIOUS_SCYTHE_HIT_AMOUNT = BUILDER.comment("Amount of hits to activate the devastating attack").defineInRange("Amount of Hits", 8, 1, 100);
        MALICIOUS_SCYTHE_DEFAULT_RADIUS = BUILDER.comment("Max radius of default area damage").defineInRange("Default Radius", 4, 1, 10);
        MALICIOUS_SCYTHE_DEFAULT_AREA_DAMAGE = BUILDER.comment("Damage of default area damage (4 = 25% damage)").defineInRange("Default Area Damage", 2.0, 1.0, 4.0);
        MALICIOUS_SCYTHE_DEVASTATING_RADIUS = BUILDER.comment("Max radius of devastating area damage").defineInRange("Devastating Radius", 8, 1, 20);
        MALICIOUS_SCYTHE_DEVASTATING_AREA_DAMAGE = BUILDER.comment("Damage of devastating area damage (1 = 100% damage)").defineInRange("Devastating Area Damage", 1.0, 1.0, 4.0);
        BUILDER.pop();

        BUILDER.push("Cybernetic Cleaver");
        CYBERNETIC_CLEAVER_ATTACK_DAMAGE = BUILDER.comment("Attack damage of cybernetic cleaver").defineInRange("Attack Damage", 20, 1, 100);
        CYBERNETIC_CLEAVER_ATTACK_SPEED = BUILDER.comment("Attack speed of cybernetic cleaver").defineInRange("Attack Speed", 0.7, 0.1, 5);
        CYBERNETIC_CLEAVER_BUGGED_MAX_STACK = BUILDER.comment("Maximum bugged stacks that can be applied").define("Bugged Stacks", 3);
        CYBERNETIC_CLEAVER_BUGGED_CHANCE = BUILDER.comment("Chance that the bugged effect gets inflicted").define("Inflict Chance", 0.75);
        CYBERNETIC_CLEAVER_BUGGED_MIN_DURATION = BUILDER.comment("Minimum duration of the bugged effect applied on hit (must be a lower and not equal value to max duration)").define("Min Duration", 3);
        CYBERNETIC_CLEAVER_BUGGED_MAX_DURATION = BUILDER.comment("Maximum duration of the bugged effect applied on hit (must be a higher and not equal value to min duration)").define("Max Duration", 6);
        BUILDER.pop();

        BUILDER.push("Quantum Axe");
        QUANTUM_AXE_ATTACK_DAMAGE = BUILDER.comment("Attack damage of quantum axe").defineInRange("Attack Damage", 20, 1, 100);
        QUANTUM_AXE_ATTACK_SPEED = BUILDER.comment("Attack speed of quantum axe").defineInRange("Attack Speed", 0.7, 0.1, 5);

        SPEC = BUILDER.build();
    }
}