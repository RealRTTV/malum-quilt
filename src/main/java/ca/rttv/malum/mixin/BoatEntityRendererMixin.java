package ca.rttv.malum.mixin;

import ca.rttv.malum.registry.MalumBoatTypes;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ca.rttv.malum.Malum.MODID;

@Mixin(BoatEntityRenderer.class)
final class BoatEntityRendererMixin {
    @Inject(method = "getTexture(Lnet/minecraft/entity/vehicle/BoatEntity$Variant;Z)Ljava/lang/String;", at = @At(value = "HEAD"), cancellable = true)
    private static void malum$useMalumNamespace(BoatEntity.Variant type, boolean isChest, CallbackInfoReturnable<String> cir) {
        if (type == MalumBoatTypes.RUNEWOOD || type == MalumBoatTypes.SOULWOOD) {
            cir.setReturnValue(isChest ? (MODID + ":textures/entity/chest_boat/" + type.key() + "_boat.png") : (MODID + ":textures/entity/boat/" + type.key() + "_boat.png"));
        }
    }
}
