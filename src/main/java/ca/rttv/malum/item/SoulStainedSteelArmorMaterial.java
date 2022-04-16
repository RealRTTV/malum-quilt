package ca.rttv.malum.item;

import ca.rttv.malum.mixin.ArmorMaterialsMixin;
import net.minecraft.entity.EquipmentSlot;

public class SoulStainedSteelArmorMaterial extends ArmorMaterialsMixin {
    @Override
    public int getDurability(EquipmentSlot slot) {
        return switch(slot.getEntitySlotId()) {
            case 0 -> 242;
            case 1 -> 352;
            case 2 -> 330;
            case 3 -> 286;
            default -> throw new IllegalStateException("Unexpected value: " + slot.getEntitySlotId());
        };
    }
}
