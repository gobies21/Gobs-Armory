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
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
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

            handleProjectileHit(targetX, targetY, targetZ);
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

                    handleProjectileHit(targetX, targetY, targetZ);
                }
            } else {
                Entity owner = this.getOwner();
                if (owner instanceof Player) {
                    double targetX = this.getX();
                    double targetY = this.getY();
                    double targetZ = this.getZ();

                    handleProjectileHit(targetX, targetY, targetZ);
                }
            }

            this.discard();
        }
    }

    private void handleProjectileHit(double x, double y, double z) {
        Entity owner = this.getOwner();
        if (owner instanceof LivingEntity livingEntity) {
            loadAndTeleport(livingEntity, x, y, z);
        }
    }

    private void loadAndTeleport(LivingEntity livingEntity, double targetX, double targetY, double targetZ) {
        Level level = livingEntity.level();
        int chunkX = (int) Math.floor(targetX / 16.0);
        int chunkZ = (int) Math.floor(targetZ / 16.0);

        ensureChunkLoaded(level, chunkX, chunkZ);
        ensureChunkLoaded(level, chunkX + 1, chunkZ);
        ensureChunkLoaded(level, chunkX - 1, chunkZ);
        ensureChunkLoaded(level, chunkX, chunkZ + 1);
        ensureChunkLoaded(level, chunkX, chunkZ - 1);

        teleport(livingEntity, targetX, targetY, targetZ);
    }

    private void ensureChunkLoaded(Level level, int chunkX, int chunkZ) {
        ChunkAccess chunk = level.getChunk(chunkX, chunkZ, ChunkStatus.FULL, false);
        if (chunk == null) {
            level.getChunk(chunkX, chunkZ, ChunkStatus.FULL, true);
        }
    }

    private void teleport(LivingEntity entity, double x, double y, double z) {
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