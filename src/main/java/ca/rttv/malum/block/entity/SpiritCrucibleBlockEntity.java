package ca.rttv.malum.block.entity;

import ca.rttv.malum.block.SpiritCatalyzerBlock;
import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.inventory.DefaultedInventory;
import ca.rttv.malum.item.SpiritItem;
import ca.rttv.malum.recipe.SpiritFocusingRecipe;
import ca.rttv.malum.recipe.SpiritRepairRecipe;
import ca.rttv.malum.util.block.entity.ICrucibleAccelerator;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.*;

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.SPIRIT_CRUCIBLE_BLOCK_ENTITY;

public class SpiritCrucibleBlockEntity extends BlockEntity implements DefaultedInventory {
    public final DefaultedList<ItemStack> heldItem = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public final DefaultedList<ItemStack> spiritSlots = DefaultedList.ofSize(9, ItemStack.EMPTY);
    private float spiritSpin = 0.0f;
    @Nullable
    public SpiritFocusingRecipe focusingRecipe;
    @Nullable
    public SpiritRepairRecipe repairRecipe;
    @Nullable
    Map<String, List<Pair<ICrucibleAccelerator, BlockPos>>> accelerators = null;
    private float progress = 0.0f;
    private float speed = 0.0f;
    private int queuedCracks = 0;

    public SpiritCrucibleBlockEntity(BlockPos pos, BlockState state) {
        this(SPIRIT_CRUCIBLE_BLOCK_ENTITY, pos, state);
    }

    public SpiritCrucibleBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
    public TabletBlockEntity getValidTablet() {
        if (focusingRecipe == null && !getTablets().isEmpty()) {
            for (TabletBlockEntity tablet : getTablets()) {
                repairRecipe = SpiritRepairRecipe.getRecipe(world, this.getHeldItem(), spiritSlots, this.getTabletStacks());
                if (repairRecipe != null) {
                    return tablet;
                }
            }
        }
        return null;
    }
    public ArrayList<TabletBlockEntity> getTablets() {
        ArrayList<TabletBlockEntity> twistedTablets = new ArrayList<>();
        BlockPos.iterate(pos.getX() - 4, pos.getY() - 2, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 2, pos.getZ() + 4).forEach(possiblePos -> {
            if (world.getBlockEntity(possiblePos) instanceof TabletBlockEntity displayBlock) {
                twistedTablets.add(displayBlock);
            }
        });
        return twistedTablets;
    }

    public List<ItemStack> getTabletStacks() {
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
        return spiritSlots.stream()
                          .filter(stack -> stack != ItemStack.EMPTY)
                          .count();
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
        // todo, make only run on first tick

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

        // bad code (i tried to make it efficient, it is but it's still heavy, especially if it has to re-cache) AND THIS RUNS  E V E R Y   T I C K, todo, fix this speed
        if (focusingRecipe != null) {
            if (accelerators == null) {
                accelerators = new HashMap<>();
                BlockPos.iterateOutwards(pos, 4, 2, 4).forEach(possiblePos -> {
                    BlockState state2 = world.getBlockState(possiblePos);
                    if (state2.getBlock() instanceof ICrucibleAccelerator accelerator && (!state2.contains(SpiritCatalyzerBlock.HALF) || state2.get(SpiritCatalyzerBlock.HALF) == DoubleBlockHalf.UPPER) && accelerator.canAccelerate(possiblePos, world)) {
                        List<Pair<ICrucibleAccelerator, BlockPos>> accs = accelerators.get(accelerator.name());
                        if (accs != null && accs.size() <= 8) {
                            accs.add(new Pair<>(accelerator, possiblePos.toImmutable()));
                        } else {
                            accelerators.put(accelerator.name(), new ArrayList<>(Collections.singleton(new Pair<>(accelerator, possiblePos.toImmutable()))));
                        }
                    }
                });
            }

            int cnt = Math.min(accelerators.values().stream().flatMap(List::stream).filter(pair -> pair.getLeft().canAccelerate(pair.getRight(), world)).mapToInt(pair -> {
                pair.getLeft().tick(pair.getRight(), world);
                return 1;
            }).sum(), SpiritCatalyzerBlock.SPEED_INCREASE.length - 1);
            speed = SpiritCatalyzerBlock.SPEED_INCREASE[cnt];
            progress += speed + 1;

            if (progress >= focusingRecipe.time()) {
                focusingRecipe.craft(this);

                int durabilityCost = focusingRecipe.durabilityCost();
                if (world.random.nextFloat() <= SpiritCatalyzerBlock.DAMAGE_CHANCES[cnt]) {
                    durabilityCost += world.random.nextInt(SpiritCatalyzerBlock.DAMAGE_MAX_VALUE[cnt] + 1);
                }
                queuedCracks += durabilityCost;
                if (this.getHeldItem().damage(durabilityCost, world.random, null)) {
                    Identifier id = Registry.ITEM.getId(this.getHeldItem().getItem());
                    this.setStack(0, Registry.ITEM.get(new Identifier(id.getNamespace(), "cracked_" + id.getPath())).getDefaultStack());
                }
                progress = 0;
                this.notifyListeners();
                this.focusingRecipe = null;
            }
        } else if (repairRecipe != null && this.getValidTablet() != null) {
            ItemStack damagedItem = this.getHeldItem();
            int time = 400 + damagedItem.getDamage() * 5;
            progress++;
            if (progress >= time) {
                repairRecipe.craft(this);
                progress = 0;
                this.notifyListeners();
                this.repairRecipe = null;
            }
        }

        if (focusingRecipe == null && repairRecipe == null) {
            progress = 0;
        }
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

        if (repairRecipe != null) {
            ArrayList<Color> colors = new ArrayList<>();
            ArrayList<Color> endColors = new ArrayList<>();
            if (this.getTabletStacks().get(0).getItem() instanceof SpiritItem spiritItem) {
                colors.add(spiritItem.type.color);
                endColors.add(spiritItem.type.endColor);
            } else if (!spiritSlots.isEmpty()) {
                for (int i = 0; i < spiritSlots.size(); i++) {
                    ItemStack item = spiritSlots.get(i);
                    if (item.getItem() instanceof SpiritItem spiritItem) {
                        colors.add(spiritItem.type.color);
                        endColors.add(spiritItem.type.endColor);
                    }
                }
            }
            for (int i = 0; i < colors.size(); i++) {
                Color color = colors.get(i);
                Color endColor = endColors.get(i);
                Vec3d tabletItemPos = this.getValidTablet().itemOffset();
                Vec3d velocity = tabletItemPos.subtract(itemPos).normalize().multiply(-0.1f);
                ParticleBuilders.create(MalumParticleRegistry.STAR_PARTICLE)
                        .setAlpha(0.24f / colors.size(), 0f)
                        .setLifetime(15)
                        .setScale(0.45f + world.random.nextFloat() * 0.15f, 0)
                        .randomOffset(0.05)
                        .setSpinOffset((0.075f * world.getTime()) % 6.28f)
                        .setColor(color, endColor)
                        .enableNoClip()
                        .repeat(world, itemPos.x, itemPos.y, itemPos.z, 1);

                ParticleBuilders.create(MalumParticleRegistry.STAR_PARTICLE)
                        .setAlpha(0.24f / colors.size(), 0f)
                        .setLifetime(15)
                        .setScale(0.45f + world.random.nextFloat() * 0.15f, 0)
                        .randomOffset(0.05)
                        .setSpinOffset((-0.075f * world.getTime()) % 6.28f)
                        .setColor(color, endColor)
                        .enableNoClip()
                        .repeat(world, tabletItemPos.x, tabletItemPos.y, tabletItemPos.z, 1);

                ParticleBuilders.create(MalumParticleRegistry.WISP_PARTICLE)
                        .setAlpha(0.4f / colors.size(), 0f)
                        .setLifetime((int) (10 + world.random.nextInt(8) + Math.sin((0.5 * world.getTime()) % 6.28f)))
                        .setScale(0.2f + world.random.nextFloat() * 0.15f, 0)
                        .randomOffset(0.05)
                        .setSpinOffset((0.075f * world.getTime() % 6.28f))
                        .setSpin(0.1f + world.random.nextFloat() * 0.05f)
                        .setColor(color.brighter(), endColor)
                        .setAlphaCurveMultiplier(0.5f)
                        .setColorCurveMultiplier(0.75f)
                        .setMotion(velocity.x, velocity.y, velocity.z)
                        .enableNoClip()
                        .repeat(world, tabletItemPos.x, tabletItemPos.y, tabletItemPos.z, 1);
            }
            return;
        }
        if (focusingRecipe != null) {
            for (int i = 0; i < spiritSlots.size(); i++) {
                ItemStack item = spiritSlots.get(i);
                if (item.getItem() instanceof SpiritItem spiritSplinterItem) {
                    Vec3d offset = spiritOffset(this, i, 0.5f);
                    Color color = spiritSplinterItem.type.color;
                    Color endColor = spiritSplinterItem.type.endColor;
                    double x = pos.getX() + offset.x;
                    double y = pos.getY() + offset.y;
                    double z = pos.getZ() + offset.z;
                    Vec3d velocity = new Vec3d(x, y, z).subtract(itemPos).normalize().multiply(-0.03f);
                    ParticleBuilders.create(MalumParticleRegistry.WISP_PARTICLE)
                            .setAlpha(0.30f, 0f)
                            .setLifetime(40)
                            .setScale(0.2f, 0)
                            .randomOffset(0.02f)
                            .randomMotion(0.01f, 0.01f)
                            .setColor(color, endColor)
                            .setColorCurveMultiplier(0.75f)
                            .randomMotion(0.0025f, 0.0025f)
                            .addMotion(velocity.x, velocity.y, velocity.z)
                            .enableNoClip()
                            .repeat(world, x, y, z, 1);

                    ParticleBuilders.create(MalumParticleRegistry.WISP_PARTICLE)
                            .setAlpha(0.12f / this.getSpiritCount(), 0f)
                            .setLifetime(25)
                            .setScale(0.2f + world.random.nextFloat() * 0.1f, 0)
                            .randomOffset(0.05)
                            .setSpinOffset((0.075f * world.getTime() % 6.28f))
                            .setColor(color, endColor)
                            .enableNoClip()
                            .repeat(world, itemPos.x, itemPos.y, itemPos.z, 1);

                    ParticleBuilders.create(MalumParticleRegistry.STAR_PARTICLE)
                            .setAlpha(0.16f / this.getSpiritCount(), 0f)
                            .setLifetime(25)
                            .setScale(0.45f + world.random.nextFloat() * 0.1f, 0)
                            .randomOffset(0.05)
                            .setSpinOffset((0.075f * world.getTime() % 6.28f))
                            .setColor(color, endColor)
                            .enableNoClip()
                            .repeat(world, itemPos.x, itemPos.y, itemPos.z, 1);
                }
            }
        }
    }

    @Override
    public World getWorld() {
        return super.getWorld();
    }

    @Override
    public BlockPos getPos() {
        return super.getPos();
    }

    @Override
    public BlockState getCachedState() {
        return super.getCachedState();
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
        nbt.putFloat("Progress", progress);
        nbt.putFloat("Speed", speed);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        heldItem.clear();
        spiritSlots.clear();
        Inventories.readNbt(nbt, heldItem);
        DataHelper.readNbt(nbt, spiritSlots, "Spirits");
        progress = nbt.getFloat("Progress");
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
    public DefaultedList<ItemStack> getInvStackList() {
        return heldItem;
    }
}
