package ca.rttv.malum.util.spirit;

import net.minecraft.item.Item;
import net.minecraft.util.StringIdentifiable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum SpiritTypeProperty implements StringIdentifiable {
    SACRED(MalumSpiritType.SACRED_SPIRIT),
    WICKED(MalumSpiritType.WICKED_SPIRIT),
    ARCANE(MalumSpiritType.ARCANE_SPIRIT),
    ELDRITCH(MalumSpiritType.ELDRITCH_SPIRIT),
    AERIAL(MalumSpiritType.AERIAL_SPIRIT),
    AQUEOUS(MalumSpiritType.AQUEOUS_SPIRIT),
    INFERNAL(MalumSpiritType.INFERNAL_SPIRIT),
    EARTHEN(MalumSpiritType.EARTHEN_SPIRIT);

    public static final Logger LOGGER = LogManager.getLogger();
    public final MalumSpiritType spirit;

    SpiritTypeProperty(MalumSpiritType spirit) {
        this.spirit = spirit;
    }

    public static SpiritTypeProperty of(Item item) {
        for (SpiritTypeProperty spirit : values()) {
            if (spirit.spirit.getSplinterItem() == item) {
                return spirit;
            }
        }

        LOGGER.warn("Defaulted to sacred spirit, could not find spirit item in this enum");
        return SACRED;
    }

    @Override
    public String asString() {
        return this.spirit.id;
    }
}
