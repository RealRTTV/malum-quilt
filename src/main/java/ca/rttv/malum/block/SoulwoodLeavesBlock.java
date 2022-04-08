package ca.rttv.malum.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;

public class SoulwoodLeavesBlock extends LeavesBlock { // todo make abstract leaves regardless of wood type
    public static final IntProperty COLOR = IntProperty.of("color", 0, 4);

    public SoulwoodLeavesBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT, COLOR);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(COLOR, 0);
    }
}
