package ca.rttv.malum.client.render.block;

import ca.rttv.malum.block.entity.SpiritJarBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.joml.Quaternionf;

import static net.minecraft.client.render.OverlayTexture.DEFAULT_UV;

public class SpiritJarRenderer implements BlockEntityRenderer<SpiritJarBlockEntity> {
    @Override
    public void render(SpiritJarBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = MinecraftClient.getInstance().world;
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        if (blockEntity.getItem() != null) {
            matrices.push();
            double y =  0.5f + Math.sin((world.getTime() + tickDelta) / 20f) * 0.2f;
            matrices.translate(0.5f,y,0.5f);
            matrices.multiply(new Quaternionf().rotateY((world.getTime() + tickDelta) * 3*((float)Math.PI/180F)));
            matrices.scale(0.6f, 0.6f, 0.6f);
            itemRenderer.renderItem(new ItemStack(blockEntity.getItem()), ModelTransformation.Mode.FIXED, light, DEFAULT_UV, matrices, vertexConsumers, 0);
            matrices.pop();
        }
    }
}
