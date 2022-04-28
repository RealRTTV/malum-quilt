package ca.rttv.malum.mixin;

import ca.rttv.malum.registry.MalumRegistry;
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffect.class)
public abstract class StatusEffectMixin {
    @Inject(method = "canApplyUpdateEffect", at = @At("HEAD"), cancellable = true)
    private void canApplyUpdateEffect(int duration, int amplifier, CallbackInfoReturnable<Boolean> cir) {
        if (((StatusEffect) (Object) this) == MalumRegistry.SACRED_AURA) {

        }
    }
}
