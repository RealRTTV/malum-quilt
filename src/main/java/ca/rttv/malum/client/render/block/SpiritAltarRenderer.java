package ca.rttv.malum.client.render.block;

import ca.rttv.malum.block.entity.SpiritAltarBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

public class SpiritAltarRenderer implements BlockEntityRenderer<SpiritAltarBlockEntity> {
    @Override
    public void render(SpiritAltarBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        final MinecraftClient client = MinecraftClient.getInstance();
        final ItemRenderer itemRenderer = client.getItemRenderer();
        final World world = client.world;
        ItemStack stack = blockEntity.getHeldItem();
        if (!stack.isEmpty()) {
            matrices.push();
            Vec3f offset = new Vec3f(0.5f, 1.25f, 0.5f);
            matrices.translate(offset.getX(), offset.getY(), offset.getZ());
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((world.getTime())* 3 + tickDelta));
            matrices.scale(0.6f, 0.6f, 0.6f);
            itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, 0);
            matrices.pop();
        }
    }
}
