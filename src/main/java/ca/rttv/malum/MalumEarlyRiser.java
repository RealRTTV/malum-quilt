package ca.rttv.malum;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

import static ca.rttv.malum.registry.MalumRegistry.RUNEWOOD_PLANKS;

public class MalumEarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
        String type = remapper.mapClassName("intermediary", "net.minecraft.class_1690$class_1692");
        String param1 = "L" + remapper.mapClassName("intermediary", "net.minecraft.class_2248") + ";";
        System.out.println(type);
        System.out.println(param1);
        ClassTinkerers.enumBuilder(type, param1, String.class).addEnum("RUNEWOOD", () -> new Object[]{RUNEWOOD_PLANKS, "runewood"}).build();
    }
}
