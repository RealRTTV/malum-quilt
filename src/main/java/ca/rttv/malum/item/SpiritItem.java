package ca.rttv.malum.item;

import ca.rttv.malum.block.TotemPoleBlock;
import ca.rttv.malum.util.spirit.SpiritType;
import ca.rttv.malum.util.spirit.SpiritTypeProperty;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.awt.*;

import static ca.rttv.malum.registry.MalumBlockRegistry.*;

public class SpiritItem extends Item implements IFloatingGlowItem {
    public static final BiMap<Block, Block> POLE_BLOCKS = new ImmutableBiMap.Builder<Block, Block>()
            .put(RUNEWOOD_LOG, RUNEWOOD_TOTEM_POLE)
            .put(SOULWOOD_LOG, SOULWOOD_TOTEM_POLE)
            .build();
    public final SpiritType type;

    public SpiritItem(Item.Settings settings, SpiritType type) {
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
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = ctx.getPlayer();
        Block pole = POLE_BLOCKS.get(state.getBlock());
        if (pole != null && ctx.getSide().getAxis() != Direction.Axis.Y) {
            world.setBlockState(pos, pole.getDefaultState().with(TotemPoleBlock.FACING, ctx.getSide()).with(TotemPoleBlock.SPIRIT_TYPE, SpiritTypeProperty.of(ctx.getStack().getItem())));
            if (!world.isClient && (player == null || !player.isCreative())) {
                ctx.getStack().decrement(1);
            }
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }
}
