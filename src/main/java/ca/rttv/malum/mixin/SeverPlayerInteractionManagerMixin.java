package ca.rttv.malum.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static ca.rttv.malum.registry.MalumStatusEffectRegistry.AQUEOUS_AURA;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class SeverPlayerInteractionManagerMixin {
    @Shadow
    @Final
    protected ServerPlayerEntity player;

    @ModifyVariable(method = "processBlockBreakingAction", at = @At("STORE"), ordinal = 3)
    private double processBlockBreakingAction(double g) {
        if (this.player.hasStatusEffect(AQUEOUS_AURA)) {
            //noinspection ConstantConditions
            return g - Math.pow((1 + this.player.getStatusEffect(AQUEOUS_AURA).getAmplifier()), 2);
        }
        return g;
    }
}
