package ca.rttv.malum.block.entity;

import ca.rttv.malum.block.ObeliskBlock;
import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.inventory.DefaultedInventory;
import ca.rttv.malum.item.SpiritItem;
import ca.rttv.malum.recipe.IngredientWithCount;
import ca.rttv.malum.recipe.SpiritInfusionRecipe;
import ca.rttv.malum.util.block.entity.IAltarAccelerator;
import ca.rttv.malum.util.helper.BlockHelper;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.util.helper.SpiritHelper;
import ca.rttv.malum.util.particle.ParticleBuilders;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
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
import java.util.List;
import java.util.*;

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.SPIRIT_ALTAR_BLOCK_ENTITY;

public class SpiritAltarBlockEntity extends BlockEntity implements DefaultedInventory {
    public float speed;
    public int progress;
    public int spinUp;

    public LinkedHashMap<BlockPos, IAltarAccelerator> accelerators = new LinkedHashMap<>();
    public float spiritAmount;
    public float spiritSpin;

    @Nullable
    public SpiritInfusionRecipe recipe;
    public final DefaultedList<ItemStack> heldItem = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public final DefaultedList<ItemStack> spiritSlots = DefaultedList.ofSize(9, ItemStack.EMPTY);

    public SpiritAltarBlockEntity(BlockPos pos, BlockState state) {
        this(SPIRIT_ALTAR_BLOCK_ENTITY, pos, state);
    }

    public SpiritAltarBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }

    public void clientTick(World world, BlockPos pos, BlockState state) {
        spiritSpin += 1 + spinUp / 5f;
        passiveParticles();
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        spiritAmount = Math.max(1, MathHelper.lerp(0.1f, spiritAmount, getSpiritCount(spiritSlots)));
        if (recipe != null && this.hasExtraItems(state, world, pos, this.getExtraItemsAndCacheAccelerators(state, world, pos), recipe)) {
            if (spinUp < 10) {
                spinUp++;
                this.notifyListeners();
            }
                progress += 1 + speed;
                int progressCap = (int) (300 * Math.exp(-0.15 * speed));
                if (progress >= progressCap) {
                    recipe.craft(this);
                    recipe = SpiritInfusionRecipe.getRecipe(world, this.getHeldItem(), spiritSlots);
                    this.progress = 0;
                    this.notifyListeners();
                }
        } else {
            progress = 0;
            if (spinUp > 0) {
                spinUp--;
            }
        }

        world.scheduleBlockTick(pos, state.getBlock(), 1);
    }

    public void passiveParticles() {
        Vec3d itemPos = itemPos(this);
        for (int i = 0; i < spiritSlots.size(); i++) {
            ItemStack item = spiritSlots.get(i);
            for (IAltarAccelerator accelerator : accelerators.values()) {
                if (accelerator != null) {
                    accelerator.addParticles(pos, itemPos);
                }
            }
            if (item.getItem() instanceof SpiritItem spiritSplinterItem && world != null) {
                Vec3d offset = spiritOffset(this, i, 0.5f); // doing 0.5f makes it so the particle is in between this tick's particle and the next ticks particle. Instead of making it perfectly precise at the start and shittier at the end of the tick we make it about right at some points and perfect at others
                Color color = spiritSplinterItem.type.color;
                Color endColor = spiritSplinterItem.type.endColor;
                double x = getPos().getX() + offset.getX();
                double y = getPos().getY() + offset.getY();
                double z = getPos().getZ() + offset.getZ();
                SpiritHelper.spawnSpiritParticles(world, x, y, z, color, endColor);
                if (recipe != null && !recipe.isEmpty()) {
                    Vec3d velocity = new Vec3d(x, y, z).subtract(itemPos).normalize().multiply(-0.03f);
                    float alpha = 0.07f /* / spiritSlots. */;
                    for (IAltarAccelerator accelerator : accelerators.values()) {
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
        if (player.getStackInHand(hand).getItem() instanceof SpiritItem) {
            this.addSpirits(state, world, pos, player, hand, hit);
        } else if (this.getHeldItem().isEmpty() && player.getStackInHand(hand).isEmpty()) {
            this.grabSpirit(state, world, pos, player, hand, hit);
        } else {
            this.swapSlots(state, world, pos, player, hand, hit);
        }
        recipe = SpiritInfusionRecipe.getRecipe(world, this.getHeldItem(), this.spiritSlots);

        return ActionResult.CONSUME;
    }

    private static int getSpiritCount(List<ItemStack> spirits) {
        for (int i = spirits.size() - 1; i >= 0; i--) {
            if (!spirits.get(i).isEmpty()) {
                return i + 1;
            }
        }
        return 0;
    }

    private void grabSpirit(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        for (int i = this.spiritSlots.size() - 1; i >= 0; i--) {
            if (!this.spiritSlots.get(i).isEmpty()) {
                player.setStackInHand(hand, this.spiritSlots.get(i));
                this.spiritSlots.set(i, ItemStack.EMPTY);
                this.notifyListeners();
                this.recipe = SpiritInfusionRecipe.getRecipe(world, this.getHeldItem(), spiritSlots);
                return;
            }
        }
    }

    private boolean hasExtraItems(BlockState state, World world, BlockPos pos, List<ItemStack> extraItems, SpiritInfusionRecipe recipe) {
        for (IngredientWithCount.Entry entry : recipe.extraItems().getEntries()) {
            boolean found = false;
            for (ItemStack extraItem : extraItems) {
                if (entry.isValidItem(extraItem)) {
                    found = true;
                    // we don't remove the entry since it can cause errors with tags accepting the "wrong" item, thus it can be used twice
                    // todo: make a config option for the removal
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
                this.notifyListeners();
                recipe = SpiritInfusionRecipe.getRecipe(world, this.getHeldItem(), spiritSlots);
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
        this.notifyListeners();
    }

    public List<ItemStack> getExtraItemsAndCacheAccelerators(BlockState state, World world, BlockPos pos) {
        // id have to use a map because I don't think there's an @Override to equals on itemstack thus memory location equals
        accelerators.clear();
        speed = 0;
        Map<Item, Integer> map = new LinkedHashMap<>();
        BlockPos.iterate(pos.getX() - 4, pos.getY() - 2, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 2, pos.getZ() + 4).forEach(possiblePos -> {
            if (world.getBlockEntity(possiblePos) instanceof AbstractItemDisplayBlockEntity displayBlock && !displayBlock.getHeldItem().isEmpty()) {
                Item key = displayBlock.getHeldItem().getItem();
                Integer value = displayBlock.getHeldItem().getCount();
                if (!map.containsKey(key)) {
                    map.put(key, value);
                } else {
                    map.put(key, value + map.get(key));
                }
            } else if (world.getBlockState(possiblePos).getBlock() instanceof IAltarAccelerator accelerator) {
                if (!world.getBlockState(possiblePos).contains(ObeliskBlock.HALF) || world.getBlockState(possiblePos).get(ObeliskBlock.HALF) == DoubleBlockHalf.LOWER) {
                    accelerators.put(possiblePos, accelerator);
                }
            }
        });
        accelerators.forEach((accPos, accelerator) -> speed += accelerator.getAcceleration());
        speed = speed > 4 ? 4 : speed;
        ArrayList<ItemStack> stacks = new ArrayList<>();
        map.forEach((item, count) -> stacks.add(new ItemStack(item, count)));
        return stacks;
    }

    @Override
    public DefaultedList<ItemStack> getInvStackList() {
        return heldItem;
    }

    public void swapSlots(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).isEmpty() && this.getHeldItem().isEmpty()) {
            return;
        }

        world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);

        ItemStack prevItem = getHeldItem();
        this.setStack(0, player.getStackInHand(hand));
        player.setStackInHand(hand, prevItem);
    }

    public static Vec3d itemPos(SpiritAltarBlockEntity blockEntity) {
        return DataHelper.fromBlockPos(blockEntity.getPos()).add(blockEntity.itemOffset());
    }

    public Vec3d itemOffset() {
        return new Vec3d(0.5f, 1.25f, 0.5f);
    }

    public static Vec3d spiritOffset(SpiritAltarBlockEntity blockEntity, int slot, float tickDelta) {
        float distance = 1 - Math.min(0.25f, blockEntity.spinUp / 40f) + (float) Math.sin(blockEntity.spiritSpin / 20f) * 0.025f;
        float height = 0.75f + Math.min(0.5f, blockEntity.spinUp / 20f);
        return DataHelper.rotatedCirclePosition(new Vec3d(0.5f, height, 0.5f), distance, slot, blockEntity.spiritAmount, (long) blockEntity.spiritSpin, 360, tickDelta);
    }

    public ItemStack getHeldItem() {
        return this.getStack(0);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound tag = super.toInitialChunkDataNbt();
        this.writeNbt(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.of(this);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.heldItem.clear();
        this.spiritSlots.clear();
        progress = nbt.getInt("progress");
        spinUp = nbt.getInt("spinUp");
        speed = nbt.getFloat("speed");

        accelerators.clear();
        int amount = nbt.getInt("acceleratorAmount");
        for (int i = 0; i < amount; i++) {
            BlockPos pos = BlockHelper.loadBlockPos(nbt, String.valueOf(i));
            if (world != null && world.getBlockEntity(pos) instanceof IAltarAccelerator accelerator) {
                accelerators.put(pos, accelerator);
            }
        }

        spiritAmount = nbt.getFloat("spiritAmount");
        Inventories.readNbt(nbt, this.heldItem);
        DataHelper.readNbt(nbt, this.spiritSlots, "Spirits");
        super.readNbt(nbt);
    }

    @Override
    public World getWorld() {
        return super.getWorld();
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("progress", progress);
        nbt.putInt("spinUp", spinUp);
        nbt.putFloat("speed", speed);
        nbt.putFloat("spiritAmount", spiritAmount);
        // maybe unnecessary
        if (!accelerators.isEmpty()) {
            nbt.putInt("acceleratorAmount", accelerators.size());
            List<BlockPos> accs = accelerators.keySet()
                                              .stream()
                                              .toList();
            for (int i = 0; i < accelerators.size(); i++) {
                BlockHelper.saveBlockPos(nbt, accs.get(i), String.valueOf(i));
            }
        }
        Inventories.writeNbt(nbt, this.heldItem);
        DataHelper.writeNbt(nbt, this.spiritSlots, "Spirits");
        super.writeNbt(nbt);
    }
}
