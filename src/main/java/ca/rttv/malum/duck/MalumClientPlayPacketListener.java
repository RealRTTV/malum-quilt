package ca.rttv.malum.duck;

import ca.rttv.malum.network.packet.s2c.play.MalumParticleS2CPacket;
import net.minecraft.network.listener.PacketListener;

public interface MalumClientPlayPacketListener {
    <T extends PacketListener> void onMagicParticle(MalumParticleS2CPacket<T> packet);
}
