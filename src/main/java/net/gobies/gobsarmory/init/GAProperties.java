package net.gobies.gobsarmory.init;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class GAProperties {
    public static void addItemProperties() {
        makeBow(GAItems.HyperwaveBow.get());
        makePull(GAItems.HyperwaveBow.get());
    }


    private static void makeBow(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (stack, clientLevel, livingEntity, value) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
    }

    private static void makePull(Item item) {
        ItemProperties.register(item, new ResourceLocation("pulling"), (stack, clientLevel, livingEntity, value)
                -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F);
    }
}