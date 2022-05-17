package ca.rttv.malum;

import ca.rttv.malum.client.model.SoulStainedSteelArmorModel;
import ca.rttv.malum.client.model.SpiritHunterArmorModel;
import ca.rttv.malum.client.render.CloakArmorRenderer;
import ca.rttv.malum.client.render.SteelArmorRenderer;
import ca.rttv.malum.client.render.entity.FloatingItemEntityRenderer;
import ca.rttv.malum.client.render.entity.ScytheBoomerangEntityRenderer;
import ca.rttv.malum.client.render.item.ScytheItemRenderer;
import ca.rttv.malum.registry.MalumEntityRegistry;
import ca.rttv.malum.registry.MalumItemRegistry;
import ca.rttv.malum.registry.MalumParticleEmitterRegistry;
import ca.rttv.malum.util.handler.RenderHandler;
import ca.rttv.malum.util.handler.ScreenParticleHandler;
import ca.rttv.malum.util.helper.DataHelper;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;

import static ca.rttv.malum.registry.MalumParticleEmitterRegistry.PARTICLE_EMITTER;

public final class MalumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        RenderHandler.init();
        MalumParticleEmitterRegistry.init();
        PARTICLE_EMITTER.forEach(ScreenParticleHandler::registerItemParticleEmitter);
        EntityModelLayerRegistry.registerModelLayer(SpiritHunterArmorModel.LAYER, SpiritHunterArmorModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SoulStainedSteelArmorModel.LAYER, SoulStainedSteelArmorModel::getTexturedModelData);
        ArmorRenderer.register(new CloakArmorRenderer(DataHelper.prefix("textures/armor/spirit_hunter_reforged.png")), MalumItemRegistry.SOUL_HUNTER_CLOAK, MalumItemRegistry.SOUL_HUNTER_ROBE, MalumItemRegistry.SOUL_HUNTER_LEGGINGS, MalumItemRegistry.SOUL_HUNTER_BOOTS);
        ArmorRenderer.register(new SteelArmorRenderer(DataHelper.prefix("textures/armor/soul_stained_steel.png")), MalumItemRegistry.SOUL_STAINED_STEEL_HELMET, MalumItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE, MalumItemRegistry.SOUL_STAINED_STEEL_LEGGINGS, MalumItemRegistry.SOUL_STAINED_STEEL_BOOTS);
        EntityRendererRegistry.register(MalumEntityRegistry.SCYTHE_BOOMERANG, ScytheBoomerangEntityRenderer::new);
        EntityRendererRegistry.register(MalumEntityRegistry.NATURAL_SPIRIT, FloatingItemEntityRenderer::new);

        for (Item item : MalumItemRegistry.SCYTHES) {
            Identifier scytheId = Registry.ITEM.getId(item);
            ScytheItemRenderer scytheItemRenderer = new ScytheItemRenderer(scytheId);
            ResourceLoader.get(ResourceType.CLIENT_RESOURCES).registerReloader(scytheItemRenderer);
            BuiltinItemRendererRegistry.INSTANCE.register(item, scytheItemRenderer);
            ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
                out.accept(new ModelIdentifier(scytheId + "_gui", "inventory"));
                out.accept(new ModelIdentifier(scytheId + "_handheld", "inventory"));
            });
        }
    }
}
