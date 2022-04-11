package ca.rttv.malum.mixin;

import ca.rttv.malum.util.helper.NbtHelper;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static ca.rttv.malum.registry.MalumRegistry.*;

@Mixin(ItemColors.class)
public abstract class ItemColorsMixin {
    @Inject(method = "create", at = @At(value = "RETURN", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void create(BlockColors blockColors, CallbackInfoReturnable<ItemColors> cir, ItemColors itemColors) {
        itemColors.register((stack, tintIndex) -> {
            if (tintIndex != 0) return -1;
            return NbtHelper.getOrDefaultInt(nbt -> NbtHelper.getOrThrowInt(nbt.getCompound("display"), "FirstColor"), 15712278, stack.getNbt());
        }, ETHER_ITEM, ETHER_TORCH_ITEM, TAINTED_ETHER_BRAZIER_ITEM, TWISTED_ETHER_BRAZIER_ITEM);
        itemColors.register((stack, tintIndex) -> {
            if (tintIndex == 1) return -1;
            if (tintIndex == 0) {
                return NbtHelper.getOrDefaultInt(nbt -> NbtHelper.getOrThrowInt(nbt.getCompound("display"), "FirstColor"), 15712278, stack.getNbt());
            }
            return NbtHelper.getOrDefaultInt(nbt -> NbtHelper.getOrThrowInt(nbt.getCompound("display"), "SecondColor"), 4607909, stack.getNbt());
        }, IRIDESCENT_ETHER_TORCH_ITEM, TAINTED_IRIDESCENT_ETHER_BRAZIER, TWISTED_IRIDESCENT_ETHER_BRAZIER);
        itemColors.register((stack, tintIndex) -> {
            if (tintIndex == -1) return -1;
            if (tintIndex == 0) {
                return NbtHelper.getOrDefaultInt(nbt -> NbtHelper.getOrThrowInt(nbt.getCompound("display"), "FirstColor"), 15712278, stack.getNbt());
            }
            return NbtHelper.getOrDefaultInt(nbt -> NbtHelper.getOrThrowInt(nbt.getCompound("display"), "SecondColor"), 4607909, stack.getNbt());
        }, IRIDESCENT_ETHER_ITEM);
    }
}
