package ca.rttv.malum.block.entity;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.item.spirit.MalumSpiritItem;
import ca.rttv.malum.recipe.SpiritInfusionRecipe;
import ca.rttv.malum.util.IngredientWithCount;
import ca.rttv.malum.util.block.entity.IAltarAccelerator;
import ca.rttv.malum.util.block.entity.SimpleBlockEntity;
import ca.rttv.malum.util.helper.BlockHelper;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.util.helper.SpiritHelper;
import ca.rttv.malum.util.particle.ParticleBuilders;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ca.rttv.malum.registry.MalumRegistry.HOLY_SAPBALL;
import static ca.rttv.malum.registry.MalumRegistry.SPIRIT_ALTAR_BLOCK_ENTITY;

public class SpiritAltarBlockEntity extends SimpleBlockEntity implements Inventory {
    public float speed;
    public int progress;
    public int spinUp;

    public ArrayList<BlockPos> acceleratorPositions = new ArrayList<>();
    public ArrayList<IAltarAccelerator> accelerators = new ArrayList<>();
    public float spiritAmount;
    public float spiritSpin;

    public SpiritInfusionRecipe recipe;
    public final DefaultedList<ItemStack> heldItem = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public final DefaultedList<ItemStack> spiritSlots = DefaultedList.ofSize(9, ItemStack.EMPTY);

    public SpiritAltarBlockEntity(BlockPos pos, BlockState state) {
        this(SPIRIT_ALTAR_BLOCK_ENTITY, pos, state);
    }

    public SpiritAltarBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }

    @Override
    public void tick() {
//        spiritAmount = Math.max(1, MathHelper.lerp(0.1f, spiritAmount, ));
//        if (updateRecipe)
//        {
//            if (level.isClientSide && possibleRecipes.isEmpty()) {
//                AltarSoundInstance.playSound(this);
//            }
//            ItemStack stack = inventory.getStackInSlot(0);
//            possibleRecipes = new ArrayList<>(DataHelper.getAll(SpiritInfusionRecipe.getRecipes(level), r -> r.doesInputMatch(stack) && r.doSpiritsMatch(spiritInventory.getNonEmptyItemStacks())));
//            recipe = SpiritInfusionRecipe.getRecipe(level, stack, spiritInventory.getNonEmptyItemStacks());
//            updateRecipe = false;
//        }
        if (!recipe.isEmpty()) {
            if (spinUp < 10) {
                spinUp++;
            }
            if (!world.isClient) {
                progress++;
                if (world.getTime() % 20L == 0) {
                    boolean canAccelerate = accelerators.stream().allMatch(IAltarAccelerator::canAccelerate);
                    if (!canAccelerate) {
//                        recalibrateAccelerators();
                    }
                }
                int progressCap = (int) (300*Math.exp(-0.15*speed));
                if (progress >= progressCap) {
//                    boolean success = consume();
//                    if (success) {
//                        craft();
//                    }
                }
            }
        } else {
            progress = 0;
            if (spinUp > 0) {
                spinUp--;
            }
        }
        if (world.isClient) {
            spiritSpin += 1 + spinUp / 5f;
            passiveParticles();
        }
    }

    public void passiveParticles() {
        Vec3d itemPos = itemPos(this);
        for (int i = 0; i < spiritSlots.size(); i++) {
            ItemStack item = spiritSlots.get(i);
            for (IAltarAccelerator accelerator : accelerators) {
                if (accelerator != null) {
                    accelerator.addParticles(pos, itemPos);
                }
            }
            if (item.getItem() instanceof MalumSpiritItem spiritSplinterItem) {
                Vec3d offset = spiritOffset(this, i);
                Color color = spiritSplinterItem.type.color;
                Color endColor = spiritSplinterItem.type.endColor;
                double x = getPos().getX() + offset.getX();
                double y = getPos().getY() + offset.getY();
                double z = getPos().getZ() + offset.getZ();
                SpiritHelper.spawnSpiritParticles(world, x, y, z, color, endColor);
                if (!recipe.isEmpty()) {
                    Vec3d velocity = new Vec3d(x, y, z).subtract(itemPos).normalize().multiply(-0.03f);
                    float alpha = 0.07f /* / spiritSlots. */;
                    for (IAltarAccelerator accelerator : accelerators) {
                        if (accelerator != null) {
                            accelerator.addParticles(color, endColor, alpha, pos, itemPos);
                        }
                    }
                    ParticleBuilders.create(MalumParticleRegistry.WISP_PARTICLE)
                            .setAlpha(0.125f, 0f)
                            .setLifetime(45)
                            .setScale(0.2f, 0)
                            .randomOffset(0.02f)
                            .randomMotion(0.01f, 0.01f)
                            .setColor(color, endColor)
                            .setColorCurveMultiplier(1.25f)
                            .setSpin(0.1f + world.random.nextFloat()*0.1f)
                            .randomMotion(0.0025f, 0.0025f)
                            .addMotion(velocity.x, velocity.y, velocity.z)
                            .enableNoClip()
                            .repeat(world, x, y, z, 2);

                    ParticleBuilders.create(MalumParticleRegistry.SPARKLE_PARTICLE)
                            .setAlpha(alpha, 0f)
                            .setLifetime(25)
                            .setScale(0.5f, 0)
                            .randomOffset(0.1, 0.1)
                            .randomMotion(0.02f, 0.02f)
                            .setColor(color, endColor)
                            .setColorCurveMultiplier(1.5f)
                            .randomMotion(0.0025f, 0.0025f)
                            .enableNoClip()
                            .repeat(world, itemPos.x, itemPos.y, itemPos.z, 2);
                }
            }
        }
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).getItem() instanceof MalumSpiritItem) {
            this.addSpirits(state, world, pos, player, hand, hit);
        } else if (this.getHeldItem().isEmpty() && player.getStackInHand(hand).isEmpty()) {
            this.grabSpirit(state, world, pos, player, hand, hit);
        } else if (player.getStackInHand(hand).getItem() != HOLY_SAPBALL) {
            this.swapSlots(state, world, pos, player, hand, hit);
        }
        recipe = SpiritInfusionRecipe.getRecipe(world, this.getHeldItem(), this.spiritSlots);
        if (recipe == null) {
            return ActionResult.CONSUME;
        }
        System.out.println(hasExtraItems(state, world, pos, getExtraItems(state, world, pos), recipe));
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

    private boolean hasExtraItems(BlockState state, World world, BlockPos pos, List<ItemStack> extraItems, SpiritInfusionRecipe recipe) {
        for (IngredientWithCount.Entry entry : recipe.extraItems.getEntries()) {
            boolean found = false;
            for (ItemStack extraItem : extraItems) {
                if (entry.isValidItem(extraItem)) {
                    found = true;
                    // we don't remove the entry since it can cause errors with index of extra items, thus is has to be used twice
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
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

    private List<ItemStack> getExtraItems(BlockState state, World world, BlockPos pos) {
        // id have to use a map cause i don't think theres an @Override to equals on itemstack thus memory location shit
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
        float distance = 1 - Math.min(0.25f, blockEntity.spiritAmount / 40f) + (float) Math.sin(blockEntity.spiritSpin / 20f) * 0.025f;
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
        progress = nbt.getInt("progress");
        spinUp = nbt.getInt("spinUp");
        speed = nbt.getFloat("speed");

        acceleratorPositions.clear();
        accelerators.clear();
        int amount = nbt.getInt("acceleratorAmount");
        for (int i = 0; i < amount; i++) {
            BlockPos pos = BlockHelper.loadBlockPos(nbt, "" + i);
            if (world != null && world.getBlockEntity(pos) instanceof IAltarAccelerator accelerator) {
                acceleratorPositions.add(pos);
                accelerators.add(accelerator);
            }
        }

        spiritAmount = nbt.getFloat("spiritAmount");
        Inventories.readNbt(nbt, this.heldItem);
        DataHelper.readNbt(nbt, this.spiritSlots, "Spirits");
        super.readNbt(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        if (progress != 0) {
            nbt.putInt("progress", progress);
        }
        if (spinUp != 0) {
            nbt.putInt("spinUp", spinUp);
        }
        if (speed != 0) {
            nbt.putFloat("speed", speed);
        }
        if (spiritAmount != 0) {
            nbt.putFloat("spiritAmount", spiritAmount);
        }

        if (!acceleratorPositions.isEmpty())
        {
            nbt.putInt("acceleratorAmount", acceleratorPositions.size());
            for (int i = 0; i < acceleratorPositions.size(); i++)
            {
                BlockHelper.saveBlockPos(nbt, acceleratorPositions.get(i), "" + i);
            }
        }
        Inventories.writeNbt(nbt, this.heldItem);
        DataHelper.writeNbt(nbt, this.spiritSlots, "Spirits");
        super.writeNbt(nbt);
    }
}
