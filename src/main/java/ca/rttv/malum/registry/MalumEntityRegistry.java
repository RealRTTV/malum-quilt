package ca.rttv.malum.registry;

import ca.rttv.malum.entity.ScytheBoomerangEntity;
import ca.rttv.malum.entity.SpiritItemEntity;
import ca.rttv.malum.util.helper.DataHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;

public interface MalumEntityRegistry {
    Map<Identifier, EntityType<?>> ENTITY_TYPES = new LinkedHashMap<>();
    // entities
    EntityType<ScytheBoomerangEntity> SCYTHE_BOOMERANG = registerEntity("scythe_boomerang", EntityType.Builder.<ScytheBoomerangEntity>create((e, w)->new ScytheBoomerangEntity(w), SpawnGroup.MISC).setDimensions(2.5F, 0.75F).maxTrackingRange(10).build(DataHelper.prefix("scythe_boomerang").toString()));
    EntityType<SpiritItemEntity> NATURAL_SPIRIT = registerEntity("natural_spirit", EntityType.Builder.<SpiritItemEntity>create((e, w)->new SpiritItemEntity(w), SpawnGroup.MISC).setDimensions(0.5F, 0.75F).maxTrackingRange(10).build(DataHelper.prefix("natural_spirit").toString()));

    static <T extends Entity> EntityType<T> registerEntity(String id, EntityType<T> type) {
        ENTITY_TYPES.put(new Identifier(MODID, id), type);
        return type;
    }

//    static <T extends LivingEntity> EntityType<T> registerEntity(String id, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
//        FabricDefaultAttributeRegistry.register(type, attributes);
//        ENTITY_TYPES.put(type, new Identifier(MODID, id));
//        return type;
//    }

    static void init() {
        ENTITY_TYPES.forEach((id, entityType) -> Registry.register(Registry.ENTITY_TYPE, id, entityType));
    }
}
