package ca.rttv.malum.enchantment;

import ca.rttv.malum.config.CommonConfig;
import ca.rttv.malum.mixin.EnchantmentTargetMixin;
import ca.rttv.malum.registry.MalumTags;
import net.minecraft.item.Item;

public class ReboundScytheEnchantmentTarget extends EnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem(Item item) {
        return item.getDefaultStack().isIn(MalumTags.SCYTHE) || (CommonConfig.ULTIMATE_REBOUND);
    }
}
