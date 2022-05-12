package ca.rttv.malum.util.spirit;

import ca.rttv.malum.util.helper.SpiritHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MalumEntitySpiritData {
    public static final String SOUL_DATA = "soul_data";
    public static final MalumEntitySpiritData EMPTY = new MalumEntitySpiritData(SpiritType.SACRED_SPIRIT, new ArrayList<>());
    public final SpiritType primaryType;
    public final int totalCount;
    public final ArrayList<SpiritDataEntry> dataEntries;

    public MalumEntitySpiritData(SpiritType primaryType, ArrayList<SpiritDataEntry> dataEntries) {
        this.primaryType = primaryType;
        this.totalCount = dataEntries.stream().mapToInt(d -> d.count).sum();
        this.dataEntries = dataEntries;
    }

    public ArrayList<Text> createTooltip() {
        return dataEntries.stream().map(SpiritDataEntry::getComponent).collect(Collectors.toCollection(ArrayList::new));
    }

    public void saveTo(NbtCompound tag) {
        tag.put(SOUL_DATA, writeNbt());
    }

    public NbtCompound writeNbt() {
        NbtCompound tag = new NbtCompound();
        tag.putString("primaryType", primaryType.id);
        tag.putInt("dataAmount", dataEntries.size());
        for (int i = 0; i < dataEntries.size(); i++) {
            NbtCompound dataTag = dataEntries.get(i).save(new NbtCompound());
            tag.put("dataEntry" + i, dataTag);
        }
        return tag;
    }

    public static MalumEntitySpiritData readNbt(NbtCompound nbt) {
        NbtCompound soulData = nbt.getCompound(SOUL_DATA);


        String type = soulData.getString("primaryType");
        int dataAmount = soulData.getInt("dataAmount");
        if (dataAmount == 0) {
            return EMPTY;
        }
        ArrayList<SpiritDataEntry> data = new ArrayList<>();
        for (int i = 0; i < dataAmount; i++) {
            data.add(SpiritDataEntry.load(soulData.getCompound("dataEntry" + i)));
        }
        return new MalumEntitySpiritData(SpiritHelper.getSpiritType(type), data);
    }

    public record SpiritDataEntry(SpiritType type, int count) {

        public Text getComponent() {
            return type.getComponent(count);
        }

        public NbtCompound save(NbtCompound tag) {
            tag.putString("type", type.id);
            tag.putInt("count", count);
            return tag;
        }

        public static SpiritDataEntry load(NbtCompound tag) {
            SpiritType type = SpiritHelper.getSpiritType(tag.getString("type"));
            int count = tag.getInt("count");
            return new SpiritDataEntry(type, count);
        }
    }
}
