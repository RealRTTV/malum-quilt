package ca.rttv.malum.rite;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class InfernalRite extends Rite {
    public InfernalRite(Item... items) {
        super(items);
    }

    @Override
    public void onUse(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick) {

    }

    @Override
    public void onCorruptUse(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick) {

    }
}
