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

public class SimpleMalumParticleType extends ParticleType<WorldParticleEffect> {
    public SimpleMalumParticleType() {
        super(false, WorldParticleEffect.DESERIALIZER);
    }

    @Override
    public Codec<WorldParticleEffect> getCodec() {
        return WorldParticleEffect.codecFor(this);
    }

        public record Factory(SpriteProvider sprite) implements ParticleFactory<WorldParticleEffect> {
        @Override
        public Particle createParticle(WorldParticleEffect data, ClientWorld world, double x, double y, double z, double mx, double my, double mz) {
            return new GenericParticle(world, data, (FabricSpriteProviderImpl) sprite, x, y, z, mx, my, mz);
        }
    }
}
