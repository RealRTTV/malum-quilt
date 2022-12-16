package ca.rttv.malum.mixin;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
final class MinecraftClientMixin {
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ReloadableResourceManager;registerReloader(Lnet/minecraft/resource/ResourceReloader;)V", ordinal = 17))
    private void malum$registerParticleFactories(RunArgs runArgs, CallbackInfo ci) {
        MalumParticleRegistry.registerFactories();
    }
}
