package ca.rttv.malum;

import ca.rttv.malum.client.model.SoulStainedSteelArmorModel;
import ca.rttv.malum.client.model.SpiritHunterArmorModel;
import ca.rttv.malum.client.render.CloakArmorRenderer;
import ca.rttv.malum.client.render.SteelArmorRenderer;
import ca.rttv.malum.client.render.entity.FloatingItemEntityRenderer;
import ca.rttv.malum.client.render.entity.ScytheBoomerangEntityRenderer;
import ca.rttv.malum.client.render.item.ScytheItemRenderer;
import ca.rttv.malum.registry.MalumEntityRegistry;
import ca.rttv.malum.registry.MalumRegistry;
import ca.rttv.malum.util.handler.RenderHandler;
import ca.rttv.malum.util.helper.DataHelper;
import ca.rttv.malum.util.listener.SpiritDataReloadListener;
import ca.rttv.malum.util.spirit.MalumSpiritType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumRegistry.*;

public final class MalumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RenderHandler.init();
        MalumRegistry.registerParticleEmitters();
        EntityModelLayerRegistry.registerModelLayer(SpiritHunterArmorModel.LAYER, SpiritHunterArmorModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SoulStainedSteelArmorModel.LAYER, SoulStainedSteelArmorModel::getTexturedModelData);
        ArmorRenderer.register(new CloakArmorRenderer(DataHelper.prefix("textures/armor/spirit_hunter.png")), MalumRegistry.SPIRIT_HUNTER_CLOAK, MalumRegistry.SPIRIT_HUNTER_ROBE, MalumRegistry.SPIRIT_HUNTER_LEGGINGS, MalumRegistry.SPIRIT_HUNTER_BOOTS);
        ArmorRenderer.register(new SteelArmorRenderer(DataHelper.prefix("textures/armor/soul_stained_steel.png")), MalumRegistry.SOUL_STAINED_STEEL_HELMET, MalumRegistry.SOUL_STAINED_STEEL_CHESTPLATE, MalumRegistry.SOUL_STAINED_STEEL_LEGGINGS, MalumRegistry.SOUL_STAINED_STEEL_BOOTS);
        EntityRendererRegistry.register(MalumEntityRegistry.SCYTHE_BOOMERANG, ScytheBoomerangEntityRenderer::new);
        EntityRendererRegistry.register(MalumEntityRegistry.NATURAL_SPIRIT, FloatingItemEntityRenderer::new);

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SpiritDataReloadListenerFabricImpl());
        for(Item item : MalumRegistry.SCYTHES) {
            Identifier scytheId = Registry.ITEM.getId(item);
            ScytheItemRenderer scytheItemRenderer = new ScytheItemRenderer(scytheId);
            ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(scytheItemRenderer);
            BuiltinItemRendererRegistry.INSTANCE.register(item, scytheItemRenderer);
            ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
                out.accept(new ModelIdentifier(scytheId + "_gui", "inventory"));
                out.accept(new ModelIdentifier(scytheId + "_handheld", "inventory"));
            });
        }
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> MalumSpiritType.SACRED_SPIRIT.color.getRGB(), SACRED_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> MalumSpiritType.AERIAL_SPIRIT.color.getRGB(), AERIAL_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> MalumSpiritType.AQUEOUS_SPIRIT.color.getRGB(), AQUEOUS_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> MalumSpiritType.ARCANE_SPIRIT.color.getRGB(), ARCANE_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> MalumSpiritType.ELDRITCH_SPIRIT.color.getRGB(), ELDRITCH_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> MalumSpiritType.INFERNAL_SPIRIT.color.getRGB(), INFERNAL_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> MalumSpiritType.EARTHEN_SPIRIT.color.getRGB(), EARTHEN_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> MalumSpiritType.WICKED_SPIRIT.color.getRGB(), WICKED_SPIRIT);
    }
    public static class SpiritDataReloadListenerFabricImpl extends SpiritDataReloadListener implements SimpleSynchronousResourceReloadListener {

        @Override
        public Identifier getFabricId() {
            return new Identifier(MODID, "spirit_data");
        }


        @Override
        public void reload(ResourceManager manager) {

        }
    }
}
