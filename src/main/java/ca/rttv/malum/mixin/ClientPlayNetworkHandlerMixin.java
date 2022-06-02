package ca.rttv.malum.mixin;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.duck.MalumClientPlayPacketListener;
import ca.rttv.malum.network.packet.s2c.play.MalumParticleS2CPacket;
import ca.rttv.malum.registry.MalumItemRegistry;
import ca.rttv.malum.util.helper.ColorHelper;
import ca.rttv.malum.util.particle.ParticleBuilders;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(ClientPlayNetworkHandler.class)
final class ClientPlayNetworkHandlerMixin implements MalumClientPlayPacketListener {
    @Shadow private ClientWorld world;

    @Override
    public void onMagicParticle(MalumParticleS2CPacket packet) {
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

    @Inject(method = "getActiveTotemOfUndying", at = @At("HEAD"), cancellable = true)
    private static void getActiveTotemOfUndying(PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        for (Hand hand : Hand.values()) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.isOf(MalumItemRegistry.CEASELESS_IMPETUS)) {
                cir.setReturnValue(itemStack);
            }
        }
    }
}
