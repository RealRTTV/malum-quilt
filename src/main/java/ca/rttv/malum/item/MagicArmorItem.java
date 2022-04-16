package ca.rttv.malum.item;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

import java.util.UUID;

import static ca.rttv.malum.registry.MalumAttributeRegistry.*;

public class MagicArmorItem extends ArmorItem {

    public MagicArmorItem(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, float magicResistance, float soulWardCapacity, float magicProficiency, float scytheProficiency, Settings settings) {
        super(armorMaterial, equipmentSlot, settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        UUID uUID = MODIFIERS[equipmentSlot.getEntitySlotId()];
        builder.put(
                EntityAttributes.GENERIC_ARMOR,
                new EntityAttributeModifier(uUID, "Armor modifier", this.getProtection(), EntityAttributeModifier.Operation.ADDITION)
        );
        builder.put(
                EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
                new EntityAttributeModifier(uUID, "Armor toughness", this.getToughness(), EntityAttributeModifier.Operation.ADDITION)
        );
        if (magicResistance > 0.0f) {
            builder.put(
                    MAGIC_RESISTANCE,
                    new EntityAttributeModifier(uUID, "Armor modifier", magicResistance, EntityAttributeModifier.Operation.ADDITION)
            );
        }
        if (soulWardCapacity > 0.0f) {
            builder.put(
                    SOUL_WARD_CAP,
                    new EntityAttributeModifier(uUID, "Armor modifier", soulWardCapacity, EntityAttributeModifier.Operation.ADDITION)
            );
        }
        if (magicProficiency > 0.0f) {
            builder.put(
                    MAGIC_PROFICIENCY,
                    new EntityAttributeModifier(uUID, "Armor modifier", magicProficiency, EntityAttributeModifier.Operation.ADDITION)
            );
        }
        if (scytheProficiency > 0.0f) {
            builder.put(
                    SCYTHE_PROFICIENCY,
                    new EntityAttributeModifier(uUID, "Armor modifier", scytheProficiency, EntityAttributeModifier.Operation.ADDITION)
            );
        }
        attributeModifiers = builder.build();
    }
}
