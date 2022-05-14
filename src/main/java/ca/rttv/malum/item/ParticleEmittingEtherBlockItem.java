package ca.rttv.malum.item;

import ca.rttv.malum.client.init.MalumScreenParticleRegistry;
import ca.rttv.malum.util.particle.Easing;
import ca.rttv.malum.util.particle.ParticleBuilders;
import ca.rttv.malum.util.particle.screen.base.ScreenParticle;
import ca.rttv.malum.util.particle.screen.emitter.ItemParticleEmitter;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.awt.*;

public class ParticleEmittingEtherBlockItem extends EtherBlockItem implements ItemParticleEmitter {
    public ParticleEmittingEtherBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        final MinecraftClient client = MinecraftClient.getInstance();
        World world = client.world;
        if (world == null) {
            return;
        }
        float gameTime = world.getTime() + client.getTickDelta();
        EtherBlockItem etherItem = (EtherBlockItem) stack.getItem();
        Color firstColor = new Color(etherItem.getFirstColor(stack));
        Color secondColor = new Color(etherItem.getSecondColor(stack));
        ParticleBuilders.create(MalumScreenParticleRegistry.STAR)
                .setAlpha(0.05f, 0f)
                .setLifetime(6)
                .setScale((float) (1.5f + Math.sin(gameTime * 0.1f) * 0.125f), 0)
                .setColor(firstColor, secondColor)
                .setColorCurveMultiplier(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset(0.025f * gameTime % 6.28f)
                .setSpin(0, 1)
                .setSpinEasing(Easing.EXPO_IN_OUT)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack, 0, 3)
                .repeat(x, y, 1)
                .setScale((float) (1.4f - Math.sin(gameTime * 0.075f) * 0.125f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f-0.01f * gameTime % 6.28f)
                .repeat(x, y, 1);
    }
}
