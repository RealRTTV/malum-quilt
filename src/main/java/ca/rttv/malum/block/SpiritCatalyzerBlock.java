package ca.rttv.malum.block;

import ca.rttv.malum.block.entity.SpiritCatalyzerBlockEntity;
import ca.rttv.malum.util.block.entity.ICrucibleAccelerator;
import net.minecraft.block.*;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
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

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.SPIRIT_CATALYZER_BLOCK_ENTITY;

@SuppressWarnings("deprecation")
public class SpiritCatalyzerBlock extends BlockWithEntity implements ICrucibleAccelerator {
    public static final float[] DAMAGE_CHANCES = new float[]{0f, 0.2f,  0.25f, 0.3f,  0.4f, 0.5f, 0.6f, 0.7f, 0.9f}; // chance to damage
    public static final int[] DAMAGE_MAX_VALUE = new   int[]{0,  1,     2,     2,     3,    3,    4,    5,    6   }; // damage max value
    public static final float[] SPEED_INCREASE = new float[]{0f, 0.25f, 0.5f,  0.75f, 1f,   1.5f, 2f,   3f,   8f  }; // speed increase
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final VoxelShape LOWER_Z = VoxelShapes.union(
            Block.createCuboidShape(0.0d, 0.0d, 0.0d, 6.0d, 4.0d, 6.0d),
            Block.createCuboidShape(10.0d, 0.0d, 10.0d, 16.0d, 4.0d, 16.0d),
            Block.createCuboidShape(0.0d, 0.0d, 10.0d, 6.0d, 4.0d, 16.0d),
            Block.createCuboidShape(10.0d, 0.0d, 0.0d, 16.0d, 4.0d, 6.0d),
            Block.createCuboidShape(6.0d, 0.0d, 1.0d, 10.0d, 3.0d, 15.0d),
            Block.createCuboidShape(1.0d, 0.0d, 5.0d, 15.0d, 16.0d, 11.0d),
            Block.createCuboidShape(4.0d, 0.0d, 3.0d, 12.0d, 16.0d, 13.0d),
            Block.createCuboidShape(4.0d, 0.0d, 3.0d, 12.0d, 16.0d, 13.0d)
    );
    public static final VoxelShape LOWER_X = VoxelShapes.union(
            Block.createCuboidShape(0.0d, 0.0d, 0.0d, 6.0d, 4.0d, 6.0d),
            Block.createCuboidShape(10.0d, 0.0d, 10.0d, 16.0d, 4.0d, 16.0d),
            Block.createCuboidShape(0.0d, 0.0d, 10.0d, 6.0d, 4.0d, 16.0d),
            Block.createCuboidShape(10.0d, 0.0d, 0.0d, 16.0d, 4.0d, 6.0d),
            Block.createCuboidShape(6.0d, 0.0d, 1.0d, 10.0d, 3.0d, 15.0d),
            Block.createCuboidShape(1.0d, 0.0d, 6.0d, 15.0d, 3.0d, 10.0d),
            Block.createCuboidShape(5.0d, 0.0d, 1.0d, 11.0d, 16.0d, 15.0d),
            Block.createCuboidShape(3.0d, 0.0d, 4.0d, 13.0d, 16.0d, 12.0d)
    );
    public static final VoxelShape UPPER_Z = VoxelShapes.union(
            Block.createCuboidShape(1.0d, 0.0d, 5.0d, 15.0d, 7.0d, 11.0d),
            Block.createCuboidShape(4.0d, 0.0d, 3.0d, 12.0d, 7.0d, 13.0d),
            Block.createCuboidShape(-1.0d, 5.0d, 3.0d, 17.0d, 10.0d, 13.0d),
            Block.createCuboidShape(2.0d, 7.0d, 1.0d, 14.0d, 11.0d, 15.0d)
    );
    public static final VoxelShape UPPER_X = VoxelShapes.union(
            Block.createCuboidShape(5.0d, 0.0d, 1.0d, 11.0d, 7.0d, 15.0d),
            Block.createCuboidShape(3.0d, 0.0d, 4.0d, 13.0d, 7.0d, 12.0d),
            Block.createCuboidShape(3.0d, 5.0d, -1.0d, 13.0d, 10.0d, 17.0d),
            Block.createCuboidShape(1.0d, 7.0d, 2.0d, 15.0d, 11.0d, 14.0d)
    );

    public SpiritCatalyzerBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
        ((SpiritCatalyzerBlockEntity) world.getBlockEntity(pos)).scheduledTick();
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            world.scheduleBlockTick(pos, state.getBlock(), 1);
        }
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
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SpiritCatalyzerBlockEntity) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
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
        builder.add(HALF, FACING);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? checkType(type, SPIRIT_CATALYZER_BLOCK_ENTITY, (world1, pos, state1, blockEntity) -> blockEntity.clientTick(world1, pos, state1)) : null;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            BlockPos up = pos.up();
            return ((SpiritCatalyzerBlockEntity) world.getBlockEntity(up)).onUse(world.getBlockState(up), world, up, player, hand, new BlockHitResult(hit.getType() == BlockHitResult.Type.MISS, hit.getPos().add(0, 1.0d, 0), hit.getSide(), hit.getBlockPos().up(), hit.isInsideBlock())); // stupid code lol
        }

        return ((SpiritCatalyzerBlockEntity) world.getBlockEntity(pos)).onUse(state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER).with(FACING, state.get(FACING)), 3);
        BlockPos.iterateOutwards(pos, 4, 2, 4).forEach(blockPos -> {

        });
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(HALF) == DoubleBlockHalf.LOWER ? ((state.get(FACING).getAxis() == Direction.Axis.Z) ? LOWER_Z : LOWER_X) : (state.get(FACING).getAxis() == Direction.Axis.Z ? UPPER_Z : UPPER_X);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
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
        return new SpiritCatalyzerBlockEntity(pos, state);
    }

    @Override
    public boolean canAccelerate(BlockPos pos, World world) {
        return AbstractFurnaceBlockEntity.canUseAsFuel(((SpiritCatalyzerBlockEntity) world.getBlockEntity(pos)).getHeldItem());
    }

    @Override
    public void tick(BlockPos pos, World world) {
        if (world.getBlockEntity(pos) instanceof SpiritCatalyzerBlockEntity spiritCatalyzerBlockEntity) {
            spiritCatalyzerBlockEntity.fuelTick();
        }
    }

    @Override
    public String name() {
        return "spirit_catalyzer";
    }
}
