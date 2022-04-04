package ca.rttv.malum.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

@SuppressWarnings("deprecation")
public class SpiritAltarBlock extends Block implements Waterloggable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final VoxelShape SHAPE = VoxelShapes.union(
        Block.createCuboidShape(1.0d, 0d, 1.0d, 15.0d, 4.0d, 15.0d),
        Block.createCuboidShape(3.0d, 4.0d, 3.0d, 13.0d, 10.0d, 13.0d),
        Block.createCuboidShape(0d, 10.0d, 0d, 16.0d, 16.0d, 16.0d),
        Block.createCuboidShape(-2.0d, 9.0d, -2.0d, 3.0d, 17.0d, 3.0d),
        Block.createCuboidShape(-2.0d, 9.0d, 13.0d, 3.0d, 17.0d, 18.0d),
        Block.createCuboidShape(13.0d, 9.0d, -2.0d, 18.0d, 17.0d, 3.0d),
        Block.createCuboidShape(13.0d, 9.0d, 13.0d, 18.0d, 17.0d, 18.0d),
        Block.createCuboidShape(13.0d, 0d, 5.0d, 16.0d, 6.0d, 11.0d),
        Block.createCuboidShape(5.0d, 0d, 0d, 11.0d, 6.0d, 3.0d),
        Block.createCuboidShape(5.0d, 0d, 13.0d, 11.0d, 6.0d, 16.0d),
        Block.createCuboidShape(0d, 0d, 5.0d, 3.0d, 6.0d, 11.0d)
    );

    public SpiritAltarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());

        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighbourState, WorldAccess world, BlockPos pos, BlockPos neighbourPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighbourState, world, pos, neighbourPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }
}
