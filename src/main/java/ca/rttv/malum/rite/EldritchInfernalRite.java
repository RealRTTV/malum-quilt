package ca.rttv.malum.rite;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;
import java.util.stream.StreamSupport;

public class EldritchInfernalRite extends Rite {
    public EldritchInfernalRite(Item... items) {
        super(items);
    }

    @Override
    public void onTick(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick) {
        if (tick % 60 != 0) {
            return;
        }

        Block downBlock = world.getBlockState(pos.down()).getBlock();

        Item item;
        if ((item = downBlock.asItem()) == Items.AIR) {
            return;
        }

        Recipe<?> recipe = world.getRecipeManager().listAllOfType(RecipeType.SMELTING).stream().filter(smeltingRecipe -> smeltingRecipe.getIngredients().get(0).test(new ItemStack(item))).findFirst().orElse(null);
        if (recipe == null) {
            return;
        }

        Block block = ((BlockItem) recipe.getOutput().getItem()).getBlock();

        StreamSupport.stream(BlockPos.iterateOutwards(pos.down(), 2, 0, 2).spliterator(), false).filter(possiblePos -> !possiblePos.up().equals(pos) && world.getBlockState(possiblePos).isOf(world.getBlockState(pos.down()).getBlock())).forEach(possiblePos -> {
            world.breakBlock(possiblePos, false);
            world.setBlockState(possiblePos, block.getDefaultState());
            // todo, particle
        });
    }

    @Override
    public void onCorruptTick(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick) {
        if (tick % 20 != 0) {
            return;
        }

        StreamSupport.stream(BlockPos.iterateOutwards(pos.down(), 2, 0, 2).spliterator(), false).filter(possiblePos -> !possiblePos.up().equals(pos) && world.getBlockState(possiblePos).isOf(Blocks.STONE)).forEach(possiblePos -> {
            world.breakBlock(possiblePos, false);
            world.setBlockState(possiblePos, Blocks.NETHERRACK.getDefaultState());
            // todo, particle
        });
    }
}
