package ca.rttv.malum.mixin;

import ca.rttv.malum.block.TotemPoleBlock;
import com.google.common.collect.ImmutableMap.Builder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static ca.rttv.malum.registry.MalumBlockRegistry.*;

@Mixin(AxeItem.class)
abstract class AxeItemMixin {
    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/AxeItem;getStrippedState(Lnet/minecraft/block/BlockState;)Ljava/util/Optional;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void malum$useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, World world, BlockPos blockPos, PlayerEntity playerEntity, BlockState blockState) {
        if (blockState.getBlock() instanceof TotemPoleBlock totemPoleBlock) {
            totemPoleBlock.onStrip(blockState, world, blockPos);
        }
    }
}
