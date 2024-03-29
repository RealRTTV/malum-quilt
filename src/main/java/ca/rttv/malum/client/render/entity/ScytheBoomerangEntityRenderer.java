package ca.rttv.malum.client.render.entity;

import ca.rttv.malum.entity.ScytheBoomerangEntity;
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
import org.joml.Quaternionf;

@SuppressWarnings("deprecation")
public class ScytheBoomerangEntityRenderer extends EntityRenderer<ScytheBoomerangEntity> {
    public final ItemRenderer itemRenderer;

    public ScytheBoomerangEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 2F;
        this.shadowOpacity = 0.5F;
    }

    @Override
    public void render(ScytheBoomerangEntity entityIn, float entityYaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider bufferIn, int packedLightIn) {
        matrixStack.push();
        ItemStack itemstack = entityIn.getItem();
        BakedModel ibakedmodel = this.itemRenderer.getHeldItemModel(itemstack, entityIn.world, null, 1);
        matrixStack.multiply(new Quaternionf().rotateX(90F*((float)Math.PI/180F)));
        matrixStack.scale(2f, 2f, 2f);
        matrixStack.multiply(new Quaternionf().rotateZ((entityIn.age + tickDelta) * 0.8f));
        itemRenderer.renderItem(itemstack, ModelTransformation.Mode.NONE, false, matrixStack, bufferIn, packedLightIn, OverlayTexture.DEFAULT_UV, ibakedmodel);

        matrixStack.pop();

        super.render(entityIn, entityYaw, tickDelta, matrixStack, bufferIn, packedLightIn);
    }

    @Override
    public Identifier getTexture(ScytheBoomerangEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
