package ca.rttv.malum.util.particle.world;

import ca.rttv.malum.util.particle.SimpleParticleEffect;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;

import java.util.Locale;

public class WorldParticleEffect extends SimpleParticleEffect implements ParticleEffect {

    public ParticleType<?> type;
    public Vec3f startingMotion = Vec3f.ZERO, endingMotion = Vec3f.ZERO;
    public WorldParticleEffect(ParticleType<?> type) {
        this.type = type;
    }

    public WorldParticleEffect(Float sx, Float sy, Float sz) {
        startingMotion.set(sx, sy, sz);
    }

    @Override
    public ParticleType<?> getType() {
        return type;
    }

    public static final Codec<WorldParticleEffect> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(Codec.FLOAT.fieldOf("sx").forGetter((worldParticleEffect) -> worldParticleEffect.startingMotion.getX()), Codec.FLOAT.fieldOf("sy").forGetter((worldParticleEffect) -> worldParticleEffect.startingMotion.getY()), Codec.FLOAT.fieldOf("zy").forGetter((worldParticleEffect) -> worldParticleEffect.startingMotion.getZ())).apply(instance, WorldParticleEffect::new));
    public static final ParticleEffect.Factory<WorldParticleEffect> PARAMETERS_FACTORY = new ParticleEffect.Factory<>() {
        public WorldParticleEffect read(ParticleType<WorldParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            float sx = (float) stringReader.readDouble();
            stringReader.expect(' ');
            float sy = (float) stringReader.readDouble();
            stringReader.expect(' ');
            float sz = (float) stringReader.readDouble();
            return new WorldParticleEffect(sx, sy, sz);
        }

        public WorldParticleEffect read(ParticleType<WorldParticleEffect> particleType, PacketByteBuf packetByteBuf) {
            return new WorldParticleEffect(packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat());
        }
    };

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeFloat(this.startingMotion.getX());
        buf.writeFloat(this.startingMotion.getY());
        buf.writeFloat(this.startingMotion.getZ());
    }

    public String asString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getId(this.getType()), this.startingMotion.getX(), this.startingMotion.getY(), this.startingMotion.getZ());
    }
    public static final Factory<WorldParticleEffect> DESERIALIZER = new Factory<>() {
        @Override
        public WorldParticleEffect read(ParticleType<WorldParticleEffect> type, StringReader reader) {
            return new WorldParticleEffect(type);
        }

        @Override
        public WorldParticleEffect read(ParticleType<WorldParticleEffect> type, PacketByteBuf buf) {
            return new WorldParticleEffect(type);
        }
    };
}
