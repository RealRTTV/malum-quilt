package ca.rttv.malum.rite;

import ca.rttv.malum.network.packet.s2c.play.MalumParticleS2CPacket;
import ca.rttv.malum.util.spirit.SpiritType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.Random;
import java.util.stream.StreamSupport;

public class EldritchEarthenRite extends Rite {
    public EldritchEarthenRite(Item... items) {
        super(items);
    }

    @Override
    public void onTick(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick) {
        if (tick % 60 != 0) {
            return;
        }

        StreamSupport.stream(BlockPos.iterateOutwards(pos.down(), 2, 0, 2).spliterator(), false).filter(possiblePos -> !possiblePos.up().equals(pos) && world.getBlockState(possiblePos).isOf(world.getBlockState(pos.down()).getBlock())).forEach(possiblePos -> {
            world.breakBlock(possiblePos, true);
            world.getPlayers(players -> players.getWorld().isChunkLoaded(new ChunkPos(possiblePos).x, new ChunkPos(possiblePos).z)).forEach(players -> players.networkHandler.sendPacket(new MalumParticleS2CPacket<ClientPlayNetworkHandler>(SpiritType.EARTHEN_SPIRIT.color.getRGB(), possiblePos.getX() + 0.5d, possiblePos.getY() + 0.5d, possiblePos.getZ() + 0.5d)));
        });
    }

    @Override
    public void onCorruptTick(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick) {
        if (tick % 20 != 0) {
            return;
        }

        StreamSupport.stream(BlockPos.iterateOutwards(pos.down(), 2, 0, 2).spliterator(), false).filter(possiblePos -> !possiblePos.up().equals(pos) && world.isAir(possiblePos)).forEach(possiblePos -> {
            world.setBlockState(possiblePos, Blocks.COBBLESTONE.getDefaultState());
            world.getPlayers(players -> players.getWorld().isChunkLoaded(new ChunkPos(possiblePos).x, new ChunkPos(possiblePos).z)).forEach(players -> players.networkHandler.sendPacket(new MalumParticleS2CPacket<ClientPlayNetworkHandler>(SpiritType.EARTHEN_SPIRIT.color.getRGB(), possiblePos.getX() + 0.5d, possiblePos.getY() + 0.5d, possiblePos.getZ() + 0.5d)));
        });
    }
}
