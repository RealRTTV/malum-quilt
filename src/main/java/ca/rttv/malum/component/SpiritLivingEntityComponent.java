package ca.rttv.malum.component;

import ca.rttv.malum.util.spirit.MalumEntitySpiritData;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class SpiritLivingEntityComponent implements AutoSyncedComponent {

    public MalumEntitySpiritData spiritData;

    public float soulHarvestProgress;
    public float exposedSoul;
    public boolean soulless;
    public boolean spawnerSpawned;
    public UUID ownerUuid;
    @Override
    public void readFromNbt(NbtCompound tag) {

        tag.putFloat("soulHarvestProgress", soulHarvestProgress);
        tag.putFloat("exposedSoul", exposedSoul);
        tag.putBoolean("soulless", soulless);
        tag.putBoolean("spawnerSpawned", spawnerSpawned);
        if (ownerUuid != null) {
            tag.putUuid("ownerUUID", ownerUuid);
        }
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
    }
}
