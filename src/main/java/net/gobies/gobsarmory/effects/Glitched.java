package net.gobies.gobsarmory.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class Glitched extends MobEffect {

    public Glitched(MobEffectCategory category,int color) {
        super(category,color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide()) {
            float damage = 3.0f + (amplifier * 3.0f);
            entity.hurt(entity.damageSources().magic(), damage);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 40 == 0; // 40 ticks = 2 seconds
    }
}
