package ca.rttv.malum.block.entity;

import ca.rttv.malum.block.TotemPoleBlock;
import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.util.particle.ParticleBuilders;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.List;

import static ca.rttv.malum.registry.MalumRegistry.TOTEM_POLE_BLOCK_ENTITY;

public class TotemPoleBlockEntity extends BlockEntity {
    public List<Item> list;
    public int desiredColor;
    public int currentColor;

    public TotemPoleBlockEntity(BlockPos pos, BlockState state) {
        this(TOTEM_POLE_BLOCK_ENTITY, pos, state);
    }

    public TotemPoleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("desiredColor", desiredColor);
        nbt.putInt("currentColor", currentColor);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }


    public void tick() {
        if (currentColor > desiredColor) {
            currentColor--;
        }
        if (currentColor < desiredColor) {
            currentColor++;
        }
        if (world.isClient) {
            if (getCachedState().get(TotemPoleBlock.SPIRIT_TYPE).spirit != null && desiredColor != 0) {
                passiveParticles();
            }
        }
    }
    public void passiveParticles() {
        Color color = getCachedState().get(TotemPoleBlock.SPIRIT_TYPE).spirit.color;
        Color endColor = getCachedState().get(TotemPoleBlock.SPIRIT_TYPE).spirit.endColor;
        ParticleBuilders.create(MalumParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.06f, 0f)
                .setLifetime(5)
                .setSpin(0.2f)
                .setScale(0.4f, 0)
                .setColor(color, endColor)
                .setColorCurveMultiplier(2f)
                .addMotion(0, MathHelper.nextFloat(world.random, -0.03f, 0.03f), 0)
                .enableNoClip()
                .randomOffset(0.1f, 0.1f)
                .randomMotion(0.01f, 0.01f)
                .evenlyRepeatEdges(world, pos, 1, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);

        ParticleBuilders.create(MalumParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0.06f, 0f)
                .setLifetime(10)
                .setSpin(0.1f)
                .setScale(0.6f, 0)
                .setColor(color, endColor)
                .setColorCurveMultiplier(2f)
                .addMotion(0, MathHelper.nextFloat(world.random, -0.03f, 0.03f), 0)
                .randomOffset(0.2f)
                .enableNoClip()
                .randomMotion(0.01f, 0.01f)
                .evenlyRepeatEdges(world, pos, 1, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);
    }
}
