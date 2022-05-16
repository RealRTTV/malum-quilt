package ca.rttv.malum.block.entity;

import ca.rttv.malum.item.SpiritItem;
import ca.rttv.malum.recipe.SpiritFocusingRecipe;
import ca.rttv.malum.recipe.SpiritRepairRecipe;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.util.helper.SpiritHelper;
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
import net.minecraft.server.world.ServerWorld;
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
import java.awt.*;
import java.util.List;
import java.util.*;

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.SPIRIT_CRUCIBLE_BLOCK_ENTITY;

public class SpiritCrucibleBlockEntity extends BlockEntity implements Inventory {
    public final DefaultedList<ItemStack> heldItem = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public final DefaultedList<ItemStack> spiritSlots = DefaultedList.ofSize(9, ItemStack.EMPTY);
    private float spiritSpin = 0.0f;
    @Nullable
    private SpiritFocusingRecipe focusingRecipe;
    @Nullable
    private SpiritRepairRecipe repairRecipe;
    private int progress;
    private float speed = 0.0f;

    public SpiritCrucibleBlockEntity(BlockPos pos, BlockState state) {
        this(SPIRIT_CRUCIBLE_BLOCK_ENTITY, pos, state);
    }

    public SpiritCrucibleBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    private List<ItemStack> getTabletStacks() {
        Map<Item, Integer> map = new LinkedHashMap<>();
        BlockPos.iterate(pos.getX() - 4, pos.getY() - 2, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 2, pos.getZ() + 4).forEach(possiblePos -> {
            if (world.getBlockEntity(possiblePos) instanceof TabletBlockEntity displayBlock && !displayBlock.getHeldItem().isEmpty()) {
                Item key = displayBlock.getHeldItem().getItem();
                Integer value = displayBlock.getHeldItem().getCount();
                if (!map.containsKey(key)) {
                    map.put(key, value);
                } else {
                    map.put(key, value + map.get(key));
                }
            }
        });
        List<ItemStack> stacks = new ArrayList<>();
        map.forEach((item, count) -> stacks.add(new ItemStack(item, count)));
        return stacks;
    }

    public static Vec3d spiritOffset(SpiritCrucibleBlockEntity blockEntity, int slot, float tickDelta) {
        float distance = 0.75f + (float) Math.sin(blockEntity.spiritSpin / 20f) * 0.025f;
        float height = 0.75f;
        return DataHelper.rotatedCirclePosition(new Vec3d(0.5f, height, 0.5f), distance, slot, blockEntity.getSpiritCount(), (long) blockEntity.spiritSpin, 360.0f, tickDelta);
    }

    private float getSpiritCount() {
        return spiritSlots.stream().filter(stack -> stack != ItemStack.EMPTY).count();
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (focusingRecipe == null) {
            focusingRecipe = SpiritFocusingRecipe.getRecipe(world, this.getHeldItem(), spiritSlots);
        }

        if (repairRecipe == null) {
            repairRecipe = SpiritRepairRecipe.getRecipe(world, this.getHeldItem(), spiritSlots, this.getTabletStacks());
        }

//        if (queuedCracks > 0) {
//            crackTimer++;
//            if (crackTimer % 7 == 0) {
//                float pitch = 0.95f + (crackTimer - 8) * 0.015f + world.random.nextFloat() * 0.05f;
//                world.playSound(null, this.pos, MalumSoundRegistry.IMPETUS_CRACK, SoundCategory.BLOCKS, 0.7f, pitch);
//                queuedCracks--;
//                if (queuedCracks == 0) {
//                    crackTimer = 0;
//                }
//            }
//        }

        if (focusingRecipe != null) {
//            if (!accelerators.isEmpty()) { // todo
//                boolean canAccelerate = true;
//                for (ICrucibleAccelerator accelerator : accelerators) {
//                    boolean canAcceleratorAccelerate = accelerator.canAccelerate();
//                    if (!canAcceleratorAccelerate) {
//                        canAccelerate = false;
//                    }
//                }
//            }
        }


        if (focusingRecipe != null) {
            progress += 1 + speed;
            if (progress >= focusingRecipe.time()) {
                focusingRecipe.craft(this); // todo
            }
            return;
        } else if (repairRecipe != null) {
            ItemStack damagedItem = this.getHeldItem();
            int time = 400 + damagedItem.getDamage() * 5;
            progress++;
            if (progress >= time) {
                repairRecipe.craft(this); // todo
            }
        }

        if (focusingRecipe == null && repairRecipe == null) {
            progress = 0;
        }

//        if (focusingRecipe == null) {
//            tabletFetchCooldown--;
//            if (tabletFetchCooldown <= 0) {
//                tabletFetchCooldown = 5;
//                fetchTablets(world, this.pos.up());
//                this.notifyListeners();
//            }
//        }
    }

    public void clientTick(World world, BlockPos pos, BlockState state) {
        spiritSpin += (1 + Math.cos(Math.sin(world.getTime() * 0.1f))) * (1 + speed * 0.1f);
        passiveParticles();
    }

    private void passiveParticles() {
        if (world == null) {
            return;
        }
        
        Vec3d itemPos = new Vec3d(0.5d, 0.6d, 0.5d);
        //passive spirit particles
        if (!spiritSlots.isEmpty()) {
            for (int i = 0; i < spiritSlots.size(); i++) {
                ItemStack item = spiritSlots.get(i);
                if (item.getItem() instanceof SpiritItem spiritSplinterItem) {
                    Vec3d offset = spiritOffset(this, i, 0.5f);
                    Color color = spiritSplinterItem.type.color;
                    Color endColor = spiritSplinterItem.type.endColor;
                    double x = pos.getX() + offset.x;
                    double y = pos.getY() + offset.y;
                    double z = pos.getZ() + offset.z;
                    SpiritHelper.spawnSpiritParticles(world, x, y, z, color, endColor);
                }
            }
        }
        //spirit particles shot out from the twisted tablet
//        if (repairRecipe != null) {
//            TwistedTabletBlockEntity tabletBlockEntity = validTablet;
//
//            ArrayList<Color> colors = new ArrayList<>();
//            ArrayList<Color> endColors = new ArrayList<>();
//            if (tabletBlockEntity.inventory.getStackInSlot(0).getItem() instanceof SpiritItem spiritItem && repairRecipe.repairMaterial.getItem() instanceof SpiritItem) {
//                colors.add(spiritItem.type.color);
//                endColors.add(spiritItem.type.endColor);
//            } else if (!spiritSlots.isEmpty()) {
//                for (int i = 0; i < spiritSlots.size(); i++) {
//                    ItemStack item = spiritSlots.get(i);
//                    if (item.getItem() instanceof SpiritItem spiritItem) {
//                        colors.add(spiritItem.type.color);
//                        endColors.add(spiritItem.type.endColor);
//                    }
//                }
//            }
//            for (int i = 0; i < colors.size(); i++) {
//                Color color = colors.get(i);
//                Color endColor = endColors.get(i);
//                Vec3d tabletItemPos = tabletBlockEntity.getItemPos();
//                Vec3d velocity = tabletItemPos.subtract(itemPos).normalize().multiply(-0.1f);
//
//                ParticleBuilders.create(MalumParticleRegistry.STAR_PARTICLE)
//                        .setAlpha(0.24f / colors.size(), 0f)
//                        .setLifetime(15)
//                        .setScale(0.45f + world.random.nextFloat() * 0.15f, 0)
//                        .randomOffset(0.05)
//                        .setSpinOffset((0.075f * world.getTime()) % 6.28f)
//                        .setColor(color, endColor)
//                        .enableNoClip()
//                        .repeat(world, itemPos.x, itemPos.y, itemPos.z, 1);
//
//                ParticleBuilders.create(MalumParticleRegistry.STAR_PARTICLE)
//                        .setAlpha(0.24f / colors.size(), 0f)
//                        .setLifetime(15)
//                        .setScale(0.45f + world.random.nextFloat() * 0.15f, 0)
//                        .randomOffset(0.05)
//                        .setSpinOffset((-0.075f * world.getTime()) % 6.28f)
//                        .setColor(color, endColor)
//                        .enableNoClip()
//                        .repeat(world, tabletItemPos.x, tabletItemPos.y, tabletItemPos.z, 1);
//
//                ParticleBuilders.create(MalumParticleRegistry.WISP_PARTICLE)
//                        .setAlpha(0.4f / colors.size(), 0f)
//                        .setLifetime((int) (10 + world.random.nextInt(8) + Math.sin((0.5 * world.getTime()) % 6.28f)))
//                        .setScale(0.2f + world.random.nextFloat() * 0.15f, 0)
//                        .randomOffset(0.05)
//                        .setSpinOffset((0.075f * world.getTime() % 6.28f))
//                        .setSpin(0.1f + world.random.nextFloat() * 0.05f)
//                        .setColor(color.brighter(), endColor)
//                        .setAlphaCurveMultiplier(0.5f)
//                        .setColorCurveMultiplier(0.75f)
//                        .setMotion(velocity.x, velocity.y, velocity.z)
//                        .enableNoClip()
//                        .repeat(world, tabletItemPos.x, tabletItemPos.y, tabletItemPos.z, 1);
//            }
//            return;
//        }
//        if (focusingRecipe != null) {
//            for (int i = 0; i < spiritSlots.size(); i++) {
//                ItemStack item = spiritSlots.get(i);
//                for (ICrucibleAccelerator accelerator : accelerators) {
//                    if (accelerator != null) {
//                        accelerator.addParticles(worldPosition, itemPos);
//                    }
//                }
//                if (item.getItem() instanceof SpiritItem spiritSplinterItem) {
//                    Vec3d offset = spiritOffset(this, i);
//                    Color color = spiritSplinterItem.type.color;
//                    Color endColor = spiritSplinterItem.type.endColor;
//                    double x = pos.getX() + offset.x;
//                    double y = pos.getY() + offset.y;
//                    double z = pos.getZ() + offset.z;
//                    Vec3d velocity = new Vec3d(x, y, z).subtract(itemPos).normalize().multiply(-0.03f);
//                    for (ICrucibleAccelerator accelerator : accelerators) {
//                        if (accelerator != null) {
//                            accelerator.addParticles(color, endColor, 0.08f / this.getSpiritCount(), worldPosition, itemPos);
//                        }
//                    }
//                    ParticleBuilders.create(MalumParticleRegistry.WISP_PARTICLE)
//                            .setAlpha(0.30f, 0f)
//                            .setLifetime(40)
//                            .setScale(0.2f, 0)
//                            .randomOffset(0.02f)
//                            .randomMotion(0.01f, 0.01f)
//                            .setColor(color, endColor)
//                            .setColorCurveMultiplier(0.75f)
//                            .randomMotion(0.0025f, 0.0025f)
//                            .addMotion(velocity.x, velocity.y, velocity.z)
//                            .enableNoClip()
//                            .repeat(world, x, y, z, 1);
//
//                    ParticleBuilders.create(MalumParticleRegistry.WISP_PARTICLE)
//                            .setAlpha(0.12f / this.getSpiritCount(), 0f)
//                            .setLifetime(25)
//                            .setScale(0.2f + world.random.nextFloat() * 0.1f, 0)
//                            .randomOffset(0.05)
//                            .setSpinOffset((0.075f * world.getTime() % 6.28f))
//                            .setColor(color, endColor)
//                            .enableNoClip()
//                            .repeat(world, itemPos.x, itemPos.y, itemPos.z, 1);
//
//                    ParticleBuilders.create(MalumParticleRegistry.STAR_PARTICLE)
//                            .setAlpha(0.16f / this.getSpiritCount(), 0f)
//                            .setLifetime(25)
//                            .setScale(0.45f + world.random.nextFloat() * 0.1f, 0)
//                            .randomOffset(0.05)
//                            .setSpinOffset((0.075f * world.getTime() % 6.28f))
//                            .setColor(color, endColor)
//                            .enableNoClip()
//                            .repeat(world, itemPos.x, itemPos.y, itemPos.z, 1);
//                }
//            }
//        }
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).getItem() instanceof SpiritItem) {
            this.addSpirits(state, world, pos, player, hand, hit);
        } else if (this.getHeldItem().isEmpty() && player.getStackInHand(hand).isEmpty()) {
            this.grabSpirit(state, world, pos, player, hand, hit);
        } else {
            this.swapSlots(state, world, pos, player, hand, hit);
        }

        return ActionResult.CONSUME;
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
                focusingRecipe = SpiritFocusingRecipe.getRecipe(world, this.getHeldItem(), spiritSlots);
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

    private void swapSlots(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).isEmpty() && this.getHeldItem().isEmpty()) {
            return;
        }

        world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);

        ItemStack prevItem = getHeldItem();
        this.setStack(0, player.getStackInHand(hand));
        player.setStackInHand(hand, prevItem);
        focusingRecipe = SpiritFocusingRecipe.getRecipe(world, this.getHeldItem(), spiritSlots);
    }

    private void grabSpirit(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        for (int i = this.spiritSlots.size() - 1; i >= 0; i--) {
            if (!this.spiritSlots.get(i).isEmpty()) {
                player.setStackInHand(hand, this.spiritSlots.get(i));
                this.spiritSlots.set(i, ItemStack.EMPTY);
                this.notifyListeners();
                focusingRecipe = SpiritFocusingRecipe.getRecipe(world, this.getHeldItem(), spiritSlots);
                return;
            }
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, heldItem);
        DataHelper.writeNbt(nbt, spiritSlots, "Spirits");
        nbt.putInt("Progress", progress);
        nbt.putFloat("Speed", speed);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        heldItem.clear();
        spiritSlots.clear();
        Inventories.readNbt(nbt, heldItem);
        DataHelper.readNbt(nbt, spiritSlots, "Spirits");
        progress = nbt.getInt("Progress");
        speed = nbt.getFloat("Speed");
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

    public ItemStack getHeldItem() {
        return this.getStack(0);
    }

    @Override
    public int size() {
        return heldItem.size();
    }

    @Override
    public boolean isEmpty() {
        return heldItem.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return heldItem.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = Inventories.splitStack(this.heldItem, slot, amount);
        this.notifyListeners();
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        this.notifyListeners();
        return Inventories.removeStack(this.heldItem, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        heldItem.set(slot, stack);
        this.notifyListeners();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return !(player.squaredDistanceTo(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) > 64.0D);
    }

    @Override
    public void clear() {
        heldItem.clear();
        this.notifyListeners();
    }

    public void notifyListeners() {
        this.markDirty();

        if (world != null && !world.isClient) {
            world.updateListeners(pos, this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
        }
    }
}
