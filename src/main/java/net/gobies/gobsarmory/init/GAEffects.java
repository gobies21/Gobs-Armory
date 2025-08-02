package net.gobies.gobsarmory.init;

import net.gobies.gobsarmory.effects.Bugged;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GAEffects {

    public static final DeferredRegister<MobEffect> REGISTRY;
    public static final RegistryObject<MobEffect> BUGGED;

     public GAEffects() {
    }

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }

    static {
        REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "gobsarmory");
        BUGGED = REGISTRY.register("bugged", () -> new Bugged(MobEffectCategory.HARMFUL, 0x00FF00));
    }
}