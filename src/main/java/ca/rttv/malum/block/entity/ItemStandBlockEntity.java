package ca.rttv.malum.block.entity;

import ca.rttv.malum.item.SpiritItem;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.util.helper.SpiritHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.ITEM_STAND_BLOCK_ENTITY;

public class ItemStandBlockEntity extends AbstractItemDisplayBlockEntity {
    public ItemStandBlockEntity(BlockPos pos, BlockState state) {
        this(ITEM_STAND_BLOCK_ENTITY, pos, state);
    }

    public ItemStandBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public void clientTick(World world, BlockPos pos, BlockState state) {
        if (getHeldItem().getItem() instanceof SpiritItem item) {
            Vec3d vec = DataHelper.fromBlockPos(pos).add(itemOffset());
            double x = vec.x;
            double y = vec.y + Math.sin(world.getTime() % 14200 / 20f) * 0.1f;
            double z = vec.z;
            SpiritHelper.spawnSpiritParticles(world, x, y, z, item.type.color, item.type.endColor);
        }
    }

    public Vec3d itemOffset() {
        Direction direction = this.getCachedState().get(Properties.FACING);
        Vec3d directionVector = new Vec3d(direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ());
        return new Vec3d(0.5f - directionVector.getX() * 0.25f, 0.5f - directionVector.getY() * 0.1f, 0.5f - directionVector.getZ() * 0.25f);
    }

}
