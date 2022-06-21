package ca.rttv.malum.registry;

import net.minecraft.util.Holder;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;

import java.util.List;

import static ca.rttv.malum.registry.MalumConfiguredFeatureRegistry.*;

@SuppressWarnings("unused")
public interface MalumPlacedFeatureRegistry {
    Holder<PlacedFeature> ORE_BRILLIANCE_PLACED      = register("malum:ore_brilliance",      ORE_BRILLIANCE_CONFIGURED,      OrePlacedFeatures.commonOrePlacementModifiers(4, HeightRangePlacementModifier.createUniform(YOffset.fixed(-80), YOffset.fixed(40)))); // y -80 and y 40, count == spawn count
    Holder<PlacedFeature> UPPER_ORE_SOULSTONE_PLACED = register("malum:upper_ore_soulstone", UPPER_ORE_SOULSTONE_CONFIGURED, OrePlacedFeatures.commonOrePlacementModifiers(5, HeightRangePlacementModifier.createUniform(YOffset.fixed(60),  YOffset.fixed(100)))); // y 60 to y 100, count == spawn count
    Holder<PlacedFeature> LOWER_ORE_SOULSTONE_PLACED = register("malum:lower_ore_soulstone", LOWER_ORE_SOULSTONE_CONFIGURED, OrePlacedFeatures.commonOrePlacementModifiers(8, HeightRangePlacementModifier.createUniform(YOffset.getBottom(),      YOffset.fixed(30)))); // bottom of world and y 30, count == spawn count
    Holder<PlacedFeature> ORE_BLAZING_QUARTZ_PLACED = register("malum:ore_blazing_quartz", ORE_BLAZING_QUARTZ_CONFIGURED, OrePlacedFeatures.commonOrePlacementModifiers(16, PlacedFeatureUtil.EIGHT_ABOVE_AND_BELOW_RANGE));

    Holder<PlacedFeature> RUNEWOOD_CHECKED = register("malum:runewood_tree", CONFIGURED_RUNEWOOD_TREE_FEATURE, VegetationPlacedFeatures.treePlacementModifiers(RarityFilterPlacementModifier.create(10), MalumBlockRegistry.RUNEWOOD_SAPLING));
    static Holder<PlacedFeature> register(String id, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers) {
        return PlacedFeatureUtil.register(id, configuredFeature, modifiers);
    }

    static void init() {

    }
}
