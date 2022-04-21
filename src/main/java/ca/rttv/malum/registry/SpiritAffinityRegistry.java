package ca.rttv.malum.registry;

import ca.rttv.malum.util.spirit.MalumSpiritAffinity;
import ca.rttv.malum.util.spirit.spiritaffinity.ArcaneAffinity;

import java.util.HashMap;

public interface SpiritAffinityRegistry {
    HashMap<String, MalumSpiritAffinity> AFFINITIES = new HashMap<>();

    MalumSpiritAffinity ARCANE_AFFINITY = create(new ArcaneAffinity());
//    public static final MalumSpiritAffinity EARTHEN_AFFINITY = create(new EarthenAffinity());

    static MalumSpiritAffinity create(MalumSpiritAffinity affinity) {
        AFFINITIES.put(affinity.identifier, affinity);
        return affinity;
    }
}
