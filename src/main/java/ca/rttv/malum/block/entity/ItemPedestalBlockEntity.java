package ca.rttv.malum.block.entity;

import ca.rttv.malum.RegistryEntries;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class ItemPedestalBlockEntity extends AbstractItemDisplayBlockEntity {
    public ItemPedestalBlockEntity(BlockPos pos, BlockState state) {
        this(RegistryEntries.ITEM_PEDESTAL_BLOCK_ENTITY, pos, state);
    }

    public ItemPedestalBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
}
