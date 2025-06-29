package net.gobies.gobsarmory.client;

import net.gobies.gobsarmory.Config;
import net.gobies.gobsarmory.item.weapons.MaliciousScytheItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MaliciousScytheRenderer {

    private static final ResourceLocation PROGRESS_BAR_LOCATION = new ResourceLocation("gobsarmory:textures/gui/progress_bar.png");
    private static final ResourceLocation FILL_BAR_LOCATION = new ResourceLocation("gobsarmory:textures/gui/fill_bar.png");


    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderGameOverlay(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LivingEntity entity = (LivingEntity) mc.getCameraEntity();
        if (entity instanceof Player player && mc.options.getCameraType().isFirstPerson()) {
            ItemStack mainHandItem = player.getMainHandItem();
            if (mainHandItem.getItem() instanceof MaliciousScytheItem) {
                CompoundTag tag = mainHandItem.getTag();
                int hitCount = tag != null ? tag.getInt("HitCount") : 0;
                int hitAmount = Config.MALICIOUS_SCYTHE_HIT_AMOUNT.get();

                int width = event.getWindow().getGuiScaledWidth();
                int height = event.getWindow().getGuiScaledHeight();

                int barWidth = Math.min((int) ((hitCount / (float) hitAmount) * 20), 20);
                int barHeight = 4;
                int x = width - 330;
                int y = height - 175;

                GuiGraphics guiGraphics = event.getGuiGraphics();
                guiGraphics.blit(PROGRESS_BAR_LOCATION, x, y, 0, 8, 20, barHeight, 20, 4);

                int middleBarHeight = 2;
                int middleBarStartY = y + (barHeight - middleBarHeight) / 2;

                int effectiveBarWidth = Math.min(barWidth, 20);


                if (effectiveBarWidth > 0) {
                    guiGraphics.blit(FILL_BAR_LOCATION, x, middleBarStartY, 0, 0, effectiveBarWidth, middleBarHeight, 20, 2);
                }
            }
        }
    }
}