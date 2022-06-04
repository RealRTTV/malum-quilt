package ca.rttv.malum.mixin;

import ca.rttv.malum.block.ObeliskBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static ca.rttv.malum.registry.MalumBlockRegistry.BRILLIANT_OBELISK;

@Mixin(EnchantmentScreenHandler.class)
abstract class EnchantmentScreenHandlerMixin {
    /**
     * messy solution because I modified the EnchantmentTableBlock method, so it actually adds 1 and then adds 4 to add a total of 5 which is 1/3rd of the bookshelf maximum
     * I modify the vanilla method (EnchantingTableBlock) too, because particles
     * in dev env make method = "m_mpsetdhw"
     * when pushing make method = "method_17411"
    */
    @ModifyVariable(method = "m_mpsetdhw", at = @At(value = "FIELD", target = "Lnet/minecraft/block/EnchantingTableBlock;field_36535:Ljava/util/List;"), index = 4)
    private int brilliantObeliskEnchantmentPower(int ix, ItemStack stack, World world, BlockPos pos) {
        for(BlockPos blockPos : EnchantingTableBlock.field_36535) {
            if (isObelisk(world, pos, blockPos)) {
                ix += 4;
            }
        }
        return ix;
    }

    private static boolean isObelisk(World world, BlockPos pos, BlockPos pos2) {
       return world.getBlockState(pos.add(pos2)).isOf(BRILLIANT_OBELISK)
           && world.isAir(pos.add(pos2.getX() / 2, pos2.getY(), pos2.getZ() / 2))
           && world.getBlockState(pos.add(pos2)).get(ObeliskBlock.HALF) == DoubleBlockHalf.LOWER;
    }
}
