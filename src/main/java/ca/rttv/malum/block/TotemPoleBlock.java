package ca.rttv.malum.block;

import ca.rttv.malum.block.entity.TotemBaseBlockEntity;
import ca.rttv.malum.block.entity.TotemPoleBlockEntity;
import ca.rttv.malum.item.spirit.MalumSpiritItem;
import ca.rttv.malum.util.spirit.SpiritTypeProperty;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static ca.rttv.malum.registry.MalumRegistry.TOTEM_POLE_BLOCK_ENTITY;

@SuppressWarnings("deprecation")
public class TotemPoleBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final EnumProperty<SpiritTypeProperty> SPIRIT_TYPE = EnumProperty.of("spirit_type", SpiritTypeProperty.class);
    public TotemPoleBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    public void onStrip(BlockState state, World world, BlockPos pos) {

    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.playSound(null, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 1.0f, 1.0f); // todo, proper noise
        TotemPoleBlockEntity blockEntity = (TotemPoleBlockEntity) world.getBlockEntity(pos);
        if (blockEntity == null || blockEntity.list == null) {
            return;
        }

        blockEntity.list.add(state.get(SPIRIT_TYPE).spirit.getSplinterItem());

        BlockPos up = pos.up();
        BlockState upState = world.getBlockState(up);
        if (upState.getBlock() instanceof TotemPoleBlock) {
            TotemPoleBlockEntity upEntity = (TotemPoleBlockEntity) world.getBlockEntity(up);
            if (upEntity == null) {
                return;
            }
            upEntity.list = blockEntity.list;
            world.scheduleBlockTick(up, world.getBlockState(up).getBlock(), 20);
        } else {
            BlockPos down = pos.down();
            while (down.getY() >= world.getBottomY()) {
                if (!(world.getBlockEntity(down) instanceof TotemBaseBlockEntity totemBaseBlockEntity)) {
                    down = down.down();
                    continue;
                }
                totemBaseBlockEntity.rite = TotemBaseBlockEntity.RITES.get(blockEntity.list.hashCode());
                totemBaseBlockEntity.tick = 0;
                world.playSound(null, down, SoundEvents.ITEM_HONEY_BOTTLE_DRINK, SoundCategory.BLOCKS, 1.0f, 1.0f); // todo, proper noise
                break;
            }
        }
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(MalumSpiritItem.POLE_BLOCKS.inverse().get(state.getBlock()));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos down = pos.down();
        BlockState downState = world.getBlockState(down);
        while (down.getY() >= world.getBottomY()) {
            if (downState.getBlock() instanceof TotemBaseBlock) {
                //noinspection ConstantConditions
                ((TotemBaseBlockEntity) world.getBlockEntity(down)).rite = null;
                super.onBreak(world, pos, state, player);
                return;
            }
            down = down.down();
            downState = world.getBlockState(down);
        }
        super.onBreak(world, pos, state, player);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TotemPoleBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, SPIRIT_TYPE);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, TOTEM_POLE_BLOCK_ENTITY, (world1, pos, state1, blockEntity) -> blockEntity.tick());
    }
}
