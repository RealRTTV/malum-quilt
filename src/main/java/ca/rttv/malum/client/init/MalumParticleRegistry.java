package ca.rttv.malum.client.init;

import ca.rttv.malum.client.particle.SimpleMalumParticleType;
import ca.rttv.malum.client.particle.cut.ScytheAttackParticle;
import ca.rttv.malum.client.particle.spiritflame.SpiritFlameParticleType;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.util.particle.world.WorldParticleEffect;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static ca.rttv.malum.util.helper.DataHelper.prefix;

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
    private static void initParticles(BiConsumer<ParticleType<?>, Identifier> b) {
        b.accept(SCYTHE_CUT_ATTACK_PARTICLE, prefix("scythe_cut_attack"));
        b.accept(SCYTHE_SWEEP_ATTACK_PARTICLE, prefix("scythe_sweep_attack"));
        b.accept(WISP_PARTICLE, prefix("wisp"));
        b.accept(SMOKE_PARTICLE, prefix("smoke"));
        b.accept(SPARKLE_PARTICLE, prefix("sparkle"));
        b.accept(TWINKLE_PARTICLE, prefix("twinkle"));
        b.accept(STAR_PARTICLE, prefix("star"));
        b.accept(SPIRIT_FLAME_PARTICLE, prefix("spirit_flame"));
    }
    // guess where this one comes from
    private static <T> BiConsumer<T, Identifier> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }
}
