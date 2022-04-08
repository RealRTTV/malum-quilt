package ca.rttv.malum.client.init;

import ca.rttv.malum.client.particle.SimpleMalumParticleType;
import ca.rttv.malum.client.particle.cut.ScytheAttackParticle;
import ca.rttv.malum.client.particle.spiritflame.SpiritFlameParticleType;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;

public class MalumParticleRegistry {
    public static DefaultParticleType SCYTHE_CUT_ATTACK_PARTICLE;
    public static DefaultParticleType SCYTHE_SWEEP_ATTACK_PARTICLE;

    public static SimpleMalumParticleType WISP_PARTICLE;
    public static SimpleMalumParticleType SMOKE_PARTICLE;
    public static SimpleMalumParticleType SPARKLE_PARTICLE;
    public static SimpleMalumParticleType TWINKLE_PARTICLE;
    public static SimpleMalumParticleType STAR_PARTICLE;

    public static SpiritFlameParticleType SPIRIT_FLAME_PARTICLE;
    public static void init() {
        SCYTHE_CUT_ATTACK_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "malum:scythe_cut_attack", FabricParticleTypes.simple(true));
        ParticleFactoryRegistry.getInstance().register(MalumParticleRegistry.SCYTHE_CUT_ATTACK_PARTICLE, ScytheAttackParticle.Factory::new);
        SCYTHE_SWEEP_ATTACK_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "malum:scythe_sweep_attack", FabricParticleTypes.simple(true));
        ParticleFactoryRegistry.getInstance().register(MalumParticleRegistry.SCYTHE_SWEEP_ATTACK_PARTICLE, ScytheAttackParticle.Factory::new);
        WISP_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "malum:wisp", new SimpleMalumParticleType());
        ParticleFactoryRegistry.getInstance().register(MalumParticleRegistry.WISP_PARTICLE, SimpleMalumParticleType.Factory::new);
        SMOKE_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "malum:smoke", new SimpleMalumParticleType());
        ParticleFactoryRegistry.getInstance().register(MalumParticleRegistry.SMOKE_PARTICLE, SimpleMalumParticleType.Factory::new);
        SPARKLE_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "malum:sparkle", new SimpleMalumParticleType());
        ParticleFactoryRegistry.getInstance().register(MalumParticleRegistry.SPARKLE_PARTICLE, SimpleMalumParticleType.Factory::new);
        TWINKLE_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "malum:twinkle", new SimpleMalumParticleType());
        ParticleFactoryRegistry.getInstance().register(MalumParticleRegistry.TWINKLE_PARTICLE, SimpleMalumParticleType.Factory::new);
        STAR_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "malum:star", new SimpleMalumParticleType());
        ParticleFactoryRegistry.getInstance().register(MalumParticleRegistry.STAR_PARTICLE, SimpleMalumParticleType.Factory::new);
        SPIRIT_FLAME_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "malum:spirit_flame", new SpiritFlameParticleType());
        ParticleFactoryRegistry.getInstance().register(MalumParticleRegistry.SPIRIT_FLAME_PARTICLE, SpiritFlameParticleType.Factory::new);
    }
}
