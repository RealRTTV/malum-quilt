package ca.rttv.malum.registry;

import ca.rttv.malum.entity.spirit.MirrorItemEntity;
import ca.rttv.malum.util.DataHelper;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumRegistry.*;

public class MalumEntityRegistry {
    private static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();
    // entities
    public static final EntityType<MirrorItemEntity> MIRROR_ITEM = registerEntity("mirror_item", EntityType.Builder.<MirrorItemEntity>create((e, w)->new MirrorItemEntity(w), SpawnGroup.MISC).setDimensions(0.5F, 0.5F).maxTrackingRange(10).build(DataHelper.prefix("mirror_item").toString()));
    private static <T extends Entity> EntityType<T> registerEntity(String name, EntityType<T> type) {
        ENTITY_TYPES.put(type, new Identifier(MODID, name));
        return type;
    }
    private static <T extends LivingEntity> EntityType<T> registerEntity(String name, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
        FabricDefaultAttributeRegistry.register(type, attributes);
        ENTITY_TYPES.put(type, new Identifier(MODID, name));
        return type;
    }
    static void init() {
        ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));
    }
}
