package ca.rttv.malum.client.particle.emitter;

import ca.rttv.malum.item.SpiritItem;
import com.sammy.lodestone.systems.rendering.particle.screen.base.ScreenParticle;
import com.sammy.lodestone.systems.rendering.particle.screen.emitter.ItemParticleEmitter;
import net.minecraft.item.ItemStack;

import static ca.rttv.malum.util.helper.SpiritHelper.spawnSpiritScreenParticles;

public class SpiritParticleEmitter implements ItemParticleEmitter {
    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        spawnSpiritScreenParticles(((SpiritItem) stack.getItem()).type.color, ((SpiritItem) stack.getItem()).type.endColor, stack, x, y, renderOrder);
    }
}
