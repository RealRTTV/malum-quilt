package ca.rttv.malum.util.listener;

import ca.rttv.malum.Malum;
import ca.rttv.malum.util.helper.SpiritHelper;
import ca.rttv.malum.util.spirit.MalumEntitySpiritData;
import com.google.gson.*;
import net.minecraft.registry.Registries;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.ArrayList;
import java.util.Map;

import static ca.rttv.malum.registry.SpiritTypeRegistry.SPIRIT_DATA;

public class SpiritDataReloadListener extends JsonDataLoader {
    private static final Gson GSON = (new GsonBuilder()).create();

    public SpiritDataReloadListener() {
        super(GSON, "spirit_data/entity");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> objectIn, ResourceManager resourceManager, Profiler profiler) {
        SPIRIT_DATA.clear();
        for (int i = 0; i < objectIn.size(); i++) {
            Identifier location = (Identifier) objectIn.keySet().toArray()[i];
            JsonObject object = objectIn.get(location).getAsJsonObject();
            String name = object.getAsJsonPrimitive("registry_name").getAsString();
            Identifier identifier = new Identifier(name);
            if (!Registries.ENTITY_TYPE.containsId(identifier)) {
                continue;
            }
            if (!object.has("primary_type")) {
                Malum.LOGGER.info("entity with registry name: " + name + " lacks a primary type. Skipping file.");
                continue;
            }
            if (SPIRIT_DATA.containsKey(identifier)) {
                Malum.LOGGER.info("entity with registry name: " + name + " already has spirit data associated with it. Overwriting.");
            }
            String primaryType = object.getAsJsonPrimitive("primary_type").getAsString();
            JsonArray array = object.getAsJsonArray("spirits");
            SPIRIT_DATA.put(identifier, new MalumEntitySpiritData(SpiritHelper.getSpiritType(primaryType), getSpiritData(array)));
        }
    }

    public static ArrayList<MalumEntitySpiritData.SpiritDataEntry> getSpiritData(JsonArray array) {
        ArrayList<MalumEntitySpiritData.SpiritDataEntry> spiritData = new ArrayList<>();
        for (JsonElement spiritElement : array) {
            JsonObject spiritObject = spiritElement.getAsJsonObject();
            String spiritName = spiritObject.getAsJsonPrimitive("spirit").getAsString();
            int count = spiritObject.getAsJsonPrimitive("count").getAsInt();
            spiritData.add(new MalumEntitySpiritData.SpiritDataEntry(SpiritHelper.getSpiritType(spiritName), count));
        }
        return spiritData;
    }
}
