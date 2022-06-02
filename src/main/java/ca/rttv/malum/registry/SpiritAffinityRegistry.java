package ca.rttv.malum.registry;

import ca.rttv.malum.util.spirit.MalumSpiritAffinity;
import ca.rttv.malum.util.spirit.spiritaffinity.ArcaneAffinity;

import java.util.HashMap;

@SuppressWarnings("unused")
public interface SpiritAffinityRegistry {
    HashMap<String, MalumSpiritAffinity> AFFINITIES = new HashMap<>();

    MalumSpiritAffinity ARCANE_AFFINITY = register(new ArcaneAffinity());
//    public static final MalumSpiritAffinity EARTHEN_AFFINITY = create(new EarthenAffinity());

    static MalumSpiritAffinity register(MalumSpiritAffinity affinity) {
        AFFINITIES.put(affinity.identifier, affinity);
        return affinity;
    }
}
