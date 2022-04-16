package ca.rttv.malum.block.entity;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.item.spirit.MalumSpiritItem;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.util.helper.SpiritHelper;
import ca.rttv.malum.util.particle.ParticleBuilders;
import ca.rttv.malum.util.spirit.MalumSpiritType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.awt.*;

import static ca.rttv.malum.registry.MalumRegistry.SPIRIT_JAR_BLOCK_ENTITY;

public class SpiritJarBlockEntity extends BlockEntity implements Inventory {
    private final DefaultedList<ItemStack> stacks = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public SpiritJarBlockEntity(BlockPos pos, BlockState state) {
        this(SPIRIT_JAR_BLOCK_ENTITY, pos, state);
    }

    public SpiritJarBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ItemStack getHeldItem() {
        return stacks.get(0);
    }

    public void clientTick(World world, BlockPos pos, BlockState state) {
        if (this.getHeldItem().getItem() instanceof MalumSpiritItem item) {
            Vec3d vec = DataHelper.fromBlockPos(pos).add(new Vec3d(0.5f, 0.5f, 0.5f));
            double x = vec.x;
            double y = vec.y + Math.sin(world.getTime() / 20f) * 0.1f;
            double z = vec.z;
            SpiritHelper.spawnSpiritParticles(world, x, y, z, item.type.color, item.type.endColor);
        }
    }

    @Override
    public int size() {
        return stacks.size();
    }

    @Override
    public boolean isEmpty() {
        return stacks.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return stacks.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(stacks, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(stacks, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        stacks.set(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return !(player.squaredDistanceTo(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) > 64.0D);
    }

    @Override
    public void clear() {
        stacks.clear();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        stacks.clear();
        Inventories.readNbt(nbt, stacks);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, stacks);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound tag = super.toInitialChunkDataNbt();
        this.writeNbt(tag);
        return tag;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).isEmpty() && this.getHeldItem().isEmpty()) {
            return ActionResult.PASS;
        }

        if (player.getStackInHand(hand).getItem() != this.getHeldItem().getItem() && !this.getHeldItem().isEmpty() && !(player.getStackInHand(hand).getItem() instanceof MalumSpiritItem)) {
            if(world.isClient) {
                spawnUseParticles(world, pos, ((MalumSpiritItem) getHeldItem().getItem()).type);
            }
            int count;
            if (player.isSneaking()) {
                count = Math.min(64, this.getHeldItem().getCount());
            } else {
                count = 1;
            }
            player.getInventory().insertStack(new ItemStack(this.getHeldItem().getItem(), count));
            this.getHeldItem().decrement(count);
            world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            return ActionResult.CONSUME;
        } else if (player.getStackInHand(hand).getItem() instanceof MalumSpiritItem && (player.getStackInHand(hand).getItem() == this.getHeldItem().getItem() || this.getHeldItem().isEmpty())) {
            ItemStack stack = player.getStackInHand(hand);
            if(world.isClient) {
                spawnUseParticles(world, pos, ((MalumSpiritItem) stack.getItem()).type);
            }
            if (this.getHeldItem().isEmpty()) {
                this.setStack(0, new ItemStack(stack.getItem(), stack.getCount())); // this is to not carry nbt
            } else {
                this.getHeldItem().increment(stack.getCount());
            }
            player.setStackInHand(hand, ItemStack.EMPTY);
            return ActionResult.CONSUME;
        }

        return ActionResult.PASS;
    }
    public void spawnUseParticles(World world, BlockPos pos, MalumSpiritType type) {
        Color color = type.color;
        ParticleBuilders.create(MalumParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.15f, 0f)
                .setLifetime(20)
                .setScale(0.3f, 0)
                .setSpin(0.2f)
                .randomMotion(0.02f)
                .randomOffset(0.1f, 0.1f)
                .setColor(color, color.darker())
                .enableNoClip()
                .repeat(world, pos.getX()+0.5f, pos.getY()+0.5f + Math.sin(world.getTime() / 20f) * 0.2f, pos.getZ()+0.5f, 10);
    }
}
