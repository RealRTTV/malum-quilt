package ca.rttv.malum.registry;

import ca.rttv.malum.client.particle.emitter.*;
import ca.rttv.malum.util.particle.screen.emitter.ItemParticleEmitter;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumItemRegistry.*;

@SuppressWarnings("unused")
public interface MalumParticleEmitterRegistry {
    RegistryKey<Registry<Pair<ItemParticleEmitter, Item[]>>> PARTICLE_EMITTER_KEY = RegistryKey.ofRegistry(new Identifier(MODID, "particle_emitter"));
    Registry<Pair<ItemParticleEmitter, Item[]>> PARTICLE_EMITTER = Registry.registerSimple(PARTICLE_EMITTER_KEY, registry -> MalumParticleEmitterRegistry.ETHER_WALL_STANDING_PARTICLE_EMITTER);
    Map<Identifier, Pair<ItemParticleEmitter, Item[]>> PARTICLE_EMITTERS = new LinkedHashMap<>();

    Pair<ItemParticleEmitter, Item[]> ETHER_WALL_STANDING_PARTICLE_EMITTER            = register("ether_wall_standing",            new EtherWallStandingParticleEmitter(), ETHER_TORCH);
    Pair<ItemParticleEmitter, Item[]> IRIDESCENT_ETHER_WALL_STANDING_PARTICLE_EMITTER = register("iridescent_ether_wall_standing", new IridescentEtherWallStandingParticleEmitter(), IRIDESCENT_ETHER_TORCH);
    Pair<ItemParticleEmitter, Item[]> ETHER_PARTICLE_EMITTER                          = register("ether",                          new EtherParticleEmitter(), ETHER);
    Pair<ItemParticleEmitter, Item[]> IRIDESCENT_ETHER_PARTICLE_EMITTER               = register("iridescent_ether",               new IridescentEtherParticleEmitter(), IRIDESCENT_ETHER);
    Pair<ItemParticleEmitter, Item[]> SACRED_SPIRIT_PARTICLE_EMITTER                  = register("spirit",                         new SpiritParticleEmitter(), SACRED_SPIRIT, WICKED_SPIRIT, ARCANE_SPIRIT, ELDRITCH_SPIRIT, EARTHEN_SPIRIT, INFERNAL_SPIRIT, AERIAL_SPIRIT, AQUEOUS_SPIRIT);
    Pair<ItemParticleEmitter, Item[]> TYRVING_PARTICLE_EMITTER                        = register("tyrving",                        new TyrvingParticleEmitter(), TYRVING);

    static <T extends ItemParticleEmitter> Pair<T, Item[]> register(String id, T particleEmitter, Item... items) {
        PARTICLE_EMITTERS.put(new Identifier(MODID, id), new Pair<>(particleEmitter, items));
        return new Pair<>(particleEmitter, items);
    }

    static void init() {
        PARTICLE_EMITTERS.forEach((id, pair) -> Registry.register(MalumParticleEmitterRegistry.PARTICLE_EMITTER, id, pair));
    }
}
