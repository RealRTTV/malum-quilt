package ca.rttv.malum.client.particle;

import ca.rttv.malum.util.particle.world.GenericParticle;
import ca.rttv.malum.util.particle.world.WorldParticleEffect;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.impl.client.particle.FabricSpriteProviderImpl;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleType;
import org.jetbrains.annotations.Nullable;

public class SimpleMalumParticleType extends ParticleType<WorldParticleEffect> {
    public SimpleMalumParticleType() {
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
            return new GenericParticle(world, data, (FabricSpriteProviderImpl) sprite, x, y, z, mx, my, mz);
        }
    }
}
