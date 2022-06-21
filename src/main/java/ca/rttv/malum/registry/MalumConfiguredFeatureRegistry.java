package ca.rttv.malum.registry;

import ca.rttv.malum.Malum;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.ConfiguredFeatureUtil;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;
import org.quiltmc.qsl.worldgen.biome.api.ModificationPhase;

import java.util.List;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumBlockRegistry.*;
import static ca.rttv.malum.registry.MalumFeatureRegistry.RUNEWOOD_TREE_FEATURE;
import static ca.rttv.malum.registry.MalumFeatureRegistry.SOULWOOD_TREE_FEATURE;
import static ca.rttv.malum.registry.MalumPlacedFeatureRegistry.*;
import static net.minecraft.world.gen.feature.OreConfiguredFeatures.*;

@SuppressWarnings("unused")
public interface MalumConfiguredFeatureRegistry {
    List<OreFeatureConfig.Target> BLAZING_QUARTZ_TARGETS  = List.of(OreFeatureConfig.createTarget(NETHERRACK, BLAZING_QUARTZ_ORE.getDefaultState()));
    List<OreFeatureConfig.Target> SOULSTONE_ORE_TARGETS  = List.of(OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, SOULSTONE_ORE.getDefaultState()), OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, DEEPSLATE_SOULSTONE_ORE.getDefaultState()));
    List<OreFeatureConfig.Target> BRILLIANCE_ORE_TARGETS = List.of(OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, BRILLIANT_STONE.getDefaultState()), OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, BRILLIANT_DEEPSLATE.getDefaultState()));

    Holder<ConfiguredFeature<OreFeatureConfig, ?>>     LOWER_ORE_SOULSTONE_CONFIGURED       = register("lower_ore_soulstone", Feature.ORE, new OreFeatureConfig(SOULSTONE_ORE_TARGETS, 12)); // i == maxSize
    Holder<ConfiguredFeature<OreFeatureConfig, ?>>     UPPER_ORE_SOULSTONE_CONFIGURED       = register("upper_ore_soulstone", Feature.ORE, new OreFeatureConfig(SOULSTONE_ORE_TARGETS, 6)); // i == maxSize
    Holder<ConfiguredFeature<OreFeatureConfig, ?>>     ORE_BRILLIANCE_CONFIGURED            = register("ore_brilliance",      Feature.ORE, new OreFeatureConfig(BRILLIANCE_ORE_TARGETS, 2)); // i == maxSize
    Holder<ConfiguredFeature<OreFeatureConfig, ?>>     ORE_BLAZING_QUARTZ_CONFIGURED        = register("blazing_quartz",      Feature.ORE, new OreFeatureConfig(BLAZING_QUARTZ_TARGETS, 14)); // i == maxSize
    Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> CONFIGURED_RUNEWOOD_TREE_FEATURE     = register("runewood_tree",       RUNEWOOD_TREE_FEATURE);
    Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> CONFIGURED_SOULWOOD_TREE_FEATURE     = register("soulwood_tree",       SOULWOOD_TREE_FEATURE);

    static Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> register(String id, Feature<DefaultFeatureConfig> feature) {
        return ConfiguredFeatureUtil.register(new Identifier(MODID, id).toString(), feature);
    }

    static <FC extends FeatureConfig, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String id, F feature, FC featureConfig) {
        return ConfiguredFeatureUtil.register(new Identifier(MODID, id).toString(), feature, featureConfig);
    }

    static void init() {
        BiomeModifications.create(new Identifier(Malum.MODID, "malum"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.isIn(BiomeTags.IS_FOREST), (biomeSelectionContext, biomeModificationContext) -> biomeModificationContext.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, RUNEWOOD_CHECKED.value()))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), biomeModificationContext -> biomeModificationContext.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, LOWER_ORE_SOULSTONE_PLACED.value()))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), biomeModificationContext -> biomeModificationContext.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, UPPER_ORE_SOULSTONE_PLACED.value()))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), biomeModificationContext -> biomeModificationContext.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, ORE_BRILLIANCE_PLACED.value()))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInTheNether(), biomeModificationContext -> biomeModificationContext.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, ORE_BLAZING_QUARTZ_PLACED.value()));

    }
}
