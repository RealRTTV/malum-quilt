package ca.rttv.malum.registry;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.entity.vehicle.BoatEntity;

public interface MalumBoatTypes {
    BoatEntity.Type RUNEWOOD = ClassTinkerers.getEnum(BoatEntity.Type.class, "RUNEWOOD");
    BoatEntity.Type SOULWOOD = ClassTinkerers.getEnum(BoatEntity.Type.class, "SOULWOOD");
}
