package ca.rttv.malum.block.sapling;

import ca.rttv.malum.registry.MalumConfiguredFeatureRegistry;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class SoulwoodSaplingGenerator extends SaplingGenerator {
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(RandomGenerator random, boolean bees) {
        return MalumConfiguredFeatureRegistry.CONFIGURED_FEATURE_KEYS.get(MalumConfiguredFeatureRegistry.CONFIGURED_SOULWOOD_TREE_FEATURE);
    }
}
