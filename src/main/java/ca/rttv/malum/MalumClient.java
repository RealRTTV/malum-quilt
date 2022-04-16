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
import static ca.rttv.malum.registry.MalumRegistry.AERIAL_SPIRIT;
import static ca.rttv.malum.registry.MalumRegistry.AQUEOUS_SPIRIT;
import static ca.rttv.malum.registry.MalumRegistry.ARCANE_SPIRIT;
import static ca.rttv.malum.registry.MalumRegistry.EARTHEN_SPIRIT;
import static ca.rttv.malum.registry.MalumRegistry.ELDRITCH_SPIRIT;
import static ca.rttv.malum.registry.MalumRegistry.INFERNAL_SPIRIT;
import static ca.rttv.malum.registry.MalumRegistry.SACRED_SPIRIT;
import static ca.rttv.malum.registry.MalumRegistry.WICKED_SPIRIT;
import static ca.rttv.malum.registry.SpiritTypeRegistry.*;

public class MalumClient implements ClientModInitializer {
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
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> SACRED_SPIRIT_COLOR.getRGB(), SACRED_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> AERIAL_SPIRIT_COLOR.getRGB(), AERIAL_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> AQUEOUS_SPIRIT_COLOR.getRGB(), AQUEOUS_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ARCANE_SPIRIT_COLOR.getRGB(), ARCANE_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ELDRITCH_SPIRIT_COLOR.getRGB(), ELDRITCH_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> INFERNAL_SPIRIT_COLOR.getRGB(), INFERNAL_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> EARTHEN_SPIRIT_COLOR.getRGB(), EARTHEN_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> WICKED_SPIRIT_COLOR.getRGB(), WICKED_SPIRIT);
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
