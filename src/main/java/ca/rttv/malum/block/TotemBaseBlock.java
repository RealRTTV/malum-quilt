package ca.rttv.malum.block;

import ca.rttv.malum.block.entity.TotemBaseBlockEntity;
import ca.rttv.malum.block.entity.TotemPoleBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.TOTEM_BASE_BLOCK_ENTITY;

@SuppressWarnings("deprecation")
public class TotemBaseBlock extends BlockWithEntity {
    public TotemBaseBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        //noinspection ConstantConditions
        return ((TotemBaseBlockEntity) world.getBlockEntity(pos)).onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        //noinspection ConstantConditions
        ((TotemBaseBlockEntity) world.getBlockEntity(pos)).scheduledTick(state, world, pos, random);
        world.scheduleBlockTick(pos, state.getBlock(), 1);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, state.getBlock(), 1);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos up = pos.up();
        BlockEntity blockEntity = world.getBlockEntity(up);
        while (up.getY() <= world.getTopY()) {
            if (blockEntity instanceof TotemPoleBlockEntity totemPoleBlockEntity) {
                totemPoleBlockEntity.setCachedBaseBlock(null);
                up = up.up();
                blockEntity = world.getBlockEntity(up);
                continue;
            }
            break;
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TotemBaseBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? checkType(type, TOTEM_BASE_BLOCK_ENTITY, (world1, pos, state1, blockEntity) -> blockEntity.clientTick(world1, pos, state1)) : null;
    }
}
