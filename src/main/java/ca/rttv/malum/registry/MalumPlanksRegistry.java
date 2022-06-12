package ca.rttv.malum.registry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;
import static net.minecraft.sound.BlockSoundGroup.WOOD;

public interface MalumPlanksRegistry { // this has to be made because of an early riser issue
    Map<Identifier, Block> PLANKS = new LinkedHashMap<>();

    Block RUNEWOOD_PLANKS = register("runewood_planks", new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(WOOD)));
    Block SOULWOOD_PLANKS = register("soulwood_planks", new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(WOOD)));

    static void init() {
        PLANKS.forEach((id, entry) -> Registry.register(Registry.BLOCK, id, entry));
    }

    static <T extends Block> T register(String id, T block) {
        PLANKS.put(new Identifier(MODID, id), block);
        return block;
    }
}
