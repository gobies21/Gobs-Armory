package net.gobies.gobsarmory.item.materials;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class IonCubeItem extends Item {
    public IonCubeItem(Properties properties) {
        super(new Properties().stacksTo(64).rarity(Rarity.RARE));
    }
}
