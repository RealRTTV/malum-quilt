package ca.rttv.malum.duck;

import ca.rttv.malum.client.screen.page.BookEntry;
import ca.rttv.malum.network.packet.s2c.play.MalumParticleS2CPacket;

import java.util.List;

public interface MalumClientPlayPacketListener {
    void malum$onMagicParticle(MalumParticleS2CPacket packet);

    void malum$onProgressionBookEntries(List<BookEntry> entries);
}
