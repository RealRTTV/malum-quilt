package ca.rttv.malum.mixin;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.item.ScytheItem;
import ca.rttv.malum.item.TyrvingItem;
import ca.rttv.malum.registry.MalumDamageSourceRegistry;
import ca.rttv.malum.registry.MalumItemRegistry;
import ca.rttv.malum.registry.MalumSoundRegistry;
import ca.rttv.malum.util.spirit.spiritaffinity.ArcaneAffinity;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
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

    @Shadow
    public abstract SoundCategory getSoundCategory();

    /**
     * Performs many reactions when being hit
     */
    @ModifyArgs(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private void malum$onDamaged(Args args) {
        DamageSource source = args.get(0);
        float value = args.get(1);
        args.set(1, ArcaneAffinity.consumeSoulWard(this, source, value));
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void malum$tick(CallbackInfo ci) {
        ArcaneAffinity.recoverSoulWard((PlayerEntity) (Object) this);
    }

    @ModifyVariable(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getMovementSpeed()F"), index = 3)
    private float malum$captureF(float f) {
        this.f = f;
        return f;
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"))
    private void malum$attack(Entity target, CallbackInfo ci) {
        if (this.getStackInHand(Hand.MAIN_HAND).getItem() instanceof ScytheItem) {
            boolean canSweep = !this.getComponent(TrinketsApi.TRINKET_COMPONENT).isEquipped(MalumItemRegistry.NECKLACE_OF_THE_NARROW_EDGE);
            SoundEvent sound;
            if (canSweep) {
                spawnSweepParticles((PlayerEntity) (Object) this, MalumParticleRegistry.SCYTHE_SWEEP_ATTACK_PARTICLE);
                sound = SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP;
            } else {
                spawnSweepParticles((PlayerEntity) (Object) this, MalumParticleRegistry.SCYTHE_CUT_ATTACK_PARTICLE);
                sound = MalumSoundRegistry.SCYTHE_CUT;
            }
            world.playSound(null, target.getX(), target.getY(), target.getZ(), sound, this.getSoundCategory(), 1, 1);

            float damage = 1.0F + EnchantmentHelper.getSweepingMultiplier(this) * f;
            ;
            world.getOtherEntities(this, target.getBoundingBox().expand(1)).forEach(e -> {
                if (e instanceof LivingEntity livingEntity) {
                    if (livingEntity.isAlive()) {
                        livingEntity.damage(MalumDamageSourceRegistry.scytheSweepDamage(this), damage);
                        livingEntity.takeKnockback(0.4F, MathHelper.sin(this.getYaw() * ((float) Math.PI / 180F)), (-MathHelper.cos(this.getYaw() * ((float) Math.PI / 180F))));
                    }
                }
            });
        }
    }
    @Unique
    public void spawnSweepParticles(PlayerEntity player, DefaultParticleType type) {
        double d0 = (-MathHelper.sin(player.getYaw() * ((float) Math.PI / 180F)));
        double d1 = MathHelper.cos(player.getYaw() * ((float) Math.PI / 180F));
        if (player.world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(type, player.getX() + d0, player.getBodyY(0.5D), player.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }
    }

    @ModifyVariable(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackCooldownProgress(F)F"), index = 3)
    private float malum$attackCooldown(float value) {
        if (this.getMainHandStack().getItem() instanceof ScytheItem) {
            return value + (float) this.getAttributeValue(SCYTHE_PROFICIENCY) * 0.5f;
        }
        return value;
    }
}
