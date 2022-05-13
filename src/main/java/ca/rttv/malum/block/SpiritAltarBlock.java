package ca.rttv.malum.block;

import ca.rttv.malum.block.entity.SpiritAltarBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
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

import java.util.Random;

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.SPIRIT_ALTAR_BLOCK_ENTITY;

@SuppressWarnings("deprecation")
public class SpiritAltarBlock extends BlockWithEntity implements Waterloggable {

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
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        ((SpiritAltarBlockEntity) world.getBlockEntity(pos)).scheduledTick(state, world, pos, random);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock()) && !world.isClient) {
            world.scheduleBlockTick(pos, this, 1);
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SpiritAltarBlockEntity) {
                ItemScatterer.spawn(world, pos, (Inventory)blockEntity);
                ItemScatterer.spawn(world, pos, ((SpiritAltarBlockEntity) blockEntity).spiritSlots);
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

        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighbourState, WorldAccess world, BlockPos pos, BlockPos neighbourPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighbourState, world, pos, neighbourPos);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        return ((SpiritAltarBlockEntity) world.getBlockEntity(pos)).onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? checkType(type, SPIRIT_ALTAR_BLOCK_ENTITY, (world1, pos, state1, blockEntity) -> blockEntity.clientTick(world1, pos, state1)) : null;
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SpiritAltarBlockEntity(pos, state);
    }
}
