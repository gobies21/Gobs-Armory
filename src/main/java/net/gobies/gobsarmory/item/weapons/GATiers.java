package net.gobies.gobsarmory.item.weapons;

import net.gobies.gobsarmory.init.GAItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class GATiers {


    public static final CyberTier CYBER_TIER = new CyberTier();
    public static final EnderTier ENDER_TIER = new EnderTier();

    // CyberTier class
    public static class CyberTier implements Tier {
        @Override
        public int getUses() {
            return 1500;
        }

        @Override
        public float getSpeed() {
            return 6.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0.0F;
        }

        @Override
        public int getLevel() {
            return 6;
        }

        @Override
        public int getEnchantmentValue() {
            return 20;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(GAItems.IonCube.get());
        }
    }

    // EnderTier class
    public static class EnderTier implements Tier {
        @Override
        public int getUses() {
            return 400;
        }

        @Override
        public float getSpeed() {
            return 3.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0;
        }

        @Override
        public int getLevel() {
            return 0;
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(Items.ENDER_PEARL);
        }
    }
}