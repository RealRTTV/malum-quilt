package ca.rttv.malum.util.particle.screen;

import ca.rttv.malum.util.particle.screen.base.ScreenParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleType;

import java.util.function.Supplier;

public class ScreenParticleType<T extends ScreenParticleEffect> {

    public Factory<T> factory;
    public ScreenParticleType() {
    }

    public interface Factory<T extends ScreenParticleEffect> {
        ScreenParticle createParticle(ClientWorld pWorld, T options, double pX, double pY, double pXSpeed, double pYSpeed);
    }
}
