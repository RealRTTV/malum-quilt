package ca.rttv.malum.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class EtherBlock extends AbstractEtherBlock {
    public static final VoxelShape SHAPE = Block.createCuboidShape(6.0d, 6.0d, 6.0d, 10.0d, 10.0d, 10.0d);

    public EtherBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
