package ca.rttv.malum.block.entity;

import ca.rttv.malum.registry.MalumRegistry;
import ca.rttv.malum.util.Rite;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static ca.rttv.malum.registry.MalumRegistry.SOULWOOD_TOTEM_BASE;
import static ca.rttv.malum.registry.MalumRegistry.TOTEM_BASE_BLOCK_ENTITY;

public class TotemBaseBlockEntity extends BlockEntity {
    public static final Map<Integer, Rite> RITES;

    @Nullable
    public Rite rite;

    public TotemBaseBlockEntity(BlockPos pos, BlockState state) {
        this(TOTEM_BASE_BLOCK_ENTITY, pos, state);
    }

    public TotemBaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void clientTick(World world, BlockPos pos, BlockState state) {

    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (!stack.isEmpty()) {
            return ActionResult.PASS;
        }

        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        List<Item> list = new ArrayList<>();
        BlockPos up = pos.up();
        if (world.getBlockEntity(up) instanceof TotemPoleBlockEntity totemPoleBlockEntity) {
            totemPoleBlockEntity.list = list;
            world.scheduleBlockTick(up, world.getBlockState(up).getBlock(), 1);
        }
        return ActionResult.CONSUME;
    }

    public boolean isCorrupt() {
        return this.getCachedState().isOf(SOULWOOD_TOTEM_BASE);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putString("Rite", Objects.requireNonNullElse(MalumRegistry.RITE.getId(rite), new Identifier("air")).toString());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        rite = MalumRegistry.RITE.get(new Identifier(nbt.getString("Rite")));
    }

    static {
        RITES = new HashMap<>();
        MalumRegistry.RITE.forEach(rite -> RITES.put(rite.hashCode(), rite));
    }
}
