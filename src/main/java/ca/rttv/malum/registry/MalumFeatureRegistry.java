package ca.rttv.malum.registry;

import ca.rttv.malum.world.gen.feature.GradientTreeFeature;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumBlockRegistry.*;

@SuppressWarnings("unused")
public interface MalumFeatureRegistry {
    Map<Identifier, Feature<? extends FeatureConfig>> FEATURES = new LinkedHashMap<>();

    Feature<DefaultFeatureConfig> RUNEWOOD_TREE_FEATURE = register("runewood_tree", new GradientTreeFeature(EXPOSED_RUNEWOOD_LOG, RUNEWOOD_LEAVES, RUNEWOOD_LOG, RUNEWOOD_SAPLING));
    Feature<DefaultFeatureConfig> SOULWOOD_TREE_FEATURE = register("soulwood_tree", new GradientTreeFeature(EXPOSED_SOULWOOD_LOG, SOULWOOD_LEAVES, SOULWOOD_LOG, SOULWOOD_SAPLING));

    static <C extends FeatureConfig, F extends Feature<C>> F register(String id, F feature) {
        FEATURES.put(new Identifier(MODID, id), feature);
        return feature;
    }

    static void init() {
        FEATURES.forEach((id, feature) -> Registry.register(Registries.FEATURE_WORLDGEN, id, feature));
    }
}
