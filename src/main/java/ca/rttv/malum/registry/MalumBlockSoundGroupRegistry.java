package ca.rttv.malum.registry;

import net.minecraft.sound.BlockSoundGroup;

import static ca.rttv.malum.registry.MalumSoundRegistry.*;
import static net.minecraft.sound.SoundEvents.*;

public interface MalumBlockSoundGroupRegistry {
    BlockSoundGroup BLOCK_ARCANE_CHARCOAL_SOUNDS               = new BlockSoundGroup      (1.0f, 1.0f, ARCANE_CHARCOAL_BREAK, ARCANE_CHARCOAL_STEP, ARCANE_CHARCOAL_PLACE, ARCANE_CHARCOAL_HIT, ARCANE_CHARCOAL_STEP);
    BlockSoundGroup BLOCK_SOULSTONE_SOUNDS                     = new BlockSoundGroup      (1.0f, 1.0f, SOULSTONE_BREAK, SOULSTONE_STEP, SOULSTONE_PLACE, SOULSTONE_HIT, SOULSTONE_STEP);
    BlockSoundGroup BLOCK_TAINTED_ROCK_SOUNDS                  = new BlockSoundGroup      (1.0f, 1.0f, TAINTED_ROCK_BREAK, TAINTED_ROCK_STEP, TAINTED_ROCK_PLACE, TAINTED_ROCK_HIT, TAINTED_ROCK_STEP);
    BlockSoundGroup BLOCK_BLAZING_QUARTZ_ORE_SOUNDS            = new BlockSoundGroup      (1.0f, 1.0f, BLAZING_QUARTZ_ORE_BREAK, BLAZING_QUARTZ_BLOCK_STEP, BLAZING_QUARTZ_ORE_PLACE, BLAZING_QUARTZ_BLOCK_HIT, BLAZING_QUARTZ_BLOCK_STEP);
    BlockSoundGroup BLOCK_BLAZING_QUARTZ_BLOCK_SOUNDS          = new BlockSoundGroup      (1.0f, 1.0f, BLAZING_QUARTZ_BLOCK_BREAK, BLAZING_QUARTZ_BLOCK_STEP, BLAZING_QUARTZ_BLOCK_PLACE, BLAZING_QUARTZ_BLOCK_HIT, BLAZING_QUARTZ_BLOCK_STEP);
    BlockSoundGroup BLOCK_BRILLIANCE_SOUNDS                    = new BlockSoundGroup      (1.0f, 1.0f, BRILLIANCE_BREAK, BLOCK_STONE_STEP, BRILLIANCE_PLACE, BLOCK_STONE_HIT, BLOCK_STONE_FALL);

    static void init() {

    }
}
