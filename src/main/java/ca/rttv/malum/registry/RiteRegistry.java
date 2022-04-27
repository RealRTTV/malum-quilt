package ca.rttv.malum.registry;

import ca.rttv.malum.util.Rite;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumRegistry.*;

public interface RiteRegistry {
    Rite SACRED_RITE             = register("sacred_rite", new Rite(ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT));
    Rite ELDRITCH_SACRED_RITE    = register("eldritch_sacred_rite", new Rite(ELDRITCH_SPIRIT, ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT));
    Rite WICKED_RITE             = register("wicked_rite", new Rite(ARCANE_SPIRIT, WICKED_SPIRIT, WICKED_SPIRIT));
    Rite ELDRITCH_WICKED_RITE    = register("eldritch_wicked_rite", new Rite(ELDRITCH_SPIRIT, ARCANE_SPIRIT, WICKED_SPIRIT, WICKED_SPIRIT));
    Rite ARCANE_RITE             = register("arcane_rite", new Rite(ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT));
    Rite EARTHEN_RITE            = register("earthen_rite", new Rite(ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT));
    Rite ELDRITCH_EARTHEN_RITE   = register("eldritch_earthen_rite", new Rite(ELDRITCH_SPIRIT, ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT));
    Rite INFERNAL_RITE           = register("infernal_rite", new Rite(ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT));
    Rite ELDRITCH_INFERNAL_RITE  = register("eldritch_infernal_rite", new Rite(ELDRITCH_SPIRIT, ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT));
    Rite AERIAL_RITE             = register("aerial_rite", new Rite(ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT));
    Rite ELDRITCH_AERIAL_RITE    = register("eldritch_aerial_rite", new Rite(ELDRITCH_SPIRIT, ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT));
    Rite AQUEOUS_RITE            = register("aqueous_rite", new Rite(ARCANE_SPIRIT, AQUEOUS_SPIRIT, AQUEOUS_SPIRIT));
    Rite ELDRITCH_AQUEOUS_RITE   = register("eldritch_aqueous_rite", new Rite(ELDRITCH_SPIRIT, ARCANE_SPIRIT, AQUEOUS_SPIRIT, AQUEOUS_SPIRIT));

    static Rite register(String id, Rite rite) {
        return Registry.register(RITE, new Identifier(MODID, id), rite);
    }
}
