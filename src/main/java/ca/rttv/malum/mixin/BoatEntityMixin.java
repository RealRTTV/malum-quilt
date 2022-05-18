package ca.rttv.malum.mixin;

import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ca.rttv.malum.registry.MalumBoatTypes.RUNEWOOD;
import static ca.rttv.malum.registry.MalumBoatTypes.SOULWOOD;
import static ca.rttv.malum.registry.MalumItemRegistry.RUNEWOOD_BOAT;
import static ca.rttv.malum.registry.MalumItemRegistry.SOULWOOD_BOAT;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {
    @Shadow public abstract BoatEntity.Type getBoatType();

    @Inject(method = "asItem", at = @At("HEAD"), cancellable = true)
    private void asItem(CallbackInfoReturnable<Item> cir) {
        BoatEntity.Type boatType = this.getBoatType();
        if (boatType == RUNEWOOD) {
            cir.setReturnValue(RUNEWOOD_BOAT);
        } else if (boatType == SOULWOOD) {
            cir.setReturnValue(SOULWOOD_BOAT);
        }
    }
}
