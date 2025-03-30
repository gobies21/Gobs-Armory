package net.gobies.gobsarmory.particle;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class MaliciousScytheParticles {

    private static final int CUBE_SIZE = 3; // Size of the cube
    private static final int RADIUS = 8;  // Radius for particle disappearing
    private static final int PARTICLE_LIFESPAN = 60; // Number of ticks particles should fall

    public static void spawnParticles(Level level, Vec3 position) {
        if (level == null) {
            System.out.println("Level is null, cannot spawn particles.");
            return;
        }

        for (int x = -CUBE_SIZE / 2; x <= CUBE_SIZE / 2; x++) {
            for (int y = -CUBE_SIZE / 2; y <= CUBE_SIZE / 2; y++) {
                for (int z = -CUBE_SIZE / 2; z <= CUBE_SIZE / 2; z++) {
                    Vec3 particlePos = position.add(x, y, z);
                    System.out.println("Spawning particle at: " + particlePos);
                    spawnSingleParticle(level, particlePos);
                }
            }
        }
    }

    private static void spawnSingleParticle(Level level, Vec3 position) {
        // Schedule particles to fall over time
        level.addParticle(ParticleTypes.CRIT, position.x, position.y, position.z, 0, -0.05, 0);
    }

    public static void updateParticles(Level level, Vec3 position) throws InstantiationException, IllegalAccessException {
        // Check for entities within the radius
        List<Entity> entities = level.getEntities(Entity.class.newInstance(), new AABB(
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
        if (tickCount >= PARTICLE_LIFESPAN) {
            // Remove particles after a certain lifespan
            removeParticles(level, position);
        } else {
            // Update particles to check for entities
            updateParticles(level, position);
        }
    }
}