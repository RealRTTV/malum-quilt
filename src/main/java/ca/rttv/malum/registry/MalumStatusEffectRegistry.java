package ca.rttv.malum.registry;

import ca.rttv.malum.util.spirit.SpiritType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;

public interface MalumStatusEffectRegistry {
    Map<Identifier, StatusEffect> STATUS_EFFECTS = new LinkedHashMap<>();

    StatusEffect AERIAL_AURA            = registerStatusEffect("aerial_aura",            new StatusEffect(StatusEffectType.BENEFICIAL, SpiritType.AERIAL_SPIRIT.color.getRGB()).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "E3F9C028-D6CC-4CF2-86A6-D5B5EFD86BE6", 0.2d, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
    StatusEffect CORRUPTED_AERIAL_AURA  = registerStatusEffect("corrupted_aerial_aura",  new StatusEffect(StatusEffectType.BENEFICIAL, SpiritType.AERIAL_SPIRIT.color.getRGB()));
    StatusEffect AQUEOUS_AURA           = registerStatusEffect("aqueous_aura",           new StatusEffect(StatusEffectType.BENEFICIAL, SpiritType.AQUEOUS_SPIRIT.color.getRGB()));
    StatusEffect SACRED_AURA            = registerStatusEffect("sacred_aura",            new StatusEffect(StatusEffectType.BENEFICIAL, SpiritType.SACRED_SPIRIT.color.getRGB()));
    StatusEffect EARTHEN_AURA           = registerStatusEffect("earthen_aura",           new StatusEffect(StatusEffectType.BENEFICIAL, SpiritType.EARTHEN_SPIRIT.color.getRGB()).addAttributeModifier(EntityAttributes.GENERIC_ARMOR, "04448cbf-ee2c-4f36-b71f-e641a312834a", 3.0d, EntityAttributeModifier.Operation.MULTIPLY_TOTAL).addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, "dc5fc5d7-db54-403f-810d-a16de6293ffd", 1.5d, EntityAttributeModifier.Operation.ADDITION));
    StatusEffect CORRUPTED_EARTHEN_AURA = registerStatusEffect("corrupted_earthen_aura", new StatusEffect(StatusEffectType.BENEFICIAL, SpiritType.EARTHEN_SPIRIT.color.getRGB()).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "e2a25284-a8b1-41a5-9472-90cc83793d44", 2.0d, EntityAttributeModifier.Operation.ADDITION));
    StatusEffect INFERNAL_AURA          = registerStatusEffect("infernal_aura",          new StatusEffect(StatusEffectType.BENEFICIAL, SpiritType.INFERNAL_SPIRIT.color.getRGB()).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "0a74b987-a6ec-4b9f-815e-a589bf435b93", 0.2d, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));

    static <T extends StatusEffect> StatusEffect registerStatusEffect(String id, T effect) {
        STATUS_EFFECTS.put(new Identifier(MODID, id), effect);
        return effect;
    }

    static void init() {
        STATUS_EFFECTS.forEach((id, effect) -> Registry.register(Registry.STATUS_EFFECT, id, effect));
    }
}
