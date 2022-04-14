package ca.rttv.malum.mixin;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static ca.rttv.malum.registry.MalumRegistry.MAGIC_DAMAGE_MODIFIER_ID;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Unique
    private EntityAttributeModifier entityAttributeModifier;

    @ModifyVariable(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttributeModifier;getId()Ljava/util/UUID;", ordinal = 0), index = 13)
    private EntityAttributeModifier getTooltip(EntityAttributeModifier value) {
        this.entityAttributeModifier = value;
        return value;
    }

    @ModifyVariable(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttributeModifier;getOperation()Lnet/minecraft/entity/attribute/EntityAttributeModifier$Operation;", ordinal = 0), index = 18)
    private boolean getTooltip(boolean value, @Nullable PlayerEntity player, TooltipContext context) {
        if (player != null) {
            if (entityAttributeModifier.getId() == MAGIC_DAMAGE_MODIFIER_ID) {
//                d += player.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_SPEED);
                return true;
            }
        }
        return value;
    }
}
