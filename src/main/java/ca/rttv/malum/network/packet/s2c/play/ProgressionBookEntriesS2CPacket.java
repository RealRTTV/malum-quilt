package ca.rttv.malum.network.packet.s2c.play;

import ca.rttv.malum.client.screen.page.BookEntry;
import ca.rttv.malum.duck.MalumClientPlayPacketListener;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;

import java.util.ArrayList;
import java.util.List;

public record ProgressionBookEntriesS2CPacket(List<BookEntry> entries) implements Packet<ClientPlayPacketListener> {
    public ProgressionBookEntriesS2CPacket(PacketByteBuf buf) {
        this(BookEntry.CODEC.listOf().parse(NbtOps.INSTANCE, buf.readUnlimitedNbt()).result().orElse(new ArrayList<>()));
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeNbt((NbtCompound) BookEntry.CODEC.listOf().encode(entries, NbtOps.INSTANCE, new NbtCompound()).result().orElse(new NbtCompound()));
    }

    public PacketByteBuf toBuf() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        write(buf);
        return buf;
    }

    @Override
    public void apply(ClientPlayPacketListener listener) {
        ((MalumClientPlayPacketListener) listener).malum$onProgressionBookEntries(entries);
    }
}
