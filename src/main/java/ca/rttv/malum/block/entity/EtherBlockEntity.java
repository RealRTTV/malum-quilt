package ca.rttv.malum.block.entity;

import ca.rttv.malum.block.EtherBrazierBlock;
import ca.rttv.malum.block.EtherTorchBlock;
import ca.rttv.malum.block.EtherWallTorchBlock;
import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.util.helper.ColorHelper;
import ca.rttv.malum.util.helper.NbtHelper;
import ca.rttv.malum.util.particle.Easing;
import ca.rttv.malum.util.particle.ParticleBuilders;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.ETHER_BLOCK_ENTITY;

public class EtherBlockEntity extends BlockEntity {
    public int firstColorRGB;
    public Color firstColor;
    public int secondColorRGB;
    public Color secondColor;

    public EtherBlockEntity(BlockPos pos, BlockState state, int firstColor, int secondColor) {
        this(ETHER_BLOCK_ENTITY, pos, state, firstColor, secondColor);
    }

    public EtherBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int firstColor, int secondColor) {
        super(type, pos, state);
        this.firstColorRGB = firstColor;
        this.secondColorRGB = secondColor;
        this.firstColor = new Color(firstColorRGB);
        this.secondColor = new Color(secondColorRGB);
    }

    public void clientTick(World world, BlockPos pos, BlockState state) {
        if (firstColor == null) {
            return;
        }
        Color firstColor = ColorHelper.darker(this.firstColor, 1);
        Color secondColor = this.secondColor == null ? firstColor : ColorHelper.brighter(this.secondColor, 1);
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.6;
        double z = pos.getZ() + 0.5;
        int lifeTime = 14 + world.random.nextInt(4);
        float scale = 0.17f + world.random.nextFloat() * 0.03f;
        float velocity = 0.04f + world.random.nextFloat() * 0.02f;
        if (getCachedState().getBlock() instanceof EtherWallTorchBlock) {
            Direction direction = getCachedState().get(WallTorchBlock.FACING);
            x += direction.getVector().getX() * -0.28f;
            y += 0.2f;
            z += direction.getVector().getZ() * -0.28f;
            lifeTime -= 6;
        }

        if (getCachedState().getBlock() instanceof EtherTorchBlock) {
            lifeTime -= 4;
        }
        if (getCachedState().getBlock() instanceof EtherBrazierBlock) {
            y -= 0.2f;
            lifeTime -= 2;
            scale *= 1.25f;
        }
        ParticleBuilders.create(MalumParticleRegistry.WISP_PARTICLE)
                .setScale(scale, 0)
                .setLifetime(lifeTime)
                .setAlpha(0.8f, 0.5f)
                .setColor(firstColor, secondColor)
                .setColorCurveMultiplier(0.8f)
                .setColorEasing(Easing.CIRC_OUT)
                .setSpinOffset(world.getTime() % 710 * 0.2f)
                .setSpin(0, 0.4f)
                .setSpinEasing(Easing.QUARTIC_IN)
                .addMotion(0, velocity, 0)
                .enableNoClip()
                .spawn(world, x, y, z);
        ParticleBuilders.create(MalumParticleRegistry.SPARKLE_PARTICLE)
                .setScale(scale * 2, 0)
                .setLifetime(lifeTime)
                .setAlpha(0.2f)
                .setColor(firstColor, secondColor)
                .setColorCurveMultiplier(1.5f)
                .setAlphaCurveMultiplier(1.5f)
                .setSpin(0, 2)
                .setSpinEasing(Easing.QUARTIC_IN)
                .enableNoClip()
                .spawn(world, x, y, z);
        if (world.getTime() % 2L == 0 && world.random.nextFloat() < 0.25f) {
            y += 0.15f;
            ParticleBuilders.create(MalumParticleRegistry.SPIRIT_FLAME_PARTICLE)
                    .setScale(0.75f, 0)
                    .setColor(firstColor, secondColor)
                    .setColorCurveMultiplier(2f)
                    .setAlphaCurveMultiplier(3f)
                    .randomOffset(0.15f, 0.2f)
                    .addMotion(0, 0.03f, 0)
                    .enableNoClip()
                    .spawn(world, x, y, z);
            ParticleBuilders.create(MalumParticleRegistry.SPIRIT_FLAME_PARTICLE)
                    .setScale(0.5f, 0)
                    .setColor(firstColor, secondColor)
                    .setColorCurveMultiplier(3f)
                    .setAlphaCurveMultiplier(3f)
                    .randomOffset(0.15f, 0.2f)
                    .addMotion(0, velocity, 0)
                    .enableNoClip()
                    .spawn(world, x, y, z);
        }
    }

    public EtherBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, 15712278, 4607909);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.firstColorRGB = nbt.getInt("FirstColor");
        this.firstColor = new Color(firstColorRGB);
        this.secondColorRGB = nbt.getInt("SecondColor");
        this.secondColor = new Color(secondColorRGB);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("FirstColor", firstColorRGB);
        nbt.putInt("SecondColor", secondColorRGB);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound tag = super.toInitialChunkDataNbt();
        this.writeNbt(tag);
        return tag;
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        firstColorRGB = NbtHelper.getOrDefaultInt(nbt -> nbt.getCompound("display").getInt("FirstColor"), 15712278, itemStack.getNbt());
        this.firstColor = new Color(firstColorRGB);
        secondColorRGB = NbtHelper.getOrDefaultInt(nbt -> nbt.getCompound("display").getInt("SecondColor"), 4607909, itemStack.getNbt());
        this.secondColor = new Color(secondColorRGB);
    }
}
