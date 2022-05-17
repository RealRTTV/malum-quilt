package ca.rttv.malum.block.entity;

import ca.rttv.malum.block.TabletBlock;
import ca.rttv.malum.item.SpiritItem;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.util.helper.SpiritHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.TABLET_BLOCK_ENTITY;

public class TabletBlockEntity extends AbstractItemDisplayBlockEntity {
    public TabletBlockEntity(BlockPos pos, BlockState state) {
        this(TABLET_BLOCK_ENTITY, pos, state);
    }

    public TabletBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public void clientTick(World world, BlockPos pos, BlockState state) {
        if (this.getHeldItem().getItem() instanceof SpiritItem item) {
            Vec3d vec = DataHelper.fromBlockPos(pos).add(itemOffset());
            double x = vec.x;
            double y = vec.y + Math.sin((world.getTime()) / 20f) * 0.1f;
            double z = vec.z;
            SpiritHelper.spawnSpiritParticles(world, x, y, z, item.type.color, item.type.endColor);
        }
    }

    @Override
    public Vec3d itemOffset() {
        Direction direction = this.getCachedState().get(TabletBlock.FACING);
        Vec3d directionVector = Vec3d.of(direction.getVector());
        return new Vec3d(0.5f + directionVector.x * 0.25f, 0.5f + directionVector.y * 0.4f, 0.5f + directionVector.z * 0.25f);
    }
}
