package ca.rttv.malum.util.block.entity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public interface IAltarAccelerator {
    AltarAcceleratorType getAcceleratorType();
    default boolean canAccelerate()
    {
        return true;
    }
    float getAcceleration();

    default void addParticles(BlockPos altarPos, Vec3d altarItemPos) {

    }
    default void addParticles(Color color, Color endColor, float alpha, BlockPos altarPos, Vec3d altarItemPos)
    {

    }

    record AltarAcceleratorType(int maximumEntries, String type) {
    }
}
