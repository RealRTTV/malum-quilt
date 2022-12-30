package ca.rttv.malum.client.render.block;

import ca.rttv.malum.block.SpiritCrucibleBlock;
import ca.rttv.malum.block.entity.SpiritCrucibleBlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
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

public class SpiritCrucibleRenderer implements BlockEntityRenderer<SpiritCrucibleBlockEntity> {
    @Override
    public void render(SpiritCrucibleBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        final MinecraftClient client = MinecraftClient.getInstance();
        if (blockEntity.getCachedState().get(SpiritCrucibleBlock.HALF) == DoubleBlockHalf.LOWER) {
            return;
        }
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
                Vec3d offset = SpiritCrucibleBlockEntity.spiritOffset(blockEntity, i, tickDelta);
                matrices.translate(offset.x, offset.y, offset.z);
                matrices.multiply(new Quaternionf().rotateY((world.getTime() + tickDelta) * 3.0f*((float)Math.PI/180F))); // todo, fix the tickDelta lag
                matrices.scale(0.5f, 0.5f, 0.5f);
                itemRenderer.renderItem(item, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
                matrices.pop();
            }
        }
        ItemStack stack = blockEntity.getHeldItem();
        if (!stack.isEmpty()) {
            matrices.push();
            Vec3d offset = new Vec3d(0.5d, 0.6d, 0.5d);
            matrices.translate(offset.x, offset.y, offset.z);
            matrices.multiply(new Quaternionf().rotateY((world.getTime() + tickDelta) * 3.0f*((float)Math.PI/180F)));
            matrices.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
            matrices.pop();
        }
    }
}
