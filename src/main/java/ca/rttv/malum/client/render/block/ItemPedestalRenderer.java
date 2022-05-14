package ca.rttv.malum.client.render.block;

import ca.rttv.malum.block.entity.ItemPedestalBlockEntity;
import ca.rttv.malum.item.SpiritItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

public class ItemPedestalRenderer implements BlockEntityRenderer<ItemPedestalBlockEntity> {
    @Override
    public void render(ItemPedestalBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        final MinecraftClient client = MinecraftClient.getInstance();
        World world = client.world;
        ItemRenderer itemRenderer = client.getItemRenderer();
        ItemStack stack = entity.getHeldItem();
        if (!stack.isEmpty() && world != null) {
            matrices.push();
            Vec3f offset = new Vec3f(entity.itemOffset());
            if (stack.getItem() instanceof SpiritItem) {
                double y = Math.sin((world.getTime() + tickDelta) / 20f) * 0.1f;
                matrices.translate(0, y, 0);
            }
            matrices.translate(offset.getX(), offset.getY(), offset.getZ());
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((world.getTime() + tickDelta) * 3));
            matrices.scale(0.6f, 0.6f, 0.6f);
            itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, 0);
            matrices.pop();
        }
    }
}
