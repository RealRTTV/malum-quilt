package ca.rttv.malum.util.spirit;

public class MalumSpiritAffinity {
    public final String identifier;

    public MalumSpiritAffinity(MalumSpiritType type) {
        this.identifier = type.id + "_affinity";
    }

    public MalumSpiritAffinity(String identifier) {
        this.identifier = identifier;
    }
}
