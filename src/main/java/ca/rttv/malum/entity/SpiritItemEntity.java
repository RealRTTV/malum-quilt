package ca.rttv.malum.entity;

import ca.rttv.malum.item.SpiritItem;
import ca.rttv.malum.registry.MalumAttributeRegistry;
import ca.rttv.malum.registry.MalumEntityRegistry;
import ca.rttv.malum.util.handler.SpiritHarvestHandler;
import ca.rttv.malum.util.helper.ItemHelper;
import ca.rttv.malum.util.helper.SpiritHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.UUID;

public class SpiritItemEntity extends FloatingItemEntity {
    public UUID ownerUuid;
    public LivingEntity owner;

    public SpiritItemEntity(World world) {
        super(MalumEntityRegistry.NATURAL_SPIRIT, world);
        maxAge = 4000;
    }

    public SpiritItemEntity(World world, UUID ownerUuid, ItemStack stack, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        super(MalumEntityRegistry.NATURAL_SPIRIT, world);
        setOwner(ownerUuid);
        setItem(stack);
        setPos(posX, posY, posZ);
        setVelocity(velX, velY, velZ);
        maxAge = 800;
    }

    public float getRange() {
        return world.isSpaceEmpty(this) ? range : range * 5f;
    }

    public void setOwner(UUID ownerUuid) {
        this.ownerUuid = ownerUuid;
        updateOwner();
    }
    public void updateOwner()
    {
        if (!world.isClient) {
            owner = (LivingEntity) ((ServerWorld) world).getEntity(ownerUuid);
            if (owner != null)
            {
                range = (int) owner.getAttributeValue(MalumAttributeRegistry.SPIRIT_REACH);
            }
        }
    }

    @Override
    public void spawnParticles(double x, double y, double z) {
        Vec3d motion = getVelocity();
        Vec3d norm = motion.normalize().multiply(0.05f);
        float extraAlpha = (float) motion.length();
        float cycles = 4;
        for (int i = 0; i < cycles; i++) {
            double lerpX = MathHelper.lerp(i / cycles, x - motion.x, x);
            double lerpY = MathHelper.lerp(i / cycles, y - motion.y, y);
            double lerpZ = MathHelper.lerp(i / cycles, z - motion.z, z);
            SpiritHelper.spawnSpiritParticles(world, lerpX, lerpY, lerpZ, 0.20f+extraAlpha, norm, color, endColor);
        }
    }

    @Override
    public void move() {
        float friction = 0.94f;
        setVelocity(getVelocity().multiply(friction, friction, friction));
        if (owner == null || !owner.isAlive()) {
            if (world.getTime() % 40L == 0) {
                PlayerEntity playerEntity = world.getClosestPlayer(this, 10);
                if (playerEntity != null) {
                    setOwner(playerEntity.getUuid());
                }
            }
            return;
        }
        Vec3d desiredLocation = owner.getPos().add(0, owner.getHeight() / 3, 0);
        float distance = (float) squaredDistanceTo(desiredLocation);
        float velocity = windUp < 0.2f ? 0 : Math.min(windUp-0.2f, 0.35f)*5f;
        moveTime++;
        Vec3d desiredVel = desiredLocation.subtract(getPos()).normalize().multiply(velocity, velocity, velocity);
        float easing = 0.01f;
        float xMotion = (float) MathHelper.lerp(easing, getVelocity().x, desiredVel.x);
        float yMotion = (float) MathHelper.lerp(easing, getVelocity().y, desiredVel.y);
        float zMotion = (float) MathHelper.lerp(easing, getVelocity().z, desiredVel.z);
        Vec3d resultingMotion = new Vec3d(xMotion, yMotion, zMotion);
        setVelocity(resultingMotion);

        if (distance < 0.4f) {
            if (isAlive()) {
                ItemStack stack = getItem();
                if (stack.getItem() instanceof SpiritItem) {
                    SpiritHarvestHandler.pickupSpirit(stack, owner);
                }
                else {
                    ItemHelper.giveItemToEntity(stack, owner);
                }
                remove(RemovalReason.DISCARDED);
            }
        }
    }


    @Override
    protected void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        if (ownerUuid != null) {
            tag.putUuid("ownerUuid", ownerUuid);
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        if (tag.contains("ownerUuid")) {
            setOwner(tag.getUuid("ownerUuid"));
        }
    }
}
