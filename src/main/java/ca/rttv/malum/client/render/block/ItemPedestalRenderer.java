package ca.rttv.malum.client.render.block;

import ca.rttv.malum.block.entity.ItemPedestalBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import static net.minecraft.client.render.OverlayTexture.DEFAULT_UV;

@Environment(EnvType.CLIENT)
public class ItemPedestalRenderer implements BlockEntityRenderer<ItemPedestalBlockEntity> {

    @Override
    public void render(ItemPedestalBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = MinecraftClient.getInstance().world;
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = entity.getHeldItem();
        if (!stack.isEmpty())
        {
            matrices.push();
            Vec3f offset = new Vec3f(entity.itemOffset());
//            if (stack.getItem() instanceof MalumSpiritItem)
//            {
//                double y = Math.sin(((world.getTime() + partialTicks) ) / 20f) * 0.1f;
//                matrices.translate(0, y, 0);
//            }
            matrices.translate(offset.getX(), offset.getY(), offset.getZ());
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((world.getTime() )* 3 + tickDelta));
            matrices.scale(0.6f, 0.6f, 0.6f);
            itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, 0);
            matrices.pop();
        }
    }
}
