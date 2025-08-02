package net.gobies.gobsarmory.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import org.jetbrains.annotations.NotNull;

public class VoidstepPiercerProjectile extends ThrownEnderpearl {

    public VoidstepPiercerProjectile(Level pLevel, LivingEntity pShooter) {
        super(pLevel, pShooter);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            double targetX = livingEntity.getX();
            double targetY = livingEntity.getY();
            double targetZ = livingEntity.getZ();
            targetX += Math.signum(this.getX() - targetX) * (livingEntity.getBbWidth() / 2 + 1.0);
            targetZ += Math.signum(this.getZ() - targetZ) * (livingEntity.getBbWidth() / 2 + 1.0);

            teleport(targetX, targetY, targetZ);
            livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 5.0F); // Adjust damage as needed
        }

        this.discard();
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        if (!this.level().isClientSide) {
            this.level().addParticle(ParticleTypes.PORTAL, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);

            if (pResult.getType() == HitResult.Type.ENTITY && pResult instanceof EntityHitResult entityHitResult) {
                Entity entity = entityHitResult.getEntity();
                if (entity instanceof LivingEntity livingEntity) {
                    double targetX = livingEntity.getX();
                    double targetY = livingEntity.getY();
                    double targetZ = livingEntity.getZ();
                    targetX += Math.signum(this.getX() - targetX) * (livingEntity.getBbWidth() / 2 + 1.0);
                    targetZ += Math.signum(this.getZ() - targetZ) * (livingEntity.getBbWidth() / 2 + 1.0);

                    teleport(targetX, targetY, targetZ);
                }
            } else {
                Entity owner = this.getOwner();
                if (owner instanceof Player) {
                    double targetX = this.getX();
                    double targetY = this.getY();
                    double targetZ = this.getZ();

                    teleport(targetX, targetY, targetZ);
                }
            }

            this.discard();
        }
    }

    private void teleport(double x, double y, double z) {
        Entity owner = this.getOwner();
        if (owner instanceof LivingEntity entity) {
            EntityTeleportEvent event = new EntityTeleportEvent(entity, x, y, z);
            if (!event.isCanceled()) {
                entity.teleportTo(event.getTargetX(), event.getTargetY(), event.getTargetZ());
                this.level().playSound(null, event.getTargetX(), event.getTargetY(), event.getTargetZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                entity.resetFallDistance();
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60, 1));
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 1));
            }
        }
    }
}