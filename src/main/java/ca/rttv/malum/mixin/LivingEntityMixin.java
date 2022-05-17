package ca.rttv.malum.mixin;

import ca.rttv.malum.registry.MalumItemRegistry;
import ca.rttv.malum.util.handler.SpiritHarvestHandler;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

import static ca.rttv.malum.registry.MalumAttributeRegistry.ATTRIBUTES;
import static ca.rttv.malum.registry.MalumAttributeRegistry.MAGIC_RESISTANCE;
import static ca.rttv.malum.registry.MalumStatusEffectRegistry.CORRUPTED_AERIAL_AURA;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract double getAttributeValue(EntityAttribute attribute);

    @Shadow
    public abstract @Nullable StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Shadow public abstract ItemStack getStackInHand(Hand hand);

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract boolean clearStatusEffects();

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow public abstract void setStackInHand(Hand hand, ItemStack stack);

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

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
            float multiplier = 1.0f - (float) Math.max(((1 - (0.5 * (1 / (0.6 * this.getAttributeValue(MAGIC_RESISTANCE))))) * 0.6), 0);
            return value * multiplier;
        }
        return value;
    }

    @ModifyVariable(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasNoGravity()Z", ordinal = 1), index = 2)
    private double setVelocity(double d) {
        // todo, fix corrupted aerial aura
        return d;
    }

    @ModifyArg(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V", ordinal = 0), index = 1)
    private double jump(double y) {
        if (this.getStatusEffect(CORRUPTED_AERIAL_AURA) != null) {
            //noinspection ConstantConditions
            return y + this.getStatusEffect(CORRUPTED_AERIAL_AURA).getAmplifier() * 0.15d;
        }
        return y;
    }

    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (source.isOutOfWorld()) {
            cir.setReturnValue(false);
        } else {
            ItemStack itemStack = null;

            for( Hand hand : Hand.values()) {
                ItemStack itemStack2 = this.getStackInHand(hand);
                if (itemStack2.isOf(MalumItemRegistry.CEASELESS_IMPETUS)) {
                    itemStack = itemStack2.copy();
                    //noinspection ConstantConditions
                    if (itemStack2.damage(1, random, (LivingEntity) (Object) this instanceof ServerPlayerEntity ? (ServerPlayerEntity) (Object) this : null)) {
                        NbtCompound nbt = itemStack2.getNbt();
                        if (nbt != null) {
                            nbt.remove("Damage");
                        }
                        this.setStackInHand(hand, new ItemStack(MalumItemRegistry.CRACKED_CEASELESS_IMPETUS, itemStack2.getCount(), Optional.ofNullable(nbt)));
                    }
                    break;
                }
            }

            if (itemStack != null) {
                if ((LivingEntity) (Object) this instanceof ServerPlayerEntity serverPlayerEntity) {
                    serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(MalumItemRegistry.CEASELESS_IMPETUS));
                    Criteria.USED_TOTEM.trigger(serverPlayerEntity, itemStack);
                }

                this.setHealth(1.0F);
                this.clearStatusEffects();
                //noinspection ConstantConditions
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 400, 3 + (this.hasStatusEffect(StatusEffects.REGENERATION) ? this.getStatusEffect(StatusEffects.REGENERATION).getAmplifier() + 1 : 0)));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 200, 0));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 400, 0));
                this.world.sendEntityStatus(this, (byte)35);
            }

            if (itemStack != null) {
                cir.setReturnValue(true);
            }
        }
    }
}
