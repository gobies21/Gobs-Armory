package net.gobies.gobsarmory.loot;

import net.gobies.gobsarmory.GobsArmory;
import net.gobies.gobsarmory.init.GAItems;
import net.gobies.gobsarmory.util.LootTableHandler;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GobsArmory.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GALootTables {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLootTableLoad(LootTableLoadEvent event) {
        LootTableHandler.addLoot(event, "chests/end_city_treasure", GAItems.IonCube.get(), 1, 1, 0.05f);
        LootTableHandler.addLoot(event, "chests/end_city_treasure", GAItems.VoidstepPiercer.get(), 1, 1, 0.01f);

    }
}