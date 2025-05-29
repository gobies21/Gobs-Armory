package net.gobies.gobsarmory.item.weapons;

import net.gobies.gobsarmory.init.GARarities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HyperwaveBowItem extends BowItem {

    public HyperwaveBowItem(Properties properties) {
        super(properties.stacksTo(1).rarity(GARarities.CYBER));
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity, int remainingUseTicks) {
        if (!level.isClientSide) {
            int useTime = this.getUseDuration(stack) - remainingUseTicks;
            float velocity = getArrowVelocity(useTime);

            if (velocity >= 0.1F) {
                AbstractArrow arrow = shootArrow(stack, level, livingEntity, velocity * 2.0F);
                arrow.setBaseDamage(velocity >= 1.0F ? arrow.getBaseDamage() * 0.5F : arrow.getBaseDamage());
                level.addFreshEntity(arrow);
                SoundEvent pixelScythe = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("gobsarmory:pixel_scythe"));
                level.playSound(null, arrow.getX(), arrow.getY(), arrow.getZ(), Objects.requireNonNull(pixelScythe), SoundSource.PLAYERS, 1.0F, 10.0F);
                if (velocity >= 1.0F) {
                    arrow.setCritArrow(true);
                    arrow.setPierceLevel((byte) 3);
                }
            }
        }
    }


    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 144000; // Slowed charge time
    }

    public float getArrowVelocity(int charge) {
        float f = charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;

        if (f > 2.0F) {
            f = 2.0F;
        }
        return f;
    }

    private AbstractArrow shootArrow(ItemStack stack, Level level, LivingEntity livingEntity, float velocity) {
        Arrow arrow = new Arrow(level, livingEntity);

        arrow.setBaseDamage(15.0F); // Set the base damage to 10

        if (livingEntity instanceof Player player) {
            int charge = getCharge(stack);

            }

        arrow.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0.0F, velocity, 1.0F);
        level.addFreshEntity(arrow); // Ensure the arrow is added to the world
        return arrow;
    }

    private int getCharge(ItemStack stack) {
        return stack.getOrCreateTag().getInt("Charge");
    }
}
