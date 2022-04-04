package ca.rttv.malum;

import ca.rttv.malum.client.render.block.ItemPedestalRenderer;
import ca.rttv.malum.client.render.block.ItemStandRenderer;
import ca.rttv.malum.client.render.item.ScytheItemRenderer;
import ca.rttv.malum.registry.MalumRegistry;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MalumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(MalumRegistry.ITEM_PEDESTAL_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new ItemPedestalRenderer());
        BlockEntityRendererRegistry.register(MalumRegistry.ITEM_STAND_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new ItemStandRenderer());
        TerraformBoatClientHelper.registerModelLayer(new Identifier(Malum.MODID, "runewood"));

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
    }
}
