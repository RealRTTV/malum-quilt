package ca.rttv.malum.mixin;

import ca.rttv.malum.item.ScytheItem;
import ca.rttv.malum.item.TyrvingItem;
import ca.rttv.malum.util.spirit.spiritaffinity.ArcaneAffinity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static ca.rttv.malum.registry.MalumAttributeRegistry.SCYTHE_PROFICIENCY;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    float f;

    @Shadow public abstract void spawnSweepAttackParticles();

    /**
     * Performs many reactions when being hit
     */
    @ModifyArgs(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private void onDamaged(Args args) {
        DamageSource source = args.get(0);
        float value = args.get(1);
        args.set(1, ArcaneAffinity.consumeSoulWard(this, source, value));
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        ArcaneAffinity.recoverSoulWard((PlayerEntity) (Object) this);
    }

    @ModifyVariable(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getMovementSpeed()F"), index = 3)
    private float captureF(float f) {
        this.f = f;
        return f;
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"))
    private void attack(Entity target, CallbackInfo ci) {
        if (this.getStackInHand(Hand.MAIN_HAND).getItem() instanceof ScytheItem) {
            float l = 1.0F + EnchantmentHelper.getSweepingMultiplier(this) * f;

            for (LivingEntity livingEntity : this.world.getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(1.0, 0.25, 1.0))) {
                if (livingEntity != this
                 && livingEntity != target
                 && !this.isTeammate(livingEntity)
                 && (!(livingEntity instanceof ArmorStandEntity) || !((ArmorStandEntity)livingEntity).isMarker())
                 && this.squaredDistanceTo(livingEntity) < 9.0)
                {
                    livingEntity.takeKnockback(0.4F, MathHelper.sin(this.getYaw() * (float) (Math.PI / 180.0)), -MathHelper.cos(this.getYaw() * (float) (Math.PI / 180.0)));
                    livingEntity.damage(DamageSource.player((PlayerEntity) (Object) this), l);
                }
            }

            this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, this.getSoundCategory(), 1.0F, 1.0F);
            this.spawnSweepAttackParticles();
        }
    }

    @ModifyVariable(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackCooldownProgress(F)F"), index = 3)
    private float attack(float value) {
        if (this.getMainHandStack().getItem() instanceof ScytheItem) {
            return value + (float) this.getAttributeValue(SCYTHE_PROFICIENCY) * 0.5f;
        }
        return value;
    }

    @ModifyVariable(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getFireAspect(Lnet/minecraft/entity/LivingEntity;)I", ordinal = 0), index = 8)
    private boolean attack(boolean bl4) {
        ItemStack itemStack = this.getStackInHand(Hand.MAIN_HAND);
        if (itemStack.getItem() instanceof TyrvingItem) {
            return false;
        }
        return bl4;
    }
}
