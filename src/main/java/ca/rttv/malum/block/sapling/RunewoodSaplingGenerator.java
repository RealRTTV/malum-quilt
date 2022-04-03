package ca.rttv.malum.block.sapling;

import ca.rttv.malum.registry.MalumRegistry;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.Holder;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class RunewoodSaplingGenerator extends SaplingGenerator {
    protected Holder<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return MalumRegistry.CONFIGURED_RUNEWOOD_TREE_FEATURE;
    }
}
