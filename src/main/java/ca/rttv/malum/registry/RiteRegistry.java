package ca.rttv.malum.registry;

import ca.rttv.malum.rite.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumRegistry.*;

public interface RiteRegistry {
    Rite SACRED_RITE             = register("sacred_rite",            new SacredRite(ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT));
    Rite ELDRITCH_SACRED_RITE    = register("eldritch_sacred_rite",   new EldritchSacredRite(ELDRITCH_SPIRIT, ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT));
    Rite WICKED_RITE             = register("wicked_rite",            new WickedRite(ARCANE_SPIRIT, WICKED_SPIRIT, WICKED_SPIRIT));
    Rite ELDRITCH_WICKED_RITE    = register("eldritch_wicked_rite",   new EldritchWickedRite(ELDRITCH_SPIRIT, ARCANE_SPIRIT, WICKED_SPIRIT, WICKED_SPIRIT));
    Rite ARCANE_RITE             = register("arcane_rite",            new ArcaneRite(ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT));
    Rite EARTHEN_RITE            = register("earthen_rite",           new EarthenRite(ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT));
    Rite ELDRITCH_EARTHEN_RITE   = register("eldritch_earthen_rite",  new EldritchEarthenRite(ELDRITCH_SPIRIT, ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT));
    Rite INFERNAL_RITE           = register("infernal_rite",          new InfernalRite(ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT));
    Rite ELDRITCH_INFERNAL_RITE  = register("eldritch_infernal_rite", new EldritchInfernalRite(ELDRITCH_SPIRIT, ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT));
    Rite AERIAL_RITE             = register("aerial_rite",            new AerialRite(ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT));
    Rite ELDRITCH_AERIAL_RITE    = register("eldritch_aerial_rite",   new EldritchAerialRite(ELDRITCH_SPIRIT, ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT));
    Rite AQUEOUS_RITE            = register("aqueous_rite",           new AqueousRite(ARCANE_SPIRIT, AQUEOUS_SPIRIT, AQUEOUS_SPIRIT));
    Rite ELDRITCH_AQUEOUS_RITE   = register("eldritch_aqueous_rite",  new EldritchAqueousRite(ELDRITCH_SPIRIT, ARCANE_SPIRIT, AQUEOUS_SPIRIT, AQUEOUS_SPIRIT));

    static <T extends Rite> T register(String id, T rite) {
        return Registry.register(RITE, new Identifier(MODID, id), rite);
    }
}
