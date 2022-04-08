package ca.rttv.malum.registry;

import ca.rttv.malum.util.spirit.MalumEntitySpiritData;
import ca.rttv.malum.util.spirit.MalumSpiritType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpiritTypeRegistry {
    public static Map<Identifier, MalumEntitySpiritData> SPIRIT_DATA = new HashMap<>();
    public static final ArrayList<MalumSpiritType> SPIRITS = new ArrayList<>();

    public static final Color SACRED_SPIRIT_COLOR = new Color(243, 65, 107);
    public static final MalumSpiritType SACRED_SPIRIT = create("sacred", SACRED_SPIRIT_COLOR, MalumRegistry.SACRED_SPIRIT);

    public static final Color WICKED_SPIRIT_COLOR = new Color(178, 29, 232);
    public static final MalumSpiritType WICKED_SPIRIT = create("wicked", WICKED_SPIRIT_COLOR, MalumRegistry.WICKED_SPIRIT);

    public static final Color ARCANE_SPIRIT_COLOR = new Color(212, 55, 255);
    public static final MalumSpiritType ARCANE_SPIRIT = create("arcane", ARCANE_SPIRIT_COLOR, MalumRegistry.ARCANE_SPIRIT);

    public static final Color ELDRITCH_SPIRIT_COLOR = new Color(148, 45, 245);
    public static final MalumSpiritType ELDRITCH_SPIRIT = create("eldritch", ELDRITCH_SPIRIT_COLOR, new Color(39, 201, 103), MalumRegistry.ELDRITCH_SPIRIT);

    public static final Color AERIAL_SPIRIT_COLOR = new Color(75, 243, 218);
    public static final MalumSpiritType AERIAL_SPIRIT = create("aerial", AERIAL_SPIRIT_COLOR, MalumRegistry.AERIAL_SPIRIT);

    public static final Color AQUEOUS_SPIRIT_COLOR = new Color(42, 114, 232);
    public static final MalumSpiritType AQUEOUS_SPIRIT = create("aqueous", AQUEOUS_SPIRIT_COLOR, MalumRegistry.AQUEOUS_SPIRIT);

    public static final Color INFERNAL_SPIRIT_COLOR = new Color(210, 134, 39);
    public static final MalumSpiritType INFERNAL_SPIRIT = create("infernal", INFERNAL_SPIRIT_COLOR, MalumRegistry.INFERNAL_SPIRIT);

    public static final Color EARTHEN_SPIRIT_COLOR = new Color(73, 234, 27);
    public static final MalumSpiritType EARTHEN_SPIRIT = create("earthen", EARTHEN_SPIRIT_COLOR, MalumRegistry.EARTHEN_SPIRIT);

    public static MalumSpiritType create(String identifier, Color color, Item splinterItem) {
        MalumSpiritType spiritType = new MalumSpiritType(identifier, color, splinterItem);
        SPIRITS.add(spiritType);
        return spiritType;
    }

    public static MalumSpiritType create(String identifier, Color color, Color endColor, Item splinterItem) {
        MalumSpiritType spiritType = new MalumSpiritType(identifier, color, endColor, splinterItem);
        SPIRITS.add(spiritType);
        return spiritType;
    }
}
