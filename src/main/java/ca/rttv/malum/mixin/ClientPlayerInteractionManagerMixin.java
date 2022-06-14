package ca.rttv.malum.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ca.rttv.malum.registry.MalumStatusEffectRegistry.AQUEOUS_AURA;

@Mixin(ClientPlayerInteractionManager.class)
final class ClientPlayerInteractionManagerMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "getReachDistance", at = @At("HEAD"), cancellable = true)
    private void malum$getReachDistance(CallbackInfoReturnable<Float> cir) {
        if (this.client.player != null && this.client.player.hasStatusEffect(AQUEOUS_AURA)) {
            //noinspection ConstantConditions
            cir.setReturnValue(6.0f + this.client.player.getStatusEffect(AQUEOUS_AURA).getAmplifier());
        }
    }
}
