package ca.rttv.malum.item;

import ca.rttv.malum.block.TotemPoleBlock;
import ca.rttv.malum.item.IFloatingGlowItem;
import ca.rttv.malum.util.particle.screen.base.ScreenParticle;
import ca.rttv.malum.util.particle.screen.emitter.ItemParticleEmitter;
import ca.rttv.malum.util.spirit.SpiritType;
import ca.rttv.malum.util.spirit.SpiritTypeProperty;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.awt.*;

import static ca.rttv.malum.registry.MalumRegistry.*;
import static ca.rttv.malum.util.helper.SpiritHelper.spawnSpiritScreenParticles;

public class MalumSpiritItem extends Item implements IFloatingGlowItem, ItemParticleEmitter {
    public static final BiMap<Block, Block> POLE_BLOCKS = new ImmutableBiMap.Builder<Block, Block>()
            .put(RUNEWOOD_LOG, RUNEWOOD_TOTEM_POLE)
            .put(SOULWOOD_LOG, SOULWOOD_TOTEM_POLE)
            .build();
    public final SpiritType type;

    public MalumSpiritItem(Item.Settings settings, SpiritType type) {
        super(settings);
        this.type = type;
    }

    @Override
    public Color getColor() {
        return type.color;
    }

    @Override
    public Color getEndColor() {
        return type.endColor;
    }

    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        spawnSpiritScreenParticles(type.color, type.endColor, stack, x, y, renderOrder);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Block pole = POLE_BLOCKS.get(state.getBlock());
        if (pole != null && ctx.getSide().getAxis() != Direction.Axis.Y) {
            world.setBlockState(pos, pole.getDefaultState().with(TotemPoleBlock.FACING, ctx.getSide()).with(TotemPoleBlock.SPIRIT_TYPE, SpiritTypeProperty.of(ctx.getStack().getItem())));
            if (!world.isClient && !ctx.getPlayer().isCreative()) {
                ctx.getStack().decrement(1);
            }
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }
}
