package ca.rttv.malum.block;

import ca.rttv.malum.block.entity.SpiritCrucibleBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.SPIRIT_CRUCIBLE_BLOCK_ENTITY;

@SuppressWarnings("deprecation")
public class SpiritCrucibleBlock extends BlockWithEntity implements Waterloggable {
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final VoxelShape LOWER = VoxelShapes.union(Block.createCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 12.0d, 16.0d),
            Block.createCuboidShape(2.0d, 12.0d, 2.0d, 14.0d, 16.0d, 14.0d));
    public static final VoxelShape UPPER = VoxelShapes.union(Block.createCuboidShape(0.0d, 0.0d, 0.0d, 16.0d, 4.0d, 16.0d),
            Block.createCuboidShape(5.0d, -2.0d, -2.0d, 11.0d, 6.0d, 4.0d),
            Block.createCuboidShape(12.0d, -2.0d, 5.0d, 18.0d, 6.0d, 11.0d),
            Block.createCuboidShape(-2.0d, -2.0d, 5.0d, 4.0d, 6.0d, 11.0d),
            Block.createCuboidShape(5.0d, -2.0d, 12.0d, 11.0d, 6.0d, 18.0d));

    public SpiritCrucibleBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        Direction direction = Direction.fromVector(fromPos.subtract(pos));
        if (!world.isAir(fromPos)) {
            return;
        }
        if (direction == Direction.UP && state.get(HALF) == DoubleBlockHalf.LOWER || direction == Direction.DOWN && state.get(HALF) == DoubleBlockHalf.UPPER) {
            world.breakBlock(pos, false);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (direction.getAxis() != Direction.Axis.Y
                || doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP)
                || neighborState.isOf(this) && neighborState.get(HALF) != doubleBlockHalf) {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        } else {
            return Blocks.AIR.getDefaultState();
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HALF, WATERLOGGED);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, state.getBlock(), 1);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            return;
        }

        ((SpiritCrucibleBlockEntity) world.getBlockEntity(pos)).scheduledTick(state, world, pos, random);

        world.scheduleBlockTick(pos, state.getBlock(), 1);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? checkType(type, SPIRIT_CRUCIBLE_BLOCK_ENTITY, (world1, pos, state1, blockEntity) -> blockEntity.clientTick(world1, pos, state1)) : null;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            BlockPos up = pos.up();
            return ((SpiritCrucibleBlockEntity) world.getBlockEntity(up)).onUse(world.getBlockState(up), world, up, player, hand, new BlockHitResult(hit.getType() == BlockHitResult.Type.MISS, hit.getPos().add(0, 1.0d, 0), hit.getSide(), hit.getBlockPos().up(), hit.isInsideBlock())); // stupid code lol
        }

        return ((SpiritCrucibleBlockEntity) world.getBlockEntity(pos)).onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SpiritCrucibleBlockEntity) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER).with(WATERLOGGED, world.getFluidState(pos.up()).getFluid() == Fluids.WATER), 3);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(HALF) == DoubleBlockHalf.LOWER ? LOWER : UPPER;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF) != DoubleBlockHalf.UPPER) {
            return world.isAir(pos.up());
        } else {
            BlockState blockState = world.getBlockState(pos.down());
            return blockState.isOf(this) && blockState.get(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SpiritCrucibleBlockEntity(pos, state);
    }
}
