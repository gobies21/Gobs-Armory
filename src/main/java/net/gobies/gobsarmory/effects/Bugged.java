package net.gobies.gobsarmory.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class Bugged extends MobEffect {

    public Bugged(MobEffectCategory category, int color) {
        super(category,color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide()) {
            float maxHealth = entity.getMaxHealth();
            float damage = maxHealth * 0.025f * (amplifier + 1);
            entity.hurt(entity.damageSources().magic(), damage);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 10 == 0; // 40 ticks = 2 seconds
    }
}
