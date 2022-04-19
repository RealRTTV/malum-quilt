package ca.rttv.malum.mixin;

import ca.rttv.malum.registry.MalumBoatTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ca.rttv.malum.Malum.MODID;

@Mixin(BoatEntityRenderer.class)
public abstract class BoatEntityRendererMixin {
    @Inject(method = "method_32163", at = @At("HEAD"), cancellable = true)
    private static void useMalumNamespace(EntityRendererFactory.Context context, BoatEntity.Type type, CallbackInfoReturnable<Pair<Identifier, BoatEntityModel>> cir) {
        if (type == MalumBoatTypes.RUNEWOOD || type == MalumBoatTypes.SOULWOOD) {
            cir.setReturnValue(Pair.of(
                    new Identifier(MODID, "textures/entity/boat/" + type.getName() + ".png"), new BoatEntityModel(context.getPart(EntityModelLayers.createBoat(type)))
            ));
        }
    }
}
