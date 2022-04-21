package ca.rttv.malum.mixin;

import ca.rttv.malum.item.ScytheItem;
import ca.rttv.malum.util.spirit.spiritaffinity.ArcaneAffinity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static ca.rttv.malum.registry.MalumAttributeRegistry.SCYTHE_PROFICIENCY;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract void spawnSweepAttackParticles();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return super.damage(source, ArcaneAffinity.consumeSoulWard((LivingEntity) (Object)this, source, amount));
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void malum$tick(CallbackInfo ci) {
        ArcaneAffinity.recoverSoulWard((PlayerEntity)(Object) this);
    }

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void attack(Entity target, CallbackInfo ci, float f, float g, boolean bl, boolean bl2, int i, boolean bl3, boolean bl4, double d) {
        if(this.getStackInHand(Hand.MAIN_HAND).getItem() instanceof ScytheItem) {
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
}
