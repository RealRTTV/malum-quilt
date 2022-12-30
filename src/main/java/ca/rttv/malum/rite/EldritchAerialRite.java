package ca.rttv.malum.rite;

import ca.rttv.malum.network.packet.s2c.play.MalumParticleS2CPacket;
import ca.rttv.malum.util.spirit.SpiritType;
import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.random.RandomGenerator;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.stream.StreamSupport;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumStatusEffectRegistry.CORRUPTED_AERIAL_AURA;

public class EldritchAerialRite extends Rite {
    public EldritchAerialRite(Item... items) {
        super(items);
    }

    @Override
    public void onTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random, long tick) {
        if (tick % 60 != 0) {
            return;
        }

        StreamSupport.stream(BlockPos.iterateOutwards(pos.down(), 2, 0, 2).spliterator(), false).filter(fallingPos -> !fallingPos.up().equals(pos)).forEach(fallingPos -> {
            FallingBlockEntity.fall(world, fallingPos, world.getBlockState(fallingPos));
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.getPlayers().stream().filter(players -> players.getWorld().isChunkLoaded(new ChunkPos(fallingPos).x, new ChunkPos(fallingPos).z)).forEach(players -> {
                PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                new MalumParticleS2CPacket(SpiritType.AERIAL_SPIRIT.color.getRGB(), fallingPos.getX() + 0.5d, fallingPos.getY() + 0.5d, fallingPos.getZ() + 0.5d).write(buf);
                ServerPlayNetworking.send(players, new Identifier(MODID, "malumparticles2cpacket"), buf);
            });
        });
    }

    @Override
    public void onCorruptTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random, long tick) {
        if (tick % 20 != 0) {
            return;
        }

        world.getEntitiesByClass(PlayerEntity.class, new Box(pos.subtract(new Vec3i(2, 2, 2)), pos.add(new Vec3i(2, 2, 2))), player -> !player.isSpectator()).forEach(player -> {
            if (!player.hasStatusEffect(CORRUPTED_AERIAL_AURA)) {
                world.getPlayers().stream().filter(players -> players.getWorld().isChunkLoaded(player.getChunkPos().x, player.getChunkPos().z)).forEach(players -> {
                    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                    new MalumParticleS2CPacket(SpiritType.AERIAL_SPIRIT.color.getRGB(), player.getX(), player.getY(), player.getZ()).write(buf);
                    ServerPlayNetworking.send(players, new Identifier(MODID, "malumparticles2cpacket"), buf);
                });
            }
            player.addStatusEffect(new StatusEffectInstance(CORRUPTED_AERIAL_AURA, 100, 40));
        });
    }
}
