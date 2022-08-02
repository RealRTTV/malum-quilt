package ca.rttv.malum.mixin;

import ca.rttv.malum.registry.MalumBoatTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ca.rttv.malum.registry.MalumItemRegistry.RUNEWOOD_BOAT;
import static ca.rttv.malum.registry.MalumItemRegistry.SOULWOOD_BOAT;

@Mixin(BoatEntity.class)
abstract class BoatEntityMixin extends Entity {
    public BoatEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract BoatEntity.Type getBoatType();

    @Inject(method = "asItem", at = @At("HEAD"), cancellable = true)
    private void malum$asItem(CallbackInfoReturnable<Item> cir) {
        BoatEntity.Type type = this.getBoatType();
        if(type == MalumBoatTypes.SOULWOOD) {
            cir.setReturnValue(SOULWOOD_BOAT);
        }
        if(type == MalumBoatTypes.RUNEWOOD) {
            cir.setReturnValue(RUNEWOOD_BOAT);
        }
    }
}
