package ca.rttv.malum.world.gen;

import ca.rttv.malum.util.helper.BlockHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Objects;

public class MalumFiller {
    public final boolean careful;
    public final ArrayList<BlockStateEntry> entries = new ArrayList<>();

    public MalumFiller(boolean careful) {
        this.careful = careful;
    }

    public void fill(StructureWorldAccess world) {
        for (BlockStateEntry entry : entries) {
            if (careful && !entry.canPlace(world)) {
                continue;
            }
            entry.place(world);
        }
    }

    public void replaceAt(int index, BlockStateEntry entry) {
        entries.set(index, entry);
    }

    public record BlockStateEntry(BlockState state, BlockPos pos) {

        public boolean canPlace(StructureWorldAccess world) {
            return canPlace(world, pos);
        }

        public boolean canPlace(StructureWorldAccess world, BlockPos pos) {
            if (world.isOutOfHeightLimit(pos)) {
                return false;
            }
            BlockState state = world.getBlockState(pos);
            return world.isAir(pos) || state.getMaterial().isReplaceable();
        }

        public void place(StructureWorldAccess world) {
            world.setBlockState(pos, state, 19);
            if (world instanceof World) {
                BlockHelper.updateState((World) world, pos);
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (BlockStateEntry) obj;
            return Objects.equals(this.state, that.state) &&
                    Objects.equals(this.pos, that.pos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(state, pos);
        }

        @Override
        public String toString() {
            return "BlockStateEntry[" +
                    "state=" + state + ", " +
                    "pos=" + pos + ']';
        }

    }
}