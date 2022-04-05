package ca.rttv.malum.client.particle;

import ca.rttv.malum.util.particle.world.WorldParticleEffect;
import com.mojang.serialization.Codec;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class SimpleMalumParticleType extends ParticleType<WorldParticleEffect> {

    protected SimpleMalumParticleType(boolean bl, ParticleEffect.Factory<WorldParticleEffect> factory) {
        super(bl, factory);
    }

    @Override
    public Codec<WorldParticleEffect> getCodec() {
        return null;
    }
}
