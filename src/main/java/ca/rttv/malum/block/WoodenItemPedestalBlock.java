package ca.rttv.malum.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class WoodenItemPedestalBlock extends AbstractItemPedestalBlock {
    private static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(4.0d, 0.0d, 4.0d, 12.0d, 3.0d, 12.0d),
            Block.createCuboidShape(5.0d, 3.0d, 5.0d, 11.0d, 11.0d, 11.0d),
            Block.createCuboidShape(4.0d, 11.0d, 4.0d, 12.0d, 13.0d, 12.0d)
    );

    public WoodenItemPedestalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
