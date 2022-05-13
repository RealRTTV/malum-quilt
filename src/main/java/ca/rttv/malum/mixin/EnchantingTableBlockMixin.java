package ca.rttv.malum.mixin;

import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ca.rttv.malum.registry.MalumBlockRegistry.BRILLIANT_OBELISK;

@Mixin(EnchantingTableBlock.class)
public abstract class EnchantingTableBlockMixin {
    @Inject(method = "method_40445", at = @At("HEAD"), cancellable = true)
    private static void method_40445(World world, BlockPos blockPos, BlockPos blockPos2, CallbackInfoReturnable<Boolean> cir) {
        if (world.getBlockState(blockPos.add(blockPos2)).isOf(BRILLIANT_OBELISK)
         && world.isAir(blockPos.add(blockPos2.getX() / 2, blockPos2.getY(), blockPos2.getZ() / 2))) {
            cir.setReturnValue(true);
        }
    }
}
