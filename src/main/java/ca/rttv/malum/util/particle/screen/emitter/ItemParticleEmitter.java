package ca.rttv.malum.util.particle.screen.emitter;

import ca.rttv.malum.util.particle.screen.base.ScreenParticle;
import net.minecraft.item.ItemStack;

public interface ItemParticleEmitter {
        void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder);
}
