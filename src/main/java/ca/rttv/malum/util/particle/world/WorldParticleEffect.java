package ca.rttv.malum.util.particle.world;

import ca.rttv.malum.util.particle.SimpleParticleEffect;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.math.Vec3f;

public class WorldParticleEffect extends SimpleParticleEffect implements ParticleEffect {

    ParticleType<?> type;
    public Vec3f startingMotion = Vec3f.ZERO, endingMotion = Vec3f.ZERO;
    public WorldParticleEffect(ParticleType<?> type) {
        this.type = type;
    }
    @Override
    public ParticleType<?> getType() {
        return type;
    }

    @Override
    public void write(PacketByteBuf buf) {

    }

    @Override
    public String asString() {
        return "";
    }
    public static final Factory<WorldParticleEffect> DESERIALIZER = new Factory<>() {
        @Override
        public WorldParticleEffect read(ParticleType<WorldParticleEffect> type, StringReader reader) throws CommandSyntaxException {
            return new WorldParticleEffect(type);
        }

        @Override
        public WorldParticleEffect read(ParticleType<WorldParticleEffect> type, PacketByteBuf buf) {
            return new WorldParticleEffect(type);
        }
    };
}
