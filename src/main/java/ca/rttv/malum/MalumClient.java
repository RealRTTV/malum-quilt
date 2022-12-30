package ca.rttv.malum;

import ca.rttv.malum.block.GradientLeavesBlock;
import ca.rttv.malum.block.entity.EtherBlockEntity;
import ca.rttv.malum.client.model.SoulStainedSteelArmorModel;
import ca.rttv.malum.client.model.SpiritHunterArmorModel;
import ca.rttv.malum.client.render.CloakArmorRenderer;
import ca.rttv.malum.client.render.SteelArmorRenderer;
import ca.rttv.malum.client.render.entity.FloatingItemEntityRenderer;
import ca.rttv.malum.client.render.entity.ScytheBoomerangEntityRenderer;
import ca.rttv.malum.client.render.item.ScytheItemRenderer;
import ca.rttv.malum.network.packet.s2c.play.MalumParticleS2CPacket;
import ca.rttv.malum.network.packet.s2c.play.ProgressionBookEntriesS2CPacket;
import ca.rttv.malum.registry.MalumEntityRegistry;
import ca.rttv.malum.registry.MalumItemRegistry;
import ca.rttv.malum.registry.MalumParticleEmitterRegistry;
import ca.rttv.malum.util.helper.DataHelper;
import com.sammy.lodestone.handlers.ScreenParticleHandler;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;

import java.awt.*;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumBlockRegistry.*;
import static ca.rttv.malum.registry.MalumParticleEmitterRegistry.PARTICLE_EMITTER;

public final class MalumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        MalumParticleEmitterRegistry.init();
        PARTICLE_EMITTER.forEach(ScreenParticleHandler::registerItemParticleEmitter);
        EntityModelLayerRegistry.registerModelLayer(SpiritHunterArmorModel.LAYER, SpiritHunterArmorModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SoulStainedSteelArmorModel.LAYER, SoulStainedSteelArmorModel::getTexturedModelData);
        ArmorRenderer.register(new CloakArmorRenderer(DataHelper.prefix("textures/armor/spirit_hunter_reforged.png")), MalumItemRegistry.SOUL_HUNTER_CLOAK, MalumItemRegistry.SOUL_HUNTER_ROBE, MalumItemRegistry.SOUL_HUNTER_LEGGINGS, MalumItemRegistry.SOUL_HUNTER_BOOTS);
        ArmorRenderer.register(new SteelArmorRenderer(DataHelper.prefix("textures/armor/soul_stained_steel.png")), MalumItemRegistry.SOUL_STAINED_STEEL_HELMET, MalumItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE, MalumItemRegistry.SOUL_STAINED_STEEL_LEGGINGS, MalumItemRegistry.SOUL_STAINED_STEEL_BOOTS);
        EntityRendererRegistry.register(MalumEntityRegistry.SCYTHE_BOOMERANG, ScytheBoomerangEntityRenderer::new);
        EntityRendererRegistry.register(MalumEntityRegistry.NATURAL_SPIRIT, FloatingItemEntityRenderer::new);
        this.registerColors();
        for (Item item : MalumItemRegistry.SCYTHES) {
            Identifier scytheId = Registries.ITEM.getId(item);
            ScytheItemRenderer scytheItemRenderer = new ScytheItemRenderer(scytheId);
            ResourceLoader.get(ResourceType.CLIENT_RESOURCES).registerReloader(scytheItemRenderer);
            BuiltinItemRendererRegistry.INSTANCE.register(item, scytheItemRenderer);
            ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
                out.accept(new ModelIdentifier(scytheId.withPath(scytheId.getPath() + "_gui"), "inventory"));
                out.accept(new ModelIdentifier(scytheId.withPath(scytheId.getPath() + "_handheld"), "inventory"));
            });
        }

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MODID, "malumparticles2cpacket"), (client, handler, buf, responceSender) -> new MalumParticleS2CPacket(buf).apply(handler));
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MODID, "progressionbookentriess2cpacket"), (client, listener, buf, packetSender) -> new ProgressionBookEntriesS2CPacket(buf).apply(listener));
    }
    private void registerColors() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world == null && pos == null) return 224 << 16 | 30 << 8 | 214;
            float color = state.get(GradientLeavesBlock.COLOR);
            Color maxColor = new Color(152, 6, 45);
            Color minColor = new Color(224, 30, 214);
            int red = (int) MathHelper.lerp(color / 5.0f, minColor.getRed(), maxColor.getRed());
            int green = (int) MathHelper.lerp(color / 5.0f, minColor.getGreen(), maxColor.getGreen());
            int blue = (int) MathHelper.lerp(color / 5.0f, minColor.getBlue(), maxColor.getBlue());
            return red << 16 | green << 8 | blue;
        }, SOULWOOD_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world == null && pos == null) return 251 << 16 | 193 << 8 | 76;
            float color = state.get(GradientLeavesBlock.COLOR);
            Color maxColor = new Color(175, 65, 48);
            Color minColor = new Color(251, 193, 76);
            int red = (int) MathHelper.lerp(color / 5.0f, minColor.getRed(), maxColor.getRed());
            int green = (int) MathHelper.lerp(color / 5.0f, minColor.getGreen(), maxColor.getGreen());
            int blue = (int) MathHelper.lerp(color / 5.0f, minColor.getBlue(), maxColor.getBlue());
            return red << 16 | green << 8 | blue;
        }, RUNEWOOD_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (tintIndex != 1 || world == null || pos == null) return -1;
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (!(blockEntity instanceof EtherBlockEntity ether)) return -1;
            return ether.firstColorRGB;
        }, WALL_ETHER_TORCH, ETHER_TORCH);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (tintIndex == -1 || tintIndex == 0 || world == null || pos == null) return -1;
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (!(blockEntity instanceof EtherBlockEntity ether)) return -1;
            return tintIndex == 1 ? ether.firstColorRGB : ether.secondColorRGB;
        }, IRIDESCENT_ETHER_TORCH, IRIDESCENT_WALL_ETHER_TORCH);
    }
}
