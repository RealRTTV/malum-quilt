package ca.rttv.malum.item;

import ca.rttv.malum.client.init.MalumScreenParticleRegistry;
import ca.rttv.malum.util.helper.ColorHelper;
import ca.rttv.malum.util.helper.SpiritHelper;
import ca.rttv.malum.util.particle.Easing;
import ca.rttv.malum.util.particle.ParticleBuilders;
import ca.rttv.malum.util.particle.screen.base.ScreenParticle;
import ca.rttv.malum.util.particle.screen.emitter.ItemParticleEmitter;
import ca.rttv.malum.util.spirit.SpiritType;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.awt.*;

import static ca.rttv.malum.registry.MalumAttributeRegistry.MAGIC_PROFICIENCY;
import static ca.rttv.malum.registry.MalumItemRegistry.NECKLACE_OF_THE_MYSTIC_MIRROR;

public class TyrvingItem extends SwordItem implements ItemParticleEmitter {
    public TyrvingItem(ToolMaterial material, float damage, float speed, Item.Settings settings) {
        super(material, (int) damage, speed, settings);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return true;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int lastDamageTaken = (int) target.lastDamageTaken;
        target.lastDamageTaken = 0;
        target.damage(DamageSource.MAGIC, (float) (SpiritHelper.getSpiritItemStacks(target).stream().mapToInt(ItemStack::getCount).reduce(0, Integer::sum) + 0.5f * attacker.getAttributeValue(MAGIC_PROFICIENCY)));
        target.lastDamageTaken += lastDamageTaken;
        if ((TrinketsApi.getTrinketComponent(attacker).orElseThrow().isEquipped(NECKLACE_OF_THE_MYSTIC_MIRROR))) {
            TrinketsApi.getTrinketComponent(attacker).orElseThrow().forEach((slot, trinket) -> {
                if (trinket.getItem() instanceof SpiritCollectActivity spiritCollectActivity) {
                    spiritCollectActivity.collect(stack, attacker, slot, trinket);
                }
            });
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        final MinecraftClient client = MinecraftClient.getInstance();
        World world = client.world;
        float gameTime = world.getTime() + client.getTickDelta();
        Color firstColor = SpiritType.ELDRITCH_SPIRIT.color;
        Color secondColor = ColorHelper.darker(SpiritType.ELDRITCH_SPIRIT.color, 2);
        ParticleBuilders.create(MalumScreenParticleRegistry.STAR)
                .setAlpha(0.03f, 0f)
                .setLifetime(8)
                .setScale((float) (0.75f + Math.sin(gameTime * 0.05f) * 0.15f), 0)
                .setColor(firstColor, secondColor)
                .setColorCurveMultiplier(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset(0.025f * gameTime % 6.28f)
                .setSpin(0, 1)
                .setSpinEasing(Easing.EXPO_IN_OUT)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack, -2, 2)
                .repeat(x, y, 1)
                .setScale((float) (0.75f - Math.sin(gameTime * 0.075f) * 0.15f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f - 0.01f * gameTime % 6.28f)
                .repeat(x, y, 1)
                .setScale((float) (0.9f - Math.sin(gameTime * 0.1f) * 0.175f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.8f - 0.01f * gameTime % 6.28f)
                .repeat(x, y, 1);
        gameTime += 31.4f;
        ParticleBuilders.create(MalumScreenParticleRegistry.STAR)
                .setAlpha(0.028f, 0f)
                .setLifetime(8)
                .setScale((float) (0.75f + Math.sin(gameTime * 0.05f) * 0.125f), 0)
                .setColor(firstColor, secondColor)
                .setColorCurveMultiplier(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset(0.025f * gameTime % 6.28f)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack, 3, -3)
                .repeat(x, y, 1)
                .setScale((float) (0.85f - Math.sin(gameTime * 0.075f) * 0.15f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f - 0.01f * gameTime % 6.28f)
                .repeat(x, y, 1)
                .setScale((float) (0.95f - Math.sin(gameTime * 0.1f) * 0.175f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.8f - 0.01f * gameTime % 6.28f)
                .repeat(x, y, 1);
    }
}
