package ca.rttv.malum.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class RevealedPillarBlock extends PillarBlock {
    private final Item item;
    private final Block log;

    public RevealedPillarBlock(Settings settings, Item item, Block log) {
        super(settings);
        log.getDefaultState().get(PillarBlock.AXIS); // this is basically to assert that the block has an axis blockstate
        this.item = item;
        this.log = log;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).getItem() == Items.GLASS_BOTTLE) {
            if (!player.isCreative()) {
                if (world.random.nextBoolean()) {
                    world.setBlockState(pos, log.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)));
                }
                player.getStackInHand(hand).decrement(1);
            }
            if (!player.getInventory().insertStack(new ItemStack(item))) {
                player.dropStack(new ItemStack(item));
            }
            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }
}
