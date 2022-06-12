package ca.rttv.malum.mixin;

import ca.rttv.malum.block.ObeliskBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static ca.rttv.malum.registry.MalumBlockRegistry.BRILLIANT_OBELISK;

@Mixin(EnchantmentScreenHandler.class)
abstract class EnchantmentScreenHandlerMixin {
    @SuppressWarnings("UnresolvedMixinReference")
    @ModifyVariable(method = "m_mpsetdhw", at = @At(value = "FIELD", target = "Lnet/minecraft/block/EnchantingTableBlock;POSSIBLE_BOOKSHELF_LOCATIONS:Ljava/util/List;"), index = 4)
    private int brilliantObeliskEnchantmentPower(int ix, ItemStack stack, World world, BlockPos pos) {
        return ix + 4 * (int) EnchantingTableBlock.POSSIBLE_BOOKSHELF_LOCATIONS.stream().filter(blockPos -> isObelisk(world, pos, blockPos)).count();
    }

    @Unique
    private static boolean isObelisk(World world, BlockPos tablePos, BlockPos possiblePos) {
       return world.getBlockState(tablePos.add(possiblePos)).isOf(BRILLIANT_OBELISK)
           && world.isAir(tablePos.add(possiblePos.getX() / 2, possiblePos.getY(), possiblePos.getZ() / 2))
           && world.getBlockState(tablePos.add(possiblePos)).get(ObeliskBlock.HALF) == DoubleBlockHalf.LOWER;
    }
}
