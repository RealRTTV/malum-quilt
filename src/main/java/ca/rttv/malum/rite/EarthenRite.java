package ca.rttv.malum.rite;

import net.minecraft.block.BlockState;
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

        world.getEntitiesByClass(PlayerEntity.class, new Box(pos.subtract(new Vec3i(8, 8, 8)), pos.add(8, 8, 8)), player -> !player.isSpectator()).forEach(player -> player.addStatusEffect(new StatusEffectInstance(EARTHEN_AURA, 200, 1)));
    }

    @Override
    public void onCorruptTick(BlockState state, ServerWorld world, BlockPos pos, Random random, long tick) {
        if (tick % 20 != 0) {
            return;
        }

        world.getEntitiesByClass(PlayerEntity.class, new Box(pos.subtract(new Vec3i(8, 8, 8)), pos.add(8, 8, 8)), player -> !player.isSpectator()).forEach(player -> player.addStatusEffect(new StatusEffectInstance(CORRUPTED_EARTHEN_AURA, 200, 1)));
    }
}
