package ca.rttv.malum.world.gen.feature;

import ca.rttv.malum.block.GradientLeavesBlock;
import ca.rttv.malum.util.helper.BlockHelper;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.world.gen.MalumFiller;
import ca.rttv.malum.world.gen.MalumFiller.BlockStateEntry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class GradientTreeFeature extends Feature<DefaultFeatureConfig> {
    private static final int minimumSapBlockCount = 2;
    private static final int extraSapBlockCount = 1;
    private static final int minimumTrunkHeight = 7;
    private static final int extraTrunkHeight = 3;
    private static final int minimumSideTrunkHeight = 0;
    private static final int extraSideTrunkHeight = 2;
    private static final int minimumDownwardsBranchOffset = 2;
    private static final int extraDownwardsBranchOffset = 2;
    private static final int minimumBranchCoreOffset = 2;
    private static final int branchCoreOffsetExtra = 1;
    private static final int minimumBranchHeight = 3;
    private static final int branchHeightExtra = 2;
    private final Block exposed;
    private final Block leaves;
    private final Block log;
    private final Block sapling;

    public GradientTreeFeature(Block exposed, Block leaves, Block log, Block sapling) {
        super(DefaultFeatureConfig.CODEC);
        this.exposed = exposed;
        this.leaves = leaves;
        this.log = log;
        this.sapling = sapling;
    }

    public void downwardsTrunk(StructureWorldAccess world, MalumFiller filler, BlockPos pos) {
        int i = 0;
        do {
            i++;
            BlockPos trunkPos = pos.down(i);
            if (canPlace(world, trunkPos)) {
                filler.entries.add(new BlockStateEntry(log.getDefaultState(), trunkPos));
            } else {
                break;
            }
            if (i > world.getHeight()) {
                break;
            }
        }
        while (true);
    }

    public void makeLeafBlob(MalumFiller filler, RandomGenerator rand, BlockPos pos) {
        makeLeafSlice(filler, pos, 1, 0);
        makeLeafSlice(filler, pos.up(1), 2, 1);
        makeLeafSlice(filler, pos.up(2), 2, 2);
        makeLeafSlice(filler, pos.up(3), 2, 3);
        makeLeafSlice(filler, pos.up(4), 1, 4);
    }

    public void makeLeafSlice(MalumFiller filler, BlockPos pos, int leavesSize, int leavesColor) {
        for (int x = -leavesSize; x <= leavesSize; x++) {
            for (int z = -leavesSize; z <= leavesSize; z++) {
                if (Math.abs(x) == leavesSize && Math.abs(z) == leavesSize) {
                    continue;
                }
                BlockPos leavesPos = new BlockPos(pos).add(x, 0, z);
                filler.entries.add(new BlockStateEntry(leaves.getDefaultState().with(LeavesBlock.DISTANCE, 1).with(GradientLeavesBlock.COLOR, leavesColor), leavesPos));
            }
        }
    }

    public boolean canPlace(StructureWorldAccess level, BlockPos pos) {
        if (level.isOutOfHeightLimit(pos)) {
            return false;
        }
        BlockState state = level.getBlockState(pos);
        return state.isOf(sapling) || level.isAir(pos) || state.getMaterial().isReplaceable();
    }

    @Override
    public boolean place(FeatureContext<DefaultFeatureConfig> ctx) {
        StructureWorldAccess world = ctx.getWorld();
        BlockPos pos = ctx.getOrigin();
        RandomGenerator rand = ctx.getRandom();
        if (world.isAir(pos.down()) || !sapling.getDefaultState().canPlaceAt(world, pos)) {
            return false;
        }
        BlockState defaultLog = log.getDefaultState();

        MalumFiller treeFiller = new MalumFiller(false);
        MalumFiller leavesFiller = new MalumFiller(true);

        int trunkHeight = minimumTrunkHeight + rand.nextInt(extraTrunkHeight + 1);
        BlockPos trunkTop = pos.up(trunkHeight);
        Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

        for (int i = 0; i <= trunkHeight; i++) //trunk placement
        {
            BlockPos trunkPos = pos.up(i);
            if (canPlace(world, trunkPos)) {
                treeFiller.entries.add(new BlockStateEntry(defaultLog, trunkPos));
            } else {
                return false;
            }
        }

        makeLeafBlob(leavesFiller, rand, trunkTop);
        for (Direction direction : directions) //side trunk placement
        {
            int sideTrunkHeight = minimumSideTrunkHeight + rand.nextInt(extraSideTrunkHeight + 1);
            for (int i = 0; i < sideTrunkHeight; i++) {
                BlockPos sideTrunkPos = pos.offset(direction).up(i);
                if (canPlace(world, sideTrunkPos)) {
                    treeFiller.entries.add(new BlockStateEntry(defaultLog, sideTrunkPos));
                } else {
                    return false;
                }
            }
            downwardsTrunk(world, treeFiller, pos.offset(direction));
        }
        for (Direction direction : directions) //tree top placement
        {
            int branchCoreOffset = minimumDownwardsBranchOffset + rand.nextInt(extraDownwardsBranchOffset + 1);
            int branchOffset = minimumBranchCoreOffset + rand.nextInt(branchCoreOffsetExtra + 1);
            BlockPos branchStartPos = trunkTop.down(branchCoreOffset).offset(direction, branchOffset);
            for (int i = 0; i < branchOffset; i++) //branch connection placement
            {
                BlockPos branchConnectionPos = branchStartPos.offset(direction.getOpposite(), i);
                if (canPlace(world, branchConnectionPos)) {
                    treeFiller.entries.add(new BlockStateEntry(defaultLog.with(PillarBlock.AXIS, direction.getAxis()), branchConnectionPos));
                } else {
                    return false;
                }
            }
            int branchHeight = minimumBranchHeight + rand.nextInt(branchHeightExtra + 1);
            for (int i = 0; i < branchHeight; i++) //branch placement
            {
                BlockPos branchPos = branchStartPos.up(i);
                if (canPlace(world, branchPos)) {
                    treeFiller.entries.add(new BlockStateEntry(defaultLog, branchPos));
                } else {
                    return false;
                }
            }
            makeLeafBlob(leavesFiller, rand, branchStartPos.up(1));
        }
        int sapBlockCount = minimumSapBlockCount + rand.nextInt(extraSapBlockCount + 1);
        int[] sapBlockIndexes = DataHelper.nextInts(rand, sapBlockCount, treeFiller.entries.size());
        for (Integer index : sapBlockIndexes) {
            BlockStateEntry oldEntry = treeFiller.entries.get(index);
            BlockState newState = BlockHelper.getBlockStateWithExistingProperties(oldEntry.state(), exposed.getDefaultState());
            treeFiller.replaceAt(index, new BlockStateEntry(newState, oldEntry.pos()));
        }
        treeFiller.fill(world);
        leavesFiller.fill(world);
        return true;
    }
}