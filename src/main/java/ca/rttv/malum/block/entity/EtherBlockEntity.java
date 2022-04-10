package ca.rttv.malum.block.entity;

import ca.rttv.malum.util.helper.NbtHelper;
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
        this(pos, state, 15712278, 4607909);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.firstColor = nbt.getInt("FirstColor");
        this.secondColor = nbt.getInt("SecondColor");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("FirstColor", firstColor);
        nbt.putInt("SecondColor", secondColor);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound tag = super.toInitialChunkDataNbt();
        this.writeNbt(tag);
        return tag;
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        firstColor = NbtHelper.getOrDefaultInt(nbt -> nbt.getCompound("display").getInt("FirstColor"), 15712278, itemStack.getNbt());
        secondColor = NbtHelper.getOrDefaultInt(nbt -> nbt.getCompound("display").getInt("SecondColor"), 4607909, itemStack.getNbt());
    }
}
