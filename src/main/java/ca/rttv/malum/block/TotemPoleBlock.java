package ca.rttv.malum.block;

import ca.rttv.malum.block.entity.TotemBaseBlockEntity;
import ca.rttv.malum.block.entity.TotemPoleBlockEntity;
import ca.rttv.malum.item.SpiritItem;
import ca.rttv.malum.registry.MalumSoundRegistry;
import ca.rttv.malum.rite.Rite;
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
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Random;

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.TOTEM_POLE_BLOCK_ENTITY;

@SuppressWarnings("deprecation")
public class TotemPoleBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final EnumProperty<SpiritTypeProperty> SPIRIT_TYPE = EnumProperty.of("spirit_type", SpiritTypeProperty.class);
    public TotemPoleBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    public void onStrip(BlockState state, World world, BlockPos pos) {
        //noinspection ConstantConditions
        TotemBaseBlockEntity cachedBaseBlock = ((TotemPoleBlockEntity) world.getBlockEntity(pos)).getCachedBaseBlock();
        if (cachedBaseBlock != null) {
            cachedBaseBlock.rite = null;
        }
        world.playSound(null, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
        TotemPoleBlockEntity blockEntity = (TotemPoleBlockEntity) world.getBlockEntity(pos);
        if (blockEntity == null) {
            return;
        }

        if (blockEntity.list == null) {
            return;
        }

        // todo, fix it being ugly

        BlockPos up = pos.up();
        BlockState upState = world.getBlockState(up);
        if (upState.getBlock() instanceof TotemPoleBlock && upState.get(FACING) == state.get(FACING)) {
            TotemPoleBlockEntity upEntity = (TotemPoleBlockEntity) world.getBlockEntity(up);
            if (upEntity == null) {
                return;
            }
            blockEntity.list.add(state.get(SPIRIT_TYPE).spirit.getSplinterItem());
            upEntity.list = blockEntity.list;
            blockEntity.particles = true;
            blockEntity.updateListeners();
            world.scheduleBlockTick(up, world.getBlockState(up).getBlock(), 20);
            world.playSound(null, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, MalumSoundRegistry.TOTEM_CHARGE, SoundCategory.BLOCKS, 1.0f, 0.9f + world.random.nextFloat() * 0.2f);
        } else if (blockEntity.particles) {
            BlockPos down = pos.down();
            Rite rite = TotemBaseBlockEntity.RITES.get(blockEntity.list.hashCode());
            blockEntity.particles = (rite != null);
            blockEntity.updateListeners();
            while (down.getY() >= world.getBottomY()) {
                if (world.getBlockEntity(down) instanceof TotemBaseBlockEntity totemBaseBlockEntity) {
                    totemBaseBlockEntity.rite = rite;
                    blockEntity.list = null;
                    totemBaseBlockEntity.tick = 0;
                    if (totemBaseBlockEntity.rite != null) {
                        world.playSound(null, down, MalumSoundRegistry.TOTEM_ACTIVATED, SoundCategory.BLOCKS, 1.0f, 0.9f + world.random.nextFloat() * 0.2f);
                    } else {
                        world.playSound(null, down, MalumSoundRegistry.TOTEM_CANCELLED, SoundCategory.BLOCKS, 1.0f, 0.9f + world.random.nextFloat() * 0.2f);
                    }
                    totemBaseBlockEntity.updateListeners();
                    break;
                }
                if (world.getBlockEntity(down) instanceof TotemPoleBlockEntity totemPoleBlockEntity) {
                    totemPoleBlockEntity.list = null;
                    if (rite == null) {
                        totemPoleBlockEntity.particles = false;
                        totemPoleBlockEntity.updateListeners();
                    }
                }
                down = down.down();
            }
        } else {
            blockEntity.list.add(state.get(SPIRIT_TYPE).spirit.getSplinterItem());
            blockEntity.particles = true;
            blockEntity.updateListeners();
            world.scheduleBlockTick(pos, world.getBlockState(pos).getBlock(), 20);
            world.playSound(null, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, MalumSoundRegistry.TOTEM_CHARGE, SoundCategory.BLOCKS, 1.0f, 0.9f + world.random.nextFloat() * 0.2f);
        }
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return SpiritItem.POLE_BLOCKS.inverse().get(state.getBlock()).asItem().getDefaultStack();
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos down = pos.down();
        BlockState downState = world.getBlockState(down);
        BlockPos up = pos.up();
        BlockState upState = world.getBlockState(up);
        while (upState.getBlock() instanceof TotemPoleBlock) {
            TotemPoleBlockEntity blockEntity = Objects.requireNonNull((TotemPoleBlockEntity) world.getBlockEntity(up));
            blockEntity.particles = false;
            blockEntity.updateListeners();
            up = up.up();
            upState = world.getBlockState(up);
        }
        while (down.getY() >= world.getBottomY()) {
            if (downState.getBlock() instanceof TotemBaseBlock) {
                //noinspection ConstantConditions
                ((TotemBaseBlockEntity) world.getBlockEntity(down)).rite = null;
                super.onBreak(world, pos, state, player);
                return;
            } else if (downState.getBlock() instanceof TotemPoleBlock) {
                TotemPoleBlockEntity blockEntity = Objects.requireNonNull((TotemPoleBlockEntity) world.getBlockEntity(down));
                blockEntity.particles = false;
                blockEntity.updateListeners();
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
        return world.isClient ? checkType(type, TOTEM_POLE_BLOCK_ENTITY, (w, p, s, b) -> b.clientTick()) : null;
    }
}
