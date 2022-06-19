package ca.rttv.malum.registry;

import ca.rttv.malum.Malum;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public interface MalumSignRegistry {
    Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();
    Block RUNEWOOD_SIGN                               = register("runewood_sign",                             new SignBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).noCollision().sounds(BlockSoundGroup.WOOD), MalumSignTypeRegistry.RUNEWOOD_SIGN_TYPE));
    Block RUNEWOOD_WALL_SIGN                          = register("runewood_wall_sign",                        new WallSignBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).noCollision().sounds(BlockSoundGroup.WOOD), MalumSignTypeRegistry.RUNEWOOD_SIGN_TYPE));
    Block SOULWOOD_SIGN                               = register("soulwood_sign",                             new SignBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).noCollision().sounds(BlockSoundGroup.WOOD), MalumSignTypeRegistry.SOULWOOD_SIGN_TYPE));
    Block SOULWOOD_WALL_SIGN                          = register("soulwood_wall_sign",                        new WallSignBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).noCollision().sounds(BlockSoundGroup.WOOD), MalumSignTypeRegistry.SOULWOOD_SIGN_TYPE));
    static <T extends Block> T register(String id, T block) {
        BLOCKS.put(new Identifier(Malum.MODID, id), block);
        return block;
    }
    static void init() {
        BLOCKS.forEach((id, entry) -> Registry.register(Registry.BLOCK, id, entry));
    }
}
