package ca.rttv.malum.registry;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.entity.vehicle.BoatEntity;

public interface MalumBoatTypes {
    BoatEntity.Variant RUNEWOOD = ClassTinkerers.getEnum(BoatEntity.Variant.class, "RUNEWOOD");
    BoatEntity.Variant SOULWOOD = ClassTinkerers.getEnum(BoatEntity.Variant.class, "SOULWOOD");
}
