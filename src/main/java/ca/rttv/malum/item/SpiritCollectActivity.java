package ca.rttv.malum.item;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface SpiritCollectActivity {
    void collect(ItemStack spirit, LivingEntity livingEntity, SlotReference slot, ItemStack trinket);
}
