package ca.rttv.malum.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import static ca.rttv.malum.registry.MalumStatusEffectRegistry.AQUEOUS_AURA;

@Mixin(ServerPlayerInteractionManager.class)
abstract class ServerPlayerInteractionManagerMixin {
    @Shadow
    @Final
    protected ServerPlayerEntity player;

    @ModifyExpressionValue(method = "processBlockBreakingAction", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;MAX_INTERACTION_DISTANCE:D", opcode = Opcodes.GETSTATIC))
    private double malum$processBlockBreakingAction(double g) {
        if (this.player.hasStatusEffect(AQUEOUS_AURA)) {
            //noinspection ConstantConditions
            return g + Math.pow((1 + this.player.getStatusEffect(AQUEOUS_AURA).getAmplifier()), 2);
        }
        return g;
    }
}
