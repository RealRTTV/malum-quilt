package ca.rttv.malum.enchantment;

import ca.rttv.malum.mixin.EnchantmentTargetMixin;
import ca.rttv.malum.registry.MalumTags;
import net.minecraft.item.Item;

public class ScytheEnchantmentTarget extends EnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem(Item item) {
        return item.getDefaultStack().isIn(MalumTags.SCYTHE);
    }
}
