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
    public static ForgeConfigSpec.ConfigValue<Integer> CYBERNETIC_CLEAVER_GLITCH_MAX_STACK;
    public static int cybernetic_cleaver_glitch_max_stack;

    public static ForgeConfigSpec.ConfigValue<Integer> QUANTUM_AXE_ATTACK_DAMAGE;
    public static int quantum_axe_attack_damage;
    public static ForgeConfigSpec.ConfigValue<Double> QUANTUM_AXE_ATTACK_SPEED;
    public static float quantum_axe_attack_speed;

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
        cybernetic_cleaver_attack_damage = CYBERNETIC_CLEAVER_ATTACK_DAMAGE.get();
        cybernetic_cleaver_attack_speed = (float) ((Double) CYBERNETIC_CLEAVER_ATTACK_SPEED.get() * (double) 1.0F);
        cybernetic_cleaver_glitch_max_stack = CYBERNETIC_CLEAVER_GLITCH_MAX_STACK.get();
        quantum_axe_attack_damage = QUANTUM_AXE_ATTACK_DAMAGE.get();
        quantum_axe_attack_speed = (float) ((Double) QUANTUM_AXE_ATTACK_SPEED.get() * (double) 1.0F);

    }

    static {
        BUILDER.push("Malicious Scythe");
        MALICIOUS_SCYTHE_ATTACK_DAMAGE = BUILDER.comment("Attack damage of malicious scythe").defineInRange("Attack Damage", 27, 1, 100);
        MALICIOUS_SCYTHE_ATTACK_SPEED = BUILDER.comment("Attack speed of malicious scythe").defineInRange("Attack Speed", 0.7, 0.1, 5);
        MALICIOUS_SCYTHE_HIT_AMOUNT = BUILDER.comment("Amount of hits to activate the devastating attack").defineInRange("Amount of Hits", 8, 1, 100);
        MALICIOUS_SCYTHE_DEFAULT_RADIUS = BUILDER.comment("Max radius of default area damage").defineInRange("Default Radius", 4, 1, 10);
        MALICIOUS_SCYTHE_DEFAULT_AREA_DAMAGE = BUILDER.comment("Damage of default area damage (4 = 25% damage)").defineInRange("Default Area Damage", 4.0, 1.0, 4.0);
        MALICIOUS_SCYTHE_DEVASTATING_RADIUS = BUILDER.comment("Max radius of devastating area damage").defineInRange("Devastating Radius", 8, 1, 20);
        MALICIOUS_SCYTHE_DEVASTATING_AREA_DAMAGE = BUILDER.comment("Damage of devastating area damage (1 = 100% damage)").defineInRange("Devastating Area Damage", 1.0, 1.0, 4.0);
        BUILDER.pop();

        BUILDER.push("Cybernetic Cleaver");
        CYBERNETIC_CLEAVER_ATTACK_DAMAGE = BUILDER.comment("Attack damage of cybernetic cleaver").defineInRange("Attack Damage", 22, 1, 100);
        CYBERNETIC_CLEAVER_ATTACK_SPEED = BUILDER.comment("Attack speed of cybernetic cleaver").defineInRange("Attack Speed", 0.7, 0.1, 5);
        CYBERNETIC_CLEAVER_GLITCH_MAX_STACK = BUILDER.comment("Maximum of glitch stacks that can be applied").define("Stacks", 5);BUILDER.pop();

        BUILDER.push("Quantum Axe");
        QUANTUM_AXE_ATTACK_DAMAGE = BUILDER.comment("Attack damage of quantum axe").defineInRange("Attack Damage", 22, 1, 100);
        QUANTUM_AXE_ATTACK_SPEED = BUILDER.comment("Attack speed of quantum axe").defineInRange("Attack Speed", 0.7, 0.1, 5);
        SPEC = BUILDER.build();
    }
}