package ca.rttv.malum.block.entity;

import ca.rttv.malum.registry.MalumRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ItemPedestalBlockEntity extends AbstractItemDisplayBlockEntity {
    public ItemPedestalBlockEntity(BlockPos pos, BlockState state) {
        this(MalumRegistry.ITEM_PEDESTAL_BLOCK_ENTITY, pos, state);
    }

    public ItemPedestalBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
    public Vec3d itemOffset() {
        return new Vec3d(0.5f, 1.1f, 0.5f);
    }

}
