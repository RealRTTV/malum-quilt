package ca.rttv.malum.registry;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.item.ArmorMaterials;

public interface MalumArmorMaterials {
    ArmorMaterials SOUL_CLOAK         = ClassTinkerers.getEnum(ArmorMaterials.class, "SOUL_CLOAK");
    ArmorMaterials SOUL_STAINED_STEEL = ClassTinkerers.getEnum(ArmorMaterials.class, "SOUL_STAINED_STEEL");
}
