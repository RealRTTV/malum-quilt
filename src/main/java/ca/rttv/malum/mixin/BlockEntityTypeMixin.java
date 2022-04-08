package ca.rttv.malum.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static ca.rttv.malum.registry.MalumRegistry.*;

@Mixin(BlockEntityType.class)
public abstract class BlockEntityTypeMixin {
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntityType$Builder;create(Lnet/minecraft/block/entity/BlockEntityType$BlockEntityFactory;[Lnet/minecraft/block/Block;)Lnet/minecraft/block/entity/BlockEntityType$Builder;"), index = 1)
    private static Block[] signAdditions(Block[] blocks) {
        Block[] newBlocks = new Block[blocks.length + 4];
        System.arraycopy(blocks, 0, newBlocks, 0, blocks.length);
        newBlocks[newBlocks.length - 1] = RUNEWOOD_SIGN;
        newBlocks[newBlocks.length - 2] = RUNEWOOD_WALL_SIGN;
        newBlocks[newBlocks.length - 3] = SOULWOOD_SIGN;
        newBlocks[newBlocks.length - 4] = SOULWOOD_WALL_SIGN;
        return newBlocks;
    }
}
