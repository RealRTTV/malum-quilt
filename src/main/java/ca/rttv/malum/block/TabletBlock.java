package ca.rttv.malum.block;

import ca.rttv.malum.block.entity.TabletBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.TABLET_BLOCK_ENTITY;

@SuppressWarnings("deprecation")
public class TabletBlock extends BlockWithEntity {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = Properties.FACING;
    public static final VoxelShape UP_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1.0d, 0.0d, 1.0d, 6.0d, 4.0d, 6.0d),
            Block.createCuboidShape(10.0d, 0.0d, 10.0d, 15.0d, 4.0d, 15.0d),
            Block.createCuboidShape(10.0d, 0.0d, 1.0d, 15.0d, 4.0d, 6.0d),
            Block.createCuboidShape(1.0d, 0.0d, 10.0d, 6.0d, 4.0d, 15.0d),
            Block.createCuboidShape(2.0d, 0.0d, 2.0d, 14.0d, 3.0d, 14.0d),
            Block.createCuboidShape(4.0d, 3.0d, 4.0d, 12.0d, 6.0d, 12.0d),
            Block.createCuboidShape(2.0d, 6.0d, 2.0d, 14.0d, 9.0d, 14.0d)
    );
    public static final VoxelShape DOWN_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1.0d, 12.0d, 1.0d, 6.0d, 16.0d, 6.0d),
            Block.createCuboidShape(10.0d, 12.0d, 10.0d, 15.0d, 16.0d, 15.0d),
            Block.createCuboidShape(10.0d, 12.0d, 1.0d, 15.0d, 16.0d, 6.0d),
            Block.createCuboidShape(1.0d, 12.0d, 10.0d, 6.0d, 16.0d, 15.0d),
            Block.createCuboidShape(2.0d, 13.0d, 2.0d, 14.0d, 16.0d, 14.0d),
            Block.createCuboidShape(4.0d, 10.0d, 4.0d, 12.0d, 13.0d, 12.0d),
            Block.createCuboidShape(2.0d, 7.0d, 2.0d, 14.0d, 10.0d, 14.0d)
    );
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1.0d, 1.0d, 12.0d, 6.0d, 6.0d, 16.0d),
            Block.createCuboidShape(10.0d, 1.0d, 12.0d, 15.0d, 6.0d, 16.0d),
            Block.createCuboidShape(1.0d, 10.0d, 12.0d, 6.0d, 15.0d, 16.0d),
            Block.createCuboidShape(10.0d, 10.0d, 12.0d, 15.0d, 15.0d, 16.0d),
            Block.createCuboidShape(2.0d, 2.0d, 13.0d, 14.0d, 14.0d, 16.0d),
            Block.createCuboidShape(4.0d, 4.0d, 10.0d, 12.0d, 12.0d, 13.0d),
            Block.createCuboidShape(2.0d, 2.0d, 7.0d, 14.0d, 14.0d, 10.0d)
    );
    public static final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0d, 1.0d, 1.0d, 4.0d, 6.0d, 6.0d),
            Block.createCuboidShape(0.0d, 1.0d, 10.0d, 4.0d, 6.0d, 15.0d),
            Block.createCuboidShape(0.0d, 10.0d, 10.0d, 4.0d, 15.0d, 15.0d),
            Block.createCuboidShape(0.0d, 10.0d, 1.0d, 4.0d, 15.0d, 6.0d),
            Block.createCuboidShape(0.0d, 2.0d, 2.0d, 3.0d, 14.0d, 14.0d),
            Block.createCuboidShape(3.0d, 4.0d, 4.0d, 6.0d, 12.0d, 12.0d),
            Block.createCuboidShape(6.0d, 2.0d, 2.0d, 9.0d, 14.0d, 14.0d)
    );
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1.0d, 1.0d, 0.0d, 6.0d, 6.0d, 4.0d),
            Block.createCuboidShape(10.0d, 1.0d, 0.0d, 15.0d, 6.0d, 4.0d),
            Block.createCuboidShape(1.0d, 10.0d, 0.0d, 6.0d, 15.0d, 4.0d),
            Block.createCuboidShape(10.0d, 10.0d, 0.0d, 15.0d, 15.0d, 4.0d),
            Block.createCuboidShape(2.0d, 2.0d, 0.0d, 14.0d, 14.0d, 3.0d),
            Block.createCuboidShape(4.0d, 4.0d, 3.0d, 12.0d, 12.0d, 6.0d),
            Block.createCuboidShape(2.0d, 2.0d, 6.0d, 14.0d, 14.0d, 9.0d)
    );
    public static final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(12.0d, 1.0d, 1.0d, 16.0d, 6.0d, 6.0d),
            Block.createCuboidShape(12.0d, 1.0d, 10.0d, 16.0d, 6.0d, 15.0d),
            Block.createCuboidShape(12.0d, 10.0d, 10.0d, 16.0d, 15.0d, 15.0d),
            Block.createCuboidShape(12.0d, 10.0d, 1.0d, 16.0d, 15.0d, 6.0d),
            Block.createCuboidShape(13.0d, 2.0d, 2.0d, 16.0d, 14.0d, 14.0d),
            Block.createCuboidShape(10.0d, 4.0d, 4.0d, 13.0d, 12.0d, 12.0d),
            Block.createCuboidShape(7.0d, 2.0d, 2.0d, 10.0d, 14.0d, 14.0d)
    );

    public TabletBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.DOWN));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        TabletBlockEntity blockEntity = (TabletBlockEntity) world.getBlockEntity(pos);
        blockEntity.swapSlots(state, world, pos, player, hand, hit);

        return ActionResult.CONSUME;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof TabletBlockEntity) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        Direction facing = ctx.getSide();

        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(FACING, facing);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighbourState, WorldAccess world, BlockPos pos, BlockPos neighbourPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighbourState, world, pos, neighbourPos);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? checkType(type, TABLET_BLOCK_ENTITY, (world1, pos, state1, blockEntity) -> blockEntity.clientTick(world1, pos, state1)) : null;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        return switch (direction) {
            case UP -> UP_SHAPE;
            case DOWN -> DOWN_SHAPE;
            case NORTH -> NORTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
        };
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TabletBlockEntity(pos, state);
    }
}
