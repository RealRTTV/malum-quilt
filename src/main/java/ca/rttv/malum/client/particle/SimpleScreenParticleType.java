package ca.rttv.malum.client.particle;

import ca.rttv.malum.util.particle.screen.GenericScreenParticle;
import ca.rttv.malum.util.particle.screen.ScreenParticleEffect;
import ca.rttv.malum.util.particle.screen.ScreenParticleType;
import ca.rttv.malum.util.particle.screen.base.ScreenParticle;
import net.fabricmc.fabric.impl.client.particle.FabricSpriteProviderImpl;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;

public class SimpleScreenParticleType extends ScreenParticleType<ScreenParticleEffect> {

    public SimpleScreenParticleType() {
        super();
    }

    public static class Factory implements ScreenParticleType.Factory<ScreenParticleEffect> {
        public final SpriteProvider sprite;

        public Factory(SpriteProvider sprite) {
            this.sprite = sprite;
        }

        @Override
        public ScreenParticle createParticle(ClientWorld pLevel, ScreenParticleEffect options, double pX, double pY, double pXSpeed, double pYSpeed) {
            return new GenericScreenParticle(pLevel, options, (FabricSpriteProviderImpl) sprite, pX, pY, pXSpeed, pYSpeed);
        }
    }
}
