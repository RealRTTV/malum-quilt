package ca.rttv.malum.mixin;

import ca.rttv.malum.item.ScytheItem;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract void spawnSweepAttackParticles();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void mixin(Entity target, CallbackInfo ci, float f, float g, boolean bl, boolean bl2, int i, boolean bl3, boolean bl4, double d) {
        if(this.getStackInHand(Hand.MAIN_HAND).getItem() instanceof ScytheItem) {
            float l = 1.0F + EnchantmentHelper.getSweepingMultiplier(this) * f;

            for(LivingEntity livingEntity : this.world.getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(1.0, 0.25, 1.0))) {
                if (livingEntity != this
                        && livingEntity != target
                        && !this.isTeammate(livingEntity)
                        && (!(livingEntity instanceof ArmorStandEntity) || !((ArmorStandEntity)livingEntity).isMarker())
                        && this.squaredDistanceTo(livingEntity) < 9.0) {
                    livingEntity.takeKnockback(
                            0.4F, (double) MathHelper.sin(this.getYaw() * (float) (Math.PI / 180.0)), (double)(-MathHelper.cos(this.getYaw() * (float) (Math.PI / 180.0)))
                    );
                    livingEntity.damage(DamageSource.player( (PlayerEntity) (Object) this), l);

                }
            }

            this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, this.getSoundCategory(), 1.0F, 1.0F);
            this.spawnSweepAttackParticles();
        }
    }
}
