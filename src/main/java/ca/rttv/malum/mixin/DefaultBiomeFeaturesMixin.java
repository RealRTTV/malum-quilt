package ca.rttv.malum.mixin;

import ca.rttv.malum.config.CommonConfig;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static ca.rttv.malum.registry.MalumPlacedFeatureRegistry.*;

@Mixin(DefaultBiomeFeatures.class)
abstract class DefaultBiomeFeaturesMixin {
    @Inject(method = "addDefaultOres(Lnet/minecraft/world/biome/GenerationSettings$Builder;Z)V", at = @At("TAIL"))
    private static void malum$addOres(GenerationSettings.Builder builder, boolean largeCopperOreBlob, CallbackInfo ci) {
        if (CommonConfig.GENERATE_UNDERGROUND_SOULSTONE) builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UNDERGROUND_SOULSTONE_PLACED);
        if (CommonConfig.GENERATE_SURFACE_SOULSTONE) builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, SURFACE_SOULSTONE_PLACED);
        if (CommonConfig.GENERATE_BRILLIANT_STONE) builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, BRILLIANT_STONE_PLACED);
    }

    @Inject(method = "addNetherMineables", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/DefaultBiomeFeatures;addAncientDebris(Lnet/minecraft/world/biome/GenerationSettings$Builder;)V"))
    private static void malum$addNetherOres(GenerationSettings.Builder builder, CallbackInfo ci) {
        if (CommonConfig.GENERATE_BLAZING_QUARTZ) builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, BLAZING_QUARTZ_PLACED);
    }
}
