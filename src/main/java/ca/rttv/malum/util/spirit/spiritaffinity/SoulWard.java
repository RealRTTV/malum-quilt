package ca.rttv.malum.util.spirit.spiritaffinity;

import ca.rttv.malum.client.init.MalumScreenParticleRegistry;
import ca.rttv.malum.client.init.MalumShaderRegistry;
import ca.rttv.malum.component.MalumComponents;
import ca.rttv.malum.component.MalumPlayerComponent;
import ca.rttv.malum.config.CommonConfig;
import ca.rttv.malum.duck.SoulWardDuck;
import ca.rttv.malum.registry.MalumAttributeRegistry;
import ca.rttv.malum.registry.MalumRegistry;
import ca.rttv.malum.registry.MalumSoundRegistry;
import ca.rttv.malum.registry.SpiritTypeRegistry;
import ca.rttv.malum.util.handler.ScreenParticleHandler;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.util.helper.ItemHelper;
import ca.rttv.malum.util.helper.RenderHelper;
import ca.rttv.malum.util.particle.ParticleBuilders;
import ca.rttv.malum.util.particle.screen.base.ScreenParticle;
import ca.rttv.malum.util.spirit.MalumSpiritAffinity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Shader;
import net.minecraft.client.util.Window;
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

public class SoulWard extends MalumSpiritAffinity {
    public SoulWard() {
        super(SpiritTypeRegistry.ARCANE_SPIRIT);
    }
// TODO: this
//
//    public static void recoverSoulWard(PlayerEntity player) {
//        PlayerDataCapability.getCapability(player).ifPresent(c -> {
//            AttributeInstance cap = player.getAttribute(AttributeRegistry.SOUL_WARD_CAP.get());
//            if (cap != null) {
//                if (c.soulWard < cap.getValue() && c.soulWardProgress <= 0) {
//                    c.soulWard++;
//                    if (player.level.isClientSide && !player.isCreative()) {
//                        player.playSound(c.soulWard >= cap.getValue() ? SoundRegistry.SOUL_WARD_CHARGE.get() : SoundRegistry.SOUL_WARD_GROW.get(), 1, Mth.nextFloat(player.getRandom(), 0.6f, 1.4f));
//                    }
//                    c.soulWardProgress = getSoulWardCooldown(player);
//                } else {
//                    c.soulWardProgress--;
//                }
//                if (c.soulWard > cap.getValue()) {
//                    c.soulWard = (float) cap.getValue();
//                }
//            }
//        });
//    }

    //use this in a ModifyVariable for the damage
    public static void consumeSoulWard(LivingEntity entity, DamageSource source, float amount) {
        if (entity instanceof PlayerEntity player) {
            if (!player.world.isClient) {
                MalumPlayerComponent component = MalumComponents.PLAYER_COMPONENT.get(player);
                    EntityAttributeInstance instance = player.getAttributeInstance(MalumAttributeRegistry.SOUL_WARD_SHATTER_COOLDOWN);
                    if (instance != null) {
                        soulWardProgress = (float) (CommonConfig.SOUL_WARD_RATE * 6 * Math.exp(-0.15 * instance.getValue()));
                        if (soulWard > 0) {
                            float multiplier = source.isMagic() ? CommonConfig.SOUL_WARD_MAGIC.floatValue() : CommonConfig.SOUL_WARD_PHYSICAL.floatValue();
                            float result = amount * multiplier;
                            float absorbed = amount - result;
                            double strength = player.getAttributeValue(MalumAttributeRegistry.SOUL_WARD_STRENGTH);
                            if (strength != 0) {
                                soulWard = (float) Math.max(0, soulWard - (absorbed / strength));
                            } else {
                                soulWard = 0;
                            }

                            player.world.playSound(null, player.getBlockPos(), MalumSoundRegistry.SOUL_WARD_HIT, SoundCategory.PLAYERS, 1, MathHelper.nextFloat(player.getRandom(), 1.5f, 2f));
                            setAmount(result);
                            if (source.getAttacker() != null) {
                                if (ItemHelper.hasTrinket(player, MalumRegistry.MAGEBANE_BELT)) {
                                    if (source instanceof EntityDamageSource entityDamageSource) {
                                        if (entityDamageSource.isThorns()) {
                                            return;
                                        }
                                    }
                                    source.getAttacker().damage(DamageSourceRegistry.causeMagebaneDamage(player), absorbed + 2);
                                }
                            }
                            MalumComponents.PLAYER_COMPONENT.sync(player);
                        }
                    }
            }
        }
    }

    public static int getSoulWardCooldown(PlayerEntity player) {
        return (int) (CommonConfig.SOUL_WARD_RATE * Math.exp(-0.15 * player.getAttributeValue(MalumAttributeRegistry.SOUL_WARD_RECOVERY_SPEED)));
    }

    @Environment(EnvType.CLIENT)
    public static class Client {
        private static final Identifier ICONS_TEXTURE = DataHelper.prefix("textures/gui/icons.png");

        public static void renderSoulWard(MatrixStack matrices, Window window) {
            final MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity player = client.player;
            if (!player.isCreative() && !player.isSpectator()) {
                float soulWard = ((SoulWardDuck) player).getSoulWard();
                    if (soulWard > 0) {
                        float absorb = MathHelper.ceil(player.getAbsorptionAmount());
                        float maxHealth = (float) player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue();
                        float armor = (float) player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).getValue();

                        int left = window.getScaledWidth() / 2 - 91;
                        int top = window.getScaledHeight() - 39;

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
                        Shader shader = MalumShaderRegistry.DISTORTED_TEXTURE.getInstance().get();
                        shader.getUniformOrDefault("YFrequency").set(15f);
                        shader.getUniformOrDefault("XFrequency").set(15f);
                        shader.getUniformOrDefault("Speed").set(550f);
                        shader.getUniformOrDefault("Intensity").set(600f);
                        for (int i = 0; i < Math.ceil(soulWard / 3f); i++) {
                            int row = (int) (Math.ceil(i) / 10f);
                            int x = left + i % 10 * 8;
                            int y = top - row * 4 + rowHeight * 2 - 15;
                            int progress = Math.min(3, (int) soulWard - i * 3);
                            int xTextureOffset = 1 + (3 - progress) * 15;

                            shader.getUniformOrDefault("UVCoordinates").set(new Vector4f(xTextureOffset / 256f, (xTextureOffset + 12) / 256f, 16 / 256f, 28 / 256f));
                            shader.getUniformOrDefault("TimeOffset").set(i * 150f);

                            RenderHelper.blit(matrices, MalumShaderRegistry.DISTORTED_TEXTURE, x - 2, y - 2, 13, 13, 1, 1, 1, 1, xTextureOffset, 16, 256f);

                            if (ScreenParticleHandler.canSpawnParticles) {
                                ParticleBuilders.create(MalumScreenParticleRegistry.WISP)
                                        .setLifetime(20)
                                        .setColor(SpiritTypeRegistry.ARCANE_SPIRIT_COLOR, SpiritTypeRegistry.ARCANE_SPIRIT.endColor)
                                        .setAlphaCurveMultiplier(0.75f)
                                        .setScale(0.2f*progress, 0f)
                                        .setAlpha(0.05f, 0)
                                        .setSpin(MinecraftClient.getInstance().world.random.nextFloat() * 6.28f)
                                        .setSpinOffset(MinecraftClient.getInstance().world.random.nextFloat() * 6.28f)
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
