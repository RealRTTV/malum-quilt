package ca.rttv.malum.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;

public class RingOfCurativeTalentItem extends TrinketItem implements SpiritCollectActivity {
    public RingOfCurativeTalentItem(Settings settings) {
        super(settings);
    }

    @Override
    public void collect(ItemStack spirit, LivingEntity livingEntity, SlotReference slot, ItemStack trinket) {
        StatusEffectInstance regeneration = livingEntity.getStatusEffect(StatusEffects.REGENERATION);
        if (regeneration == null) {
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0));
        } else {
            livingEntity.removeStatusEffect(StatusEffects.REGENERATION);
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100 + regeneration.getDuration(), regeneration.getAmplifier()));
        }
    }
}
