package ca.rttv.malum.rite;

import ca.rttv.malum.network.packet.s2c.play.MalumParticleS2CPacket;
import ca.rttv.malum.util.spirit.SpiritType;
import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.random.RandomGenerator;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.List;
import java.util.stream.StreamSupport;

import static ca.rttv.malum.Malum.MODID;

public class EldritchSacredRite extends Rite {
    public EldritchSacredRite(Item... items) {
        super(items);
    }

    @Override
    public void onTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random, long tick) {
        if (tick % 100 != 0) {
            return;
        }

        StreamSupport.stream(BlockPos.iterateOutwards(pos, 4, 0, 4).spliterator(), false).filter(possiblePos -> world.getBlockState(possiblePos).getBlock() instanceof Fertilizable).forEach(possiblePos -> {
            if (world.random.nextFloat() <= 0.1f) {
                BlockState state2 = world.getBlockState(possiblePos);

                for (int i = 0; i < 5 + world.random.nextInt(3); i++) {
                    state2.randomTick(world, possiblePos, world.random);
                }

                BlockPos particlePos = state2.isOpaque() ? possiblePos : possiblePos.down();
                world.getPlayers(players -> players.getWorld().isChunkLoaded(new ChunkPos(particlePos).x, new ChunkPos(particlePos).z)).forEach(players -> {
                    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                    new MalumParticleS2CPacket(SpiritType.SACRED_SPIRIT.color.getRGB(), particlePos.getX() + 0.5d, particlePos.getY() + 0.5d, particlePos.getZ() + 0.5d).write(buf);
                    ServerPlayNetworking.send(players, new Identifier(MODID, "malumparticles2cpacket"), buf);
                });
            }
        });
    }

    @Override
    public void onCorruptTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random, long tick) {
        if (tick % 100 != 0) {
            return;
        }
        List<AnimalEntity> animals = world.getEntitiesByClass(AnimalEntity.class, new Box(pos.add(-4, -4, -4), pos.add(4, 4, 4)), entity -> entity.getBreedingAge() == 0);
        if (animals.size() < 32) {
            float chance = (float) Math.pow(1.12, 32 - animals.size()) / 180;
            animals.forEach(entity -> {
                if (entity.canEat() && world.random.nextFloat() < chance) {
                    entity.setLoveTicks(1200);
                    world.sendEntityStatus(entity, (byte)18);
                    world.getPlayers(players -> players.getWorld().isChunkLoaded(entity.getChunkPos().x, entity.getChunkPos().z)).forEach(players -> {
                        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                        new MalumParticleS2CPacket(SpiritType.SACRED_SPIRIT.color.getRGB(), entity.getX(), entity.getY(), entity.getZ()).write(buf);
                        ServerPlayNetworking.send(players, new Identifier(MODID, "malumparticles2cpacket"), buf);
                    });
                }
            });
        }
    }
}
