package ca.rttv.malum.mixin;

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
    private static void addSoulstoneOre(GenerationSettings.Builder builder, boolean largeCopperOreBlob, CallbackInfo ci) {
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, LOWER_ORE_SOULSTONE_PLACED);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UPPER_ORE_SOULSTONE_PLACED);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, ORE_BRILLIANCE_PLACED);
    }
}
