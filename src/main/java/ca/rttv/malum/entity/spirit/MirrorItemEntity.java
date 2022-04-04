package ca.rttv.malum.entity.spirit;

import ca.rttv.malum.entity.FloatingItemEntity;
import ca.rttv.malum.item.interfaces.IFloatingGlowItem;
import ca.rttv.malum.registry.MalumEntityRegistry;
import ca.rttv.malum.registry.SpiritTypeRegistry;
import ca.rttv.malum.util.helper.ColorHelper;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class MirrorItemEntity extends FloatingItemEntity {

    public Direction cachedDirection;
    public Direction direction;
    public BlockPos cachedBlockPos;
    public float desiredMoveTime=1f;

    public MirrorItemEntity(World world) {
        super(MalumEntityRegistry.MIRROR_ITEM, world);
        maxAge = 4000;
        direction = Direction.NORTH;
        moveTime = 1f;
    }

    public MirrorItemEntity(World world, Direction direction, ItemStack stack, BlockPos pos) {
        super(MalumEntityRegistry.MIRROR_ITEM, world);
        this.direction = direction;
        setItem(stack);
        setPos(pos.getX() + 0.5f, pos.getY() + 0.25f, pos.getZ() + 0.5f);
        maxAge = 4000;
        moveTime = 1f;
        float multiplier = 0.02f;
        setVelocity(direction.getOffsetX() * multiplier, direction.getOffsetY() * multiplier, direction.getOffsetZ() * multiplier);
    }

    @Override
    public void setItem(ItemStack pStack) {
        if (!(pStack.getItem() instanceof IFloatingGlowItem)) {
            setColor(ColorHelper.brighter(SpiritTypeRegistry.ARCANE_SPIRIT_COLOR, 2), SpiritTypeRegistry.ARCANE_SPIRIT.endColor);
        }
        super.setItem(pStack);
    }

//    @Override
//    public void spawnParticles(double x, double y, double z) {
//        SpiritHelper.spawnSpiritParticles(level, x, y, z, 1.5f, Vec3.ZERO, color, endColor);
//    }

    @Override
    public void move() {
        if (moveTime < desiredMoveTime)
        {
            moveTime+=0.1f;
        }
        if (moveTime > desiredMoveTime)
        {
            moveTime-=0.1f;
        }
        if (cachedBlockPos != getBlockPos() || world.getTime() % 10L == 0) {
            cachedBlockPos = getBlockPos();
            BlockPos ahead = cachedBlockPos.offset(direction, 1);
            BlockState state = world.getBlockState(ahead);
            if (state.isSideSolidFullSquare(world, ahead, direction)) {
                desiredMoveTime = 0f;
            } else {
                desiredMoveTime = 1f;
            }
        }
        if (cachedDirection != direction) {
            cachedDirection = direction;
        }
        float multiplier = 0.02f * moveTime;
        setVelocity(direction.getOffsetX() * multiplier, direction.getOffsetY() * multiplier, direction.getOffsetZ() * multiplier);
    }


    @Override
    protected void writeCustomDataToNbt(NbtCompound tag) {
        tag.putString("direction", direction.toString());
        tag.putFloat("desiredMoveTime", desiredMoveTime);
        super.writeCustomDataToNbt(tag);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound tag) {
        direction = Direction.byName(tag.getString("direction"));
        cachedDirection = direction;
        desiredMoveTime = tag.getFloat("desiredMoveTime");
        super.readCustomDataFromNbt(tag);
    }
}
