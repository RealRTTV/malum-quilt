package ca.rttv.malum.rite;

import ca.rttv.malum.network.packet.s2c.play.MalumParticleS2CPacket;
import ca.rttv.malum.util.spirit.SpiritType;
import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.random.RandomGenerator;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.stream.StreamSupport;

import static ca.rttv.malum.Malum.MODID;

public class EldritchEarthenRite extends Rite {
    public EldritchEarthenRite(Item... items) {
        super(items);
    }

    @Override
    public void onTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random, long tick) {
        if (tick % 60 != 0) {
            return;
        }

        StreamSupport.stream(BlockPos.iterateOutwards(pos.down(), 2, 0, 2).spliterator(), false).filter(possiblePos -> !possiblePos.up().equals(pos) && world.getBlockState(possiblePos).isOf(world.getBlockState(pos.down()).getBlock())).forEach(possiblePos -> {
            world.breakBlock(possiblePos, true);
            world.getPlayers(players -> players.getWorld().isChunkLoaded(new ChunkPos(possiblePos).x, new ChunkPos(possiblePos).z)).forEach(players -> {
                PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                new MalumParticleS2CPacket(SpiritType.EARTHEN_SPIRIT.color.getRGB(), possiblePos.getX() + 0.5d, possiblePos.getY() + 0.5d, possiblePos.getZ() + 0.5d).write(buf);
                ServerPlayNetworking.send(players, new Identifier(MODID, "malumparticles2cpacket"), buf);
            });
        });
    }

    @Override
    public void onCorruptTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random, long tick) {
        if (tick % 20 != 0) {
            return;
        }

        StreamSupport.stream(BlockPos.iterateOutwards(pos.down(), 2, 0, 2).spliterator(), false).filter(possiblePos -> !possiblePos.up().equals(pos) && world.isAir(possiblePos)).forEach(possiblePos -> {
            world.setBlockState(possiblePos, Blocks.COBBLESTONE.getDefaultState());
            world.getPlayers(players -> players.getWorld().isChunkLoaded(new ChunkPos(possiblePos).x, new ChunkPos(possiblePos).z)).forEach(players -> {
                PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                new MalumParticleS2CPacket(SpiritType.EARTHEN_SPIRIT.color.getRGB(), possiblePos.getX() + 0.5d, possiblePos.getY() + 0.5d, possiblePos.getZ() + 0.5d).write(buf);
                ServerPlayNetworking.send(players, new Identifier(MODID, "malumparticles2cpacket"), buf);
            });
        });
    }
}
