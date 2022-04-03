package ca.rttv.malum.entity;

import ca.rttv.malum.registry.SpiritTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.awt.*;

public class FloatingEntity extends ProjectileEntity {
    protected static final TrackedData<Integer> DATA_COLOR = DataTracker.registerData(FloatingEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> DATA_END_COLOR = DataTracker.registerData(FloatingEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public Color color = SpiritTypeRegistry.SACRED_SPIRIT_COLOR;
    public Color endColor = SpiritTypeRegistry.SACRED_SPIRIT.endColor;
    public int maxAge;
    public int age;
    public float moveTime;
    public int speed = 3;
    public float windUp;
    public final float hoverStart;

    public FloatingEntity(EntityType<? extends FloatingEntity> type, World world) {
        super(type, world);
        noClip = false;
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
    }
    @Override
    protected void initDataTracker() {
        this.getDataTracker().startTracking(DATA_COLOR, SpiritTypeRegistry.SACRED_SPIRIT_COLOR.getRGB());
        this.getDataTracker().startTracking(DATA_END_COLOR, SpiritTypeRegistry.SACRED_SPIRIT.endColor.getRGB());
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putInt("age", age);
        tag.putFloat("moveTime", moveTime);
        tag.putInt("range", speed);
        tag.putFloat("windUp", windUp);
        tag.putInt("red", color.getRed());
        tag.putInt("green", color.getGreen());
        tag.putInt("blue", color.getBlue());
        tag.putInt("endRed", endColor.getRed());
        tag.putInt("endGreen", endColor.getGreen());
        tag.putInt("endBlue", endColor.getBlue());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        age = tag.getInt("age");
        moveTime = tag.getFloat("moveTime");
        int range = tag.getInt("range");
        if (range > 0) {
            this.speed = range;
        }
        windUp = tag.getFloat("windUp");
        color = new Color(tag.getInt("red"), tag.getInt("green"), tag.getInt("blue"));
        endColor = new Color(tag.getInt("endRed"), tag.getInt("endGreen"), tag.getInt("endBlue"));
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (DATA_COLOR.equals(data)) {
            color = new Color(dataTracker.get(DATA_COLOR));
        }
        if (DATA_END_COLOR.equals(data)) {
            endColor = new Color(dataTracker.get(DATA_END_COLOR));
        }
        super.onTrackedDataSet(data);
    }
    @Override
    public void tick() {
        super.tick();
        baseTick();
        age++;
        if (windUp < 1f) {
            windUp += 0.02f;
        }
        if (age > maxAge) {
            remove(RemovalReason.KILLED);
        }
        if (world.isClient) {
            double x = getX(), y = getY() + getYOffset(0) + 0.25f, z = getZ();
            spawnParticles(x, y, z);
        } else {
            move();
        }
    }
    public void baseTick()
    {
        HitResult hitresult = ProjectileUtil.getCollision(this, this::canHit);
        boolean flag = false;
        if (hitresult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult) hitresult).getBlockPos();
            BlockState blockstate = this.world.getBlockState(blockpos);
            if (blockstate.isOf(Blocks.NETHER_PORTAL)) {
                this.setInNetherPortal(blockpos);
                flag = true;
            } else if (blockstate.isOf(Blocks.END_GATEWAY)) {
                BlockEntity blockentity = this.world.getBlockEntity(blockpos);
                if (blockentity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
                    EndGatewayBlockEntity.tryTeleportingEntity(this.world, blockpos, blockstate, this, (EndGatewayBlockEntity) blockentity);
                }

                flag = true;
            }
        }

        if (hitresult.getType() != HitResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }

        this.checkInsideBlocks();
        Vec3d vec3 = this.getVelocity();
        double d2 = this.getX() + vec3.x;
        double d0 = this.getY() + vec3.y;
        double d1 = this.getZ() + vec3.z;
        this.updateRotation();

        this.setVelocity(vec3.scale(1f)); //this is apparently important, don't remove it
        this.setPos(d2, d0, d1);
    }
    public void spawnParticles(double x, double y, double z) {

    }

    public void move() {
    }

    public float getYOffset(float partialTicks) {
        return MathHelper.sin(((float) age + partialTicks) / 20.0F + hoverStart) * 0.1F + 0.1F;
    }

    public float getRotation(float partialTicks) {
        return ((float) age + partialTicks) / 20.0F + this.hoverStart;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }
}
