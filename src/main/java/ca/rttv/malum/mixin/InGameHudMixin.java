package ca.rttv.malum.mixin;

import ca.rttv.malum.util.handler.ScreenParticleHandler;
import ca.rttv.malum.util.spirit.spiritaffinity.ArcaneAffinity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
final class InGameHudMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "renderHotbar")
    private void malum$renderHotbarStart(float l1, MatrixStack j1, CallbackInfo ci) {
        ScreenParticleHandler.renderingHotbar = true;
    }

    @Inject(method = "renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;pop()V"))
    private void malum$renderArmorOverlay(MatrixStack matrices, CallbackInfo info) {
        ArcaneAffinity.Client.renderSoulWard(matrices, this.client.getWindow());
    }

    @Inject(at = @At("RETURN"), method = "renderHotbar")
    private void malum$renderHotbarEnd(float l1, MatrixStack j1, CallbackInfo ci) {
        ScreenParticleHandler.renderingHotbar = false;
    }
}
