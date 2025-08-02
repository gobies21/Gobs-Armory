package net.gobies.gobsarmory.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GASounds {

    public static final DeferredRegister<SoundEvent> REGISTRY;
    public static final RegistryObject<SoundEvent> PIXEL_SCYTHE;

    public GASounds() {
    }

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }

    static {
        REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "gobsarmory");
        PIXEL_SCYTHE = REGISTRY.register("pixel_scythe", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("gobsarmory", "pixel_scythe")));
    }
}