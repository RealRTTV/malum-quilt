package ca.rttv.malum.rite;

import ca.rttv.malum.network.packet.s2c.play.MalumParticleS2CPacket;
import ca.rttv.malum.util.spirit.SpiritType;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;

import java.util.Random;

import static ca.rttv.malum.registry.MalumRegistry.CORRUPTED_EARTHEN_AURA;
import static ca.rttv.malum.registry.MalumRegistry.EARTHEN_AURA;

public class EarthenRite extends Rite {
    public EarthenRite(Item... items) {
        super(items);
    }

    @Override
    public void onTick(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick) {
        if (tick % 20 != 0) {
            return;
        }

        world.getEntitiesByClass(PlayerEntity.class, new Box(pos.subtract(new Vec3i(8, 8, 8)), pos.add(8, 8, 8)), player -> !player.isSpectator()).forEach(player -> {
            if (!player.hasStatusEffect(EARTHEN_AURA)) {
                world.getPlayers(players -> players.getWorld().isChunkLoaded(player.getChunkPos().x, player.getChunkPos().z)).forEach(players -> players.networkHandler.sendPacket(new MalumParticleS2CPacket<ClientPlayNetworkHandler>(SpiritType.EARTHEN_SPIRIT.color.getRGB(), player.getX(), player.getY(), player.getZ())));
            }
            player.addStatusEffect(new StatusEffectInstance(EARTHEN_AURA, 220, 1));
        });
    }

    @Override
    public void onCorruptTick(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick) {
        if (tick % 20 != 0) {
            return;
        }

        world.getEntitiesByClass(PlayerEntity.class, new Box(pos.subtract(new Vec3i(8, 8, 8)), pos.add(8, 8, 8)), player -> !player.isSpectator()).forEach(player -> {
            if (!player.hasStatusEffect(CORRUPTED_EARTHEN_AURA)) {
                world.getPlayers(players -> players.getWorld().isChunkLoaded(player.getChunkPos().x, player.getChunkPos().z)).forEach(players -> players.networkHandler.sendPacket(new MalumParticleS2CPacket<ClientPlayNetworkHandler>(SpiritType.EARTHEN_SPIRIT.color.getRGB(), player.getX(), player.getY(), player.getZ())));
            }
            player.addStatusEffect(new StatusEffectInstance(CORRUPTED_EARTHEN_AURA, 220, 1));
        });
    }
}
