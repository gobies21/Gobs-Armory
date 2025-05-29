package net.gobies.gobsarmory.item.weapons;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.gobsarmory.Config;
import net.gobies.gobsarmory.init.GARarities;
import net.gobies.gobsarmory.item.GAItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class QuantumAxeItem extends AxeItem {
    public QuantumAxeItem(Properties properties) {
        super(new QuantumAxeItem.QuantumAxeTier(), 0, 0, properties.stacksTo(1).durability(1500).rarity(GARarities.CYBER));
    }

    private int attackCount = 0;

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create(super.getAttributeModifiers(slot, stack));
        if (slot == EquipmentSlot.MAINHAND) {
            modifiers.removeAll(Attributes.ATTACK_DAMAGE);
            modifiers.removeAll(Attributes.ATTACK_SPEED);

            int attackDamage = Config.QUANTUM_AXE_ATTACK_DAMAGE.get() - 1;
            modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon Attack Damage", attackDamage, AttributeModifier.Operation.ADDITION));

            float attackSpeed = (float) (-4.0F + Config.QUANTUM_AXE_ATTACK_SPEED.get());
            modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon Attack Speed", attackSpeed, AttributeModifier.Operation.ADDITION));

        }
        return modifiers;
    }

    public boolean hurtEnemy(@NotNull ItemStack stack, LivingEntity target, @NotNull LivingEntity attacker) {
        // Get the player's attack damage
        float playerAttackDamage = attacker instanceof Player ? ((Player) attacker).getAttackStrengthScale(0.5F) * 2.0F : 0.0F;

        // Calculate the armor ignore percentage, up to a maximum of 100%
        float armorIgnorePercentage = Math.min(attackCount * 25.0F, 100.0F) / 100.0F;
        float effectiveArmor = target.getArmorValue() * (1.0F - armorIgnorePercentage); // Adjust the effective armor based on the ignore percentage

        // Calculate the damage after accounting for armor reduction
        float damage = playerAttackDamage - effectiveArmor;
        if (damage > 0.0F) {
            assert attacker instanceof Player;
            target.hurt(target.damageSources().playerAttack((Player) attacker), damage); // Deal the calculated damage
        }

        attackCount++; // Increment the attack count each time the axe hits an entity

        return true;
    }

    private static class QuantumAxeTier implements Tier {
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
}
