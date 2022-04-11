package ca.rttv.malum.util.helper;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.function.Function;

public final class NbtHelper {
    public static NbtList getOrDefaultList(Function<NbtCompound, NbtList> getter, NbtList defaultValue, NbtCompound nbt) {
        try {
            return getter.apply(nbt);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    public static NbtCompound getOrDefaultCompound(Function<NbtCompound, NbtCompound> getter, NbtCompound defaultValue, NbtCompound nbt) {
        try {
            return getter.apply(nbt);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    public static int getOrDefaultInt(Function<NbtCompound, Integer> getter, int defaultValue, NbtCompound nbt) {
        try {
            return getter.apply(nbt);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    public static double getOrDefaultDouble(Function<NbtCompound, Double> getter, double defaultValue, NbtCompound nbt) {
        try {
            return getter.apply(nbt);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    public static int getOrThrowInt(NbtCompound nbt, String value) {
        if (!nbt.contains(value)) {
            throw new NullPointerException();
        }
        return nbt.getInt(value);
    }

    public static double getOrThrowDouble(NbtCompound nbt, String value) {
        if (!nbt.contains(value)) {
            throw new NullPointerException();
        }
        return nbt.getDouble(value);
    }
}
