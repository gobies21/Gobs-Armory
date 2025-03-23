package net.gobies.gobsarmory.item;

import net.gobies.gobsarmory.GobsArmory;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.swing.*;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GobsArmory.MOD_ID);
    public static final RegistryObject<CreativeModeTab> MORE_ARTIFACTS_TAB = CREATIVE_MODE_TABS.register("gobsarmory_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MaliciousScythe.get()))
                    .title(Component.translatable("creativetab.gobsarmory_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.MaliciousScythe.get());

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}