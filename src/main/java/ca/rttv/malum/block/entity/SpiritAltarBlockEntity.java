package ca.rttv.malum.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.registry.MalumRegistry.SPIRIT_ALTAR_BLOCK_ENTITY;

public class SpiritAltarBlockEntity extends BlockEntity implements Inventory {

    public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public SpiritAltarBlockEntity(BlockPos pos, BlockState state) {
        this(SPIRIT_ALTAR_BLOCK_ENTITY, pos, state);
    }

    public SpiritAltarBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        this.swapSlots(state, world, pos, player, hand, hit);
        this.getReagents(state, world, pos).forEach((item, count) -> System.out.println(item + " " + count));
        return ActionResult.CONSUME;
    }

    public Map<Item, Integer> getReagents(BlockState state, World world, BlockPos pos) {
        Map<Item, Integer> map = new LinkedHashMap<>();
        BlockPos.iterate(pos.getX() - 4, pos.getY() - 2, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 2, pos.getZ() + 4).forEach(reagentPosition -> {
            if (world.getBlockEntity(reagentPosition) instanceof AbstractItemDisplayBlockEntity displayBlock && !displayBlock.getHeldItem().isEmpty()) {
                Item key = displayBlock.getHeldItem().getItem();
                Integer value = displayBlock.getHeldItem().getCount();
                if (!map.containsKey(key)) {
                    map.put(key, value);
                } else {
                    map.put(key, value + map.get(key));
                }
            }
        });
        return map;
    }

    public void notifyListeners() {
        this.markDirty();

        if(world != null && !world.isClient())
            world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    public void swapSlots(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).isEmpty() && this.getHeldItem().isEmpty()) {
            return;
        }

        if (!this.getHeldItem().isEmpty()) {
            world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
        }

        ItemStack prevItem = getHeldItem();
        setStack(0, player.getStackInHand(hand));
        player.setStackInHand(hand, prevItem);
    }

    public ItemStack getHeldItem() {
        return this.getStack(0);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound tag = super.toInitialChunkDataNbt();
        writeNbt(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.of(this);
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = Inventories.splitStack(this.inventory, slot, amount);
        this.notifyListeners();
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        this.notifyListeners();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return !(player.squaredDistanceTo(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) > 64.0D);
    }

    @Override
    public void clear() {
        this.inventory.clear();
        this.notifyListeners();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.inventory.clear();
        Inventories.readNbt(nbt, this.inventory);
        super.readNbt(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, this.inventory);
        super.writeNbt(nbt);
    }
}
