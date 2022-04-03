package ca.rttv.malum.block.entity;

import ca.rttv.malum.registry.MalumRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class ItemStandBlockEntity extends AbstractItemDisplayBlockEntity {
    public ItemStandBlockEntity(BlockPos pos, BlockState state) {
        this(MalumRegistry.ITEM_STAND_BLOCK_ENTITY, pos, state);
    }

    public ItemStandBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public Vec3d itemOffset() {
        Direction direction = this.getCachedState().get(Properties.FACING);
        Vec3d directionVector = new Vec3d(direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ());
        return new Vec3d(0.5f - directionVector.getX() * 0.25f, 0.5f - directionVector.getY() * 0.1f, 0.5f - directionVector.getZ() * 0.25f);
    }

}
