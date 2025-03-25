package net.gobies.gobsarmory.item.weapons;

import net.gobies.gobsarmory.Config;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.SweepingEdgeEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class MaliciousScytheItem extends SwordItem {
    public int hitCount = 0;

    public MaliciousScytheItem(Properties properties) {
        super(new MaliciousScytheTier(), 26, -3.3F, new Properties().stacksTo(1).durability(2468).rarity(Rarity.EPIC));

    }


    // Define a custom tier for the scythe
    private static class MaliciousScytheTier implements Tier {
        @Override
        public int getUses() {
            return 2468; // durability
        }

        @Override
        public float getSpeed() {
            return 0.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0.0F;
        }

        @Override
        public int getLevel() {
            return 6; // material level
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(Items.NETHERITE_INGOT); // repair material
        }
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        // call original method to deal damage to target
        boolean success = super.hurtEnemy(pStack, pTarget, pAttacker);

        double targetX = pTarget.getX();
        double targetY = pTarget.getY();
        double targetZ = pTarget.getZ();
        double radius = Config.MALICIOUS_SCYTHE_DEFAULT_RADIUS.get(); // 4x4 area damage

        if (success && pAttacker instanceof Player player) {
            float attackStrength = player.getAttackStrengthScale(1.0F);
            if (attackStrength >= 1.0F) { //attack energy
                Level level = pAttacker.level();
                CompoundTag tag = pStack.getOrCreateTag(); // get or create NBT tag
                int hitCount = tag.getInt("HitCount");
                hitCount++;
                tag.putInt("HitCount", hitCount); // update and store hitCount
                if (hitCount % Config.MALICIOUS_SCYTHE_HIT_AMOUNT.get() == 0) {
                    radius = Config.MALICIOUS_SCYTHE_DEVASTATING_RADIUS.get(); // change to 8x8 area damage every 5 hits
                    SoundEvent pixelScythe = (SoundEvent) BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("gobsarmory:pixel_scythe"));
                    level.playSound(null, targetX, targetY, targetZ, Objects.requireNonNull(pixelScythe), SoundSource.PLAYERS, 1.0F, 1.5F);
                    tag.putInt("HitCount", 0); // reset hitCount
                }
                AABB area = new AABB(targetX - radius, targetY - radius, targetZ - radius, targetX + radius, targetY + radius, targetZ + radius);

                float baseDamage = (float) pAttacker.getAttributeValue(Attributes.ATTACK_DAMAGE); //half of player attack damage
                // add damage bonus from sharpness enchantment
                int sharpnessLevel = pAttacker.getMainHandItem().getEnchantmentLevel(Enchantments.SHARPNESS);
                float enchantmentBonus = sharpnessLevel * 1.25F; // bonus per level of Sharpness

                // total damage to be applied to the attackers target
                float totalDamage = baseDamage + enchantmentBonus;

                // area damage is half of the total damage for 4x4 area and full damage for 8x8 area
                float areaDamage = (radius == Config.MALICIOUS_SCYTHE_DEVASTATING_RADIUS.get()) ? totalDamage : totalDamage / 4.0F;
                // get all entities in the area
                List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(LivingEntity.class, area);

                // deal damage to each nearby entity except the attacker and the target
                for (LivingEntity entity : nearbyEntities) {
                    if (entity != pAttacker && entity != pTarget) {
                        entity.hurt(pAttacker.damageSources().mobAttack(pAttacker), areaDamage);
                    }
                }
            }
        }

        return success;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment instanceof SweepingEdgeEnchantment) {
            return false;
        } else if (enchantment.category == EnchantmentCategory.WEAPON) {
            return true;
        } else {
            return super.canApplyAtEnchantingTable(stack, enchantment);
        }
    }



    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getTag(); // get NBT tag
        int hitCount = tag != null ? tag.getInt("HitCount") : 0; // read hitCount from NBT
        pTooltipComponents.add(Component.literal("§2A weapon formed from malicious software"));
        pTooltipComponents.add(Component.literal("§aDeals large area damage to nearby entities"));
        pTooltipComponents.add(Component.literal(String.format("§aEvery §3%d §ahits deal a devastating attack §3" + hitCount +"§3/%d", Config.MALICIOUS_SCYTHE_HIT_AMOUNT.get(), Config.MALICIOUS_SCYTHE_HIT_AMOUNT.get())));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}

