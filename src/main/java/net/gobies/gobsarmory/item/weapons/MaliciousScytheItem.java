package net.gobies.gobsarmory.item.weapons;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.gobsarmory.Config;
import net.gobies.gobsarmory.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.SweepingEdgeEnchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import static net.gobies.gobsarmory.init.GobsArmoryRarities.CYBER;


public class MaliciousScytheItem extends SwordItem {

    public MaliciousScytheItem(Properties properties) {
        super(new MaliciousScytheTier(), 0, 0, new Properties().stacksTo(1).durability(1500).rarity(CYBER));
    }

    //speed is appearing before attack sometimes for unknown reason
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create(super.getAttributeModifiers(slot, stack));
        if (slot == EquipmentSlot.MAINHAND) {
            modifiers.removeAll(Attributes.ATTACK_DAMAGE);
            modifiers.removeAll(Attributes.ATTACK_SPEED);

            int attackDamage = Config.MALICIOUS_SCYTHE_ATTACK_DAMAGE.get() - 1;
            modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon Attack Damage", attackDamage, AttributeModifier.Operation.ADDITION));

            float attackSpeed = (float) ((float) -4.0F + Config.MALICIOUS_SCYTHE_ATTACK_SPEED.get());
            modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon Attack Speed", attackSpeed, AttributeModifier.Operation.ADDITION));

        }
        return modifiers;
    }


    // Define a custom tier for the scythe
    private static class MaliciousScytheTier implements Tier {
        @Override
        public int getUses() {
            return 1500; // durability
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
            return Ingredient.of(ModItems.IonCube.get()); // repair material
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
                    // spawn particles
                    //MaliciousScytheParticles.spawnParticles(level, new Vec3(targetX, targetY, targetZ));
                    radius = Config.MALICIOUS_SCYTHE_DEVASTATING_RADIUS.get(); // change to 8x8 area damage every 5 hits
                    SoundEvent pixelScythe = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("gobsarmory:pixel_scythe"));
                    level.playSound(null, targetX, targetY, targetZ, Objects.requireNonNull(pixelScythe), SoundSource.PLAYERS, 1.0F, 1.5F);
                    tag.putInt("HitCount", 0); // reset hitCount
                }
                AABB area = new AABB(targetX - radius, targetY - radius, targetZ - radius, targetX + radius, targetY + radius, targetZ + radius);

                float baseDamage = (float) pAttacker.getAttributeValue(Attributes.ATTACK_DAMAGE); //half of player attack damage
                // add damage bonus from sharpness enchantment
                int sharpnessLevel = pAttacker.getMainHandItem().getEnchantmentLevel(Enchantments.SHARPNESS);
                float enchantmentBonus = sharpnessLevel * 0.50F; // bonus per level of Sharpness

                // total damage to be applied to the attackers target
                float totalDamage = baseDamage + enchantmentBonus;

                // area damage is half of the total damage for 4x4 area and full damage for 8x8 area
                float areaDamage = (radius == Config.MALICIOUS_SCYTHE_DEVASTATING_RADIUS.get()) ?
                        (float) ((float) totalDamage / Config.MALICIOUS_SCYTHE_DEVASTATING_AREA_DAMAGE.get()) :
                        (float) (totalDamage / Config.MALICIOUS_SCYTHE_DEFAULT_AREA_DAMAGE.get());
                // get all entities in the area
                List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(LivingEntity.class, area);

                // deal damage to each nearby entity except the attacker and the target
                for (LivingEntity entity : nearbyEntities) {
                    if (entity != pAttacker && entity != pTarget) {
                        if (entity instanceof TamableAnimal && ((TamableAnimal) entity).isTame()) {
                            continue; // Skip this entity if it is tamed
                        }

                        Vec3 attackerPos = player.getEyePosition();
                        Vec3 entityPos = entity.getEyePosition();
                        BlockHitResult result = level.clip(new ClipContext(attackerPos, entityPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

                        // check if there is line of sight to the entity
                        if (result.getType() == HitResult.Type.MISS) {
                            entity.hurt(pAttacker.damageSources().mobAttack(pAttacker), areaDamage);
                        } else {
                            if (result.getType() == HitResult.Type.ENTITY) {
                                result.getType();
                            }
                        }
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

