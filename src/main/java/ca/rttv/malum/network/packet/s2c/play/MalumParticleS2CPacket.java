package ca.rttv.malum.network.packet.s2c.play;

import ca.rttv.malum.duck.MalumClientPlayPacketListener;
import ca.rttv.malum.util.spirit.SpiritType;
import io.netty.buffer.Unpooled;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import static ca.rttv.malum.Malum.MODID;

public record MalumParticleS2CPacket(int color, double x, double y, double z) implements Packet<ClientPlayPacketListener> {
    public MalumParticleS2CPacket(PacketByteBuf buf) {
        this(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public void write(PacketByteBuf buf) {
        buf.writeInt(color);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public void apply(ClientPlayPacketListener packetListener) {
        ((MalumClientPlayPacketListener) packetListener).malum$onMagicParticle(this);
    }

    public static void sendParticle(ServerWorld world, BlockPos pos, SpiritType spirit) {
        world.getPlayers(players -> players.getWorld().isChunkLoaded(new ChunkPos(pos).x, new ChunkPos(pos).z)).forEach(players -> {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            new MalumParticleS2CPacket(spirit.color.getRGB(), pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d).write(buf);
            ServerPlayNetworking.send(players, new Identifier(MODID, "malumparticles2cpacket"), buf);
        });
    }
}
