package ca.rttv.malum.block;

import ca.rttv.malum.registry.MalumSoundRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TheDevice extends Block {
    public TheDevice(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hand.equals(Hand.MAIN_HAND)) {
            player.swingHand(hand, true);
            playSound(world, pos);
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        boolean flag = world.isReceivingRedstonePower(pos);
        if (flag) {
            playSound(world, pos);
        }
    }

    public void playSound(World level, BlockPos pos)
    {
        level.playSound(null, pos, MalumSoundRegistry.SUSPICIOUS_SOUND, SoundCategory.BLOCKS, 1, 1 );
    }
}
