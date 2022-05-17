package ca.rttv.malum.util.block.entity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ICrucibleAccelerator {
    boolean canAccelerate(BlockPos pos, World world);

    void tick(BlockPos pos, World world);

    String name();
}
