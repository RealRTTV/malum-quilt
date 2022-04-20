package ca.rttv.malum.component;

import ca.rttv.malum.Malum;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class MalumComponents implements EntityComponentInitializer {
    public static final ComponentKey<MalumPlayerComponent> PLAYER_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Malum.MODID, "player"), MalumPlayerComponent.class);
    public static final ComponentKey<SpiritLivingEntityComponent> SPIRIT_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Malum.MODID, "spirit"), SpiritLivingEntityComponent.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(LivingEntity.class, SPIRIT_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(SpiritLivingEntityComponent::new);
        registry.beginRegistration(LivingEntity.class, PLAYER_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(MalumPlayerComponent::new);
    }
}
