package ca.rttv.malum.rite;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;

import java.util.Random;
import java.util.stream.StreamSupport;

public class EldritchAqueousRite extends Rite {
    public EldritchAqueousRite(Item... items) {
        super(items);
    }

    @Override
    public void onTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random, long tick) {
        if (tick % 40 != 0) {
            return;
        }

        StreamSupport.stream(BlockPos.iterateOutwards(pos, 8, 8, 8).spliterator(), false).filter(dripstonePos -> world.getBlockState(dripstonePos).getBlock() instanceof PointedDripstoneBlock).forEach(dripstonePos -> {
            if (world.random.nextFloat() <= 0.1f) {
                world.getBlockState(dripstonePos).randomTick(world, dripstonePos, world.random);
            }
        });
    }

    @Override
    public void onCorruptTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random, long tick) {
        if (tick % 100 != 0) {
            return;
        }

        StreamSupport.stream(BlockPos.iterateOutwards(pos.down(), 4, 0, 4).spliterator(), false).forEach(icePos -> {
            if (world.getFluidState(icePos).isOf(Fluids.WATER)) {
                world.setBlockState(icePos, Blocks.ICE.getDefaultState());
            } else if (world.getFluidState(icePos).isOf(Fluids.LAVA)) {
                world.setBlockState(icePos, Blocks.OBSIDIAN.getDefaultState());
            }
        });
    }
}
