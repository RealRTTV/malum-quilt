package ca.rttv.malum.mixin;

import ca.rttv.malum.enchantment.ReboundEnchantment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ca.rttv.malum.registry.MalumStatusEffectRegistry.AQUEOUS_AURA;

@Mixin(ClientPlayerInteractionManager.class)
public final class ClientPlayerInteractionManagerMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "getReachDistance", at = @At("HEAD"), cancellable = true)
    private void getReachDistance(CallbackInfoReturnable<Float> cir) {
        if (this.client.player != null && this.client.player.hasStatusEffect(AQUEOUS_AURA)) {
            //noinspection ConstantConditions
            cir.setReturnValue(6.0f + this.client.player.getStatusEffect(AQUEOUS_AURA).getAmplifier());
        }
    }

    @Inject(method = "interactBlock", at = @At("HEAD"))
    private void interactBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        ReboundEnchantment.onRightClickItem(player, world, hand);
    }

    @Inject(method = "interactItem", at = @At("HEAD"))
    private void interactItem(PlayerEntity player, World world, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ReboundEnchantment.onRightClickItem(player, world, hand);
    }
}
