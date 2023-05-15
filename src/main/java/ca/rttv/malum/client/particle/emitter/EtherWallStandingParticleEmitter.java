package ca.rttv.malum.client.particle.emitter;

import ca.rttv.malum.client.init.MalumScreenParticleRegistry;
import ca.rttv.malum.item.EtherWallStandingBlockItem;
import ca.rttv.malum.util.particle.Easing;
import ca.rttv.malum.util.particle.ParticleBuilders;
import ca.rttv.malum.util.particle.screen.base.ScreenParticle;
import ca.rttv.malum.util.particle.screen.emitter.ItemParticleEmitter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.awt.*;

public class EtherWallStandingParticleEmitter implements ItemParticleEmitter {
    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        final MinecraftClient client = MinecraftClient.getInstance();
        World world = client.world;
        if (world == null) {
            return;
        }
        float gameTime = world.getTime() % 710 + client.getTickDelta();
        Color firstColor = new Color(EtherWallStandingBlockItem.getFirstColor(stack));
        Color secondColor = new Color(EtherWallStandingBlockItem.getSecondColor(stack));
        ParticleBuilders.create(MalumScreenParticleRegistry.STAR)
                .setAlpha(0.06f, 0f)
                .setLifetime(7)
                .setScale((float) (0.75f + Math.sin(gameTime * 0.05f) * 0.125f), 0)
                .setColor(firstColor, secondColor)
                .setColorCurveMultiplier(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset(0.025f * gameTime)
                .setSpin(0, 1)
                .setSpinEasing(Easing.EXPO_IN_OUT)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack, 0, -1)
                .repeat(x, y, 1)
                .setScale((float) (0.75f - Math.sin(gameTime * 0.075f) * 0.125f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f-0.01f * gameTime)
                .repeat(x, y, 1);
    }
}
