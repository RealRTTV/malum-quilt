package ca.rttv.malum.rite;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public abstract class Rite {
    private final Item[] items;

    public Rite(Item... items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        List<Item> list = new ArrayList<>();
        Collections.addAll(list, items);
        return list.hashCode();
    }

    public abstract void onTick(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick);

    public abstract void onCorruptTick(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick);

    public Item[] items() {
        return items;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Rite that = (Rite) obj;
        return Arrays.equals(this.items, that.items);
    }

    @Override
    public String toString() {
        return "Rite[" +
                "items=" + Arrays.toString(items) + ']';
    }

}
