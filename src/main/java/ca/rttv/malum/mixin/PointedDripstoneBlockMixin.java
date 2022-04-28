package ca.rttv.malum.mixin;

import ca.rttv.malum.block.entity.TotemBaseBlockEntity;
import ca.rttv.malum.registry.RiteRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.stream.StreamSupport;

@Mixin(PointedDripstoneBlock.class)
public abstract class PointedDripstoneBlockMixin {
    @ModifyVariable(method = "dripTick", at = @At(value = "LOAD", opcode = Opcodes.FLOAD, ordinal = 0), index = 5)
    private static float dripTick(float f, BlockState state, ServerWorld world, BlockPos pos, float dripChance) {
        return f * (1 + StreamSupport.stream(BlockPos.iterateOutwards(pos, 8, 8, 8).spliterator(), false).filter(totemBasePos -> world.getBlockEntity(pos) instanceof TotemBaseBlockEntity totem && totem.rite == RiteRegistry.ELDRITCH_AQUEOUS_RITE && !totem.isCorrupt()).count());
    }
}
