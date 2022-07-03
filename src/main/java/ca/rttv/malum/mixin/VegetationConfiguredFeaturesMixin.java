package ca.rttv.malum.mixin;

import ca.rttv.malum.config.CommonConfig;
import ca.rttv.malum.registry.MalumConfiguredFeatureRegistry;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.RandomFeatureConfig;
import net.minecraft.world.gen.feature.VegetationConfiguredFeatures;
import net.minecraft.world.gen.feature.WeightedPlacedFeature;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.List;

@Mixin(VegetationConfiguredFeatures.class)
abstract class VegetationConfiguredFeaturesMixin {
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/util/ConfiguredFeatureUtil;register(Ljava/lang/String;Lnet/minecraft/world/gen/feature/Feature;Lnet/minecraft/world/gen/feature/FeatureConfig;)Lnet/minecraft/util/Holder;", ordinal = 34), index = 2)
    private static <FC extends FeatureConfig> FC plainsRunewoodTrees(FC featureConfig) {
        RandomFeatureConfig config = (RandomFeatureConfig) featureConfig;
        List<WeightedPlacedFeature> features = new ArrayList<>(config.features);
        if (CommonConfig.GENERATE_RUNEWOOD_TREES) {
            features.add(new WeightedPlacedFeature(PlacedFeatureUtil.placedInline(MalumConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE_FEATURE), (float) CommonConfig.RUNEWOOD_TREES_CHANCE_PLAINS));
        }
        //noinspection unchecked
        return (FC) new RandomFeatureConfig(features, config.defaultFeature);
    }

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/util/ConfiguredFeatureUtil;register(Ljava/lang/String;Lnet/minecraft/world/gen/feature/Feature;Lnet/minecraft/world/gen/feature/FeatureConfig;)Lnet/minecraft/util/Holder;", ordinal = 33), index = 2)
    private static <FC extends FeatureConfig> FC forestRunewoodTrees(FC featureConfig) {
        RandomFeatureConfig config = (RandomFeatureConfig) featureConfig;
        List<WeightedPlacedFeature> features = new ArrayList<>(config.features);
        if (CommonConfig.GENERATE_RUNEWOOD_TREES) {
            features.add(new WeightedPlacedFeature(PlacedFeatureUtil.placedInline(MalumConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE_FEATURE), (float) CommonConfig.RUNEWOOD_TREES_CHANCE_FOREST));
        }
        //noinspection unchecked
        return (FC) new RandomFeatureConfig(features, config.defaultFeature);
    }
}
