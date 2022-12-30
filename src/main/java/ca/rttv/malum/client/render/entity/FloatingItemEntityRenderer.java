package ca.rttv.malum.client.render.entity;

import ca.rttv.malum.entity.FloatingItemEntity;
import ca.rttv.malum.util.helper.DataHelper;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.lodestone.helpers.ColorHelper;
import com.sammy.lodestone.helpers.EntityHelper;
import com.sammy.lodestone.setup.LodestoneRenderLayers;
import com.sammy.lodestone.systems.rendering.VFXBuilders;
import com.sammy.lodestone.systems.rendering.particle.Easing;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector4f;
import org.quiltmc.qsl.entity.event.mixin.client.ClientWorldMixin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sammy.lodestone.handlers.RenderHandler.DELAYED_RENDER;
import static com.sammy.lodestone.setup.LodestoneRenderLayers.queueUniformChanges;

public class FloatingItemEntityRenderer extends EntityRenderer<FloatingItemEntity> {
    private static final Identifier LIGHT_TRAIL = DataHelper.prefix("textures/vfx/light_trail.png");
    private static final RenderLayer LIGHT_TYPE = LodestoneRenderLayers.ADDITIVE_TEXTURE.apply(LIGHT_TRAIL);
    private static final Identifier MESSY_TRAIL = DataHelper.prefix("textures/vfx/messy_trail.png");
    private static final RenderLayer MESSY_TYPE = LodestoneRenderLayers.SCROLLING_TEXTURE_TRIANGLE.apply(MESSY_TRAIL);
    public final ItemRenderer itemRenderer;

    public FloatingItemEntityRenderer(EntityRendererFactory.Context context)
    {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowOpacity = 0;
    }


    @Override
    public void render(FloatingItemEntity entity, float entityYaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider provider, int packedLightIn) {
        matrixStack.push();
        List<EntityHelper.PastPosition> positions = new ArrayList<>(entity.pastPositions);
        if (positions.size() > 1) {
            for (int i = 0; i < positions.size() - 2; i++) {
                EntityHelper.PastPosition position = positions.get(i);
                EntityHelper.PastPosition nextPosition = positions.get(i + 1);
                float x = (float) MathHelper.lerp(tickDelta, position.position.x, nextPosition.position.x);
                float y = (float) MathHelper.lerp(tickDelta, position.position.y, nextPosition.position.y);
                float z = (float) MathHelper.lerp(tickDelta, position.position.z, nextPosition.position.z);
                positions.set(i, new EntityHelper.PastPosition(new Vec3d(x, y, z), position.time));
            }
        }
        float x = (float) MathHelper.lerp(tickDelta, entity.prevX, entity.getX());
        float y = (float) MathHelper.lerp(tickDelta, entity.prevY, entity.getY());
        float z = (float) MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ());
        if (positions.size() > 1) {
            positions.set(positions.size() - 1, new EntityHelper.PastPosition(new Vec3d(x, y + entity.getYOffset(tickDelta) + 0.25F, z).add(entity.getVelocity().multiply(tickDelta, tickDelta, tickDelta)), 0));
        }

        List<Vector4f> mappedPastPositions = positions.stream().map(p -> p.position).map(p -> new Vector4f((float) p.x, (float) p.y, (float) p.z, 1)).collect(Collectors.toList());
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setOffset(-x, -y, -z);

        VertexConsumer lightBuffer = DELAYED_RENDER.getBuffer(LIGHT_TYPE);

        for (int i = 0; i < 3; i++) {
            float size = 0.225f + i * 0.15f;
            float alpha = (0.3f - i * 0.12f);
            int finalI = i;
            VertexConsumer messy = DELAYED_RENDER.getBuffer(queueUniformChanges(LodestoneRenderLayers.copy(i, MESSY_TYPE),
                (instance -> instance.getUniformOrDefault("Speed").setFloat(1000 + 250f * finalI))));
            builder
                .setAlpha(alpha)
                .renderTrail(messy, matrixStack, mappedPastPositions, f -> size, f -> builder.setAlpha(alpha * f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 3f, entity.endColor, entity.color)))
                .renderTrail(messy, matrixStack, mappedPastPositions, f -> 1.5f * size, f -> builder.setAlpha(alpha * f / 2f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 2f, entity.endColor, entity.color)))
                .renderTrail(lightBuffer, matrixStack, mappedPastPositions, f -> size * 2.5f, f -> builder.setAlpha(alpha * f / 4f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 2f, entity.endColor, entity.color)));
        }
        ItemStack itemStack = entity.getItem();
        BakedModel model = this.itemRenderer.getHeldItemModel(itemStack, entity.world, null, entity.getItem().getCount());
        float yOffset = entity.getYOffset(tickDelta);
        float scale = model.getTransformation().getTransformation(ModelTransformation.Mode.GROUND).scale.y();
        float rotation = entity.getRotation(tickDelta);
        matrixStack.translate(0.0D, (yOffset + 0.25F * scale), 0.0D);
        matrixStack.multiply(new Quaternionf().rotateY(rotation*((float)Math.PI/180F)));
        this.itemRenderer.renderItem(itemStack, ModelTransformation.Mode.GROUND, false, matrixStack, provider, packedLightIn, OverlayTexture.DEFAULT_UV, model);
        matrixStack.pop();
        matrixStack.push();
        renderSpirit(entity, itemRenderer, tickDelta, matrixStack, provider, packedLightIn);
        matrixStack.pop();
        super.render(entity, entityYaw, tickDelta, matrixStack, provider, packedLightIn);
    }

    public static void renderSpirit(FloatingItemEntity entity, ItemRenderer itemRenderer, float partialTicks, MatrixStack poseStack, VertexConsumerProvider bufferIn, int packedLightIn) {
        ItemStack itemStack = entity.getItem();
        BakedModel model = itemRenderer.getHeldItemModel(itemStack, entity.world, null, entity.getItem().getCount());
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setColor(entity.color);
        float yOffset = entity.getYOffset(partialTicks);
        float scale = model.getTransformation().getTransformation(ModelTransformation.Mode.GROUND).scale.y();
        float rotation = entity.getRotation(partialTicks);
        poseStack.push();
        poseStack.translate(0.0D, (yOffset + 0.25F * scale), 0.0D);
        poseStack.multiply(new Quaternionf().rotateY(rotation*((float)Math.PI/180F)));
        itemRenderer.renderItem(itemStack, ModelTransformation.Mode.GROUND, false, poseStack, bufferIn, packedLightIn, OverlayTexture.DEFAULT_UV, model);
        poseStack.pop();
        poseStack.push();
        poseStack.translate(0.0D, (yOffset + 0.5F * scale), 0.0D);
        renderSpiritGlimmer(poseStack, builder, partialTicks);
        poseStack.pop();
    }

    public static void renderSpiritGlimmer(MatrixStack poseStack, VFXBuilders.WorldVFXBuilder builder, float partialTicks) {
        ClientWorld level = MinecraftClient.getInstance().world;
        float v = level.getTime() + partialTicks;
        float time = (float) ((Math.sin(v) + v % 15f) / 15f);
        if (time >= 0.5f) {
            time = 1f - time;
        }
        float multiplier = 1 + Easing.BOUNCE_IN_OUT.ease(time*2f, 0, 0.25f, 1);
        poseStack.push();
        poseStack.multiply(MinecraftClient.getInstance().getEntityRenderDispatcher().getRotation());
        poseStack.multiply(new Quaternionf().rotateY((float)Math.PI));

        builder.setOffset(0, 0, 0);
        for (int i = 0; i < 3; i++) {
            float size = (0.125f + i * 0.13f) * multiplier;
            float alpha = (0.75f - i * 0.3f);
            builder.setAlpha(alpha * 0.6f).renderQuad(DELAYED_RENDER.getBuffer(LodestoneRenderLayers.ADDITIVE_TEXTURE.applyAndCache(DataHelper.prefix("textures/particle/wisp.png"))), poseStack, size * 0.75f);
            builder.setAlpha(alpha).renderQuad(DELAYED_RENDER.getBuffer(LodestoneRenderLayers.ADDITIVE_TEXTURE.applyAndCache(DataHelper.prefix("textures/particle/twinkle.png"))), poseStack, size);
        }
        poseStack.pop();
    }

    @Override
    public Identifier getTexture(FloatingItemEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
