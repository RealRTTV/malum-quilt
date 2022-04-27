package ca.rttv.malum.util;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Rite(Item... items) {
    @Override
    public int hashCode() {
        List<Item> list = new ArrayList<>();
        Collections.addAll(list, items);
        return list.hashCode();
    }
} // todo, radius
