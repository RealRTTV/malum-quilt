package ca.rttv.malum.registry;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.item.ToolMaterials;

public interface MalumToolMaterials {
    ToolMaterials SOUL_STAINED_STEEL = ClassTinkerers.getEnum(ToolMaterials.class, "SOUL_STAINED_STEEL");
    ToolMaterials TWISTED_ROCK = ClassTinkerers.getEnum(ToolMaterials.class, "TWISTED_ROCK");
}
