package ca.rttv.malum.util.spirit;

import net.minecraft.item.Item;
import net.minecraft.util.StringIdentifiable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum SpiritTypeProperty implements StringIdentifiable {
    SACRED(SpiritType.SACRED_SPIRIT),
    WICKED(SpiritType.WICKED_SPIRIT),
    ARCANE(SpiritType.ARCANE_SPIRIT),
    ELDRITCH(SpiritType.ELDRITCH_SPIRIT),
    AERIAL(SpiritType.AERIAL_SPIRIT),
    AQUEOUS(SpiritType.AQUEOUS_SPIRIT),
    INFERNAL(SpiritType.INFERNAL_SPIRIT),
    EARTHEN(SpiritType.EARTHEN_SPIRIT);

    public static final Logger LOGGER = LogManager.getLogger();
    public final SpiritType spirit;

    SpiritTypeProperty(SpiritType spirit) {
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
