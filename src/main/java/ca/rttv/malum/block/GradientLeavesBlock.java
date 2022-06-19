package ca.rttv.malum.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;

public class GradientLeavesBlock extends LeavesBlock {
    public static final IntProperty COLOR = IntProperty.of("color", 0, 4);

    public GradientLeavesBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(COLOR, 0));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(COLOR);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(COLOR, 0);
    }
}
