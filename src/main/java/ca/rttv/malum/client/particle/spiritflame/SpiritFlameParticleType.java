package ca.rttv.malum.client.particle.spiritflame;


import ca.rttv.malum.util.particle.world.WorldParticleEffect;
import com.mojang.serialization.Codec;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleType;

import javax.annotation.Nullable;

public class SpiritFlameParticleType extends ParticleType<WorldParticleEffect> {

    public SpiritFlameParticleType() {
        super(false, WorldParticleEffect.DESERIALIZER);
    }

    @Override
    public Codec<WorldParticleEffect> getCodec() {
        return WorldParticleEffect.CODEC;
    }

    public static class Factory implements ParticleFactory<WorldParticleEffect> {
        private final SpriteProvider sprite;

        public Factory(SpriteProvider sprite) {
            this.sprite = sprite;
        }


        @Nullable
        @Override
        public Particle createParticle(WorldParticleEffect data, ClientWorld world, double x, double y, double z, double mx, double my, double mz) {
            return new SpiritFlameParticle(world, data, (ParticleManager.SimpleSpriteProvider) sprite, x, y, z, mx, my, mz);
        }
    }
}
