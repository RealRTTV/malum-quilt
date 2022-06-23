package ca.rttv.malum.rite;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Rite {
    protected final Item[] items;

    public Rite(Item... items) {
        this.items = items;
    }

    @Override
    public final int hashCode() {
        List<Item> list = new ArrayList<>();
        Collections.addAll(list, items);
        return list.hashCode();
    }

    public abstract void onTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random, long tick);

    public abstract void onCorruptTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random, long tick);

    public final Item[] items() {
        return items;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Rite that = (Rite) obj;
        return Arrays.equals(this.items, that.items);
    }

    @Override
    public final String toString() {
        return "Rite[" +
                "items=" + Arrays.toString(items) + ']';
    }

}
