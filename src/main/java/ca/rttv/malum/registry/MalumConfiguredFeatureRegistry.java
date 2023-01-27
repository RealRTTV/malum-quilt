package ca.rttv.malum.registry;

import ca.rttv.malum.config.CommonConfig;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.ConfiguredFeatureUtil;
import org.quiltmc.qsl.registry.api.event.DynamicRegistryManagerSetupContext;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumBlockRegistry.*;
import static ca.rttv.malum.registry.MalumFeatureRegistry.RUNEWOOD_TREE_FEATURE;
import static ca.rttv.malum.registry.MalumFeatureRegistry.SOULWOOD_TREE_FEATURE;
import static net.minecraft.registry.tag.BlockTags.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.registry.tag.BlockTags.STONE_ORE_REPLACEABLES;


public interface MalumConfiguredFeatureRegistry {
    Map<Identifier, ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = new LinkedHashMap<>();
    Map<ConfiguredFeature<?, ?>, RegistryKey<ConfiguredFeature<?, ?>>> CONFIGURED_FEATURE_KEYS = new LinkedHashMap<>();
    List<OreFeatureConfig.Target> BLAZING_QUARTZ_TARGETS = List.of(OreFeatureConfig.createTarget(new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER), BLAZING_QUARTZ_ORE.getDefaultState()));
    List<OreFeatureConfig.Target> SOULSTONE_ORE_TARGETS  = List.of(OreFeatureConfig.createTarget(new TagMatchRuleTest(STONE_ORE_REPLACEABLES), SOULSTONE_ORE.getDefaultState()), OreFeatureConfig.createTarget(new TagMatchRuleTest(DEEPSLATE_ORE_REPLACEABLES), DEEPSLATE_SOULSTONE_ORE.getDefaultState()));
    List<OreFeatureConfig.Target> BRILLIANCE_ORE_TARGETS = List.of(OreFeatureConfig.createTarget(new TagMatchRuleTest(STONE_ORE_REPLACEABLES), BRILLIANT_STONE.getDefaultState()), OreFeatureConfig.createTarget(new TagMatchRuleTest(DEEPSLATE_ORE_REPLACEABLES), BRILLIANT_DEEPSLATE.getDefaultState()));

    ConfiguredFeature<?, ?> UNDERGROUND_SOULSTONE_CONFIGURED = register("underground_soulstone", new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(SOULSTONE_ORE_TARGETS, CommonConfig.UNDERGROUND_SOULSTONE_VEIN_SIZE)));
    ConfiguredFeature<?, ?> SURFACE_SOULSTONE_CONFIGURED     = register("surface_soulstone", new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(SOULSTONE_ORE_TARGETS, CommonConfig.SURFACE_SOULSTONE_VEIN_SIZE)));
    ConfiguredFeature<?, ?> BRILLIANT_STONE_VEIN_CONFIGURED  = register("brilliant_stone", new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(BRILLIANCE_ORE_TARGETS, CommonConfig.BRILLIANT_STONE_VEIN_SIZE)));
    ConfiguredFeature<?, ?> BLAZING_QUARTZ_VEIN_CONFIGURED   = register("blazing_quartz", new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(BLAZING_QUARTZ_TARGETS, CommonConfig.BLAZING_QUARTZ_VEIN_SIZE)));
    ConfiguredFeature<?, ?> CONFIGURED_RUNEWOOD_TREE_FEATURE = register("runewood_tree", new ConfiguredFeature<>(RUNEWOOD_TREE_FEATURE, DefaultFeatureConfig.INSTANCE));
    ConfiguredFeature<?, ?> CONFIGURED_SOULWOOD_TREE_FEATURE = register("soulwood_tree", new ConfiguredFeature<>(SOULWOOD_TREE_FEATURE, DefaultFeatureConfig.INSTANCE));

    static <C extends FeatureConfig, E extends Feature<C>, F extends ConfiguredFeature<C, E>> F register(String id, F feature) {
        CONFIGURED_FEATURES.put(new Identifier(MODID, id), feature);
        return feature;
    }
    static void init(Registry<ConfiguredFeature<?, ?>> configured) {
        CONFIGURED_FEATURES.forEach((id, feature) -> {
            Registry.register(configured, id, feature);
            CONFIGURED_FEATURE_KEYS.put(feature, configured.getKey(feature).orElseThrow());
        });

//        ConfiguredFeatureUtil.m_rajyrrbd(bootstrapContext, SURFACE_SOULSTONE_CONFIGURED, Feature.ORE, new OreFeatureConfig(SOULSTONE_ORE_TARGETS, CommonConfig.SURFACE_SOULSTONE_VEIN_SIZE));
//        ConfiguredFeatureUtil.m_rajyrrbd(bootstrapContext, UNDERGROUND_SOULSTONE_CONFIGURED, Feature.ORE, new OreFeatureConfig(SOULSTONE_ORE_TARGETS, CommonConfig.UNDERGROUND_SOULSTONE_VEIN_SIZE));
//        ConfiguredFeatureUtil.m_rajyrrbd(bootstrapContext, BRILLIANT_STONE_VEIN_CONFIGURED, Feature.ORE, new OreFeatureConfig(BRILLIANCE_ORE_TARGETS, CommonConfig.BRILLIANT_STONE_VEIN_SIZE));
//        ConfiguredFeatureUtil.m_rajyrrbd(bootstrapContext, BLAZING_QUARTZ_VEIN_CONFIGURED, Feature.ORE, new OreFeatureConfig(BLAZING_QUARTZ_TARGETS, CommonConfig.BLAZING_QUARTZ_VEIN_SIZE));
    }
}
