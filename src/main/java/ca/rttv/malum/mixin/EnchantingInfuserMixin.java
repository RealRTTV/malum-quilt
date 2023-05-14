package ca.rttv.malum.mixin;

import ca.rttv.malum.block.ObeliskBlock;
import fuzs.enchantinginfuser.core.FabricAbstractions;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ca.rttv.malum.registry.MalumBlockRegistry.BRILLIANT_OBELISK;

@Mixin(FabricAbstractions.class)
abstract class EnchantingInfuserMixin {
    @Inject(method = "getEnchantPowerBonus", at = @At("HEAD"), cancellable = true)
    private void malum$getEnchantPowerBonus(BlockState state, World level, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (state.isOf(BRILLIANT_OBELISK)) {
            cir.setReturnValue(state.get(ObeliskBlock.HALF) == DoubleBlockHalf.LOWER ? 5.0f : 1.0f);
            cir.cancel();
        }
    }
}
