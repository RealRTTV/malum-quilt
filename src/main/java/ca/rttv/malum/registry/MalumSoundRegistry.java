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

    SoundEvent SOULSTONE_BREAK = register("soulstone_break");
    SoundEvent SOULSTONE_PLACE = register("soulstone_place");
    SoundEvent SOULSTONE_STEP  = register("soulstone_step");
    SoundEvent SOULSTONE_HIT   = register("soulstone_hit");

    SoundEvent DEEPSLATE_SOULSTONE_BREAK   = register("deepslate_soulstone_break");
    SoundEvent DEEPSLATE_SOULSTONE_PLACE   = register("deepslate_soulstone_place");
    SoundEvent DEEPSLATE_SOULSTONE_STEP    = register("deepslate_soulstone_step");
    SoundEvent DEEPSLATE_SOULSTONE_HIT     = register("deepslate_soulstone_hit");

    SoundEvent ARCANE_CHARCOAL_BREAK       = register("arcane_charcoal_block_break");
    SoundEvent ARCANE_CHARCOAL_STEP        = register("arcane_charcoal_block_step");
    SoundEvent ARCANE_CHARCOAL_PLACE       = register("arcane_charcoal_block_place");
    SoundEvent ARCANE_CHARCOAL_HIT         = register("arcane_charcoal_block_hit");

    SoundEvent BRILLIANCE_BREAK            = register("brilliance_break");
    SoundEvent BRILLIANCE_PLACE            = register("brilliance_place");

    SoundEvent BLAZING_QUARTZ_ORE_BREAK    = register("blazing_quartz_ore_break");
    SoundEvent BLAZING_QUARTZ_ORE_PLACE    = register("blazing_quartz_ore_place");

    SoundEvent BLAZING_QUARTZ_BLOCK_BREAK  = register("blazing_quartz_block_break");
    SoundEvent BLAZING_QUARTZ_BLOCK_PLACE  = register("blazing_quartz_block_place");
    SoundEvent BLAZING_QUARTZ_BLOCK_STEP   = register("blazing_quartz_block_step");
    SoundEvent BLAZING_QUARTZ_BLOCK_HIT    = register("blazing_quartz_block_hit");

    SoundEvent ARCANE_CHARCOAL_BLOCK_BREAK = register("arcane_charcoal_block_break");
    SoundEvent ARCANE_CHARCOAL_BLOCK_PLACE = register("arcane_charcoal_block_place");
    SoundEvent ARCANE_CHARCOAL_BLOCK_STEP  = register("arcane_charcoal_block_step");
    SoundEvent ARCANE_CHARCOAL_BLOCK_HIT   = register("arcane_charcoal_block_hit");

    SoundEvent TAINTED_ROCK_BREAK          = register("tainted_rock_break");
    SoundEvent TAINTED_ROCK_PLACE          = register("tainted_rock_place");
    SoundEvent TAINTED_ROCK_STEP           = register("tainted_rock_step");
    SoundEvent TAINTED_ROCK_HIT            = register("tainted_rock_hit");

    SoundEvent HALLOWED_GOLD_BREAK         = register("hallowed_gold_break");
    SoundEvent HALLOWED_GOLD_HIT           = register("hallowed_gold_hit");
    SoundEvent HALLOWED_GOLD_PLACE         = register("hallowed_gold_place");
    SoundEvent HALLOWED_GOLD_STEP          = register("hallowed_gold_step");

    SoundEvent SOUL_STAINED_STEEL_BREAK    = register("soul_stained_steel_break");
    SoundEvent SOUL_STAINED_STEEL_HIT      = register("soul_stained_steel_hit");
    SoundEvent SOUL_STAINED_STEEL_PLACE    = register("soul_stained_steel_place");
    SoundEvent SOUL_STAINED_STEEL_STEP     = register("soul_stained_steel_step");

    SoundEvent ETHER_PLACE                 = register("ether_place");
    SoundEvent ETHER_BREAK                 = register("ether_break");

    SoundEvent SCYTHE_CUT                  = register("scythe_cut");
    SoundEvent SPIRIT_HARVEST              = register("spirit_harvest");

    SoundEvent TOTEM_CHARGE                = register("totem_charge");
    SoundEvent TOTEM_ACTIVATED             = register("totem_activated");
    SoundEvent TOTEM_CANCELLED             = register("totem_cancelled");
    SoundEvent TOTEM_ENGRAVE               = register("totem_engrave");

    SoundEvent ALTAR_CRAFT                 = register("altar_craft");
    SoundEvent ALTAR_LOOP                  = register("altar_loop");
    SoundEvent ALTAR_CONSUME               = register("altar_consume");
    SoundEvent ALTAR_SPEED_UP              = register("altar_speed_up");

    SoundEvent CRUCIBLE_CRAFT              = register("crucible_craft");
    SoundEvent CRUCIBLE_LOOP               = register("crucible_loop");
    SoundEvent IMPETUS_CRACK               = register("impetus_crack");

    SoundEvent SINISTER_EQUIP              = register("sinister_equip");
    SoundEvent HOLY_EQUIP                  = register("holy_equip");

    SoundEvent VOID_SLASH                  = register("void_slash");

    SoundEvent SOUL_WARD_HIT               = register("soul_ward_hit");
    SoundEvent SOUL_WARD_GROW              = register("soul_ward_grow");
    SoundEvent SOUL_WARD_CHARGE            = register("soul_ward_charge");

    SoundEvent HEART_OF_STONE_HIT          = register("heart_of_stone_hit");
    SoundEvent HEART_OF_STONE_GROW         = register("heart_of_stone_grow");

    SoundEvent SUSPICIOUS_SOUND            = register("suspicious_sound");

    static SoundEvent register(String name) {
        Identifier id = new Identifier(Malum.MODID, name);
        SoundEvent soundEvent = new SoundEvent(id);
        SOUND_EVENTS.put(id, soundEvent);
        return soundEvent;
    }

    static void init() {
        SOUND_EVENTS.forEach((id, sound) -> Registry.register(Registry.SOUND_EVENT, id, sound));
    }
}
