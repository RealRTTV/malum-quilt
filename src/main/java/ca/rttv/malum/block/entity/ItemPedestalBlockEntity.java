package ca.rttv.malum.block.entity;

import ca.rttv.malum.item.MalumSpiritItem;
import ca.rttv.malum.registry.MalumBlockEntityRegistry;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.util.helper.SpiritHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemPedestalBlockEntity extends AbstractItemDisplayBlockEntity {
    public ItemPedestalBlockEntity(BlockPos pos, BlockState state) {
        this(MalumBlockEntityRegistry.ITEM_PEDESTAL_BLOCK_ENTITY, pos, state);
    }

    public ItemPedestalBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public void clientTick(World world, BlockPos pos, BlockState state) {
        if (getHeldItem().getItem() instanceof MalumSpiritItem item) {
            Vec3d vec = DataHelper.fromBlockPos(pos).add(itemOffset());
            double x = vec.x;
            double y = vec.y + Math.sin(world.getTime() / 20f) * 0.1f;
            double z = vec.z;
            SpiritHelper.spawnSpiritParticles(world, x, y, z, item.type.color, item.type.endColor);
        }
    }

    public Vec3d itemOffset() {
        return new Vec3d(0.5f, 1.1f, 0.5f);
    }
}
