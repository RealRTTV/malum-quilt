package ca.rttv.malum.rite;

import ca.rttv.malum.network.packet.s2c.play.MalumParticleS2CPacket;
import ca.rttv.malum.util.spirit.SpiritType;
import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.Random;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumStatusEffectRegistry.SACRED_AURA;

public class SacredRite extends Rite {
    public SacredRite(Item... items) {
        super(items);
    }

    @Override
    public void onTick(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick) {
        if (tick % 20 != 0) {
            return;
        }

        world.getEntitiesByClass(PlayerEntity.class, new Box(pos.add(-4, -4, -4), pos.add(4, 4, 4)), player -> !player.isSpectator()).forEach(player -> {
            if (!player.hasStatusEffect(SACRED_AURA)) {
                world.getPlayers(players -> players.getWorld().isChunkLoaded(player.getChunkPos().x, player.getChunkPos().z)).forEach(players -> {
                    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                    new MalumParticleS2CPacket(SpiritType.SACRED_SPIRIT.color.getRGB(), player.getX(), player.getY(), player.getZ()).write(buf);
                    ServerPlayNetworking.send(players, new Identifier(MODID, "MalumParticleS2CPacket"), buf);
                });
            }
            player.addStatusEffect(new StatusEffectInstance(SACRED_AURA, 200, 0));
        });
    }

    @Override
    public void onCorruptTick(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick) {
        if (tick % 20 != 0) {
            return;
        }

        world.getEntitiesByClass(AnimalEntity.class, new Box(pos.add(-4, -4, -4), pos.add(4, 4, 4)), Entity::isLiving).forEach(entity -> {
            if (world.random.nextFloat() <= 0.04f) {
                if (entity.getBreedingAge() < 0) {
                    world.getPlayers(players -> players.getWorld().isChunkLoaded(entity.getChunkPos().x, entity.getChunkPos().z)).forEach(players -> {
                        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                        new MalumParticleS2CPacket(SpiritType.SACRED_SPIRIT.color.getRGB(), entity.getX(), entity.getY(), entity.getZ()).write(buf);
                        ServerPlayNetworking.send(players, new Identifier(MODID, "MalumParticleS2CPacket"), buf);
                    });
                    entity.setBreedingAge(entity.getBreedingAge() + 25);
                }
            }
        });
    }
}
