package ca.rttv.malum.mixin;

import ca.rttv.malum.util.handler.SpiritHarvestHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ca.rttv.malum.registry.MalumAttributeRegistry.ATTRIBUTES;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void createLivingAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
        ATTRIBUTES.forEach((id, entityAttribute) -> {
            info.getReturnValue().add(entityAttribute);
        });
    }
    @Inject(method = "onDeath", at = @At("HEAD"))
    private void malum$onDeath(DamageSource source, CallbackInfo ci) {
        if(!this.world.isClient)
        SpiritHarvestHandler.shatterSoul(source, (LivingEntity) (Object) this);
    }
    @Inject(method = "damage", at = @At("HEAD"))
    private void malum$damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(!this.world.isClient)
        SpiritHarvestHandler.exposeSoul(source, amount, (LivingEntity) (Object) this);
    }

}
