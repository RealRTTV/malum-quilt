package ca.rttv.malum.item;

import ca.rttv.malum.client.init.MalumScreenParticleRegistry;
import ca.rttv.malum.util.particle.Easing;
import ca.rttv.malum.util.particle.ParticleBuilders;
import ca.rttv.malum.util.particle.screen.base.ScreenParticle;
import ca.rttv.malum.util.particle.screen.emitter.ItemParticleEmitter;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.awt.*;

public class EtherBlockItem extends BlockItem implements DyeableItem, ItemParticleEmitter {
    public EtherBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void setColor(ItemStack stack, int color) {
        stack.getOrCreateSubNbt("display").putInt("FirstColor", color);
    }

    @Override
    public boolean hasColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt("display");
        return nbtCompound != null && nbtCompound.contains("FirstColor", 99);
    }

    @Override
    public int getColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt("display");
        return nbtCompound != null && nbtCompound.contains("FirstColor", 99) ? nbtCompound.getInt("FirstColor") : 15712278;
    }

    public int getFirstColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt("display");
        return nbtCompound != null && nbtCompound.contains("FirstColor", 99) ? nbtCompound.getInt("FirstColor") : 15712278;
    }


    public int getSecondColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt("display");
        return nbtCompound != null && nbtCompound.contains("SecondColor", 99) ? nbtCompound.getInt("SecondColor") : 4607909;
    }

    @Override
    public void removeColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt("display");
        if (nbtCompound != null) {
            if (nbtCompound.contains("FirstColor")) {
                nbtCompound.remove("FirstColor");
            }
            if (nbtCompound.contains("SecondColor")) {
                nbtCompound.remove("SecondColor");
            }
        }
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = new ItemStack(this);
        NbtCompound nbt = new NbtCompound();
        NbtCompound nbtDisplay = new NbtCompound();
        nbtDisplay.putInt("FirstColor", 15712278);
        nbtDisplay.putInt("SecondColor", 4607909);
        nbt.put("display", nbtDisplay);
        stack.setNbt(nbt);
        return stack;
    }

    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        World world = MinecraftClient.getInstance().world;
        float gameTime = world.getTime() + MinecraftClient.getInstance().getTickDelta();
        EtherBlockItem etherItem = (EtherBlockItem) stack.getItem();
        Color firstColor = new Color(etherItem.getFirstColor(stack));
        Color secondColor = new Color(etherItem.getSecondColor(stack));
        ParticleBuilders.create(MalumScreenParticleRegistry.STAR)
                .setAlpha(0.05f, 0f)
                .setLifetime(6)
                .setScale((float) (1.5f + Math.sin(gameTime * 0.1f) * 0.125f), 0)
                .setColor(firstColor, secondColor)
                .setColorCurveMultiplier(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset(0.025f * gameTime % 6.28f)
                .setSpin(0, 1)
                .setSpinEasing(Easing.EXPO_IN_OUT)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack, 0, 3)
                .repeat(x, y, 1)
                .setScale((float) (1.4f - Math.sin(gameTime * 0.075f) * 0.125f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f-0.01f * gameTime % 6.28f)
                .repeat(x, y, 1);
    }
}
