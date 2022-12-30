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
import net.minecraft.world.World;
import org.joml.Quaternionf;
import org.joml.Vector3f;

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
                Vector3f offset = SpiritAltarBlockEntity.spiritOffset(blockEntity, i, tickDelta).m_sruzucpd();
                matrices.translate(offset.x(), offset.y(), offset.z());
                matrices.multiply(new Quaternionf().rotateY((world.getTime() + tickDelta) * 3*((float)Math.PI/180F)));
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
            matrices.multiply(new Quaternionf().rotateY((world.getTime() + tickDelta) * 3*((float)Math.PI/180F)));
            matrices.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
            matrices.pop();
        }
    }
}
