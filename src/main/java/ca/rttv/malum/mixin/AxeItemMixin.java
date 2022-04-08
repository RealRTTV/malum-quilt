package ca.rttv.malum.mixin;

import ca.rttv.malum.registry.MalumRegistry;
import com.google.common.collect.ImmutableMap.Builder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;

@Mixin(value = AxeItem.class, priority = 990)
public abstract class AxeItemMixin {

    @Unique
    private static final Map<Block, Block> MALUM_STRIPPED_BLOCKS = (new Builder<Block, Block>())
            .put(MalumRegistry.RUNEWOOD, MalumRegistry.STRIPPED_RUNEWOOD)
            .put(MalumRegistry.RUNEWOOD_LOG, MalumRegistry.STRIPPED_RUNEWOOD_LOG)
            .put(MalumRegistry.EXPOSED_RUNEWOOD_LOG, MalumRegistry.REVEALED_RUNEWOOD_LOG)
            .put(MalumRegistry.SOULWOOD, MalumRegistry.STRIPPED_SOULWOOD)
            .put(MalumRegistry.SOULWOOD_LOG, MalumRegistry.STRIPPED_SOULWOOD_LOG)
            .put(MalumRegistry.EXPOSED_SOULWOOD_LOG, MalumRegistry.REVEALED_SOULWOOD_LOG)
            .build();

    @Inject(method = "getStrippedState", at = @At("HEAD"), cancellable = true)
    private void getStrippedState(BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir) {
        Block strippedVariant = MALUM_STRIPPED_BLOCKS.get(state.getBlock());
        if (strippedVariant != null) {
            cir.setReturnValue(Optional.of(strippedVariant.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS))));
        }
    }
}
