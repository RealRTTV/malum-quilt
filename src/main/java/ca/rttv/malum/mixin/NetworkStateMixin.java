package ca.rttv.malum.mixin;

import ca.rttv.malum.network.packet.s2c.play.MalumParticleS2CPacket;
import net.minecraft.network.NetworkState;
import net.minecraft.network.NetworkState.PacketHandler;
import net.minecraft.network.listener.PacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(NetworkState.class)
public abstract class NetworkStateMixin {
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkState$PacketHandlerInitializer;setup(Lnet/minecraft/network/NetworkSide;Lnet/minecraft/network/NetworkState$PacketHandler;)Lnet/minecraft/network/NetworkState$PacketHandlerInitializer;", ordinal = 1), index = 1)
    private static <T extends PacketListener> PacketHandler<T> playPackets(PacketHandler<T> handler) {
        return handler.register(MalumParticleS2CPacket.class, MalumParticleS2CPacket::new);
    }
}
