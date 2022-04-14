package ca.rttv.malum.enchantment;

import ca.rttv.malum.registry.MalumEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;

public class SpiritPlunderEnchantment extends Enchantment {
    public SpiritPlunderEnchantment() {
        super(Rarity.COMMON, MalumEnchantments.SOUL_HUNTER_WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
