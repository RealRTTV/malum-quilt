package ca.rttv.malum.entity;

import ca.rttv.malum.item.ScytheItem;
import ca.rttv.malum.registry.MalumEnchantments;
import ca.rttv.malum.registry.MalumEntityRegistry;
import ca.rttv.malum.registry.MalumSoundRegistry;
import ca.rttv.malum.util.helper.ItemHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.UUID;

public class ScytheBoomerangEntity extends ThrownItemEntity {
    public static final TrackedData<ItemStack> SCYTHE = DataTracker.registerData(ScytheBoomerangEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    public ItemStack scythe;
    public UUID ownerUUID;
    @Nullable
    public PlayerEntity owner;
    public int slot;
    public float damage;
    public int age;
    public int returnAge = 8;
    public boolean returning;

    @Override
    public boolean hasNetherPortalCooldown() {
        return true;
    }

    public ScytheBoomerangEntity(World world) {
        super(MalumEntityRegistry.SCYTHE_BOOMERANG, world);
        noClip = false;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        returning = true;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        getActualOwner();
        if (!world.isClient && (owner == null || owner.isDead())) {
            ItemEntity entityitem = new ItemEntity(world, getX(), getY() + 0.5, getZ(), scythe);
            entityitem.setPickupDelay(40);
            entityitem.setVelocity(entityitem.getVelocity().multiply(0, 1, 0));
            world.spawnEntity(entityitem);
            remove(RemovalReason.DISCARDED);
            return;
        }

        DamageSource source = DamageSource.mobProjectile(this, owner);
        Entity entity = entityHitResult.getEntity();
        if (world.isClient) {
            return;
        }
        if (entity.equals(owner)) {
            return;
        }
        boolean success = !entity.isInvulnerable();
        if (success) {
            if (!world.isClient) {
                if (entity instanceof LivingEntity livingentity) {
                    scythe.damage(1, owner, (e) -> remove(RemovalReason.KILLED));
                    ItemHelper.applyEnchantments(owner, livingentity, scythe);
                    int i = EnchantmentHelper.getLevel(Enchantments.FIRE_ASPECT, scythe);
                    if (i > 0) {
                        livingentity.setOnFireFor(i * 4);
                    }
                }
                entity.damage(source, damage);
            }
            returnAge += 4;
            entity.world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), MalumSoundRegistry.SCYTHE_CUT, entity.getSoundCategory(), 1.0F, 0.9f + entity.world.random.nextFloat() * 0.2f);
        }
        super.onEntityHit(entityHitResult);
    }

    @Override
    public void tick() {
        super.tick();
        age++;
        getActualOwner();
        if (!world.isClient && (owner == null || owner.isDead())) {
            ItemEntity entityitem = new ItemEntity(world, getX(), getY() + 0.5, getZ(), scythe);
            entityitem.setPickupDelay(40);
            entityitem.setVelocity(entityitem.getVelocity().multiply(0, 1, 0));
            world.spawnEntity(entityitem);
            remove(RemovalReason.DISCARDED);
            return;
        }
        if (world.isClient && this.scythe != null) {
            if (!isInsideWaterOrBubbleColumn()) {
                if (EnchantmentHelper.getLevel(Enchantments.FIRE_ASPECT, getItem()) > 0) {
                    Vec3d vector = new Vec3d(getParticleX(0.7), getRandomBodyY(), getParticleZ(0.7));
                    if (scythe.getItem() instanceof ScytheItem) {
                        Random random = new Random();
                        float f1 = random.nextFloat();
//                        float f2 = random.nextFloat();
                        vector = new Vec3d(Math.cos(this.age) * 0.8f + this.getX(), getBodyY(0.1), Math.sin(this.age) * 0.8f + this.getZ());
                        world.addParticle(ParticleTypes.FLAME, Math.cos(this.age + f1 * 2 - 1) * 0.8f + this.getX(), vector.y, Math.sin(this.age + f1 * 2 - 1) * 0.8f + this.getZ(), 0, 0, 0);
                        world.addParticle(ParticleTypes.FLAME, Math.cos(this.age + f1 * 2 - 1) * 0.8f + this.getX(), vector.y, Math.sin(this.age + f1 * 2 - 1) * 0.8f + this.getZ(), 0, 0, 0);
                    }
                    world.addParticle(ParticleTypes.FLAME, vector.x, vector.y, vector.z, 0, 0, 0);
                }
            } else {
                Vec3d vector = new Vec3d(getParticleX(0.7), getRandomBodyY(), getParticleZ(0.7));
                if (scythe.getItem() instanceof ScytheItem) {
                    Random random = new Random();
                    float f1 = random.nextFloat();
//                    float f2 = random.nextFloat();
                    vector = new Vec3d(Math.cos(this.age) * 0.8f + this.getX(), getBodyY(0.1), Math.sin(this.age) * 0.8f + this.getZ());
                    world.addParticle(ParticleTypes.BUBBLE, Math.cos(this.age + f1 * 2 - 1) * 0.8f + this.getX(), vector.y, Math.sin(this.age + f1 * 2 - 1) * 0.8f + this.getZ(), 0, 0, 0);
                    world.addParticle(ParticleTypes.BUBBLE, Math.cos(this.age + f1 * 2 - 1) * 0.8f + this.getX(), vector.y, Math.sin(this.age + f1 * 2 - 1) * 0.8f + this.getZ(), 0, 0, 0);
                }
                world.addParticle(ParticleTypes.BUBBLE, vector.x, vector.y, vector.z, 0, 0, 0);
            }
        }

        if (!world.isClient) {
            if (age % 3 == 0) {
                world.playSound(null, getBlockPos(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1, 1.25f);
            }
            if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
                Vec3d vector3d = getVelocity();
//                float f = Mth.sqrt(horizontalMag(vector3d));
                setYaw((float) (MathHelper.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI)));
//                setXRot((float) (Mth.atan2(vector3d.y, f) * (double) (180F / (float) Math.PI)));
                prevYaw = getYaw();
                prevPitch = getPitch();
            }
            if (age > returnAge) {
                returning = true;
            }
            if (returning) {
                noClip = true;
                Vec3d ownerPos = owner.getPos().add(0, 1, 0);
                Vec3d motion = ownerPos.subtract(getPos());
                setVelocity(motion.normalize().multiply(0.75f));
            }
            float distance = distanceTo(owner);
            if (age > 8) {
                if (distance < 3f) {
                    if (isAlive()) {
                        owner.giveItemStack(scythe);
                        if (!owner.getAbilities().creativeMode) {
                            int cooldown = 100 - 25 * (EnchantmentHelper.getLevel(MalumEnchantments.REBOUND, scythe) - 1);
                            owner.getItemCooldownManager().set(scythe.getItem(), cooldown);
                        }
                        remove(RemovalReason.DISCARDED);
                    }
                }
            }
        }
    }

    public PlayerEntity getActualOwner() {
        if (owner == null) {
            if (world instanceof ServerWorld serverWorld) {
                owner = serverWorld.getEntity(ownerUUID) instanceof PlayerEntity playerEntity ? playerEntity : null;
            }
        }
        return owner;
    }

    public void setData(float damage, UUID ownerUUID, int slot, ItemStack scythe) {
        this.damage = damage;
        this.ownerUUID = ownerUUID;
        this.slot = slot;
        this.scythe = scythe;
    }

    public void shootFromRotation(Entity shooter, float rotationPitch, float rotationYaw, float pitchOffset, float velocity, float innacuracy) {
        float f = -MathHelper.sin(rotationYaw * (float) Math.PI / 180F) * MathHelper.cos(rotationPitch * ((float) Math.PI / 180F));
        float f1 = -MathHelper.sin((rotationPitch + pitchOffset) * ((float) Math.PI / 180F));
        float f2 = MathHelper.cos(rotationYaw * (float) Math.PI / 180F) * MathHelper.cos(rotationPitch * ((float) Math.PI / 180F));
        this.setVelocity(f, f1, f2, velocity, innacuracy);
        Vec3d vec3 = shooter.getVelocity();
        this.setVelocity(this.getVelocity().add(vec3.x, 0, vec3.z));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("scythe", scythe.getOrCreateNbt());
        if (ownerUUID != null) {
            nbt.putUuid("ownerUUID", ownerUUID);
        }
        nbt.putInt("slot", slot);
        nbt.putFloat("damage", damage);
        nbt.putInt("age", age);
        nbt.putBoolean("returning", returning);
        nbt.putInt("returnAge", returnAge);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("scythe")) {
            scythe = ItemStack.fromNbt(nbt.getCompound("scythe"));
        }
        dataTracker.set(SCYTHE, scythe);

        if (nbt.contains("ownerUUID")) {
            ownerUUID = nbt.getUuid("ownerUUID");
            owner = getActualOwner();
        }
        slot = nbt.getInt("slot");
        damage = nbt.getFloat("damage");
        age = nbt.getInt("age");
        returning = nbt.getBoolean("returning");
        returnAge = nbt.getInt("returnAge");
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        if (scythe == null) {
            scythe = dataTracker.get(SCYTHE);
        }
        return scythe.getItem();
    }

    @Override
    public ItemStack getItem() {
        if (scythe == null) {
            scythe = dataTracker.get(SCYTHE);
        }
        return scythe;
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    public boolean isImmuneToExplosion() {
        return true;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public float getTargetingMargin() {
        return 4;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SCYTHE, ItemStack.EMPTY);
    }
}
