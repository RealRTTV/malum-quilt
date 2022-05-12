package ca.rttv.malum.registry;

import ca.rttv.malum.util.spirit.MalumEntitySpiritData;
import ca.rttv.malum.util.spirit.SpiritType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface SpiritTypeRegistry {
    Map<Identifier, MalumEntitySpiritData> SPIRIT_DATA = new HashMap<>();
    ArrayList<SpiritType> SPIRITS = new ArrayList<>();
}
