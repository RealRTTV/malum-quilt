package ca.rttv.malum.mixin;

import ca.rttv.malum.registry.MalumPlacedFeatureRegistry;
import net.minecraft.world.gen.BootstrapContext;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlacedFeatureUtil.class)
public class PlacedFeatureUtilMixin {
    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void malum$initConfFeatures(BootstrapContext<PlacedFeature> bootstrapContext, CallbackInfo ci) {
        MalumPlacedFeatureRegistry.bootstrap(bootstrapContext);
    }
}
