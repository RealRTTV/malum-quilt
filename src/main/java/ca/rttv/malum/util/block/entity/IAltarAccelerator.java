package ca.rttv.malum.util.block.entity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public interface IAltarAccelerator {
    public AltarAcceleratorType getAcceleratorType();
    public default boolean canAccelerate()
    {
        return true;
    }
    public float getAcceleration();

    default void addParticles(BlockPos altarPos, Vec3d altarItemPos) {

    }
    public default void addParticles(Color color, Color endColor, float alpha, BlockPos altarPos, Vec3d altarItemPos)
    {

    }
    public static class AltarAcceleratorType
    {
        public final int maximumEntries;
        public final String type;

        public AltarAcceleratorType(int maximumEntries, String type) {
            this.maximumEntries = maximumEntries;
            this.type = type;
        }
    }
}
