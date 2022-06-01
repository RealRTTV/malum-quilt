package ca.rttv.malum.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ca.rttv.malum.registry.MalumStatusEffectRegistry.SACRED_AURA;

@Mixin(StatusEffect.class)
abstract class StatusEffectMixin {
    @Inject(method = "canApplyUpdateEffect", at = @At("HEAD"), cancellable = true)
    private void canApplyUpdateEffect(int duration, int amplifier, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this == SACRED_AURA) {
            int i = 20 >> amplifier;
            if (i > 0) {
                cir.setReturnValue(duration % i == 0);
            } else {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "applyUpdateEffect", at = @At("HEAD"))
    private void applyUpdateEffect(LivingEntity entity, int amplifier, CallbackInfo ci) {
        if ((Object) this == SACRED_AURA) {
            if (entity.getHealth() < entity.getMaxHealth()) {
                entity.heal(1.0f);
            }
        }
    }
}
