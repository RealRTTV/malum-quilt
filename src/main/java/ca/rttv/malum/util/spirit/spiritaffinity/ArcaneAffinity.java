package ca.rttv.malum.util.spirit.spiritaffinity;

import ca.rttv.malum.client.init.MalumScreenParticleRegistry;
import ca.rttv.malum.client.init.MalumShaderRegistry;
import ca.rttv.malum.component.MalumComponents;
import ca.rttv.malum.component.MalumPlayerComponent;
import ca.rttv.malum.config.CommonConfig;
import ca.rttv.malum.registry.MalumAttributeRegistry;
import ca.rttv.malum.registry.MalumDamageSourceRegistry;
import ca.rttv.malum.registry.MalumSoundRegistry;
import ca.rttv.malum.util.handler.ScreenParticleHandler;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.util.helper.ItemHelper;
import ca.rttv.malum.util.helper.RenderHelper;
import ca.rttv.malum.util.particle.ParticleBuilders;
import ca.rttv.malum.util.particle.screen.base.ScreenParticle;
import ca.rttv.malum.util.spirit.MalumSpiritAffinity;
import com.mojang.blaze3d.glfw.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vector4f;

import java.awt.*;
import java.util.Objects;

import static ca.rttv.malum.registry.MalumItemRegistry.MAGEBANE_BELT;
import static ca.rttv.malum.util.spirit.SpiritType.ARCANE_SPIRIT;

public class ArcaneAffinity extends MalumSpiritAffinity {
    public ArcaneAffinity() {
        super(ARCANE_SPIRIT);
    }

    public static void recoverSoulWard(PlayerEntity player) {
        MalumPlayerComponent component = MalumComponents.PLAYER_COMPONENT.get(player);
        EntityAttributeInstance cap = player.getAttributeInstance(MalumAttributeRegistry.SOUL_WARD_CAP);
        if (cap != null) {
            if (component.soulWard < cap.getValue() && component.soulWardProgress <= 0) {
                component.soulWard++;
                if (player.world.isClient && !player.isCreative()) {
                    player.playSound(component.soulWard >= cap.getValue() ? MalumSoundRegistry.SOUL_WARD_CHARGE : MalumSoundRegistry.SOUL_WARD_GROW, 1, MathHelper.nextFloat(player.getRandom(), 0.6f, 1.4f));
                }
                component.soulWardProgress = getSoulWardCooldown(player);
            } else {
                component.soulWardProgress--;
            }
            if (component.soulWard > cap.getValue()) {
                component.soulWard = (float) cap.getValue();
            }
        }
    }

    public static float consumeSoulWard(LivingEntity entity, DamageSource source, float amount) {
        if (entity instanceof PlayerEntity player) {
            if (!player.world.isClient) {
                MalumPlayerComponent component = MalumComponents.PLAYER_COMPONENT.get(player);
                EntityAttributeInstance instance = player.getAttributeInstance(MalumAttributeRegistry.SOUL_WARD_SHATTER_COOLDOWN);
                if (instance != null) {
                    component.soulWardProgress = (float) (CommonConfig.SOUL_WARD_RATE * 6 * Math.exp(-0.15 * instance.getValue()));
                    if (component.soulWard > 0) {
                        float multiplier = source.isMagic() ? CommonConfig.SOUL_WARD_MAGIC : CommonConfig.SOUL_WARD_PHYSICAL;
                        float result = amount * multiplier;
                        float absorbed = amount - result;
                        double strength = player.getAttributeValue(MalumAttributeRegistry.SOUL_WARD_STRENGTH);
                        if (strength != 0) {
                            component.soulWard = (float) Math.max(0, component.soulWard - (absorbed / strength));
                        } else {
                            component.soulWard = 0;
                        }

                        player.world.playSound(null, player.getBlockPos(), MalumSoundRegistry.SOUL_WARD_HIT, SoundCategory.PLAYERS, 1, MathHelper.nextFloat(player.getRandom(), 1.5f, 2f));

                        if (source.getAttacker() != null) {
                            if (ItemHelper.hasTrinket(player, MAGEBANE_BELT)) {
                                if (!(source instanceof EntityDamageSource entityDamageSource) || (!entityDamageSource.isThorns())) {
                                    source.getAttacker().damage(MalumDamageSourceRegistry.causeMagebaneDamage(player), absorbed + 2);
                                }
                            }
                        }
                        MalumComponents.PLAYER_COMPONENT.sync(player);
                        return result;
                    }
                }
            }

        }
        return amount;
    }

    public static int getSoulWardCooldown(PlayerEntity player) {
        return (int) (CommonConfig.SOUL_WARD_RATE * Math.exp(-0.15 * player.getAttributeValue(MalumAttributeRegistry.SOUL_WARD_RECOVERY_SPEED)));
    }

        public static class Client {
        private static final Identifier ICONS_TEXTURE = DataHelper.prefix("textures/gui/soul_ward/trans_pride.png");

        public static void renderSoulWard(MatrixStack matrices, Window window) {
            final MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity player = client.player;
            if (player != null && client.world != null && !player.isCreative() && !player.isSpectator()) {
                float soulWard = MalumComponents.PLAYER_COMPONENT.get(player).soulWard;
                    if (soulWard > 0) {
                        float absorb = MathHelper.ceil(player.getAbsorptionAmount());
                        float maxHealth = (float) Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).getValue();
                        float armor = (float) Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR)).getValue();

                        int left = window.getScaledWidth() / 2 - 91;
                        int top = window.getScaledHeight() - 59;

                        if (armor == 0) {
                            top += 4;
                        }
                        int healthRows = MathHelper.ceil((maxHealth + absorb) / 2.0F / 10.0F);
                        int rowHeight = Math.max(10 - (healthRows - 2), 3);

                        matrices.push();
                        RenderSystem.depthMask(false);
                        RenderSystem.enableBlend();
                        RenderSystem.defaultBlendFunc();
                        RenderSystem.setShaderTexture(0, ICONS_TEXTURE);
                        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                        ShaderProgram shader = MalumShaderRegistry.DISTORTED_TEXTURE.getInstance().get();
                        shader.getUniformOrDefault("YFrequency").setFloat(15f);
                        shader.getUniformOrDefault("XFrequency").setFloat(15f);
                        shader.getUniformOrDefault("Speed").setFloat(550f);
                        shader.getUniformOrDefault("Intensity").setFloat(150f);
                        for (int i = 0; i < Math.ceil(soulWard / 3f); i++) {
                            int row = (int) (Math.ceil(i) / 10f);
                            int x = left + i % 10 * 8;
                            int y = top - row * 4 + rowHeight * 2 - 15;
                            int progress = Math.min(3, (int) soulWard - i * 3);
                            int xTextureOffset = 1 + (3 - progress) * 15;

                            shader.getUniformOrDefault("UVCoordinates").setVec4(new Vector4f(xTextureOffset / 45f, (xTextureOffset + 12) / 45f, 1 / 45f, 13 / 45f));
                            shader.getUniformOrDefault("TimeOffset").setFloat(i * 150f);

                            RenderHelper.blit(matrices, MalumShaderRegistry.DISTORTED_TEXTURE, x - 2, y - 2, 13, 13, 1, 1, 1, 1, xTextureOffset, 1, 45f);

                            if (ScreenParticleHandler.canSpawnParticles) {
                                ParticleBuilders.create(MalumScreenParticleRegistry.WISP)
                                        .setLifetime(20)
                                        .setColor(ARCANE_SPIRIT.color, ARCANE_SPIRIT.endColor)
                                        .setAlphaCurveMultiplier(0.75f)
                                        .setScale(0.2f*progress, 0f)
                                        .setAlpha(0.05f, 0)
                                        .setSpin(client.world.random.nextFloat() * 6.28f)
                                        .setSpinOffset(client.world.random.nextFloat() * 6.28f)
                                        .randomOffset(2)
                                        .randomMotion(0.5f, 0.5f)
                                        .overwriteRenderOrder(ScreenParticle.RenderOrder.BEFORE_UI)
                                        .repeat(x + 5, y + 5, 1);
                            }
                        }
                        RenderSystem.depthMask(true);
                        RenderSystem.disableBlend();
                        matrices.pop();
                }
            }
        }
    }
}
