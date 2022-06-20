package ca.rttv.malum.rite;

import ca.rttv.malum.block.TotemPoleBlock;
import ca.rttv.malum.block.entity.TotemBaseBlockEntity;
import ca.rttv.malum.recipe.BlockTransmutationRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;

import java.util.stream.StreamSupport;

import static ca.rttv.malum.registry.MalumBlockRegistry.SOULWOOD_TOTEM_BASE;
import static ca.rttv.malum.registry.MalumBlockRegistry.SOULWOOD_TOTEM_POLE;

public class ArcaneRite extends Rite {
    public ArcaneRite(Item... items) {
        super(items);
    }

    @Override
    public void onTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random, long tick) {
        //noinspection ConstantConditions
        Rite rite = ((TotemBaseBlockEntity) world.getBlockEntity(pos)).rite;
        world.setBlockState(pos, SOULWOOD_TOTEM_BASE.getDefaultState());
        BlockPos up = pos.up();
        BlockState upState = world.getBlockState(up);
        while (upState.getBlock() instanceof TotemPoleBlock) {
            world.setBlockState(up, SOULWOOD_TOTEM_POLE.getStateWithProperties(upState));
            up = up.up();
            upState = world.getBlockState(up);
        }
        //noinspection ConstantConditions
        ((TotemBaseBlockEntity) world.getBlockEntity(pos)).rite = rite;
    }

    @Override
    public void onCorruptTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random, long tick) {
        if (tick % 100 != 0) {
            return;
        }

        StreamSupport.stream(BlockPos.iterateOutwards(pos.down(), 2, 0, 2).spliterator(), false).filter(mutationPos -> !mutationPos.up().equals(pos)).forEach(mutationPos -> {
            BlockTransmutationRecipe recipe = BlockTransmutationRecipe.getRecipe(world.getBlockState(mutationPos).getBlock(), world);
            if (recipe != null) {
                world.setBlockState(mutationPos, Blocks.AIR.getDefaultState());
                Item item = recipe.getOutput().getItem();
                if (item instanceof BlockItem blockItem) {
                    FallingBlockEntity.fall(world, mutationPos, blockItem.getBlock().getDefaultState());
                } else {
                    world.spawnEntity(new ItemEntity(world, mutationPos.getX() + 0.5d, mutationPos.getY() + 0.5d, mutationPos.getZ() + 0.5d, item.getDefaultStack(), 0.0d, 0.2d, 0.0d));
                }
            }
        });
    }
}
