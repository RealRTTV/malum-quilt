package ca.rttv.malum.client.init;

import ca.rttv.malum.client.particle.cut.ScytheAttackParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;

public class MalumParticleRegistry {
    public static DefaultParticleType SCYTHE_CUT_ATTACK_PARTICLE;
    public static DefaultParticleType SCYTHE_SWEEP_ATTACK_PARTICLE;
    public static void init() {
        SCYTHE_CUT_ATTACK_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "malum:scythe_cut_attack", FabricParticleTypes.simple(true));
        ParticleFactoryRegistry.getInstance().register(MalumParticleRegistry.SCYTHE_CUT_ATTACK_PARTICLE, ScytheAttackParticle.Factory::new);
        SCYTHE_SWEEP_ATTACK_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, "malum:scythe_sweep_attack", FabricParticleTypes.simple(true));
        ParticleFactoryRegistry.getInstance().register(MalumParticleRegistry.SCYTHE_SWEEP_ATTACK_PARTICLE, ScytheAttackParticle.Factory::new);
    }
}
