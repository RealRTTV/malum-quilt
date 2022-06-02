package ca.rttv.malum.rite;

import ca.rttv.malum.network.packet.s2c.play.MalumParticleS2CPacket;
import ca.rttv.malum.registry.MalumDamageSourceRegistry;
import ca.rttv.malum.util.spirit.SpiritType;
import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.List;
import java.util.Random;

import static ca.rttv.malum.Malum.MODID;

public class EldritchWickedRite extends Rite {
    public EldritchWickedRite(Item... items) {
        super(items);
    }

    @Override
    public void onTick(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick) {
        if (tick % 20 != 0) {
            world.getEntitiesByClass(LivingEntity.class, new Box(pos.add(-4, -4, -4), pos.add(4, 4, 4)), entity -> (!(entity instanceof PlayerEntity playerEntity) || !playerEntity.getAbilities().creativeMode)).forEach(entity -> entity.damage(MalumDamageSourceRegistry.VOODOO, 2.0f));
        }
    }

    @Override
    public void onCorruptTick(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick) {
        List<AnimalEntity> list = world.getEntitiesByClass(AnimalEntity.class, new Box(pos.add(-4, -4, -4), pos.add(4, 4, 4)), Entity::isLiving);
        if (list.size() < 30) {
            return;
        }

        final int[] maxKills = {list.size() - 30};
        list.forEach(entity -> {
            if (!entity.isInLove() && entity.getBreedingAge() > 0) {
                entity.damage(MalumDamageSourceRegistry.VOODOO, entity.getMaxHealth());
                world.getPlayers(players -> players.getWorld().isChunkLoaded(entity.getChunkPos().x, entity.getChunkPos().z)).forEach(players -> {
                    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                    new MalumParticleS2CPacket(SpiritType.WICKED_SPIRIT.color.getRGB(), entity.getX(), entity.getY(), entity.getZ()).write(buf);
                    ServerPlayNetworking.send(players, new Identifier(MODID, "malumparticles2cpacket"), buf);
                });
                maxKills[0]--;
            }
        });
    }
}
