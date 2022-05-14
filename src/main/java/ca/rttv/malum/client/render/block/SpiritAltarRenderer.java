package ca.rttv.malum.client.render.block;

import ca.rttv.malum.block.entity.SpiritAltarBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

public class SpiritAltarRenderer implements BlockEntityRenderer<SpiritAltarBlockEntity> {
    @Override
    public void render(SpiritAltarBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        final MinecraftClient client = MinecraftClient.getInstance();
        World world = client.world;
        if (world == null) {
            return;
        }
        ItemRenderer itemRenderer = client.getItemRenderer();
        DefaultedList<ItemStack> inventory = blockEntity.spiritSlots;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack item = inventory.get(i);
            if (!item.isEmpty()) {
                matrices.push();
                Vec3f offset = new Vec3f(SpiritAltarBlockEntity.spiritOffset(blockEntity, i, tickDelta));
                matrices.translate(offset.getX(), offset.getY(), offset.getZ());
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((world.getTime() + tickDelta) * 3));
                matrices.scale(0.5f, 0.5f, 0.5f);
                itemRenderer.renderItem(item, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
                matrices.pop();
            }
        }
        ItemStack stack = blockEntity.getHeldItem();
        if (!stack.isEmpty()) {
            matrices.push();
            Vec3d offset = blockEntity.itemOffset();
            matrices.translate(offset.x, offset.y, offset.z);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((world.getTime() + tickDelta) * 3));
            matrices.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
            matrices.pop();
        }
    }
}
