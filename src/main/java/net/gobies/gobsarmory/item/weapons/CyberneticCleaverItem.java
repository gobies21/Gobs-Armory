package net.gobies.gobsarmory.item.weapons;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.gobsarmory.Config;
import net.gobies.gobsarmory.init.GobsArmoryEffects;
import net.gobies.gobsarmory.item.ModItems;

import java.util.Random;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

import static net.gobies.gobsarmory.init.GobsArmoryRarities.CYBER;

public class CyberneticCleaverItem extends SwordItem {
    private long lastHitTime = 0;

    public CyberneticCleaverItem(Item.Properties properties) {
        super(new CyberneticCleaverItem.CyberneticCleaverTier(), 0, 0, new Item.Properties().stacksTo(1).durability(1500).rarity(CYBER));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create(super.getAttributeModifiers(slot, stack));
        if (slot == EquipmentSlot.MAINHAND) {
            modifiers.removeAll(Attributes.ATTACK_DAMAGE);
            modifiers.removeAll(Attributes.ATTACK_SPEED);

            int attackDamage = Config.CYBERNETIC_CLEAVER_ATTACK_DAMAGE.get() - 1;
            modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon Attack Damage", attackDamage, AttributeModifier.Operation.ADDITION));

            float attackSpeed = (float) ((float) -4.0F + Config.CYBERNETIC_CLEAVER_ATTACK_SPEED.get());
            modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon Attack Speed", attackSpeed, AttributeModifier.Operation.ADDITION));

        }
        return modifiers;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (System.currentTimeMillis() - lastHitTime >= 1000) {
            applyEffectStack(target);
            lastHitTime = System.currentTimeMillis();
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    private void applyEffectStack(LivingEntity target) {
        int MAX_EFFECT_STACKS = Config.CYBERNETIC_CLEAVER_GLITCH_MAX_STACK.get();
        MobEffectInstance currentEffect = target.getEffect(GobsArmoryEffects.GLITCHED.get());
        int newAmplifier = currentEffect == null ? 0 : Math.min(currentEffect.getAmplifier() + 1, MAX_EFFECT_STACKS - 1);
        int randomDuration = new Random().nextInt(3, 10) * 20; // convert seconds to ticks (3-10 seconds)
        int finalDuration = currentEffect != null ? Math.max(currentEffect.getDuration(), randomDuration) : randomDuration;
        target.addEffect(new MobEffectInstance(GobsArmoryEffects.GLITCHED.get(), finalDuration, newAmplifier));
    }

    private static class CyberneticCleaverTier implements Tier {
        @Override
        public int getUses() {
            return 1500;
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
            return 6;
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(ModItems.IonCube.get());
        }
    }
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§2Many powerful viruses formed into one entity"));
        pTooltipComponents.add(Component.literal("§aApplies a powerful stacking glitch effect to hit enemies"));
        pTooltipComponents.add(Component.literal("§aGlitched enemies take §33 §adamage for each level every §32 §aseconds"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}