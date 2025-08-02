package net.gobies.gobsarmory.item.weapons;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.gobsarmory.Config;
import net.gobies.gobsarmory.init.GARarities;
import net.gobies.gobsarmory.projectile.VoidstepPiercerProjectile;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.SweepingEdgeEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class VoidstepPiercerItem extends TridentItem {
    public VoidstepPiercerItem(Properties properties) {
        super(properties.stacksTo(1).durability(400).rarity(GARarities.ENDER));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create(super.getAttributeModifiers(slot, stack));
        if (slot == EquipmentSlot.MAINHAND) {
            modifiers.removeAll(Attributes.ATTACK_DAMAGE);
            modifiers.removeAll(Attributes.ATTACK_SPEED);

            int attackDamage = Config.VOIDSTEP_PIERCER_ATTACK_DAMAGE.get() - 1;
            modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon Attack Damage", attackDamage, AttributeModifier.Operation.ADDITION));

            float attackSpeed = (float) (-4.0F + Config.VOIDSTEP_PIERCER_ATTACK_SPEED.get());
            modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon Attack Speed", attackSpeed, AttributeModifier.Operation.ADDITION));

        }
        return modifiers;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        if (pPlayer.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(itemstack);
        }

        pPlayer.startUsingItem(pUsedHand);

        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving, int pTimeLeft) {
        int i = this.getUseDuration(pStack) - pTimeLeft;
        if (i >= 10) {
            if (!pLevel.isClientSide) {
                pLevel.playSound(null, pEntityLiving.getX(), pEntityLiving.getY(), pEntityLiving.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                VoidstepPiercerProjectile enderPearl = new VoidstepPiercerProjectile(pLevel, pEntityLiving);
                Double f1 = Config.VOIDSTEP_PIERCER_TELEPORT_SPEED.get();
                float f2 = -Mth.sin(pEntityLiving.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(pEntityLiving.getXRot() * Mth.DEG_TO_RAD);
                float f3 = -Mth.sin(pEntityLiving.getXRot() * Mth.DEG_TO_RAD);
                float f4 = Mth.cos(pEntityLiving.getYRot() * Mth.DEG_TO_RAD) * Mth.cos(pEntityLiving.getXRot() * Mth.DEG_TO_RAD);
                Vec3 vec3 = (new Vec3(f2, f3, f4)).normalize().add(pEntityLiving.getRandom().nextGaussian() * 0.007499999832361933D, pEntityLiving.getRandom().nextGaussian() * 0.007499999832361933D, pEntityLiving.getRandom().nextGaussian() * 0.007499999832361933D);
                vec3 = vec3.scale(f1);
                enderPearl.setDeltaMovement(vec3);
                pLevel.addFreshEntity(enderPearl);
                pLevel.gameEvent(GameEvent.PROJECTILE_SHOOT, enderPearl.position(), GameEvent.Context.of(pEntityLiving));

                if (pEntityLiving instanceof Player player) {
                    player.getCooldowns().addCooldown(this, 20 * Config.VOIDSTEP_PIERCER_COOLDOWN.get());
                    if (!player.getAbilities().instabuild) {
                        pStack.hurt(1, player.getRandom(), (ServerPlayer) pEntityLiving);
                    }
                }
            }
        }
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 72000;
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
    public int getEnchantmentValue() {
        return 10;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, @NotNull ItemStack repair) {
        return toRepair.getItem() == this && repair.getItem() == Items.ENDER_PEARL;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}