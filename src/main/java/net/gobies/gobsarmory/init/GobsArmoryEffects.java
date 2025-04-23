package net.gobies.gobsarmory.init;

import net.gobies.gobsarmory.effects.Glitched;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GobsArmoryEffects {

    public static final DeferredRegister<MobEffect> REGISTRY;
    public static final RegistryObject<MobEffect> GLITCHED;

     public GobsArmoryEffects() {
    }

    static {
        REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "gobsarmory");
        GLITCHED = REGISTRY.register("glitched", () -> new Glitched(MobEffectCategory.HARMFUL, 0x00FF00));
    }
}