package net.gobies.gobsarmory.item;

import net.gobies.gobsarmory.GobsArmory;
import net.gobies.gobsarmory.item.weapons.MaliciousScytheItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item>ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GobsArmory.MOD_ID);
    public static final RegistryObject<Item> MaliciousScythe = ITEMS.register("malicious_scythe", () -> new MaliciousScytheItem(new SwordItem.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}