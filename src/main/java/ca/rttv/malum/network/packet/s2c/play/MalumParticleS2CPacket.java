package ca.rttv.malum.network.packet.s2c.play;

import ca.rttv.malum.duck.MalumClientPlayPacketListener;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.PacketListener;

public record MalumParticleS2CPacket<T extends PacketListener>(int color, double x, double y, double z) implements Packet<T> {
    public MalumParticleS2CPacket(PacketByteBuf buf) {
        this(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public void write(PacketByteBuf buf) {
        buf.writeInt(color);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public void apply(T packetListener) {
        ((MalumClientPlayPacketListener) packetListener).onMagicParticle(this);
    }
}
