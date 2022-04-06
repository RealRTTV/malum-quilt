package ca.rttv.malum.block.entity;

import ca.rttv.malum.item.spirit.MalumSpiritItem;
import ca.rttv.malum.recipe.SpiritInfusionRecipe;
import ca.rttv.malum.util.block.entity.IAltarAccelerator;
import ca.rttv.malum.util.helper.BlockHelper;
import ca.rttv.malum.util.helper.DataHelper;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

import static ca.rttv.malum.registry.MalumRegistry.SPIRIT_ALTAR_BLOCK_ENTITY;

public class SpiritAltarBlockEntity extends BlockEntity implements Inventory {
    public float speed;
    public int progress;
    public int spinUp;
    public ArrayList<BlockPos> acceleratorPositions = new ArrayList<>();
    public ArrayList<IAltarAccelerator> accelerators = new ArrayList<>();
    public float spiritAmount;
    public float spiritSpin;
    public ArrayList<SpiritInfusionRecipe> possibleRecipes = new ArrayList<>();
    public SpiritInfusionRecipe recipe;
    public boolean updateRecipe;

    public final DefaultedList<ItemStack> heldItem = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public final DefaultedList<ItemStack> spiritSlots = DefaultedList.ofSize(7, ItemStack.EMPTY);

    public SpiritAltarBlockEntity(BlockPos pos, BlockState state) {
        this(SPIRIT_ALTAR_BLOCK_ENTITY, pos, state);
    }

    public SpiritAltarBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).getItem() instanceof MalumSpiritItem) {
            this.addSpirits(state, world, pos, player, hand, hit);
        } else if (this.getHeldItem().isEmpty() && player.getStackInHand(hand).isEmpty()) {
            this.grabSpirit(state, world, pos, player, hand, hit);
        } else {
            this.swapSlots(state, world, pos, player, hand, hit);
        }
        recipe = SpiritInfusionRecipe.getRecipe(world, this.getHeldItem(), this.spiritSlots);
        if (recipe == null) {
            // do stuff
            return ActionResult.CONSUME;
        }
        return ActionResult.CONSUME;
    }

    private void grabSpirit(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        for (int i = this.spiritSlots.size() - 1; i >= 0; i--) {
            if (!this.spiritSlots.get(i).isEmpty()) {
                player.setStackInHand(hand, this.spiritSlots.get(i));
                this.spiritSlots.set(i, ItemStack.EMPTY);
                return;
            }
        }
    }

    private void addSpirits(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack handStack = player.getStackInHand(hand);
        if (handStack.isEmpty()) return;
        for (ItemStack stack : this.spiritSlots) {
            if (stack.getItem() == handStack.getItem()) {
                if (stack.getCount() + handStack.getCount() <= stack.getMaxCount()) {
                    stack.increment(handStack.getCount());
                    player.setStackInHand(hand, ItemStack.EMPTY);
                } else {
                    int maxAddition = Math.max(stack.getMaxCount() - stack.getCount(), 0);
                    stack.increment(maxAddition);
                    handStack.decrement(maxAddition);
                }
                return;
            }
        }
        int index = -1;
        for (int i = 0; i < this.spiritSlots.size(); i++) {
            if (this.spiritSlots.get(i).isEmpty()) {
                index = i;
                break;
            }
        }
        if (index == -1) return;
        this.spiritSlots.set(index, handStack);
        player.setStackInHand(hand, ItemStack.EMPTY);
    }

    private List<ItemStack> getReagents(BlockState state, World world, BlockPos pos) {
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
        ArrayList<ItemStack> stacks = new ArrayList<>();
        map.forEach((item, count) -> stacks.add(new ItemStack(item, count)));
        return stacks;
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

        world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);

        ItemStack prevItem = getHeldItem();
        setStack(0, player.getStackInHand(hand));
        player.setStackInHand(hand, prevItem);
    }

    public static Vec3d itemPos(SpiritAltarBlockEntity blockEntity) {
        return DataHelper.fromBlockPos(blockEntity.getPos()).add(blockEntity.itemOffset());
    }

    public Vec3d itemOffset() {
        return new Vec3d(0.5f, 1.25f, 0.5f);
    }

    public static Vec3d spiritOffset(SpiritAltarBlockEntity blockEntity, int slot) {
        float distance = 1 - Math.min(0.25f, blockEntity.spinUp / 40f) + (float) Math.sin(blockEntity.spiritSpin / 20f) * 0.025f;
        float height = 0.75f + Math.min(0.5f, blockEntity.spinUp / 20f);
        return DataHelper.rotatedCirclePosition(new Vec3d(0.5f, height, 0.5f), distance, slot, blockEntity.spiritAmount, (long) blockEntity.spiritSpin, 360);
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
        return this.heldItem.size();
    }

    @Override
    public boolean isEmpty() {
        return this.heldItem.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.heldItem.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = Inventories.splitStack(this.heldItem, slot, amount);
        this.notifyListeners();
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.heldItem, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.heldItem.set(slot, stack);
        this.notifyListeners();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return !(player.squaredDistanceTo(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) > 64.0D);
    }

    @Override
    public void clear() {
        this.heldItem.clear();
        this.notifyListeners();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.heldItem.clear();
        this.spiritSlots.clear();
        Inventories.readNbt(nbt, this.heldItem);
        DataHelper.readNbt(nbt, this.spiritSlots, "Spirits");
        super.readNbt(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        if (!acceleratorPositions.isEmpty()) {
            nbt.putInt("acceleratorAmount", acceleratorPositions.size());
            for (int i = 0; i < acceleratorPositions.size(); i++) {
                BlockHelper.saveBlockPos(nbt, acceleratorPositions.get(i), "" + i);
            }
        }
        Inventories.writeNbt(nbt, this.heldItem);
        DataHelper.writeNbt(nbt, this.spiritSlots, "Spirits");
        super.writeNbt(nbt);
    }
}
