package ca.rttv.malum.item;

import ca.rttv.malum.mixin.ArmorMaterialsMixin;
import net.minecraft.entity.EquipmentSlot;

public class SoulCloakArmorMaterial extends ArmorMaterialsMixin {
    @Override
    public int getDurability(EquipmentSlot slot) {
        return switch (slot.getEntitySlotId()) {
            case 0 -> 176;
            case 1 -> 256;
            case 2 -> 240;
            case 3 -> 208;
            default -> throw new IllegalStateException("Unexpected value: " + slot.getEntitySlotId());
        };
    }
}
