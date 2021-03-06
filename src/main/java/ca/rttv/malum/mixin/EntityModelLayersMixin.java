package ca.rttv.malum.mixin;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ca.rttv.malum.Malum.MODID;

@Mixin(EntityModelLayers.class)
final class EntityModelLayersMixin {
    // can't use modifyarg or modifyargs cause it only works on methodinsnnodes
    @Inject(method = "create", at = @At("HEAD"), cancellable = true)
    private static void malum$changeSignNamespace(String id, String layer, CallbackInfoReturnable<EntityModelLayer> cir) {
        if (id.equals("sign/runewood") || id.equals("sign/soulwood")) {
            cir.setReturnValue(new EntityModelLayer(new Identifier(MODID, id), layer));
        }
    }
}
