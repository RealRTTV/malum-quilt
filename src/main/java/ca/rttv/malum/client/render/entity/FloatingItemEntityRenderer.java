package ca.rttv.malum.client.render.entity;

import ca.rttv.malum.entity.FloatingItemEntity;
import ca.rttv.malum.util.RenderLayers;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.util.helper.RenderHelper;
import ca.rttv.malum.util.particle.Easing;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vector4f;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static ca.rttv.malum.util.handler.RenderHandler.DELAYED_RENDER;

public class FloatingItemEntityRenderer extends EntityRenderer<FloatingItemEntity> {
    private static final Identifier LIGHT_TRAIL = DataHelper.prefix("textures/vfx/light_trail.png");
    private static final RenderLayer LIGHT_TYPE = RenderLayers.ADDITIVE_TEXTURE.apply(LIGHT_TRAIL);
    public final ItemRenderer itemRenderer;

    public FloatingItemEntityRenderer(EntityRendererFactory.Context context)
    {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowOpacity = 0;
    }


    @Override
    public void render(FloatingItemEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, VertexConsumerProvider provider, int packedLightIn) {
        matrixStack.push();
                ArrayList<Vec3d> positions = new ArrayList<>(entity.pastPositions);
        RenderHelper.VertexBuilder builder = RenderHelper.create();

        int amount = 3;
        for (int i = 0; i < amount; i++) {
            float index = (amount - 1) - i;
            float size = index * 0.15f + (float) Math.exp(index * 0.3f);
            float alpha = 0.1f * (float) Math.exp(i * 0.3f);
            Color color = entity.color;
            builder.setColor(color).setOffset((float) -entity.getX(), (float) -entity.getY(), (float) -entity.getZ())
                    .setAlpha(alpha)
                    .renderTrail(
                            DELAYED_RENDER.getBuffer(LIGHT_TYPE),
                            matrixStack,
                            positions.stream()
                                     .map(p -> new Vector4f((float) p.x, (float) p.y, (float) p.z, 1))
                                     .toList(),
                            f -> f * size
                    )
                    .renderTrail(
                            DELAYED_RENDER.getBuffer(LIGHT_TYPE),
                            matrixStack,
                            positions.stream()
                                     .map(p -> new Vector4f((float) p.x, (float) p.y, (float) p.z, 1))
                                     .toList(),
                            f -> Easing.QUARTIC_IN_OUT.ease(f, 0, size, 1)
                    );
        }
        ItemStack itemStack = entity.getItem();
        BakedModel ibakedmodel = this.itemRenderer.getHeldItemModel(itemStack, entity.world, null, entity.getItem().getCount());
        float f1 = entity.getYOffset(partialTicks);
        float f2 = ibakedmodel.getTransformation().getTransformation(ModelTransformation.Mode.GROUND).scale.getY();
        float f3 = entity.getRotation(partialTicks);
        matrixStack.translate(0.0D, (f1 + 0.25F * f2), 0.0D);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(f3));
        this.itemRenderer.renderItem(itemStack, ModelTransformation.Mode.GROUND, false, matrixStack, provider, packedLightIn, OverlayTexture.DEFAULT_UV, ibakedmodel);
        matrixStack.pop();
        super.render(entity, entityYaw, partialTicks, matrixStack, provider, packedLightIn);
    }

    @Override
    public Identifier getTexture(FloatingItemEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
