package ca.rttv.malum;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;

import static ca.rttv.malum.registry.MalumRegistry.BLOCK_OF_SOULSTONE;

public class MalumEarlyRiser implements Runnable {
    @Override
    public void run() {
        var remapper = FabricLoader.getInstance().getMappingResolver();
        String woodTypeTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1690$class_1692");
        ClassTinkerers.enumBuilder(woodTypeTarget, Block.class, String.class).addEnum("SOULSTONE", BLOCK_OF_SOULSTONE, "soulstone").build();
    }
}
