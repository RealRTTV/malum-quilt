package ca.rttv.malum.mixin;

import ca.rttv.malum.block.GradientLeavesBlock;
import ca.rttv.malum.block.entity.EtherBlockEntity;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.awt.*;

import static ca.rttv.malum.registry.MalumRegistry.*;

@Mixin(BlockColors.class)
public abstract class BlockColorsMixin {
    @Inject(method = "create", at = @At(value = "RETURN", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void create(CallbackInfoReturnable<BlockColors> cir, BlockColors blockColors) {
        blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
            if (world == null && pos == null) return 251 << 16 | 193 << 8 | 76;
            float color = state.get(GradientLeavesBlock.COLOR);
            Color maxColor = new Color(175, 65, 48);
            Color minColor = new Color(251, 193, 76);
            int red = (int) MathHelper.lerp(color / 5.0f, minColor.getRed(), maxColor.getRed());
            int green = (int) MathHelper.lerp(color / 5.0f, minColor.getGreen(), maxColor.getGreen());
            int blue = (int) MathHelper.lerp(color / 5.0f, minColor.getBlue(), maxColor.getBlue());
            return red << 16 | green << 8 | blue;
        }, RUNEWOOD_LEAVES);
        blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
            if (world == null && pos == null) return 224 << 16 | 30 << 8 | 214;
            float color = state.get(GradientLeavesBlock.COLOR);
            Color maxColor = new Color(152, 6, 45);
            Color minColor = new Color(224, 30, 214);
            int red = (int) MathHelper.lerp(color / 5.0f, minColor.getRed(), maxColor.getRed());
            int green = (int) MathHelper.lerp(color / 5.0f, minColor.getGreen(), maxColor.getGreen());
            int blue = (int) MathHelper.lerp(color / 5.0f, minColor.getBlue(), maxColor.getBlue());
            return red << 16 | green << 8 | blue;
        }, SOULWOOD_LEAVES);
        blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
            if (tintIndex != 1 || world == null || pos == null) return -1;
            EtherBlockEntity blockEntity = (EtherBlockEntity) world.getBlockEntity(pos);
            if (blockEntity == null) return -1;
            return blockEntity.firstColor;
        }, WALL_ETHER_TORCH, ETHER_TORCH);
        blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
            if (tintIndex == -1 || tintIndex == 0 || world == null || pos == null) return -1;
            EtherBlockEntity blockEntity = (EtherBlockEntity) world.getBlockEntity(pos);
            if (blockEntity == null) return -1;
            return tintIndex == 1 ? blockEntity.firstColor : blockEntity.secondColor;
        }, IRIDESCENT_ETHER_TORCH, IRIDESCENT_WALL_ETHER_TORCH);
    }
}
