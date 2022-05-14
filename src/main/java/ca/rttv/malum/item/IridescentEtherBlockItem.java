package ca.rttv.malum.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class IridescentEtherBlockItem extends BlockItem implements DyeableItem {
    public IridescentEtherBlockItem(Block block, Item.Settings settings) {
        super(block, settings);
    }

    @Override
    public void setColor(ItemStack stack, int color) {
        stack.getOrCreateSubNbt("display").putInt("SecondColor", color);
    }

    @Override
    public boolean hasColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt("display");
        return nbtCompound != null && nbtCompound.contains("SecondColor", 99);
    }

    @Override
    public int getColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt("display");
        return nbtCompound != null && nbtCompound.contains("SecondColor", 99) ? nbtCompound.getInt("SecondColor") : 4607909;
    }

    @Override
    public void removeColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt("display");
        if (nbtCompound != null) {
            if (nbtCompound.contains("FirstColor")) {
                nbtCompound.remove("FirstColor");
            }
            if (nbtCompound.contains("SecondColor")) {
                nbtCompound.remove("SecondColor");
            }
        }
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = new ItemStack(this);
        NbtCompound nbt = new NbtCompound();
        NbtCompound nbtDisplay = new NbtCompound();
        nbtDisplay.putInt("FirstColor", 15712278);
        nbtDisplay.putInt("SecondColor", 4607909);
        nbt.put("display", nbtDisplay);
        stack.setNbt(nbt);
        return stack;
    }
    public int getFirstColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt("display");
        return nbtCompound != null && nbtCompound.contains("FirstColor", 99) ? nbtCompound.getInt("FirstColor") : 15712278;
    }


    public int getSecondColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt("display");
        return nbtCompound != null && nbtCompound.contains("SecondColor", 99) ? nbtCompound.getInt("SecondColor") : 4607909;
    }
}
