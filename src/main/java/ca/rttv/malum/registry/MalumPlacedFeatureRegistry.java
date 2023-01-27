package ca.rttv.malum.registry;

import ca.rttv.malum.config.CommonConfig;
import ca.rttv.malum.util.helper.DataHelper;
import net.minecraft.block.Blocks;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.gen.BootstrapContext;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.BlockPredicateFilterPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import org.quiltmc.qsl.registry.api.event.DynamicRegistryManagerSetupContext;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.ModificationPhase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumConfiguredFeatureRegistry.*;

@SuppressWarnings("unused")
public interface MalumPlacedFeatureRegistry {
    RegistryKey<PlacedFeature> BRILLIANT_STONE_PLACED       = RegistryKey.of(RegistryKeys.PLACED_FEATURE, DataHelper.prefix("brilliant_stone"));
    RegistryKey<PlacedFeature> SURFACE_SOULSTONE_PLACED     = RegistryKey.of(RegistryKeys.PLACED_FEATURE, DataHelper.prefix("surface_soulstone"));
    RegistryKey<PlacedFeature> UNDERGROUND_SOULSTONE_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, DataHelper.prefix("underground_soulstone"));
    RegistryKey<PlacedFeature> BLAZING_QUARTZ_PLACED        = RegistryKey.of(RegistryKeys.PLACED_FEATURE, DataHelper.prefix("blazing_quartz"));
    RegistryKey<PlacedFeature> RUNEWOOD_CHECKED        = RegistryKey.of(RegistryKeys.PLACED_FEATURE, DataHelper.prefix("runewood_tree"));

    static void init(Registry<ConfiguredFeature<?, ?>> configured, DynamicRegistryManagerSetupContext.RegistryMap registryMap) {
        Holder<ConfiguredFeature<?, ?>> runewood = configured.getHolder(MalumConfiguredFeatureRegistry.CONFIGURED_FEATURE_KEYS.get(MalumConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE_FEATURE)).orElseThrow();
        registryMap.register(RegistryKeys.PLACED_FEATURE, DataHelper.prefix("runewood_tree"), new PlacedFeature(runewood, VegetationPlacedFeatures.treePlacementModifiers(RarityFilterPlacementModifier.create(40), MalumBlockRegistry.RUNEWOOD_SAPLING)));
        registryMap.register(RegistryKeys.PLACED_FEATURE, DataHelper.prefix("brilliant_stone"), new PlacedFeature(configured.getHolder(MalumConfiguredFeatureRegistry.CONFIGURED_FEATURE_KEYS.get(BRILLIANT_STONE_VEIN_CONFIGURED)).orElseThrow(), OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.BRILLIANT_STONE_VEIN_AMOUNT, HeightRangePlacementModifier.createUniform(YOffset.fixed(CommonConfig.BRILLIANT_STONE_VEIN_MIN_Y), YOffset.fixed(CommonConfig.BRILLIANT_STONE_VEIN_MAX_Y)))));
        registryMap.register(RegistryKeys.PLACED_FEATURE, DataHelper.prefix("blazing_quartz"), new PlacedFeature(configured.getHolder(MalumConfiguredFeatureRegistry.CONFIGURED_FEATURE_KEYS.get(BLAZING_QUARTZ_VEIN_CONFIGURED)).orElseThrow(), OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.BLAZING_QUARTZ_VEIN_AMOUNT, PlacedFeatureUtil.EIGHT_ABOVE_AND_BELOW_RANGE)));
        registryMap.register(RegistryKeys.PLACED_FEATURE, DataHelper.prefix("surface_soulstone"), new PlacedFeature(configured.getHolder(MalumConfiguredFeatureRegistry.CONFIGURED_FEATURE_KEYS.get(SURFACE_SOULSTONE_CONFIGURED)).orElseThrow(), OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.SURFACE_SOULSTONE_VEIN_AMOUNT, HeightRangePlacementModifier.createUniform(YOffset.fixed(CommonConfig.SURFACE_SOULSTONE_VEIN_MIN_Y), YOffset.fixed(CommonConfig.SURFACE_SOULSTONE_VEIN_MAX_Y)))));
        registryMap.register(RegistryKeys.PLACED_FEATURE, DataHelper.prefix("underground_soulstone"), new PlacedFeature(configured.getHolder(MalumConfiguredFeatureRegistry.CONFIGURED_FEATURE_KEYS.get(UNDERGROUND_SOULSTONE_CONFIGURED)).orElseThrow(), OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.UNDERGROUND_SOULSTONE_VEIN_AMOUNT, HeightRangePlacementModifier.createUniform(YOffset.fixed(CommonConfig.UNDERGROUND_SOULSTONE_VEIN_MIN_Y), YOffset.fixed(CommonConfig.UNDERGROUND_SOULSTONE_VEIN_MAX_Y)))));
        BiomeModifications.create(DataHelper.prefix("worldgen"))
            .add(ModificationPhase.ADDITIONS, (b) -> b.isIn(BiomeTags.FOREST), context -> context.getGenerationSettings().addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, RUNEWOOD_CHECKED))
            .add(ModificationPhase.ADDITIONS, (b) -> b.isIn(BiomeTags.OVERWORLD), context -> {
                context.getGenerationSettings().addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, SURFACE_SOULSTONE_PLACED);
                context.getGenerationSettings().addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, UNDERGROUND_SOULSTONE_PLACED);
                context.getGenerationSettings().addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, BRILLIANT_STONE_PLACED);
            }).add(ModificationPhase.ADDITIONS, (b) -> b.isIn(BiomeTags.NETHER), context -> {
                context.getGenerationSettings().addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, BLAZING_QUARTZ_PLACED);
            });
    }
}
