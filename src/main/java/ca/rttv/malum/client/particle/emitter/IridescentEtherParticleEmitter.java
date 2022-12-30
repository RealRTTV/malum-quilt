package ca.rttv.malum.client.particle.emitter;

import ca.rttv.malum.item.IridescentEtherBlockItem;
import com.sammy.lodestone.setup.LodestoneScreenParticles;
import com.sammy.lodestone.systems.rendering.particle.Easing;
import com.sammy.lodestone.systems.rendering.particle.ParticleBuilders;
import com.sammy.lodestone.systems.rendering.particle.screen.base.ScreenParticle;
import com.sammy.lodestone.systems.rendering.particle.screen.emitter.ItemParticleEmitter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.awt.*;

public class IridescentEtherParticleEmitter implements ItemParticleEmitter {
    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        final MinecraftClient client = MinecraftClient.getInstance();
        World world = client.world;
        if (world == null) {
            return;
        }
        double gameTime = world.getTime() + client.getTickDelta();
        IridescentEtherBlockItem etherItem = (IridescentEtherBlockItem) stack.getItem();
        Color firstColor = new Color(etherItem.getFirstColor(stack));
        Color secondColor = new Color(etherItem.getSecondColor(stack));
        ParticleBuilders.create(LodestoneScreenParticles.STAR)
                .setAlpha(0.05f * 1.5f, 0f)
                .setLifetime(6)
                .setScale((float) (1.5f + Math.sin(gameTime * 0.1f) * 0.125f), 0)
                .setColor(firstColor, secondColor)
                .setColorCoefficient(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset((float) (0.025d * gameTime % 6.28d))
                .setSpin(0, 1)
                .setSpinEasing(Easing.EXPO_IN_OUT)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .overrideRenderOrder(renderOrder)
                .centerOnStack(stack, -1, 3)
                .repeat(x, y, 1)
                .setScale((float) (1.4f - Math.sin(gameTime * 0.075f) * 0.125f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset((float) (0.785d - (0.01d * gameTime % 6.28d)))
                .repeat(x, y, 1);
    }
}
