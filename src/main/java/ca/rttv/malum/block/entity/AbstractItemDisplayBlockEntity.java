package ca.rttv.malum.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractItemDisplayBlockEntity extends BlockEntity {
    private ItemStack heldItem = ItemStack.EMPTY;

    public AbstractItemDisplayBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public void swapSlots(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).isEmpty()) {
            world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
        }
        ItemStack prevItem = heldItem;
        heldItem = player.getStackInHand(hand);
        player.setStackInHand(hand, prevItem);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        NbtCompound nbtCompound = new NbtCompound();
        heldItem.writeNbt(nbtCompound);
        nbt.put("Item", nbtCompound);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        NbtCompound nbtCompound = nbt.getCompound("Item");
        heldItem = ItemStack.fromNbt(nbtCompound);
    }
}
