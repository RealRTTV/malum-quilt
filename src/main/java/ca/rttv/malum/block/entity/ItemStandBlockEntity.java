package ca.rttv.malum.block.entity;

import ca.rttv.malum.RegistryEntries;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class ItemStandBlockEntity extends AbstractItemDisplayBlockEntity {
    public ItemStandBlockEntity(BlockPos pos, BlockState state) {
        this(RegistryEntries.ITEM_STAND_BLOCK_ENTITY, pos, state);
    }

    public ItemStandBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
}
