package ca.rttv.malum.registry;

import ca.rttv.malum.util.spirit.MalumSpiritAffinity;
import ca.rttv.malum.util.spirit.spiritaffinity.SoulWard;

import java.util.HashMap;

public class SpiritAffinityRegistry {
    public static HashMap<String, MalumSpiritAffinity> AFFINITIES = new HashMap<>();

    public static final MalumSpiritAffinity ARCANE_AFFINITY = create(new SoulWard());
//    public static final MalumSpiritAffinity EARTHEN_AFFINITY = create(new EarthenAffinity());

    public static MalumSpiritAffinity create(MalumSpiritAffinity affinity)
    {
        AFFINITIES.put(affinity.identifier, affinity);
        return affinity;
    }
}
