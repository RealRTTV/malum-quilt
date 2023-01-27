package ca.rttv.malum.registry;

import ca.rttv.malum.config.CommonConfig;
import ca.rttv.malum.util.helper.DataHelper;
import net.minecraft.block.Blocks;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.gen.BootstrapContext;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import org.quiltmc.qsl.registry.api.event.DynamicRegistryManagerSetupContext;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;

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

    static void init() {
        BiomeModifications.addFeature((b) -> b.isIn(BiomeTags.FOREST), GenerationStep.Feature.VEGETAL_DECORATION, RUNEWOOD_CHECKED);
    }
    static void init(Registry<ConfiguredFeature<?, ?>> configured, DynamicRegistryManagerSetupContext.RegistryMap registryMap) {
        Holder<ConfiguredFeature<?, ?>> runewood = configured.getHolder(MalumConfiguredFeatureRegistry.CONFIGURED_FEATURE_KEYS.get(MalumConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE_FEATURE)).orElseThrow();
        registryMap.register(RegistryKeys.PLACED_FEATURE, DataHelper.prefix("runewood_tree"), new PlacedFeature(runewood, List.of(new net.minecraft.world.gen.decorator.BlockPredicateFilterPlacementModifier[]{PlacedFeatureUtil.createWouldSurvivePlacementModifier(Blocks.OAK_SAPLING)})));
    }

//    static void bootstrap(BootstrapContext<PlacedFeature> bootstrapContext) {
//        HolderProvider<ConfiguredFeature<?, ?>> holderProvider = bootstrapContext.lookup(RegistryKeys.CONFIGURED_FEATURE);
//        Holder<ConfiguredFeature<?, ?>> soulstoneSurface = holderProvider.getHolderOrThrow(SURFACE_SOULSTONE_CONFIGURED);
//        Holder<ConfiguredFeature<?, ?>> soulstoneUnderground = holderProvider.getHolderOrThrow(UNDERGROUND_SOULSTONE_CONFIGURED);
//        Holder<ConfiguredFeature<?, ?>> bq = holderProvider.getHolderOrThrow(BLAZING_QUARTZ_VEIN_CONFIGURED);
//        Holder<ConfiguredFeature<?, ?>> bs = holderProvider.getHolderOrThrow(BRILLIANT_STONE_VEIN_CONFIGURED);
//        Holder<ConfiguredFeature<?, ?>> runewood = holderProvider.getHolderOrThrow(CONFIGURED_RUNEWOOD_TREE_FEATURE);
//        PlacedFeatureUtil.m_wsgklyng(bootstrapContext, RUNEWOOD_CHECKED, runewood, PlacedFeatureUtil.createWouldSurvivePlacementModifier(Blocks.OAK_SAPLING));
//        PlacedFeatureUtil.m_sdevhksy(bootstrapContext, BRILLIANT_STONE_PLACED, bs, OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.BRILLIANT_STONE_VEIN_AMOUNT, HeightRangePlacementModifier.createUniform(YOffset.fixed(CommonConfig.BRILLIANT_STONE_VEIN_MIN_Y), YOffset.fixed(CommonConfig.BRILLIANT_STONE_VEIN_MAX_Y))));
//        PlacedFeatureUtil.m_sdevhksy(bootstrapContext, SURFACE_SOULSTONE_PLACED, soulstoneSurface, OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.SURFACE_SOULSTONE_VEIN_AMOUNT, HeightRangePlacementModifier.createUniform(YOffset.fixed(CommonConfig.SURFACE_SOULSTONE_VEIN_MIN_Y), YOffset.fixed(CommonConfig.SURFACE_SOULSTONE_VEIN_MAX_Y))));
//        PlacedFeatureUtil.m_sdevhksy(bootstrapContext, UNDERGROUND_SOULSTONE_PLACED, soulstoneUnderground, OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.UNDERGROUND_SOULSTONE_VEIN_AMOUNT, HeightRangePlacementModifier.createUniform(YOffset.fixed(CommonConfig.UNDERGROUND_SOULSTONE_VEIN_MIN_Y), YOffset.fixed(CommonConfig.UNDERGROUND_SOULSTONE_VEIN_MAX_Y))));
//        PlacedFeatureUtil.m_sdevhksy(bootstrapContext, BLAZING_QUARTZ_PLACED, bq, OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.BLAZING_QUARTZ_VEIN_AMOUNT, PlacedFeatureUtil.EIGHT_ABOVE_AND_BELOW_RANGE));
//    }
}
