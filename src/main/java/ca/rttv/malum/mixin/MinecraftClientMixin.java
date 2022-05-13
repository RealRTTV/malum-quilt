package ca.rttv.malum.mixin;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.client.init.MalumScreenParticleRegistry;
import ca.rttv.malum.util.handler.ScreenParticleHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public final class MinecraftClientMixin {

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ReloadableResourceManager;registerReloader(Lnet/minecraft/resource/ResourceReloader;)V", ordinal = 17, shift = At.Shift.AFTER))
    private void malum$registerParticleFactories(RunArgs runArgs, CallbackInfo ci) {
        MalumParticleRegistry.registerFactories();
        MalumScreenParticleRegistry.registerParticleFactories();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void malum$clientTick(CallbackInfo ci) {
        ScreenParticleHandler.clientTick();
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;pop()V", ordinal = 4, shift = At.Shift.AFTER))
    private void malum$renderTickThingamajig(boolean tick, CallbackInfo ci) {
        ScreenParticleHandler.renderParticles();
    }
}
