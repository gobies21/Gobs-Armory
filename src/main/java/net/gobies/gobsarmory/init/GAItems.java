package net.gobies.gobsarmory.init;

import net.gobies.gobsarmory.GobsArmory;
import net.gobies.gobsarmory.item.materials.CorruptCircuitItem;
import net.gobies.gobsarmory.item.materials.IonCubeItem;
import net.gobies.gobsarmory.item.weapons.*;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GAItems {
    public static final DeferredRegister<Item> ITEMS;
    public static final RegistryObject<Item> MaliciousScythe;
    public static final RegistryObject<Item> CyberneticCleaver;
    public static final RegistryObject<Item> HyperwaveBow;
    public static final RegistryObject<Item> QuantumAxe;
    public static final RegistryObject<Item> IonCube;
    public static final RegistryObject<Item> CorruptCircuit;

    public static final RegistryObject<Item> YinKnife;
    public static final RegistryObject<Item> XinKnife;

    public GAItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    static {
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GobsArmory.MOD_ID);
        MaliciousScythe = ITEMS.register("malicious_scythe", () -> new MaliciousScytheItem(new SwordItem.Properties()));
        CyberneticCleaver = ITEMS.register("cybernetic_cleaver", () -> new CyberneticCleaverItem(new SwordItem.Properties()));
        HyperwaveBow = ITEMS.register("hyperwave_bow", () -> new HyperwaveBowItem(new BowItem.Properties()));
        QuantumAxe = ITEMS.register("quantum_axe", () -> new QuantumAxeItem(new AxeItem.Properties()));
        IonCube = ITEMS.register("ion_cube", () -> new IonCubeItem(new Item.Properties()));
        CorruptCircuit = ITEMS.register("corrupt_circuit", () -> new CorruptCircuitItem(new Item.Properties()));
        YinKnife = ITEMS.register("yin_knife", () -> new YinKnifeItem(new SwordItem.Properties()));
        XinKnife = ITEMS.register("xin_knife", () -> new XinKnifeItem(new SwordItem.Properties()));
    }
}