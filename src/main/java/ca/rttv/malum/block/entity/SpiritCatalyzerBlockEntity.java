package ca.rttv.malum.block.entity;

import ca.rttv.malum.item.SpiritItem;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.util.helper.SpiritHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.SPIRIT_CATALYZER_BLOCK_ENTITY;

public class SpiritCatalyzerBlockEntity extends AbstractItemDisplayBlockEntity {
    private int fuelTicks;

    public SpiritCatalyzerBlockEntity(BlockPos pos, BlockState state) {
        this(SPIRIT_CATALYZER_BLOCK_ENTITY, pos, state);
    }

    public SpiritCatalyzerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }

    @Override
    public Vec3d itemOffset() {
        return new Vec3d(0.5d, 0.95d, 0.5d);
    }

    public void clientTick(World world, BlockPos pos, BlockState state) {
        if (this.getHeldItem().getItem() instanceof SpiritItem item) {
            Vec3d vec = DataHelper.fromBlockPos(pos).add(itemOffset());
            double x = vec.x;
            double y = vec.y + Math.sin(world.getTime() % 14200 / 20f) * 0.1f;
            double z = vec.z;
            SpiritHelper.spawnSpiritParticles(world, x, y, z, item.type.color, item.type.endColor);
        }
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        this.swapSlots(state, world, pos, player, hand, hit);
        return ActionResult.CONSUME;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("FuelTicks", fuelTicks);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        fuelTicks = nbt.getInt("FuelTicks");
    }

    public void fuelTick() {
        fuelTicks--;
        if (fuelTicks <= 0) {
            fuelTicks = AbstractFurnaceBlockEntity.createFuelTimeMap().get(this.getHeldItem().getItem()) / 2;
            this.getHeldItem().decrement(1);
            if (this.getHeldItem().isEmpty()) {
                this.setStack(0, ItemStack.EMPTY);
            }
        }
    }
}
