package ca.rttv.malum.rite;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;

import java.util.Random;

public class TransRite extends Rite {
    public TransRite(Item... items) {
        super(items);
    }

    @Override
    public void onTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random, long tick) { /* done */ }

    @Override
    public void onCorruptTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random, long tick) { /* done */ }
}
