package ca.rttv.malum.client.init;

import ca.rttv.malum.client.particle.SimpleScreenParticleType;
import ca.rttv.malum.mixin.FabricSpriteProviderImplAccessor;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.util.particle.screen.ScreenParticleEffect;
import ca.rttv.malum.util.particle.screen.ScreenParticleType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class MalumScreenParticleRegistry {
    public static final ArrayList<ScreenParticleType<?>> PARTICLE_TYPES = new ArrayList<>();
    public static final ScreenParticleType<ScreenParticleEffect> WISP = registerType(new SimpleScreenParticleType());
    public static final ScreenParticleType<ScreenParticleEffect> SMOKE = registerType(new SimpleScreenParticleType());
    public static final ScreenParticleType<ScreenParticleEffect> SPARKLE = registerType(new SimpleScreenParticleType());
    public static final ScreenParticleType<ScreenParticleEffect> TWINKLE = registerType(new SimpleScreenParticleType());
    public static final ScreenParticleType<ScreenParticleEffect> STAR = registerType(new SimpleScreenParticleType());

    public static void registerParticleFactories() {
        registerProvider(WISP, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("wisp"))));
        registerProvider(SMOKE, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("smoke"))));
        registerProvider(SPARKLE, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("sparkle"))));
        registerProvider(TWINKLE, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("twinkle"))));
        registerProvider(STAR, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("star"))));
    }

    public static <T extends ScreenParticleEffect> ScreenParticleType<T> registerType(ScreenParticleType<T> type) {
        PARTICLE_TYPES.add(type);
        return type;
    }

    public static <T extends ScreenParticleEffect> void registerProvider(ScreenParticleType<T> type, ScreenParticleType.Factory<T> provider) {
        type.factory = provider;
    }

    public static SpriteProvider getSpriteSet(Identifier resourceLocation) {
        final MinecraftClient client = MinecraftClient.getInstance();
        return FabricSpriteProviderImplAccessor.FabricSpriteProviderImpl(client.particleManager, client.particleManager.spriteAwareFactories.get(resourceLocation));
    }
}
