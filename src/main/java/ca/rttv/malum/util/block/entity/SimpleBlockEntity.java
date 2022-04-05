package ca.rttv.malum.util.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class SimpleBlockEntity extends BlockEntity {
    public SimpleBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public void onBreak() {

    }

    public void onPlace(LivingEntity placer, ItemStack stack) {
    }

    public void onNeighborUpdate(BlockState state, BlockPos pos, BlockPos neighbor) {
    }

    public ItemStack onClone(BlockState state, HitResult target, BlockView level, BlockPos pos, PlayerEntity player) {
        return ItemStack.EMPTY;
    }

    public ActionResult onUse(PlayerEntity player, Hand hand) {
        return ActionResult.PASS;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.toNbt();
    }


//    @Override
//    public void handleUpdateTag(CompoundTag tag) {
//        if (tag != null) {
//            super.handleUpdateTag(tag);
//        }
//    }
//
//    @Override
//    public ClientboundBlockEntityDataPacket getUpdatePacket() {
//        return ClientboundBlockEntityDataPacket.create(this);
//    }

//
//    @Override
//    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
//        super.onDataPacket(net, pkt);
//        handleUpdateTag(getUpdatePacket().getTag());
//    }

    public void tick() {

    }
}
