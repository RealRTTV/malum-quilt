package ca.rttv.malum.mixin;

import ca.rttv.malum.util.handler.SpiritHarvestHandler;
import ca.rttv.malum.util.spirit.spiritaffinity.ArcaneAffinity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ca.rttv.malum.registry.MalumAttributeRegistry.ATTRIBUTES;
import static ca.rttv.malum.registry.MalumAttributeRegistry.MAGIC_RESISTANCE;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract double getAttributeValue(EntityAttribute attribute);

    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void createLivingAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
        ATTRIBUTES.forEach((id, entityAttribute) -> info.getReturnValue().add(entityAttribute));
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void malum$onDeath(DamageSource source, CallbackInfo ci) {
        if (!world.isClient) {
            SpiritHarvestHandler.shatterSoul(source, (LivingEntity) (Object) this);
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void malum$damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!world.isClient) {
            SpiritHarvestHandler.exposeSoul(source, amount, (LivingEntity) (Object) this);
        }
    }

    @ModifyVariable(method = "applyEnchantmentsToDamage", at = @At(value = "RETURN", ordinal = 2, shift = At.Shift.BEFORE), index = 2, argsOnly = true)
    private float applyEnchantmentsToDamage(float value, DamageSource source, float amount) {
        if (source == DamageSource.MAGIC) {
            float multiplier = 1.0f - (float) Math.max(((1 - (0.5 * (1 / (0.6 * ((LivingEntity) (Object) this).getAttributeValue(MAGIC_RESISTANCE))))) * 0.6), 0);
            System.out.println(value * multiplier);
            return value * multiplier;
        }
        return value;
    }
}
