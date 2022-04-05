package ca.rttv.malum.registry;

import ca.rttv.malum.Malum;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class MalumSoundRegistry {
    private static final Map<Identifier, SoundEvent> SOUND_EVENTS = new LinkedHashMap<>();
    public static final SoundEvent SOULSTONE_BREAK = create("soulstone_break");
    public static final SoundEvent SOULSTONE_PLACE = create("soulstone_place");
    public static final SoundEvent SOULSTONE_STEP = create("soulstone_step");
    public static final SoundEvent SOULSTONE_HIT = create("soulstone_hit");

    public static final SoundEvent DEEPSLATE_SOULSTONE_BREAK = create("deepslate_soulstone_break");
    public static final SoundEvent DEEPSLATE_SOULSTONE_PLACE = create("deepslate_soulstone_place");
    public static final SoundEvent DEEPSLATE_SOULSTONE_STEP = create("deepslate_soulstone_step");
    public static final SoundEvent DEEPSLATE_SOULSTONE_HIT = create("deepslate_soulstone_hit");

    public static final SoundEvent BRILLIANCE_BREAK = create("brilliance_break");
    public static final SoundEvent BRILLIANCE_PLACE = create("brilliance_place");

    public static final SoundEvent BLAZING_QUARTZ_ORE_BREAK = create("blazing_quartz_ore_break");
    public static final SoundEvent BLAZING_QUARTZ_ORE_PLACE = create("blazing_quartz_ore_place");

    public static final SoundEvent BLAZING_QUARTZ_BLOCK_BREAK = create("blazing_quartz_block_break");
    public static final SoundEvent BLAZING_QUARTZ_BLOCK_PLACE = create("blazing_quartz_block_place");
    public static final SoundEvent BLAZING_QUARTZ_BLOCK_STEP = create("blazing_quartz_block_step");
    public static final SoundEvent BLAZING_QUARTZ_BLOCK_HIT = create("blazing_quartz_block_hit");

    public static final SoundEvent ARCANE_CHARCOAL_BLOCK_BREAK = create("arcane_charcoal_block_break");
    public static final SoundEvent ARCANE_CHARCOAL_BLOCK_PLACE = create("arcane_charcoal_block_place");
    public static final SoundEvent ARCANE_CHARCOAL_BLOCK_STEP = create("arcane_charcoal_block_step");
    public static final SoundEvent ARCANE_CHARCOAL_BLOCK_HIT = create("arcane_charcoal_block_hit");

    public static final SoundEvent TAINTED_ROCK_BREAK = create("tainted_rock_break");
    public static final SoundEvent TAINTED_ROCK_PLACE = create("tainted_rock_place");
    public static final SoundEvent TAINTED_ROCK_STEP = create("tainted_rock_step");
    public static final SoundEvent TAINTED_ROCK_HIT = create("tainted_rock_hit");

    public static final SoundEvent HALLOWED_GOLD_BREAK = create("hallowed_gold_break");
    public static final SoundEvent HALLOWED_GOLD_HIT = create("hallowed_gold_hit");
    public static final SoundEvent HALLOWED_GOLD_PLACE = create("hallowed_gold_place");
    public static final SoundEvent HALLOWED_GOLD_STEP = create("hallowed_gold_step");

    public static final SoundEvent SOUL_STAINED_STEEL_BREAK = create("soul_stained_steel_break");
    public static final SoundEvent SOUL_STAINED_STEEL_HIT = create("soul_stained_steel_hit");
    public static final SoundEvent SOUL_STAINED_STEEL_PLACE = create("soul_stained_steel_place");
    public static final SoundEvent SOUL_STAINED_STEEL_STEP = create("soul_stained_steel_step");

    public static final SoundEvent ETHER_PLACE = create("ether_place");
    public static final SoundEvent ETHER_BREAK = create("ether_break");

    public static final SoundEvent SCYTHE_CUT = create("scythe_cut");
    public static final SoundEvent SPIRIT_HARVEST = create("spirit_harvest");

    public static final SoundEvent TOTEM_CHARGE = create("totem_charge");
    public static final SoundEvent TOTEM_ACTIVATED = create("totem_activated");
    public static final SoundEvent TOTEM_CANCELLED = create("totem_cancelled");
    public static final SoundEvent TOTEM_ENGRAVE = create("totem_engrave");

    public static final SoundEvent ALTAR_CRAFT = create("altar_craft");
    public static final SoundEvent ALTAR_LOOP = create("altar_loop");
    public static final SoundEvent ALTAR_CONSUME = create("altar_consume");
    public static final SoundEvent ALTAR_SPEED_UP = create("altar_speed_up");

    public static final SoundEvent CRUCIBLE_CRAFT = create("crucible_craft");
    public static final SoundEvent CRUCIBLE_LOOP = create("crucible_loop");
    public static final SoundEvent IMPETUS_CRACK = create("impetus_crack");

    public static final SoundEvent SINISTER_EQUIP = create("sinister_equip");
    public static final SoundEvent HOLY_EQUIP = create("holy_equip");

    public static final SoundEvent VOID_SLASH = create("void_slash");

    public static final SoundEvent SOUL_WARD_HIT = create("soul_ward_hit");
    public static final SoundEvent SOUL_WARD_GROW = create("soul_ward_grow");
    public static final SoundEvent SOUL_WARD_CHARGE = create("soul_ward_charge");

    public static final SoundEvent HEART_OF_STONE_HIT = create("heart_of_stone_hit");
    public static final SoundEvent HEART_OF_STONE_GROW = create("heart_of_stone_grow");

    public static final SoundEvent SUSPICIOUS_SOUND = create("suspicious_sound");

    private static SoundEvent create(String name) {
        Identifier id = new Identifier(Malum.MODID, name);
        SoundEvent soundEvent = new SoundEvent(id);
        SOUND_EVENTS.put(id, soundEvent);
        return soundEvent;
    }

    public static void init() {
        SOUND_EVENTS.forEach((id, sound) -> Registry.register(Registry.SOUND_EVENT, id, sound));
    }
}
