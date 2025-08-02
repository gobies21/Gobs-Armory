package net.gobies.gobsarmory.init;

import net.gobies.gobsarmory.GobsArmory;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GACreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS;
    public static final RegistryObject<CreativeModeTab> GOBSARMORY_TAB;


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    static {
        CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GobsArmory.MOD_ID);
        GOBSARMORY_TAB = CREATIVE_MODE_TABS.register("gobsarmory_tab",
                () -> CreativeModeTab.builder().icon(() -> new ItemStack(GAItems.MaliciousScythe.get()))
                        .title(Component.translatable("creativetab.gobsarmory_tab"))
                        .displayItems((pParameters, pOutput) -> {
                            pOutput.accept(GAItems.MaliciousScythe.get());
                            pOutput.accept(GAItems.CyberneticCleaver.get());
                            //pOutput.accept(GAItems.QuantumAxe.get());
                            //pOutput.accept(GAItems.HyperwaveBow.get());
                            pOutput.accept(GAItems.IonCube.get());
                            pOutput.accept(GAItems.CorruptCircuit.get());
                            pOutput.accept(GAItems.VoidstepPiercer.get());
                        })
                        .build());
    }
}