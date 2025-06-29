package net.gobies.gobsarmory.item.weapons;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.gobsarmory.Config;
import net.gobies.gobsarmory.init.GAEffects;
import net.gobies.gobsarmory.init.GARarities;
import net.gobies.gobsarmory.init.GAItems;

import java.util.Random;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class CyberneticCleaverItem extends SwordItem {
    private long lastHitTime = 0;

    public CyberneticCleaverItem(Properties properties) {
        super(new CyberneticCleaverItem.CyberneticCleaverTier(), 0, 0, properties.stacksTo(1).durability(1500).rarity(GARarities.CYBER));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create(super.getAttributeModifiers(slot, stack));
        if (slot == EquipmentSlot.MAINHAND) {
            modifiers.removeAll(Attributes.ATTACK_DAMAGE);
            modifiers.removeAll(Attributes.ATTACK_SPEED);

            int attackDamage = Config.CYBERNETIC_CLEAVER_ATTACK_DAMAGE.get() - 1;
            modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon Attack Damage", attackDamage, AttributeModifier.Operation.ADDITION));

            float attackSpeed = (float) (-4.0F + Config.CYBERNETIC_CLEAVER_ATTACK_SPEED.get());
            modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon Attack Speed", attackSpeed, AttributeModifier.Operation.ADDITION));

        }
        return modifiers;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (attacker instanceof Player player) {
            float bugChance = player.getRandom().nextFloat();
            float attackStrength = player.getAttackStrengthScale(1.0F);
            if (attackStrength >= 0.8F) {
                if (System.currentTimeMillis() - lastHitTime >= 1000 && bugChance < Config.CYBERNETIC_CLEAVER_BUGGED_CHANCE.get()) {
                    applyEffectStack(target);
                    lastHitTime = System.currentTimeMillis();
                }
                return super.hurtEnemy(stack, target, attacker);
            }
        }
        return true;
    }

    private void applyEffectStack(LivingEntity target) {
        int MAX_EFFECT_STACKS = Config.CYBERNETIC_CLEAVER_BUGGED_MAX_STACK.get();
        MobEffectInstance currentEffect = target.getEffect(GAEffects.BUGGED.get());
        int newAmplifier = currentEffect == null ? 0 : Math.min(currentEffect.getAmplifier() + 1, MAX_EFFECT_STACKS - 1);
        int randomDuration = new Random().nextInt(Config.CYBERNETIC_CLEAVER_BUGGED_MIN_DURATION.get(), Config.CYBERNETIC_CLEAVER_BUGGED_MAX_DURATION.get()) * 20;
        int finalDuration = currentEffect != null ? currentEffect.getDuration() : randomDuration;

        MobEffectInstance newEffect = new MobEffectInstance(GAEffects.BUGGED.get(), finalDuration, newAmplifier, false, false);
        target.addEffect(newEffect, null);
    }


    private static class CyberneticCleaverTier implements Tier {
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

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§2Many powerful viruses formed into one entity"));
        pTooltipComponents.add(Component.literal("§aChance to apply a powerful stacking bug effect to hit enemies"));
        pTooltipComponents.add(Component.literal("§aBugged enemies loose §32.5% §aof their health every §30.5 §aseconds"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}