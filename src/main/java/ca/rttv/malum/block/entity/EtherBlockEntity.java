package ca.rttv.malum.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static ca.rttv.malum.registry.MalumRegistry.ETHER_BLOCK_ENTITY;

public class EtherBlockEntity extends BlockEntity {
    public int firstColor;
    public int secondColor;

    public EtherBlockEntity(BlockPos pos, BlockState state, int firstColor, int secondColor) {
        this(ETHER_BLOCK_ENTITY, pos, state, firstColor, secondColor);
    }

    public EtherBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int firstColor, int secondColor) {
        super(type, pos, state);
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }

    public EtherBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, -1, -1);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (!itemStack.hasNbt()) {
            this.firstColor = -1;
            this.secondColor = -1;
            return;
        }
        this.firstColor = itemStack.getNbt().contains("first_color") ? itemStack.getNbt().getInt("first_color") : -1;
        this.secondColor = itemStack.getNbt().contains("second_color") ? itemStack.getNbt().getInt("second_color") : -1;
    }
}
