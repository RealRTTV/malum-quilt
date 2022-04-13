package ca.rttv.malum.client.render.entity;

import ca.rttv.malum.entity.FloatingItemEntity;
import net.minecraft.client.render.OverlayTexture;
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
import net.minecraft.util.math.Vec3f;

public class FloatingItemEntityRenderer extends EntityRenderer<FloatingItemEntity>
{
    public final ItemRenderer itemRenderer;

    public FloatingItemEntityRenderer(EntityRendererFactory.Context context)
    {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowOpacity = 0;
    }


    @Override
    public void render(FloatingItemEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStack, VertexConsumerProvider provider, int packedLightIn)
    {
        matrixStack.push();
        ItemStack itemStack = entityIn.getItem();
        BakedModel ibakedmodel = this.itemRenderer.getHeldItemModel(itemStack, entityIn.world, null, entityIn.getItem().getCount());
        float f1 = entityIn.getYOffset(partialTicks);
        float f2 = ibakedmodel.getTransformation().getTransformation(ModelTransformation.Mode.GROUND).scale.getY();
        float f3 = entityIn.getRotation(partialTicks);
        matrixStack.translate(0.0D, (f1 + 0.25F * f2), 0.0D);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(f3));
        this.itemRenderer.renderItem(itemStack, ModelTransformation.Mode.GROUND, false, matrixStack, provider, packedLightIn, OverlayTexture.DEFAULT_UV, ibakedmodel);
        matrixStack.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStack, provider, packedLightIn);
    }

    @Override
    public Identifier getTexture(FloatingItemEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
