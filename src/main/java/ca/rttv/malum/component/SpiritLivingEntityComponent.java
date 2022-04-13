package ca.rttv.malum.component;

import ca.rttv.malum.util.helper.SpiritHelper;
import ca.rttv.malum.util.spirit.MalumEntitySpiritData;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class SpiritLivingEntityComponent implements AutoSyncedComponent {
    private final LivingEntity obj;

    private MalumEntitySpiritData spiritData;

    private float soulHarvestProgress;
    public float exposedSoul;
    private boolean soulless = false;
    private boolean spawnerSpawned = false;
    private UUID ownerUuid;

    public SpiritLivingEntityComponent(LivingEntity livingEntity) {
        obj = livingEntity;
        spiritData = SpiritHelper.getEntitySpiritData(obj);
    }

    public float getPreviewProgress() {
        return soulless ? 10 : Math.min(10, soulHarvestProgress);
    }
    public float getHarvestProgress() {
        return Math.max(0, soulHarvestProgress-10);
    }

    public void setOwnerUuid(UUID ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    public void setSoulHarvestProgress(float soulHarvestProgress) {
        this.soulHarvestProgress = soulHarvestProgress;
    }

    public void setSoulless(boolean soulless) {
        this.soulless = soulless;
    }

    public boolean isSoulless() {
        return soulless;
    }

    public boolean isSpawnerSpawned() {
        return spawnerSpawned;
    }

    public void setSpawnerSpawned(boolean spawnerSpawned) {
        this.spawnerSpawned = spawnerSpawned;
    }

    public void setSpiritData(MalumEntitySpiritData spiritData) {
        this.spiritData = spiritData;
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
        tag.putFloat("soulHarvestProgress", soulHarvestProgress);
        tag.putFloat("exposedSoul", exposedSoul);
        tag.putBoolean("soulless", soulless);
        tag.putBoolean("spawnerSpawned", spawnerSpawned);
        if (ownerUuid != null) {
            tag.putUuid("ownerUUID", ownerUuid);
        }
        if(spiritData != null)
        spiritData.saveTo(tag);
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
        if(MalumEntitySpiritData.readNbt(tag) != null)
        spiritData = MalumEntitySpiritData.readNbt(tag);
    }
}
