package ca.rttv.malum;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.client.render.entity.ScytheBoomerangEntityRenderer;
import ca.rttv.malum.client.render.item.ScytheItemRenderer;
import ca.rttv.malum.registry.MalumEntityRegistry;
import ca.rttv.malum.registry.MalumRegistry;
import ca.rttv.malum.util.handler.RenderHandler;
import ca.rttv.malum.util.listener.SpiritDataReloadListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Function;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.SpiritTypeRegistry.*;

public class MalumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MalumParticleRegistry.registerFactories();
        RenderHandler.init();
        EntityRendererRegistry.register(MalumEntityRegistry.SCYTHE_BOOMERANG, ScytheBoomerangEntityRenderer::new);

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
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> SACRED_SPIRIT_COLOR.getRGB(), MalumRegistry.SACRED_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> AERIAL_SPIRIT_COLOR.getRGB(), MalumRegistry.AERIAL_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> AQUEOUS_SPIRIT_COLOR.getRGB(), MalumRegistry.AQUEOUS_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ARCANE_SPIRIT_COLOR.getRGB(), MalumRegistry.ARCANE_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ELDRITCH_SPIRIT_COLOR.getRGB(), MalumRegistry.ELDRITCH_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> INFERNAL_SPIRIT_COLOR.getRGB(), MalumRegistry.INFERNAL_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> EARTHEN_SPIRIT_COLOR.getRGB(), MalumRegistry.EARTHEN_SPIRIT);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> WICKED_SPIRIT_COLOR.getRGB(), MalumRegistry.WICKED_SPIRIT);
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
