package net.gobies.gobsarmory.item.weapons;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class MaliciousScytheItem extends SwordItem {
    public MaliciousScytheItem(Properties properties) {
        super(new MaliciousScytheTier(), 26, -3.4F, new Properties().stacksTo(1).durability(2468).rarity(Rarity.EPIC));

    }


    // Define a custom tier for the scythe
    private static class MaliciousScytheTier implements Tier {
        @Override
        public int getUses() {
            return 2468; // Durability
        }

        @Override
        public float getSpeed() {
            return 1.0F; // Base speed; attack speed is set in the constructor of SwordItem
        }

        @Override
        public float getAttackDamageBonus() {
            return 0.0F; // Attack damage is set in the constructor of SwordItem
        }

        @Override
        public int getLevel() {
            return 5; // Material level, similar to diamond
        }

        @Override
        public int getEnchantmentValue() {
            return 15; // Enchantment value
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(Items.NETHERITE_INGOT); // Repair material
        }
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        // Call the original method to deal damage to the target
        boolean success = super.hurtEnemy(pStack, pTarget, pAttacker);

        double targetX = pTarget.getX();
        double targetY = pTarget.getY();
        double targetZ = pTarget.getZ();
        double radius = 4; // 4x4 area damage

        if (success && pAttacker instanceof Player player) {
            float attackStrength = player.getAttackStrengthScale(1.0F);
            if (attackStrength >= 1.0F) { //attack energy
                Level level = pAttacker.level();
                AABB area = new AABB(targetX - radius, targetY - radius, targetZ - radius, targetX + radius, targetY + radius, targetZ + radius);

                float damageAmount = (float) pAttacker.getAttributeValue(Attributes.ATTACK_DAMAGE) / 2.0f; //half of player attack damage
                // get all entities in the area
                List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(LivingEntity.class, area);

                // deal damage to each nearby entity except the attacker and the target
                for (LivingEntity entity : nearbyEntities) {
                    if (entity != pAttacker && entity != pTarget) {
                        entity.hurt(pAttacker.damageSources().mobAttack(pAttacker), damageAmount);
                    }
                }
            }
        }

        return success;
    }
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§2A weapon formed from malicious software"));
        pTooltipComponents.add(Component.literal("§aDeals large area damage to nearby entities"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}