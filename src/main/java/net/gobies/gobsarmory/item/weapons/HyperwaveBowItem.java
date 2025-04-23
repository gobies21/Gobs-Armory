package net.gobies.gobsarmory.item.weapons;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class HyperwaveBowItem extends BowItem {
    private static final float THIRD_CHARGE_POWER = 1.5F;

    public HyperwaveBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            boolean hasInfiniteArrows = player.getAbilities().instabuild || pStack.getEnchantmentLevel(net.minecraft.world.item.enchantment.Enchantments.INFINITY_ARROWS) > 0;
            ItemStack itemstack = player.getProjectile(pStack);

            int charge = this.getUseDuration(pStack) - pTimeLeft;
            charge = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(pStack, pLevel, player, charge, !itemstack.isEmpty() || hasInfiniteArrows);
            if (charge < 0) return;

            if (!itemstack.isEmpty() || hasInfiniteArrows) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float power = getPowerForTime(charge);
                if (!(power < 0.1D)) {
                    boolean isInfiniteArrow = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem) itemstack.getItem()).isInfinite(itemstack, pStack, player));

                    if (!pLevel.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem) (itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow abstractarrow = arrowitem.createArrow(pLevel, itemstack, player);
                        abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, power * 3.0F, 1.0F);

                        if (power == THIRD_CHARGE_POWER) {
                            abstractarrow.setCritArrow(true);
                        }

                        pStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));

                        pLevel.addFreshEntity(abstractarrow);
                    }

                    pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F);

                    if (!isInfiniteArrow && !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                    }
                }
            }
        }
    }

    public static float getPowerForTime(int charge) {
        float power = (float) charge / 20.0F;
        power = (power * power + power * 2.0F) / 3.0F;
        if (power > 1.0F) {
            power = THIRD_CHARGE_POWER;
        }
        return Math.min(power, THIRD_CHARGE_POWER);
    }
}
