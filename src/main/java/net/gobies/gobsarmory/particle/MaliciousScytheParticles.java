package net.gobies.gobsarmory.particle;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class MaliciousScytheParticles {

    private static final int CUBE_SIZE = 3; // Size of the cube
    private static final int RADIUS = 8;  // Radius for particle disappearing
    private static final int PARTICLE_LIFESPAN = 60; // Number of ticks particles should fall
    private static final double GRAVITY = -0.05; // Gravity effect on particles
    private static final double SPREAD = 0.02; // Particle spread factor

    public static void spawnParticles(Level level, Vec3 position) {
        if (level == null || !level.isClientSide()) {
            return;
        }

        int particleCount = 0;
        // Spawn particles on cube edges only
        for (int x = -CUBE_SIZE / 2; x <= CUBE_SIZE / 2; x++) {
            for (int y = -CUBE_SIZE / 2; y <= CUBE_SIZE / 2; y++) {
                for (int z = -CUBE_SIZE / 2; z <= CUBE_SIZE / 2; z++) {
                    // Only spawn if on edge of cube
                    if (Math.abs(x) == CUBE_SIZE / 2 || Math.abs(y) == CUBE_SIZE / 2 || Math.abs(z) == CUBE_SIZE / 2) {
                        Vec3 particlePos = position.add(x, y, z);
                        spawnSingleParticle(level, particlePos);
                        particleCount++;
                    }
                }
            }
        }
        System.out.println("Spawned " + particleCount + " particles at position: " + position);
    }

    private static void spawnSingleParticle(Level level, Vec3 position) {
        double velocityX = (Math.random() - 0.5) * 0.1;
        double velocityY = Math.random() * 0.2;
        double velocityZ = (Math.random() - 0.5) * 0.1;

        level.addParticle(ParticleTypes.WITCH,
                position.x, position.y, position.z,
                velocityX, velocityY, velocityZ);
    }

    public static void updateParticles(Level level, Vec3 position) {
        // Check for entities within the radius
        List<Entity> entities = level.getEntities((Entity) null, new AABB(
                position.x - RADIUS, position.y - RADIUS, position.z - RADIUS,
                position.x + RADIUS, position.y + RADIUS, position.z + RADIUS
        ));

        // If entities are found, remove particles
        if (!entities.isEmpty()) {
            removeParticles(level, position);
        }
    }

    private static void removeParticles(Level level, Vec3 position) {
        // Logic to remove particles (this is a placeholder, actual implementation may vary)
        System.out.println("Particles removed at position: " + position);
    }

    // Example method to be called in the tick or similar method
    public static void tickParticles(Level level, Vec3 position, int tickCount) throws InstantiationException, IllegalAccessException {
        if (tickCount % 2 == 0) { // Spawn particles every other tick
            spawnParticles(level, position);
        }

        if (tickCount >= PARTICLE_LIFESPAN) {
            removeParticles(level, position);
        } else {
            updateParticles(level, position);
        }
    }
}