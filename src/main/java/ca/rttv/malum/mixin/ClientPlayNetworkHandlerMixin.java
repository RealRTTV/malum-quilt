package ca.rttv.malum.mixin;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.duck.MalumClientPlayPacketListener;
import ca.rttv.malum.network.packet.s2c.play.MalumParticleS2CPacket;
import ca.rttv.malum.util.helper.ColorHelper;
import ca.rttv.malum.util.particle.ParticleBuilders;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.listener.PacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;

@Mixin(ClientPlayNetworkHandler.class)
public final class ClientPlayNetworkHandlerMixin implements MalumClientPlayPacketListener {
    @Shadow private ClientWorld world;

    @Override
    public <T extends PacketListener> void onMagicParticle(MalumParticleS2CPacket<T> packet) {
        ParticleBuilders.create(MalumParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.1f, 0f)
                .setLifetime(10)
                .setSpin(0.4f)
                .setScale(0.4f, 0)
                .setColor(new Color(packet.color()), ColorHelper.darker(new Color(packet.color()), 1))
                .enableNoClip()
                .randomOffset(0.2f, 0.2f)
                .randomMotion(0.01f, 0.01f)
                .repeat(world, packet.x(), packet.y(), packet.z(), 20);

        ParticleBuilders.create(MalumParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0.05f, 0f)
                .setLifetime(20)
                .setSpin(0.1f)
                .setScale(0.6f, 0)
                .setColor(new Color(packet.color()), ColorHelper.darker(new Color(packet.color()), 1))
                .randomOffset(0.4f)
                .enableNoClip()
                .randomMotion(0.025f, 0.025f)
                .repeat(world, packet.x(), packet.y(), packet.z(), 20);
    }
}
