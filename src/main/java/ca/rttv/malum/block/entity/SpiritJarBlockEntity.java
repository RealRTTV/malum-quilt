package ca.rttv.malum.block.entity;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.inventory.DefaultedInventory;
import ca.rttv.malum.item.SpiritItem;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.util.helper.SpiritHelper;
import ca.rttv.malum.util.particle.ParticleBuilders;
import ca.rttv.malum.util.spirit.SpiritType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
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

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.SPIRIT_JAR_BLOCK_ENTITY;

public class SpiritJarBlockEntity extends BlockEntity implements DefaultedInventory {
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
        if (this.getHeldItem().getItem() instanceof SpiritItem item) {
            Vec3d vec = DataHelper.fromBlockPos(pos).add(new Vec3d(0.5f, 0.5f, 0.5f));
            double x = vec.x;
            double y = vec.y + Math.sin(world.getTime() / 20f) * 0.1f;
            double z = vec.z;
            SpiritHelper.spawnSpiritParticles(world, x, y, z, item.type.color, item.type.endColor);
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

    @Override
    public DefaultedList<ItemStack> getInvStackList() {
        return stacks;
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

        if (player.getStackInHand(hand).getItem() != this.getHeldItem().getItem() && !this.getHeldItem().isEmpty() && !(player.getStackInHand(hand).getItem() instanceof SpiritItem)) {
            if(world.isClient) {
                spawnUseParticles(world, pos, ((SpiritItem) getHeldItem().getItem()).type);
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
        } else if (player.getStackInHand(hand).getItem() instanceof SpiritItem && (player.getStackInHand(hand).getItem() == this.getHeldItem().getItem() || this.getHeldItem().isEmpty())) {
            ItemStack stack = player.getStackInHand(hand);
            if(world.isClient) {
                spawnUseParticles(world, pos, ((SpiritItem) stack.getItem()).type);
            }
            if (this.getHeldItem().isEmpty()) {
                this.setStack(0, new ItemStack(stack.getItem(), stack.getCount())); // this is to not carry nbt and it's better than .copy().setNbt(null);
            } else {
                this.getHeldItem().increment(stack.getCount());
            }
            player.setStackInHand(hand, ItemStack.EMPTY);
            return ActionResult.CONSUME;
        }

        return ActionResult.PASS;
    }
    public void spawnUseParticles(World world, BlockPos pos, SpiritType type) {
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
