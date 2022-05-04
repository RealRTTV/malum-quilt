package ca.rttv.malum.client.render.block;

import ca.rttv.malum.block.TotemPoleBlock;
import ca.rttv.malum.block.entity.TotemPoleBlockEntity;
import ca.rttv.malum.registry.SpiritTypeRegistry;
import ca.rttv.malum.util.RenderLayers;
import ca.rttv.malum.util.handler.RenderHandler;
import ca.rttv.malum.util.helper.RenderHelper;
import ca.rttv.malum.util.spirit.MalumSpiritType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

import java.awt.*;
import java.util.HashMap;

import static ca.rttv.malum.util.helper.RenderHelper.FULL_BRIGHT;

@SuppressWarnings("deprecation")
public class TotemPoleRenderer implements BlockEntityRenderer<TotemPoleBlockEntity> {
    public static HashMap<MalumSpiritType, SpriteIdentifier> overlayHashmap = new HashMap<>();

    public TotemPoleRenderer() {
        SpiritTypeRegistry.SPIRITS.forEach((s) ->
                overlayHashmap.put(s, new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, s.getOverlayTexture()))
        );
    }

    @Override
    public void render(TotemPoleBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Direction direction = entity.getCachedState().get(Properties.HORIZONTAL_FACING);
        if (entity.getCachedState().get(TotemPoleBlock.SPIRIT_TYPE) == null) {
            return;
        }
        this.renderQuad(overlayHashmap.get(entity.getCachedState().get(TotemPoleBlock.SPIRIT_TYPE).spirit), entity.getCachedState().get(TotemPoleBlock.SPIRIT_TYPE).spirit.color, (entity.currentColor + tickDelta) / 20f, direction, matrices);
    }

    public void renderQuad(SpriteIdentifier spriteId, Color color, float alpha, Direction direction, MatrixStack matrices) {
        Sprite sprite = spriteId.getSprite();
        VertexConsumer consumer = RenderHandler.DELAYED_RENDER.getBuffer(RenderLayers.ADDITIVE_BLOCK);

        Vec3f[] positions = new Vec3f[]{new Vec3f(0, 0, 2.01f), new Vec3f(2, 0, 2.01f), new Vec3f(2, 2, 2.01f), new Vec3f(0, 2, 2.01f)};

        matrices.push();
        matrices.translate(0.5f, 0.5f, 0.5f);
        matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(direction.asRotation()));
        matrices.translate(-0.5f, -0.5f, -0.5f);
        RenderHelper.create()
                .setColor(color, alpha)
                .setLight(FULL_BRIGHT)
                .setUV(sprite.getMinU(), sprite.getMinV(), sprite.getMaxU(), sprite.getMaxV())
                .renderQuad(consumer, matrices, positions, 0.5f);
        matrices.pop();
    }

    public float rotation(Direction direction) {
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            return direction.getOpposite().asRotation();
        }
        return direction.asRotation();
    }
}
