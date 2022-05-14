package ca.rttv.malum.client.init;

import ca.rttv.malum.client.particle.SimpleMalumParticleType;
import ca.rttv.malum.client.particle.cut.ScytheAttackParticle;
import ca.rttv.malum.client.particle.spiritflame.SpiritFlameParticleType;
import ca.rttv.malum.util.helper.DataHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.BiConsumer;

@SuppressWarnings("SameParameterValue")
public class MalumParticleRegistry {
    public static final DefaultParticleType SCYTHE_CUT_ATTACK_PARTICLE = FabricParticleTypes.simple(true);
    public static final DefaultParticleType SCYTHE_SWEEP_ATTACK_PARTICLE = FabricParticleTypes.simple(true);

    public static final SimpleMalumParticleType WISP_PARTICLE = new SimpleMalumParticleType();
    public static final SimpleMalumParticleType SMOKE_PARTICLE = new SimpleMalumParticleType();
    public static final SimpleMalumParticleType SPARKLE_PARTICLE = new SimpleMalumParticleType();
    public static final SimpleMalumParticleType TWINKLE_PARTICLE = new SimpleMalumParticleType();
    public static final SimpleMalumParticleType STAR_PARTICLE = new SimpleMalumParticleType();

    public static final SpiritFlameParticleType SPIRIT_FLAME_PARTICLE = new SpiritFlameParticleType();

    public static void init() {
        initParticles(bind(Registry.PARTICLE_TYPE));
    }

    public static void registerFactories() {
        ParticleFactoryRegistry.getInstance().register(SCYTHE_CUT_ATTACK_PARTICLE, ScytheAttackParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SCYTHE_SWEEP_ATTACK_PARTICLE, ScytheAttackParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(WISP_PARTICLE, SimpleMalumParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SMOKE_PARTICLE, SimpleMalumParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SPARKLE_PARTICLE, SimpleMalumParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(TWINKLE_PARTICLE, SimpleMalumParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(STAR_PARTICLE, SimpleMalumParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SPIRIT_FLAME_PARTICLE, SpiritFlameParticleType.Factory::new);
    }
    // shamelessly stolen from Botania
    private static void initParticles(BiConsumer<ParticleType<?>, Identifier> registry) {
        registry.accept(SCYTHE_CUT_ATTACK_PARTICLE, DataHelper.prefix("scythe_cut_attack"));
        registry.accept(SCYTHE_SWEEP_ATTACK_PARTICLE, DataHelper.prefix("scythe_sweep_attack"));
        registry.accept(WISP_PARTICLE, DataHelper.prefix("wisp"));
        registry.accept(SMOKE_PARTICLE, DataHelper.prefix("smoke"));
        registry.accept(SPARKLE_PARTICLE, DataHelper.prefix("sparkle"));
        registry.accept(TWINKLE_PARTICLE, DataHelper.prefix("twinkle"));
        registry.accept(STAR_PARTICLE, DataHelper.prefix("star"));
        registry.accept(SPIRIT_FLAME_PARTICLE, DataHelper.prefix("spirit_flame"));
    }
    // guess where this one comes from
    private static <T> BiConsumer<T, Identifier> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }
}
