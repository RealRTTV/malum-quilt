package ca.rttv.malum.registry;

import ca.rttv.malum.util.spirit.MalumEntitySpiritData;
import ca.rttv.malum.util.spirit.SpiritType;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public interface SpiritTypeRegistry {
    Map<Identifier, MalumEntitySpiritData> SPIRIT_DATA = new HashMap<>();
    Map<String, SpiritType> SPIRITS = new HashMap<>();
}
