package ca.rttv.malum.mixin;

import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DefaultBiomeFeatures.class)
abstract class DefaultBiomeFeaturesMixin {
//    @Inject(method = "addDefaultOres(Lnet/minecraft/world/biome/GenerationSettings$Builder;Z)V", at = @At("TAIL"))
//    private static void malum$addOres(GenerationSettings.Builder builder, boolean largeCopperOreBlob, CallbackInfo ci) {
//        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, LOWER_ORE_SOULSTONE_PLACED);
//        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, UPPER_ORE_SOULSTONE_PLACED);
//        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, ORE_BRILLIANCE_PLACED);
//    }
}
