package ca.rttv.malum.registry;

import ca.rttv.malum.config.CommonConfig;
import net.minecraft.registry.Holder;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;

import java.util.List;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumConfiguredFeatureRegistry.*;

@SuppressWarnings("unused")
public interface MalumPlacedFeatureRegistry {
    Holder<PlacedFeature> BRILLIANT_STONE_PLACED       = register("brilliant_stone",       BRILLIANT_STONE_VEIN_CONFIGURED,  OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.BRILLIANT_STONE_VEIN_AMOUNT,       HeightRangePlacementModifier.createUniform(YOffset.fixed(CommonConfig.BRILLIANT_STONE_VEIN_MIN_Y),       YOffset.fixed(CommonConfig.BRILLIANT_STONE_VEIN_MAX_Y))));
    Holder<PlacedFeature> SURFACE_SOULSTONE_PLACED     = register("surface_soulstone",     SURFACE_SOULSTONE_CONFIGURED,     OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.SURFACE_SOULSTONE_VEIN_AMOUNT,     HeightRangePlacementModifier.createUniform(YOffset.fixed(CommonConfig.SURFACE_SOULSTONE_VEIN_MIN_Y),     YOffset.fixed(CommonConfig.SURFACE_SOULSTONE_VEIN_MAX_Y))));
    Holder<PlacedFeature> UNDERGROUND_SOULSTONE_PLACED = register("underground_soulstone", UNDERGROUND_SOULSTONE_CONFIGURED, OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.UNDERGROUND_SOULSTONE_VEIN_AMOUNT, HeightRangePlacementModifier.createUniform(YOffset.fixed(CommonConfig.UNDERGROUND_SOULSTONE_VEIN_MIN_Y), YOffset.fixed(CommonConfig.UNDERGROUND_SOULSTONE_VEIN_MAX_Y))));
    Holder<PlacedFeature> BLAZING_QUARTZ_PLACED        = register("blazing_quartz",        BLAZING_QUARTZ_VEIN_CONFIGURED,   OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.BLAZING_QUARTZ_VEIN_AMOUNT,        PlacedFeatureUtil.EIGHT_ABOVE_AND_BELOW_RANGE));
    Holder<PlacedFeature> RUNEWOOD_CHECKED             = register("runewood_tree",         CONFIGURED_RUNEWOOD_TREE_FEATURE, VegetationPlacedFeatures.treePlacementModifiers(RarityFilterPlacementModifier.create(10),     MalumBlockRegistry.RUNEWOOD_SAPLING));

    static Holder<PlacedFeature> register(String id, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers) {
        return PlacedFeatureUtil.register(MODID + ":" + id, configuredFeature, modifiers);
    }

    static void init() {

    }
}
