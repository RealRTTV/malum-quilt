package ca.rttv.malum.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;

public class RingOfWickedIntentItem extends TrinketItem implements SpiritCollectActivity {
    public RingOfWickedIntentItem(Settings settings) {
        super(settings);
    }

    @Override
    public void collect(ItemStack spirit, LivingEntity livingEntity, SlotReference slot, ItemStack trinket) {
        StatusEffectInstance strength = livingEntity.getStatusEffect(StatusEffects.STRENGTH);
        if (strength == null) {
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 100, 0));
        } else {
            livingEntity.removeStatusEffect(StatusEffects.STRENGTH);
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 100 + strength.getDuration(), strength.getAmplifier()));
        }
    }
}
