package ca.rttv.malum.block.entity;

import ca.rttv.malum.registry.MalumRiteRegistry;
import ca.rttv.malum.registry.MalumSoundRegistry;
import ca.rttv.malum.rite.Rite;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.TOTEM_BASE_BLOCK_ENTITY;
import static ca.rttv.malum.registry.MalumBlockRegistry.SOULWOOD_TOTEM_BASE;

public class TotemBaseBlockEntity extends BlockEntity {
    public static final Map<Integer, Rite> RITES;

    @Nullable
    public Rite rite;
    public long tick;

    public TotemBaseBlockEntity(BlockPos pos, BlockState state) {
        this(TOTEM_BASE_BLOCK_ENTITY, pos, state);
    }

    public TotemBaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        if (world == null) {
            return;
        }

        BlockPos up = pos.up();
        while (world.getBlockEntity(up) instanceof TotemPoleBlockEntity totemPoleBlockEntity) {
            totemPoleBlockEntity.setCachedBaseBlock(this);
            up = up.up();
        }
    }


    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (!stack.isEmpty()) {
            return ActionResult.PASS;
        }

        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        if (rite != null) {
            world.playSound(null, pos, MalumSoundRegistry.TOTEM_CANCELLED, SoundCategory.BLOCKS, 1.0f, 0.9f + world.random.nextFloat() * 0.2f);
            rite = null;
            this.updateListeners();
            BlockPos up = pos.up();
            BlockEntity blockEntity = world.getBlockEntity(up);
            while (up.getY() <= world.getTopY()) {
                if (blockEntity instanceof TotemPoleBlockEntity totemPoleBlockEntity) {
                    totemPoleBlockEntity.setCachedBaseBlock(null);
                    totemPoleBlockEntity.particles = false;
                    totemPoleBlockEntity.updateListeners();
                    up = up.up();
                    blockEntity = world.getBlockEntity(up);
                    continue;
                }
                break;
            }
            return ActionResult.CONSUME;
        }

        List<Item> list = new ArrayList<>();
        BlockPos up = pos.up();
        if (world.getBlockEntity(up) instanceof TotemPoleBlockEntity totemPoleBlockEntity) {
            totemPoleBlockEntity.list = list;
            totemPoleBlockEntity.particles = true;
            world.scheduleBlockTick(up, world.getBlockState(up).getBlock(), 1);
        } else {
            rite = null;
        }
        return ActionResult.CONSUME;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = super.toInitialChunkDataNbt();
        this.writeNbt(nbt);
        return nbt;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.of(this);
    }

    public void updateListeners() {
        this.markDirty();

        if (world != null && !world.isClient) {
            world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
        }
    }

    public boolean isCorrupt() {
        return this.getCachedState().isOf(SOULWOOD_TOTEM_BASE);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putString("Rite", Objects.requireNonNullElse(MalumRiteRegistry.RITE.getId(rite), new Identifier("air")).toString());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        rite = MalumRiteRegistry.RITE.get(new Identifier(nbt.getString("Rite")));
    }

    static {
        RITES = new HashMap<>();
        MalumRiteRegistry.RITE.forEach(rite -> RITES.put(rite.hashCode(), rite));
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
        if (rite == null) {
            return;
        }

        if (this.isCorrupt()) {
            rite.onCorruptTick(state, world, pos, random, ++tick);
        } else {
            rite.onTick(state, world, pos, random, ++tick);
        }
    }
}
