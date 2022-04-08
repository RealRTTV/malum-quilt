package ca.rttv.malum.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

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
        this(pos, state, 16777216, 16777216);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        firstColor = nbt.getInt("first_color");
        secondColor = nbt.getInt("second_color");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("first_color", firstColor);
        nbt.putInt("second_color", secondColor);
    }
}
