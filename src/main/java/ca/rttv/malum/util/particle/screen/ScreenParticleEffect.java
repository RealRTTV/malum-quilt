package ca.rttv.malum.util.particle.screen;

import ca.rttv.malum.util.particle.SimpleParticleEffect;
import ca.rttv.malum.util.particle.screen.base.ScreenParticle;
import net.minecraft.item.ItemStack;

public class ScreenParticleEffect extends SimpleParticleEffect {

    public final ScreenParticleType<?> type;
    public ScreenParticle.RenderOrder renderOrder;
    public ItemStack stack;
    public float xOrigin;
    public float yOrigin;
    public float xOffset;
    public float yOffset;

    public ScreenParticleEffect(ScreenParticleType<?> type) {
        this.type = type;
    }
}
