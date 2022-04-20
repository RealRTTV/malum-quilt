package ca.rttv.malum.mixin;

import ca.rttv.malum.duck.SoulWardDuck;
import ca.rttv.malum.util.handler.SpiritHarvestHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.checkerframework.common.aliasing.qual.Unique;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ca.rttv.malum.registry.MalumAttributeRegistry.*;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements SoulWardDuck {
    @Shadow public abstract double getAttributeValue(EntityAttribute attribute);

    @Unique
    float soulWard;

    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void createLivingAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
        ATTRIBUTES.forEach((id, entityAttribute) -> info.getReturnValue().add(entityAttribute));
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (soulWard < this.getAttributeValue(SOUL_WARD_CAP)) {
            soulWard = Math.min((float) this.getAttributeValue(SOUL_WARD_CAP), soulWard + 1.0f / 60.0f);
        }
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void malum$onDeath(DamageSource source, CallbackInfo ci) {
        if (!world.isClient) {
            SpiritHarvestHandler.shatterSoul(source, (LivingEntity) (Object) this);
        }
    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getAbsorptionAmount()F", ordinal = 0, shift = At.Shift.BY, by = -2), argsOnly = true, index = 2)
    private float applyDamage(float value, DamageSource source, float amount) {
        return this.applySoulWardToDamage(value, source);
    }

    private float applySoulWardToDamage(float value, DamageSource source) {
        if (source == DamageSource.MAGIC) {
            float absorbed = value * 0.9f; // todo: config
            float left = value - absorbed;

        } else {

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

    @Override
    public float getSoulWard() {
        return soulWard;
    }
}
