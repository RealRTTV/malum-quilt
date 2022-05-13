package ca.rttv.malum.mixin;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

import static ca.rttv.malum.registry.MalumItemRegistry.*;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Unique
    private static Map<Item, Integer> map;

    @ModifyVariable(method = "createFuelTimeMap", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/AbstractFurnaceBlockEntity;addFuel(Ljava/util/Map;Lnet/minecraft/item/ItemConvertible;I)V", ordinal = 0), index = 0)
    private static Map<Item, Integer> captureMap(Map<Item, Integer> value) {
        map = value;
        return value;
    }

    @Inject(method = "createFuelTimeMap", at = @At(value = "TAIL", shift = At.Shift.BEFORE))
    private static void createFuelTimeMap(CallbackInfoReturnable<Map<Item, Integer>> cir) {
        addFuel(map, ARCANE_CHARCOAL, 3200);
        addFuel(map, BLOCK_OF_ARCANE_CHARCOAL, 28800);
        addFuel(map, ARCANE_CHARCOAL_FRAGMENT, 400);
        addFuel(map, BLAZING_QUARTZ, 1600);
        addFuel(map, BLAZING_QUARTZ_FRAGMENT, 200);
        addFuel(map, BLOCK_OF_BLAZING_QUARTZ, 14400);
        addFuel(map, CHARCOAL_FRAGMENT, 200);
    }

    @Shadow
    private static void addFuel(Map<Item, Integer> fuelTimes, ItemConvertible item, int fuelTime) {}
}
