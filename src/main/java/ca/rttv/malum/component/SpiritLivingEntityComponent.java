package ca.rttv.malum.component;

import ca.rttv.malum.util.spirit.MalumEntitySpiritData;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class SpiritLivingEntityComponent implements AutoSyncedComponent {
    private final LivingEntity obj;

    private MalumEntitySpiritData spiritData;

    private float soulHarvestProgress;
    private float exposedSoul;
    private boolean soulless;
    private boolean spawnerSpawned;
    private UUID ownerUuid;

    public SpiritLivingEntityComponent(LivingEntity livingEntity) {
        obj = livingEntity;
    }

    public float getPreviewProgress() {
        return soulless ? 10 : Math.min(10, soulHarvestProgress);
    }
    public float getHarvestProgress() {
        return Math.max(0, soulHarvestProgress-10);
    }

    public void setExposedSoul(float exposedSoul) {
        this.exposedSoul = exposedSoul;
        MalumComponents.SPIRIT_COMPONENT.sync(obj);
    }

    public void setOwnerUuid(UUID ownerUuid) {
        this.ownerUuid = ownerUuid;
        MalumComponents.SPIRIT_COMPONENT.sync(obj);
    }

    public void setSoulHarvestProgress(float soulHarvestProgress) {
        this.soulHarvestProgress = soulHarvestProgress;
        MalumComponents.SPIRIT_COMPONENT.sync(obj);
    }

    public void setSoulless(boolean soulless) {
        this.soulless = soulless;
        MalumComponents.SPIRIT_COMPONENT.sync(obj);
    }

    public void setSpawnerSpawned(boolean spawnerSpawned) {
        this.spawnerSpawned = spawnerSpawned;
        MalumComponents.SPIRIT_COMPONENT.sync(obj);
    }

    public void setSpiritData(MalumEntitySpiritData spiritData) {
        this.spiritData = spiritData;
        MalumComponents.SPIRIT_COMPONENT.sync(obj);
    }

    public float getExposedSoul() {
        return exposedSoul;
    }

    public float getSoulHarvestProgress() {
        return soulHarvestProgress;
    }

    public MalumEntitySpiritData getSpiritData() {
        return spiritData;
    }

    public UUID getOwnerUuid() {
        return ownerUuid;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
//        tag.putFloat("soulHarvestProgress", soulHarvestProgress);
//        tag.putFloat("exposedSoul", exposedSoul);
//        tag.putBoolean("soulless", soulless);
//        tag.putBoolean("spawnerSpawned", spawnerSpawned);
//        if (ownerUuid != null) {
//            tag.putUuid("ownerUUID", ownerUuid);
//        }
//        spiritData.saveTo(tag);
    }
    @Override
    public void writeToNbt(NbtCompound tag) {
        soulHarvestProgress = tag.getFloat("soulHarvestProgress");
        exposedSoul = tag.getFloat("exposedSoul");
        soulless = tag.getBoolean("soulless");
        spawnerSpawned = tag.getBoolean("spawnerSpawned");
        if (tag.contains("ownerUUID")) {
            ownerUuid = tag.getUuid("ownerUUID");
        }
        spiritData = MalumEntitySpiritData.readNbt(tag);
    }
}
