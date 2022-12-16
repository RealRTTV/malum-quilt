package ca.rttv.malum.registry;

import ca.rttv.malum.config.CommonConfig;
import net.minecraft.registry.Holder;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.ConfiguredFeatureUtil;

import java.util.List;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumBlockRegistry.*;
import static ca.rttv.malum.registry.MalumFeatureRegistry.RUNEWOOD_TREE_FEATURE;
import static ca.rttv.malum.registry.MalumFeatureRegistry.SOULWOOD_TREE_FEATURE;
import static net.minecraft.world.gen.feature.OreConfiguredFeatures.*;


public interface MalumConfiguredFeatureRegistry {
    List<OreFeatureConfig.Target> BLAZING_QUARTZ_TARGETS = List.of(OreFeatureConfig.createTarget(NETHERRACK, BLAZING_QUARTZ_ORE.getDefaultState()));
    List<OreFeatureConfig.Target> SOULSTONE_ORE_TARGETS  = List.of(OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, SOULSTONE_ORE.getDefaultState()), OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, DEEPSLATE_SOULSTONE_ORE.getDefaultState()));
    List<OreFeatureConfig.Target> BRILLIANCE_ORE_TARGETS = List.of(OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, BRILLIANT_STONE.getDefaultState()), OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, BRILLIANT_DEEPSLATE.getDefaultState()));

    Holder<ConfiguredFeature<OreFeatureConfig, ?>> UNDERGROUND_SOULSTONE_CONFIGURED = register("underground_soulstone", Feature.ORE, new OreFeatureConfig(SOULSTONE_ORE_TARGETS,  CommonConfig.UNDERGROUND_SOULSTONE_VEIN_SIZE));
    Holder<ConfiguredFeature<OreFeatureConfig, ?>>     SURFACE_SOULSTONE_CONFIGURED     = register("surface_soulstone",     Feature.ORE, new OreFeatureConfig(SOULSTONE_ORE_TARGETS,  CommonConfig.SURFACE_SOULSTONE_VEIN_SIZE));
    Holder<ConfiguredFeature<OreFeatureConfig, ?>>     BRILLIANT_STONE_VEIN_CONFIGURED  = register("brilliant_stone",       Feature.ORE, new OreFeatureConfig(BRILLIANCE_ORE_TARGETS, CommonConfig.BRILLIANT_STONE_VEIN_SIZE));
    Holder<ConfiguredFeature<OreFeatureConfig, ?>>     BLAZING_QUARTZ_VEIN_CONFIGURED   = register("blazing_quartz",        Feature.ORE, new OreFeatureConfig(BLAZING_QUARTZ_TARGETS, CommonConfig.BLAZING_QUARTZ_VEIN_SIZE));
    Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> CONFIGURED_RUNEWOOD_TREE_FEATURE = register("runewood_tree",         RUNEWOOD_TREE_FEATURE);
    Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> CONFIGURED_SOULWOOD_TREE_FEATURE = register("soulwood_tree",         SOULWOOD_TREE_FEATURE);

    static Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> register(String id, Feature<DefaultFeatureConfig> feature) {
        return ConfiguredFeatureUtil.register(new Identifier(MODID, id).toString(), feature);
    }

    static <FC extends FeatureConfig, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String id, F feature, FC featureConfig) {
        return ConfiguredFeatureUtil.register(new Identifier(MODID, id).toString(), feature, featureConfig);
    }

    static void init() {

    }
}
