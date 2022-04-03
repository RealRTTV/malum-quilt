package ca.rttv.malum.util;

import ca.rttv.malum.registry.SpiritTypeRegistry;
import ca.rttv.malum.util.spirit.MalumEntitySpiritData;
import ca.rttv.malum.util.spirit.MalumSpiritType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import static net.minecraft.util.math.MathHelper.nextFloat;

public class SpiritHelper {
//    public static void createSpiritsFromSoul(MalumEntitySpiritData data, World world, Vec3d position, LivingEntity attacker) {
//        ArrayList<ItemStack> spirits = getSpiritItemStacks(data, attacker, ItemStack.EMPTY, 2);
//        createSpiritEntities(spirits, data.totalCount, world, position, attacker);
//    }

//    public static void createSpiritsFromWeapon(LivingEntity target, LivingEntity attacker, ItemStack harvestStack) {
//        ArrayList<ItemStack> spirits = getSpiritItemStacks(target, attacker, harvestStack, 1);
//        createSpiritEntities(spirits, target, attacker);
//    }

//    public static void createSpiritEntities(LivingEntity target, LivingEntity attacker) {
//        ArrayList<ItemStack> spirits = getSpiritItemStacks(target);
//        if (!spirits.isEmpty()) {
//            createSpiritEntities(spirits, target, attacker);
//        }
//    }

//    public static void createSpiritEntities(LivingEntity target) {
//        ArrayList<ItemStack> spirits = getSpiritItemStacks(target);
//        if (!spirits.isEmpty()) {
//            createSpiritEntities(spirits, target, null);
//        }
//    }

//    public static void createSpiritEntities(ArrayList<ItemStack> spirits, LivingEntity target, @Nullable LivingEntity attacker) {
//        if (spirits.isEmpty()) {
//            return;
//        }
//        createSpiritEntities(spirits, spirits.stream().mapToInt(ItemStack::getCount).sum(), target.world, target.getPos().add(0, target.getEyeHeight() / 2f, 0), attacker);
//    }

//    public static void createSpiritEntities(MalumEntitySpiritData data, World world, Vec3d position, LivingEntity attacker) {
//        createSpiritEntities(getSpiritItemStacks(data), data.totalCount, world, position, attacker);
//    }

//    public static void createSpiritEntities(ArrayList<ItemStack> spirits, float totalCount, World world, Vec3d position, @Nullable LivingEntity attacker) {
//        if (attacker == null) {
//            attacker = world.getClosestPlayer(position.x, position.y, position.z, 8, e -> true);
//        }
//        float speed = 0.1f + 0.2f / (totalCount + 1);
//        for (ItemStack stack : spirits) {
//            int count = stack.getCount();
//            if (count == 0) {
//                continue;
//            }
//            for (int j = 0; j < count; j++) {
//                SpiritItemEntity entity = new SpiritItemEntity(world, attacker == null ? null : attacker.getUUID(), ItemHelper.copyWithNewCount(stack, 1),
//                        position.x,
//                        position.y,
//                        position.z,
//                        nextFloat(MalumMod.RANDOM, -speed, speed),
//                        nextFloat(MalumMod.RANDOM, 0.015f, 0.05f),
//                        nextFloat(MalumMod.RANDOM, -speed, speed));
//                world.addFreshEntity(entity);
//            }
//        }
//        world.playSound(null, position.x, position.y, position.z, SoundRegistry.SPIRIT_HARVEST.get(), SoundSource.PLAYERS, 1.0F, 0.7f + world.random.nextFloat() * 0.4f);
//    }


    public static MalumSpiritType getSpiritType(String spirit) {
        Optional<MalumSpiritType> type = SpiritTypeRegistry.SPIRITS.stream().filter(s -> s.identifier.equals(spirit)).findFirst();
        if (type.isEmpty()) {
            return SpiritTypeRegistry.ELDRITCH_SPIRIT;
        }
        return type.get();
    }

//    public static MalumEntitySpiritData getEntitySpiritData(LivingEntity entity) {
//        return SpiritDataReloadListener.SPIRIT_DATA.get(entity.getType().getRegistryName());
//    }

//    public static int getEntitySpiritCount(LivingEntity entity) {
//        MalumEntitySpiritData bundle = getEntitySpiritData(entity);
//        if (bundle == null) {
//            return 0;
//        }
//        return bundle.totalCount;
//    }

//    public static ArrayList<ItemStack> getSpiritItemStacks(LivingEntity entity, LivingEntity attacker, ItemStack harvestStack, float spoilsMultiplier) {
//        return getSpiritItemStacks(getEntitySpiritData(entity), attacker, harvestStack, spoilsMultiplier);
//    }

//    public static ArrayList<ItemStack> getSpiritItemStacks(MalumEntitySpiritData data, LivingEntity attacker, ItemStack harvestStack, float spoilsMultiplier) {
//        ArrayList<ItemStack> spirits = getSpiritItemStacks(data);
//        if (spirits.isEmpty()) {
//            return spirits;
//        }
//        int spiritSpoils = (int) attacker.getAttributeValue(AttributeRegistry.SPIRIT_SPOILS.get());
//        if (!harvestStack.isEmpty()) {
//            int spiritPlunder = EnchantmentHelper.getLevel(MalumEnchantments.SPIRIT_PLUNDER.get(), harvestStack);
//            if (spiritPlunder > 0) {
//                harvestStack.hurtAndBreak(spiritPlunder, attacker, (e) -> e.broadcastBreakEvent(MAINHAND));
//            }
//            spiritSpoils += spiritPlunder;
//        }
//        for (int i = 0; i < spiritSpoils * spoilsMultiplier; i++) {
//            int random = attacker.world.random.nextInt(spirits.size());
//            spirits.get(random).grow(1);
//        }
//        return spirits;
//    }

//    public static ArrayList<ItemStack> getSpiritItemStacks(LivingEntity entity) {
//        return getSpiritItemStacks(getEntitySpiritData(entity));
//    }

//    public static ArrayList<ItemStack> getSpiritItemStacks(MalumEntitySpiritData data) {
//        ArrayList<ItemStack> spirits = new ArrayList<>();
//        if (data == null) {
//            return spirits;
//        }
//        for (MalumEntitySpiritData.SpiritDataEntry spiritDataEntry : data.dataEntries) {
//            spirits.add(new ItemStack(spiritDataEntry.type.getSplinterItem(), spiritDataEntry.count));
//        }
//        return spirits;
//    }

//    public static void spawnSpiritParticles(World world, double x, double y, double z, Color color, Color endColor) {
//        spawnSpiritParticles(world, x, y, z, 1, Vec3d.ZERO, color, endColor);
//    }

//    public static void spawnSpiritParticles(World world, double x, double y, double z, float alphaMultiplier, Vec3d extraVelocity, Color color, Color endColor) {
//        Random rand = world.getRandom();
//        ParticleBuilders.create(ParticleRegistry.TWINKLE_PARTICLE)
//                .setAlpha(0.21f * alphaMultiplier, 0f)
//                .setLifetime(10 + rand.nextInt(4))
//                .setScale(0.3f + rand.nextFloat() * 0.1f, 0)
//                .setColor(color, endColor)
//                .setColorCurveMultiplier(2f)
//                .randomOffset(0.05f)
//                .enableNoClip()
//                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
//                .randomMotion(0.02f, 0.02f)
//                .repeat(world, x, y, z, 1);
//
//        ParticleBuilders.create(ParticleRegistry.WISP_PARTICLE)
//                .setAlpha(0.1f * alphaMultiplier, 0f)
//                .setLifetime(20 + rand.nextInt(4))
//                .setSpin(nextFloat(rand, 0.05f, 0.1f))
//                .setScale(0.2f + rand.nextFloat() * 0.05f, 0)
//                .setColor(color, endColor)
//                .setColorCurveMultiplier(1.25f)
//                .randomOffset(0.1f)
//                .enableNoClip()
//                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
//                .randomMotion(0.02f, 0.02f)
//                .repeat(world, x, y, z, 1)
//                .setAlpha(0.2f * alphaMultiplier, 0f)
//                .setLifetime(10 + rand.nextInt(2))
//                .setSpin(nextFloat(rand, 0.05f, 0.1f))
//                .setScale(0.15f + rand.nextFloat() * 0.05f, 0f)
//                .randomMotion(0.01f, 0.01f)
//                .repeat(world, x, y, z, 1);
//    }

//    public static void spawnSoulParticles(World world, double x, double y, double z, Color color, Color endColor) {
//        spawnSoulParticles(world, x, y, z, 1, 1, Vec3d.ZERO, color, endColor);
//    }

//    public static void spawnSoulParticles(World world, double x, double y, double z, float alphaMultiplier, float scaleMultiplier, Vec3d extraVelocity, Color color, Color endColor) {
//        Random rand = world.getRandom();
//        ParticleBuilders.create(ParticleRegistry.WISP_PARTICLE)
//                .setAlpha(0.25f * alphaMultiplier, 0)
//                .setLifetime(8 + rand.nextInt(5))
//                .setScale((0.3f + rand.nextFloat() * 0.2f) * scaleMultiplier, 0)
//                .setColor(color, endColor)
//                .randomOffset(0.05f)
//                .enableNoClip()
//                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
//                .randomMotion(0.01f * scaleMultiplier, 0.01f * scaleMultiplier)
//                .repeat(world, x, y, z, 1);
//
//        ParticleBuilders.create(ParticleRegistry.SMOKE_PARTICLE)
//                .setAlpha(0.1f * alphaMultiplier, 0f)
//                .setLifetime(20 + rand.nextInt(10))
//                .setSpin(nextFloat(rand, 0.05f, 0.4f))
//                .setScale((0.2f + rand.nextFloat() * 0.1f) * scaleMultiplier, 0.1f * scaleMultiplier)
//                .setColor(color, endColor)
//                .randomOffset(0.1f)
//                .enableNoClip()
//                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
//                .randomMotion(0.04f * scaleMultiplier, 0.04f * scaleMultiplier)
//                .repeat(world, x, y, z, 1)
//                .setAlpha(0.12f * alphaMultiplier, 0f)
//                .setLifetime(10 + rand.nextInt(5))
//                .setSpin(nextFloat(rand, 0.1f, 0.5f))
//                .setScale((0.15f + rand.nextFloat() * 0.1f) * scaleMultiplier, 0.1f * scaleMultiplier)
//                .randomMotion(0.03f * scaleMultiplier, 0.03f * scaleMultiplier)
//                .repeat(world, x, y, z, 1);
//    }

//    public static void spawnSpiritScreenParticles(Color color, Color endColor, ItemStack stack, float pXPosition, float pYPosition, ScreenParticle.RenderOrder renderOrder) {
//        Random rand = MinecraftClient.getInstance().world.getRandom();
//        ParticleBuilders.create(ScreenParticleRegistry.TWINKLE)
//                .setAlpha(0.07f, 0f)
//                .setLifetime(10 + rand.nextInt(10))
//                .setScale(0.4f + rand.nextFloat(), 0)
//                .setColor(color, endColor)
//                .setColorCurveMultiplier(2f)
//                .randomOffset(0.05f)
//                .randomMotion(0.05f, 0.05f)
//                .overwriteRenderOrder(renderOrder)
//                .centerOnStack(stack)
//                .repeat(pXPosition, pYPosition, 1);
//
//        ParticleBuilders.create(ScreenParticleRegistry.WISP)
//                .setAlpha(0.01f, 0f)
//                .setLifetime(20 + rand.nextInt(8))
//                .setSpin(nextFloat(rand, 0.2f, 0.4f))
//                .setScale(0.6f + rand.nextFloat() * 0.4f, 0)
//                .setColor(color, endColor)
//                .setColorCurveMultiplier(1.25f)
//                .randomOffset(0.1f)
//                .randomMotion(0.4f, 0.4f)
//                .overwriteRenderOrder(renderOrder)
//                .centerOnStack(stack)
//                .repeat(pXPosition, pYPosition, 1)
//                .setLifetime(10 + rand.nextInt(2))
//                .setSpin(nextFloat(rand, 0.05f, 0.1f))
//                .setScale(0.8f + rand.nextFloat() * 0.4f, 0f)
//                .randomMotion(0.01f, 0.01f)
//                .repeat(pXPosition, pYPosition, 1);
//    }
}
