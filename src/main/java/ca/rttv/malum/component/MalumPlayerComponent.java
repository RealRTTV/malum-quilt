package ca.rttv.malum.component;

import ca.rttv.malum.registry.SpiritAffinityRegistry;
import ca.rttv.malum.util.spirit.MalumSpiritAffinity;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class MalumPlayerComponent implements AutoSyncedComponent {
    private final LivingEntity obj;
    public UUID targetedSoulUUID;
    public int targetedSoulId;
    public int soulFetchCooldown;

    public boolean firstTimeJoin;

    public MalumSpiritAffinity affinity;

    public float soulWard;
    public float soulWardProgress;

    public float heartOfStone;
    public float heartOfStoneProgress;

    public MalumPlayerComponent(LivingEntity player) {
        obj = player;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        affinity = SpiritAffinityRegistry.AFFINITIES.get(tag.getString("affinity"));

        if (tag.contains("targetedSoulUUID")) {
            targetedSoulUUID = tag.getUuid("targetedSoulUUID");
        }
        targetedSoulId = tag.getInt("targetedSoulId");
        soulFetchCooldown = tag.getInt("soulFetchCooldown");

        firstTimeJoin = tag.getBoolean("firstTimeJoin");

        soulWard = tag.getFloat("soulWard");
        soulWardProgress = tag.getFloat("soulWardProgress");

        heartOfStone = tag.getFloat("heartOfStone");
        heartOfStoneProgress = tag.getInt("heartOfStoneProgress");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        if (targetedSoulUUID != null) {
            tag.putUuid("targetedSoulUUID", targetedSoulUUID);
        }
        tag.putInt("targetedSoulId", targetedSoulId);
        tag.putInt("soulFetchCooldown", soulFetchCooldown);

        tag.putBoolean("firstTimeJoin", firstTimeJoin);

        if (affinity != null) {
            tag.putString("affinity", affinity.identifier);
        }
        tag.putFloat("soulWard", soulWard);
        tag.putFloat("soulWardProgress", soulWardProgress);

        tag.putFloat("heartOfStone", heartOfStone);
        tag.putFloat("heartOfStoneProgress", heartOfStoneProgress);
    }
}
