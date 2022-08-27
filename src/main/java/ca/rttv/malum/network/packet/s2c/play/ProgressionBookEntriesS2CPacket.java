package ca.rttv.malum.network.packet.s2c.play;

import ca.rttv.malum.client.screen.page.BookEntry;
import ca.rttv.malum.duck.MalumClientPlayPacketListener;
import com.mojang.serialization.DataResult;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.EncoderException;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;

import java.util.List;

public record ProgressionBookEntriesS2CPacket(List<BookEntry> entries) implements Packet<ClientPlayPacketListener> {
    public ProgressionBookEntriesS2CPacket(PacketByteBuf buf) {
        this(BookEntry.CODEC.listOf().parse(NbtOps.INSTANCE, buf.readUnlimitedNbt().get("Entries")).result().orElseThrow());
    }

    @Override
    public void write(PacketByteBuf buf) {
        DataResult<NbtElement> result = BookEntry.CODEC.listOf().encode(entries, NbtOps.INSTANCE, new NbtList());
        NbtCompound root = new NbtCompound();
        root.put("Entries", result.result().orElseThrow(() -> new EncoderException(result.error().orElseThrow().message())));
        buf.writeNbt(root);
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
