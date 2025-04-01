package net.gobies.gobsarmory.item.materials;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class CorruptCircuitItem extends Item {
    public CorruptCircuitItem(Item.Properties properties) {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.EPIC));
    }
}