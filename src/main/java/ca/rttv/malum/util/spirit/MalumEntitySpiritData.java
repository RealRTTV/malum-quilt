package ca.rttv.malum.util.spirit;

import ca.rttv.malum.registry.SpiritTypeRegistry;
import ca.rttv.malum.util.SpiritHelper;
import net.minecraft.nbt.NbtCompound;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MalumEntitySpiritData {
    public static final String SOUL_DATA = "soul_data";
    public static final MalumEntitySpiritData EMPTY = new MalumEntitySpiritData(SpiritTypeRegistry.SACRED_SPIRIT, new ArrayList<>());
    public final MalumSpiritType primaryType;
    public final int totalCount;
    public final ArrayList<SpiritDataEntry> dataEntries;

    public MalumEntitySpiritData(MalumSpiritType primaryType, ArrayList<SpiritDataEntry> dataEntries) {
        this.primaryType = primaryType;
        this.totalCount = dataEntries.stream().mapToInt(d -> d.count).sum();
        this.dataEntries = dataEntries;
    }

    public ArrayList<Component> createTooltip() {
        return dataEntries.stream().map(SpiritDataEntry::getComponent).collect(Collectors.toCollection(ArrayList::new));
    }

    public void saveTo(NbtCompound tag) {
        tag.put(SOUL_DATA, save());
    }

    public NbtCompound save() {
        NbtCompound tag = new NbtCompound();
        tag.putString("primaryType", primaryType.identifier);
        tag.putInt("dataAmount", dataEntries.size());
        for (int i = 0; i < dataEntries.size(); i++) {
            NbtCompound dataTag = dataEntries.get(i).save(new NbtCompound());
            tag.put("dataEntry" + i, dataTag);
        }
        return tag;
    }

    public static MalumEntitySpiritData load(NbtCompound tag) {
        NbtCompound nbt = tag.getCompound(SOUL_DATA);


        String type = nbt.getString("primaryType");
        int dataAmount = nbt.getInt("dataAmount");
        if (dataAmount == 0) {
            return EMPTY;
        }
        ArrayList<SpiritDataEntry> data = new ArrayList<>();
        for (int i = 0; i < dataAmount; i++) {
            data.add(SpiritDataEntry.load(nbt.getCompound("dataEntry" + i)));
        }
        return new MalumEntitySpiritData(SpiritHelper.getSpiritType(type), data);
    }

    public static class SpiritDataEntry {
        public final MalumSpiritType type;
        public final int count;

        public SpiritDataEntry(MalumSpiritType type, int count) {
            this.type = type;
            this.count = count;
        }

        public Component getComponent() {
            return type.getComponent(count);
        }

        public NbtCompound save(NbtCompound tag) {
            tag.putString("type", type.identifier);
            tag.putInt("count", count);
            return tag;
        }

        public static SpiritDataEntry load(NbtCompound tag) {
            MalumSpiritType type = SpiritHelper.getSpiritType(tag.getString("type"));
            int count = tag.getInt("count");
            return new SpiritDataEntry(type, count);
        }
    }
}
