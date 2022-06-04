package ca.rttv.malum.duck;

import ca.rttv.malum.network.packet.s2c.play.MalumParticleS2CPacket;

public interface MalumClientPlayPacketListener {
    void malum$onMagicParticle(MalumParticleS2CPacket packet);
}
