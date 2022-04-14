package ca.rttv.malum.registry;

import ca.rttv.malum.Malum;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public interface MalumSoundRegistry {
    Map<Identifier, SoundEvent> SOUND_EVENTS = new LinkedHashMap<>();

    SoundEvent SOULSTONE_BREAK = create("soulstone_break");
    SoundEvent SOULSTONE_PLACE = create("soulstone_place");
    SoundEvent SOULSTONE_STEP = create("soulstone_step");
    SoundEvent SOULSTONE_HIT = create("soulstone_hit");

    SoundEvent DEEPSLATE_SOULSTONE_BREAK = create("deepslate_soulstone_break");
    SoundEvent DEEPSLATE_SOULSTONE_PLACE = create("deepslate_soulstone_place");
    SoundEvent DEEPSLATE_SOULSTONE_STEP = create("deepslate_soulstone_step");
    SoundEvent DEEPSLATE_SOULSTONE_HIT = create("deepslate_soulstone_hit");

    SoundEvent ARCANE_CHARCOAL_BREAK = create("arcane_charcoal_block_break");
    SoundEvent ARCANE_CHARCOAL_STEP = create("arcane_charcoal_block_step");
    SoundEvent ARCANE_CHARCOAL_PLACE = create("arcane_charcoal_block_place");
    SoundEvent ARCANE_CHARCOAL_HIT = create("arcane_charcoal_block_hit");

    SoundEvent BRILLIANCE_BREAK = create("brilliance_break");
    SoundEvent BRILLIANCE_PLACE = create("brilliance_place");

    SoundEvent BLAZING_QUARTZ_ORE_BREAK = create("blazing_quartz_ore_break");
    SoundEvent BLAZING_QUARTZ_ORE_PLACE = create("blazing_quartz_ore_place");

    SoundEvent BLAZING_QUARTZ_BLOCK_BREAK = create("blazing_quartz_block_break");
    SoundEvent BLAZING_QUARTZ_BLOCK_PLACE = create("blazing_quartz_block_place");
    SoundEvent BLAZING_QUARTZ_BLOCK_STEP = create("blazing_quartz_block_step");
    SoundEvent BLAZING_QUARTZ_BLOCK_HIT = create("blazing_quartz_block_hit");

    SoundEvent ARCANE_CHARCOAL_BLOCK_BREAK = create("arcane_charcoal_block_break");
    SoundEvent ARCANE_CHARCOAL_BLOCK_PLACE = create("arcane_charcoal_block_place");
    SoundEvent ARCANE_CHARCOAL_BLOCK_STEP = create("arcane_charcoal_block_step");
    SoundEvent ARCANE_CHARCOAL_BLOCK_HIT = create("arcane_charcoal_block_hit");

    SoundEvent TAINTED_ROCK_BREAK = create("tainted_rock_break");
    SoundEvent TAINTED_ROCK_PLACE = create("tainted_rock_place");
    SoundEvent TAINTED_ROCK_STEP = create("tainted_rock_step");
    SoundEvent TAINTED_ROCK_HIT = create("tainted_rock_hit");

    SoundEvent HALLOWED_GOLD_BREAK = create("hallowed_gold_break");
    SoundEvent HALLOWED_GOLD_HIT = create("hallowed_gold_hit");
    SoundEvent HALLOWED_GOLD_PLACE = create("hallowed_gold_place");
    SoundEvent HALLOWED_GOLD_STEP = create("hallowed_gold_step");

    SoundEvent SOUL_STAINED_STEEL_BREAK = create("soul_stained_steel_break");
    SoundEvent SOUL_STAINED_STEEL_HIT = create("soul_stained_steel_hit");
    SoundEvent SOUL_STAINED_STEEL_PLACE = create("soul_stained_steel_place");
    SoundEvent SOUL_STAINED_STEEL_STEP = create("soul_stained_steel_step");

    SoundEvent ETHER_PLACE = create("ether_place");
    SoundEvent ETHER_BREAK = create("ether_break");

    SoundEvent SCYTHE_CUT = create("scythe_cut");
    SoundEvent SPIRIT_HARVEST = create("spirit_harvest");

    SoundEvent TOTEM_CHARGE = create("totem_charge");
    SoundEvent TOTEM_ACTIVATED = create("totem_activated");
    SoundEvent TOTEM_CANCELLED = create("totem_cancelled");
    SoundEvent TOTEM_ENGRAVE = create("totem_engrave");

    SoundEvent ALTAR_CRAFT = create("altar_craft");
    SoundEvent ALTAR_LOOP = create("altar_loop");
    SoundEvent ALTAR_CONSUME = create("altar_consume");
    SoundEvent ALTAR_SPEED_UP = create("altar_speed_up");

    SoundEvent CRUCIBLE_CRAFT = create("crucible_craft");
    SoundEvent CRUCIBLE_LOOP = create("crucible_loop");
    SoundEvent IMPETUS_CRACK = create("impetus_crack");

    SoundEvent SINISTER_EQUIP = create("sinister_equip");
    SoundEvent HOLY_EQUIP = create("holy_equip");

    SoundEvent VOID_SLASH = create("void_slash");

    SoundEvent SOUL_WARD_HIT = create("soul_ward_hit");
    SoundEvent SOUL_WARD_GROW = create("soul_ward_grow");
    SoundEvent SOUL_WARD_CHARGE = create("soul_ward_charge");

    SoundEvent HEART_OF_STONE_HIT = create("heart_of_stone_hit");
    SoundEvent HEART_OF_STONE_GROW = create("heart_of_stone_grow");

    SoundEvent SUSPICIOUS_SOUND = create("suspicious_sound");

    static SoundEvent create(String name) {
        Identifier id = new Identifier(Malum.MODID, name);
        SoundEvent soundEvent = new SoundEvent(id);
        SOUND_EVENTS.put(id, soundEvent);
        return soundEvent;
    }

    static void init() {
        SOUND_EVENTS.forEach((id, sound) -> Registry.register(Registry.SOUND_EVENT, id, sound));
    }
}
