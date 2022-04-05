package ca.rttv.malum.registry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;

public class MalumAttributeRegistry {
    private static final Map<EntityAttribute, Identifier> ATTRIBUTES = new LinkedHashMap<>();

    public static final EntityAttribute MAGIC_RESISTANCE = create("magic_resistance", new ClampedEntityAttribute("attribute.name.generic." + MODID + ".magic_resistance", 0.0D, 0.0D, 2048.0D).setTracked(true));
    public static final EntityAttribute MAGIC_PROFICIENCY = create("magic_proficiency", new ClampedEntityAttribute("attribute.name.generic." + MODID + ".magic_proficiency", 0.0D, 0.0D, 2048.0D).setTracked(true));
    public static final EntityAttribute MAGIC_DAMAGE = create("magic_damage", new ClampedEntityAttribute("attribute.name.generic." + MODID + ".magic_damage", 0.0D, 0.0D, 2048.0D).setTracked(true));
    public static final EntityAttribute SCYTHE_PROFICIENCY = create("scythe_proficiency", new ClampedEntityAttribute("attribute.name.generic." + MODID + ".scythe_proficiency", 0.0D, 0.0D, 2048.0D).setTracked(true));
    public static final EntityAttribute SPIRIT_SPOILS = create("spirit_spoils", new ClampedEntityAttribute("attribute.name.generic." + MODID + ".spirit_spoils", 0.0D, 0.0D, 2048.0D).setTracked(true));
    public static final EntityAttribute SPIRIT_REACH = create("spirit_reach", new ClampedEntityAttribute("attribute.name.generic." + MODID + ".spirit_reach", 5D, 0.0D, 2048.0D).setTracked(true));

    public static final EntityAttribute HEART_OF_STONE_STRENGTH = create("heart_of_stone_strength", new ClampedEntityAttribute("attribute.name.generic." + MODID + ".heart_of_stone_strength", 2D, 0.0D, 2048.0D).setTracked(true));
    public static final EntityAttribute HEART_OF_STONE_RECOVERY_SPEED = create("heart_of_stone_recovery_speed", new ClampedEntityAttribute("attribute.name.generic." + MODID + ".heart_of_stone_recovery_speed", 0D, 0.0D, 2048.0D).setTracked(true));
    public static final EntityAttribute HEART_OF_STONE_COST = create("heart_of_stone_hunger_cost", new ClampedEntityAttribute("attribute.name.generic." + MODID + ".heart_of_stone_hunger_cost", 0D, 0.0D, 2048.0D).setTracked(true));
    public static final EntityAttribute HEART_OF_STONE_CAP = create("heart_of_stone_capacity", new ClampedEntityAttribute("attribute.name.generic." + MODID + ".heart_of_stone_capacity", 0D, 0.0D, 2048.0D).setTracked(true));

    public static final EntityAttribute SOUL_WARD_STRENGTH = create("soul_ward_strength", new ClampedEntityAttribute("attribute.name.generic." + MODID + ".soul_ward_strength", 1D, 0.0D, 2048.0D).setTracked(true));
    public static final EntityAttribute SOUL_WARD_RECOVERY_SPEED = create("soul_ward_recovery_speed", new ClampedEntityAttribute("attribute.name.generic." + MODID + ".soul_ward_recovery_speed", 0D, 0.0D, 2048.0D).setTracked(true));
    public static final EntityAttribute SOUL_WARD_DAMAGE_PENALTY = create("soul_ward_damage_penalty", new ClampedEntityAttribute("attribute.name.generic." + MODID + ".soul_ward_damage_penalty", 3D, 0.0D, 2048.0D).setTracked(true));
    public static final EntityAttribute SOUL_WARD_CAP = create("soul_ward_capacity", new ClampedEntityAttribute("attribute.name.generic." + MODID + ".soul_ward_capacity", 0D, 0.0D, 2048.0D).setTracked(true));


    private static <T extends EntityAttribute> EntityAttribute create(String id, EntityAttribute attribute) {
        ATTRIBUTES.put(attribute, new Identifier(MODID, id));
        return attribute;
    }
    public static void init() {
        ATTRIBUTES.keySet().forEach(effect -> Registry.register(Registry.ATTRIBUTE, ATTRIBUTES.get(effect), effect));
    }
}
