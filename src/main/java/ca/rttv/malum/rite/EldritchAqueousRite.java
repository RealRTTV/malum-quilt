package ca.rttv.malum.rite;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class EldritchAqueousRite extends Rite {
    public EldritchAqueousRite(Item... items) {
        super(items);
    }

    @Override
    public void onTick(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick) { /* done */ }

    @Override
    public void onCorruptTick(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick) {
        if (tick % 80 != 0) {
            return;
        }

        BlockPos.iterateOutwards(pos.down(), 4, 0, 4).forEach(icePos -> {
            if (world.getFluidState(icePos).isOf(Fluids.WATER)) {
                world.setBlockState(icePos, Blocks.ICE.getDefaultState());
            }
        });
    }
}
