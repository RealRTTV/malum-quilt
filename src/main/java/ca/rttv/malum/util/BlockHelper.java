package ca.rttv.malum.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

public final class BlockHelper {
    public static BlockState getBlockStateWithExistingProperties(BlockState oldState, BlockState newState) {
        BlockState finalState = newState;
        for (Property<?> property : oldState.getProperties()) {
            if (newState.contains(property)) {
                finalState = newStateWithOldProperty(oldState, finalState, property);
            }
        }
        return finalState;
    }

    public static BlockState setBlockStateWithExistingProperties(World world, BlockPos pos, BlockState newState, int flags) {
        BlockState oldState = world.getBlockState(pos);
        BlockState finalState = getBlockStateWithExistingProperties(oldState, newState);
        world.updateListeners(pos, oldState, finalState, flags);
        world.setBlockState(pos, finalState, flags);
        return finalState;
    }

    public static <T extends Comparable<T>> BlockState newStateWithOldProperty(BlockState oldState, BlockState newState, Property<T> property) {
        return newState.with(property, oldState.get(property));
    }

    public static void saveBlockPos(NbtCompound compound, BlockPos pos) {
        compound.putInt("X", pos.getX());
        compound.putInt("Y", pos.getY());
        compound.putInt("Z", pos.getZ());
    }

    public static void saveBlockPos(NbtCompound compound, BlockPos pos, String prefix) {
        compound.putInt(prefix + "X", pos.getX());
        compound.putInt(prefix + "Y", pos.getY());
        compound.putInt(prefix + "Z", pos.getZ());
    }

    public static BlockPos loadBlockPos(NbtCompound tag) {
        return new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z"));
    }

    public static BlockPos loadBlockPos(NbtCompound compound, String prefix) {
        return new BlockPos(compound.getInt(prefix + "X"), compound.getInt(prefix + "Y"), compound.getInt(prefix + "Z"));
    }

    public static <T> ArrayList<T> getBlockEntities(Class<T> type, World world, BlockPos pos, int range, Predicate<T> predicate) {
        return getBlockEntities(type, world, pos, range, range, range, predicate);
    }

    public static <T> ArrayList<T> getBlockEntities(Class<T> type, World world, BlockPos pos, int x, int y, int z, Predicate<T> predicate) {
        ArrayList<T> blockEntities = getBlockEntities(type, world, pos, x, y, z);
        blockEntities.removeIf(b -> !predicate.test(b));
        return blockEntities;
    }

    public static <T> ArrayList<T> getBlockEntities(Class<T> type, World world, BlockPos pos, int range) {
        return getBlockEntities(type, world, pos, range, range, range);
    }

    public static <T> ArrayList<T> getBlockEntities(Class<T> type, World world, BlockPos pos, int x, int y, int z) {
        return getBlockEntities(type, world, pos, -x, -y, -z, x, y, z);
    }

    public static <T> ArrayList<T> getBlockEntities(Class<T> type, World world, BlockPos pos, int x1, int y1, int z1, int x2, int y2, int z2) {
        return getBlockEntities(type, world, new Box(pos.getX() + x1, pos.getY() + y1, pos.getZ() + z1, pos.getX() + x2, pos.getY() + y2, pos.getZ() + z2));
    }

    public static <T> ArrayList<T> getBlockEntities(Class<T> type, World world, Box box) {
        ArrayList<T> tileList = new ArrayList<>();
        for (int i = (int) Math.floor(box.minX); i < (int) Math.ceil(box.maxX) + 16; i += 16) {
            for (int j = (int) Math.floor(box.minZ); j < (int) Math.ceil(box.maxZ) + 16; j += 16) {
                Chunk chunk = world.getChunk(new BlockPos(i, 0, j));
                Set<BlockPos> tiles = chunk.getBlockEntityPositions();
                for (BlockPos p : tiles)
                    if (box.contains(p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5)) {
                        BlockEntity t = world.getBlockEntity(p);
                        if (type.isInstance(t)) {
                            tileList.add((T) t);
                        }
                    }
            }
        }
        return tileList;
    }

    public static ArrayList<BlockPos> getBlocks(BlockPos pos, int range, Predicate<BlockPos> predicate) {
        return getBlocks(pos, range, range, range, predicate);
    }

    public static ArrayList<BlockPos> getBlocks(BlockPos pos, int x, int y, int z, Predicate<BlockPos> predicate) {
        ArrayList<BlockPos> blocks = getBlocks(pos, x, y, z);
        blocks.removeIf(b -> !predicate.test(b));
        return blocks;
    }

    public static ArrayList<BlockPos> getBlocks(BlockPos pos, int x, int y, int z) {
        return getBlocks(pos, -x, -y, -z, x, y, z);
    }

    public static ArrayList<BlockPos> getBlocks(BlockPos pos, int x1, int y1, int z1, int x2, int y2, int z2) {
        ArrayList<BlockPos> positions = new ArrayList<>();
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    positions.add(pos.add(x, y, z));
                }
            }
        }
        return positions;
    }

    public static ArrayList<BlockPos> getPlaneOfBlocks(BlockPos pos, int range, Predicate<BlockPos> predicate) {
        return getPlaneOfBlocks(pos, range, range, predicate);
    }

    public static ArrayList<BlockPos> getPlaneOfBlocks(BlockPos pos, int x, int z, Predicate<BlockPos> predicate) {
        ArrayList<BlockPos> blocks = getPlaneOfBlocks(pos, x, z);
        blocks.removeIf(b -> !predicate.test(b));
        return blocks;
    }

    public static ArrayList<BlockPos> getPlaneOfBlocks(BlockPos pos, int x, int z) {
        return getPlaneOfBlocks(pos, -x, -z, x, z);
    }

    public static ArrayList<BlockPos> getPlaneOfBlocks(BlockPos pos, int x1, int z1, int x2, int z2) {
        ArrayList<BlockPos> positions = new ArrayList<>();
        for (int x = x1; x <= x2; x++) {
            for (int z = z1; z <= z2; z++) {
                positions.add(new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z));
            }
        }
        return positions;
    }

    public static void updateState(World world, BlockPos pos) {
        updateState(world.getBlockState(pos), world, pos);
    }

    public static void updateState(BlockState state, World world, BlockPos pos) {
        world.updateListeners(pos, state, state, 2);
        world.markDirty(pos);
    }

    public static void updateAndNotifyState(World world, BlockPos pos) {
        updateAndNotifyState(world.getBlockState(pos), world, pos);
    }

    public static void updateAndNotifyState(BlockState state, World world, BlockPos pos) {
        updateState(state, world, pos);
        state.updateNeighbors(world, pos, 2);
        world.updateComparators(pos, state.getBlock());
    }
}