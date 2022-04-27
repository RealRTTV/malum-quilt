package ca.rttv.malum.util.particle.screen.emitter;

import ca.rttv.malum.util.particle.screen.base.ScreenParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;

public interface ItemParticleEmitter {
    @Environment(EnvType.CLIENT)
    void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder);
}
