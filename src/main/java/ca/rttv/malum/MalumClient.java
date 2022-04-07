package ca.rttv.malum;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.client.render.entity.ScytheBoomerangEntityRenderer;
import ca.rttv.malum.client.render.item.ScytheItemRenderer;
import ca.rttv.malum.registry.MalumEntityRegistry;
import ca.rttv.malum.registry.MalumRegistry;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static ca.rttv.malum.registry.SpiritTypeRegistry.*;

public class MalumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TerraformBoatClientHelper.registerModelLayer(new Identifier(Malum.MODID, "runewood"));
        MalumParticleRegistry.init();
        EntityRendererRegistry.register(MalumEntityRegistry.SCYTHE_BOOMERANG, ScytheBoomerangEntityRenderer::new);

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
}
