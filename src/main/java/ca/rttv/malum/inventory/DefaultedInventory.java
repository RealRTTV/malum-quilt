package ca.rttv.malum.inventory;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface DefaultedInventory extends Inventory {
    DefaultedList<ItemStack> getInvStackList();

    default void notifyListeners() {
        markDirty();

        if (getWorld() != null && !getWorld().isClient) {
            getWorld().updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    @Override
    default boolean canPlayerUse(PlayerEntity player) {
        return !(player.squaredDistanceTo(getPos().getX() + 0.5D, getPos().getY() + 0.5D, getPos().getZ() + 0.5D) > 64.0D);
    }

    @Override
    default int size() {
        return getInvStackList().size();
    }

    @Override
    default boolean isEmpty() {
        return getInvStackList().isEmpty();
    }

    @Override
    default ItemStack getStack(int slot) {
        return getInvStackList().get(slot);
    }

    @Override
    default ItemStack removeStack(int slot, int amount) {
        ItemStack stack = Inventories.splitStack(getInvStackList(), slot, amount);
        this.notifyListeners();
        return stack;
    }

    @Override
    default ItemStack removeStack(int slot) {
        ItemStack stack = Inventories.removeStack(getInvStackList(), slot);
        this.notifyListeners();
        return stack;
    }

    @Override
    default void setStack(int slot, ItemStack stack) {
        getInvStackList().set(slot, stack);
        this.notifyListeners();
    }

    @Override
    default void clear() {
        getInvStackList().clear();
        this.notifyListeners();
    }

    World getWorld();

    BlockPos getPos();

    BlockState getCachedState();
}
