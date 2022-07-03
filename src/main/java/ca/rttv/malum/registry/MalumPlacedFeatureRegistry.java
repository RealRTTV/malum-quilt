package ca.rttv.malum.registry;

import ca.rttv.malum.config.CommonConfig;
import net.minecraft.util.Holder;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.OrePlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacementModifier;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;

import java.util.List;

import static ca.rttv.malum.registry.MalumConfiguredFeatureRegistry.*;

@SuppressWarnings("unused")
public interface MalumPlacedFeatureRegistry {
    Holder<PlacedFeature> ORE_BRILLIANCE_PLACED      = register("ore_brilliance",      ORE_BRILLIANCE_CONFIGURED,      OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.BRILLIANT_STONE_VEIN_AMOUNT, HeightRangePlacementModifier.createUniform(YOffset.fixed(CommonConfig.BRILLIANT_STONE_VEIN_MIN_Y), YOffset.fixed(CommonConfig.BRILLIANT_STONE_VEIN_MAX_Y)))); // y -80 and y 40, count == spawn count
    Holder<PlacedFeature> UPPER_ORE_SOULSTONE_PLACED = register("upper_ore_soulstone", UPPER_ORE_SOULSTONE_CONFIGURED, OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.SURFACE_SOULSTONE_VEIN_AMOUNT, HeightRangePlacementModifier.createUniform(YOffset.fixed(CommonConfig.SURFACE_SOULSTONE_VEIN_MIN_Y),  YOffset.fixed(CommonConfig.SURFACE_SOULSTONE_VEIN_MAX_Y)))); // y 60 to y 100, count == spawn count
    Holder<PlacedFeature> LOWER_ORE_SOULSTONE_PLACED = register("lower_ore_soulstone", LOWER_ORE_SOULSTONE_CONFIGURED, OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.UNDERGROUND_SOULSTONE_VEIN_AMOUNT, HeightRangePlacementModifier.createUniform(YOffset.fixed(CommonConfig.UNDERGROUND_SOULSTONE_VEIN_MIN_Y), YOffset.fixed(CommonConfig.UNDERGROUND_SOULSTONE_VEIN_MAX_Y)))); // bottom of world and y 30, count == spawn count
    Holder<PlacedFeature> BLAZING_QUARTZ_PLACED      = register("blazing_quartz",      BLAZING_QUARTZ_CONFIGURED,      OrePlacedFeatures.commonOrePlacementModifiers(CommonConfig.BLAZING_QUARTZ_VEIN_AMOUNT, PlacedFeatureUtil.EIGHT_ABOVE_AND_BELOW_RANGE));

    static Holder<PlacedFeature> register(String id, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers) {
        return PlacedFeatureUtil.register(id, configuredFeature, modifiers);
    }

    static void init() {

    }
}
