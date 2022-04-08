package ca.rttv.malum.registry;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.entity.vehicle.BoatEntity;

public class MalumBoatTypes {
    public static final BoatEntity.Type RUNEWOOD = ClassTinkerers.getEnum(BoatEntity.Type.class, "RUNEWOOD");
    public static final BoatEntity.Type SOULWOOD = ClassTinkerers.getEnum(BoatEntity.Type.class, "SOULWOOD");
}
