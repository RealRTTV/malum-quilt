package ca.rttv.malum.util.particle.world;

import ca.rttv.malum.util.particle.SimpleParticleEffect;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;

import java.util.ArrayList;

public class FrameSetParticle extends GenericParticle {
    public ArrayList<Integer> frameSet = new ArrayList<>();
    public FrameSetParticle(ClientWorld world, WorldParticleEffect data, ParticleManager.SimpleSpriteProvider spriteSet, double x, double y, double z, double xd, double yd, double zd) {
        super(world, data, spriteSet, x, y, z, xd, yd, zd);
    }

    @Override
    public void tick() {
        if (age < frameSet.size()) {
            pickSprite(frameSet.get(age));
        }
        super.tick();
    }

    @Override
    public SimpleParticleEffect.Animator getAnimator() {
        return SimpleParticleEffect.Animator.FIRST_INDEX;
    }

    protected void addLoop(int min, int max, int times) {
        for (int i = 0; i < times; i++) {
            addFrames(min, max);
        }
    }

    protected void addFrames(int min, int max) {
        for (int i = min; i <= max; i++) {
            frameSet.add(i);
        }
    }

    protected void insertFrames(int insertIndex, int min, int max) {
        for (int i = min; i <= max; i++) {
            frameSet.add(insertIndex, i);
        }
    }
}
