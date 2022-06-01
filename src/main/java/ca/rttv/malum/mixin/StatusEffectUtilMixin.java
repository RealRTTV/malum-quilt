package ca.rttv.malum.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ca.rttv.malum.registry.MalumStatusEffectRegistry.INFERNAL_AURA;

@Mixin(StatusEffectUtil.class)
abstract class StatusEffectUtilMixin {
    @Inject(method = "hasHaste", at = @At("HEAD"), cancellable = true)
    private static void hasHaste(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity.hasStatusEffect(INFERNAL_AURA)) {
            cir.setReturnValue(true);
        }
    }

    @ModifyVariable(method = "getHasteAmplifier", at = @At(value = "RETURN", shift = At.Shift.BY, by = -3), index = 1)
    private static int getHasteAmplifier(int value, LivingEntity entity) {
        if (entity.hasStatusEffect(INFERNAL_AURA)) {
            //noinspection ConstantConditions
            value = Math.max(value, entity.getStatusEffect(INFERNAL_AURA).getAmplifier());
        }
        return value;
    }
}
