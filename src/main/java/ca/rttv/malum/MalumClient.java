package ca.rttv.malum;

import ca.rttv.malum.client.render.block.ItemPedestalRenderer;
import ca.rttv.malum.client.render.block.ItemStandRenderer;
import ca.rttv.malum.registry.MalumRegistry;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.util.Identifier;

public class MalumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(MalumRegistry.ITEM_PEDESTAL_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new ItemPedestalRenderer());
        BlockEntityRendererRegistry.register(MalumRegistry.ITEM_STAND_BLOCK_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new ItemStandRenderer());
        TerraformBoatClientHelper.registerModelLayer(new Identifier(Malum.MODID, "runewood"));

    }
}
