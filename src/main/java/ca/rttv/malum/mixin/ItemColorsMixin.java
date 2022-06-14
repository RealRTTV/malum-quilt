package ca.rttv.malum.mixin;

import ca.rttv.malum.util.helper.NbtHelper;
import ca.rttv.malum.util.spirit.SpiritType;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static ca.rttv.malum.registry.MalumItemRegistry.*;

@Mixin(ItemColors.class)
final class ItemColorsMixin {
    @Inject(method = "create", at = @At(value = "RETURN", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void malum$create(BlockColors blockColors, CallbackInfoReturnable<ItemColors> cir, ItemColors itemColors) {
        itemColors.register((stack, tintIndex) -> {
            if (tintIndex != 0) return -1;
            return NbtHelper.getOrDefaultInt(nbt -> NbtHelper.getOrThrowInt(nbt.getCompound("display"), "FirstColor"), 15712278, stack.getNbt());
        }, ETHER, ETHER_TORCH, TAINTED_ETHER_BRAZIER, TWISTED_ETHER_BRAZIER);
        itemColors.register((stack, tintIndex) -> {
            if (tintIndex == 1) return -1;
            if (tintIndex == 0) {
                return NbtHelper.getOrDefaultInt(nbt -> NbtHelper.getOrThrowInt(nbt.getCompound("display"), "FirstColor"), 15712278, stack.getNbt());
            }
            return NbtHelper.getOrDefaultInt(nbt -> NbtHelper.getOrThrowInt(nbt.getCompound("display"), "SecondColor"), 4607909, stack.getNbt());
        }, IRIDESCENT_ETHER_TORCH, TAINTED_IRIDESCENT_ETHER_BRAZIER, TWISTED_IRIDESCENT_ETHER_BRAZIER);
        itemColors.register((stack, tintIndex) -> {
            if (tintIndex == -1) return -1;
            if (tintIndex == 0) {
                return NbtHelper.getOrDefaultInt(nbt -> NbtHelper.getOrThrowInt(nbt.getCompound("display"), "FirstColor"), 15712278, stack.getNbt());
            }
            return NbtHelper.getOrDefaultInt(nbt -> NbtHelper.getOrThrowInt(nbt.getCompound("display"), "SecondColor"), 4607909, stack.getNbt());
        }, IRIDESCENT_ETHER);
        itemColors.register((stack, tintIndex) -> {
            if (tintIndex != 0) return -1;
            return 251 << 16 | 193 << 8 | 76;
        }, RUNEWOOD_LEAVES);
        itemColors.register((stack, tintIndex) -> {
            if (tintIndex != 0) return -1;
            return 224 << 16 | 30 << 8 | 214;
        }, SOULWOOD_LEAVES);
        itemColors.register((stack, tintIndex) -> SpiritType.SACRED_SPIRIT.color.getRGB(), SACRED_SPIRIT);
        itemColors.register((stack, tintIndex) -> SpiritType.AERIAL_SPIRIT.color.getRGB(), AERIAL_SPIRIT);
        itemColors.register((stack, tintIndex) -> SpiritType.AQUEOUS_SPIRIT.color.getRGB(), AQUEOUS_SPIRIT);
        itemColors.register((stack, tintIndex) -> SpiritType.ARCANE_SPIRIT.color.getRGB(), ARCANE_SPIRIT);
        itemColors.register((stack, tintIndex) -> SpiritType.ELDRITCH_SPIRIT.color.getRGB(), ELDRITCH_SPIRIT);
        itemColors.register((stack, tintIndex) -> SpiritType.INFERNAL_SPIRIT.color.getRGB(), INFERNAL_SPIRIT);
        itemColors.register((stack, tintIndex) -> SpiritType.EARTHEN_SPIRIT.color.getRGB(), EARTHEN_SPIRIT);
        itemColors.register((stack, tintIndex) -> SpiritType.WICKED_SPIRIT.color.getRGB(), WICKED_SPIRIT);
    }
}
