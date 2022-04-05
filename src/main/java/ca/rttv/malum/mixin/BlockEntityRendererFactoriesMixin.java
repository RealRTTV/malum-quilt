package ca.rttv.malum.mixin;

import ca.rttv.malum.client.render.block.ItemPedestalRenderer;
import ca.rttv.malum.client.render.block.ItemStandRenderer;
import ca.rttv.malum.client.render.block.SpiritAltarRenderer;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import static ca.rttv.malum.registry.MalumRegistry.*;

@Mixin(BlockEntityRendererFactories.class)
public abstract class BlockEntityRendererFactoriesMixin {
    @Shadow
    private static <T extends BlockEntity> void register(BlockEntityType<? extends T> type, BlockEntityRendererFactory<T> factory) {}

    static {
        register(ITEM_PEDESTAL_BLOCK_ENTITY, rendererDispatcher -> new ItemPedestalRenderer());
        register(ITEM_STAND_BLOCK_ENTITY, rendererDispatcherIn -> new ItemStandRenderer());
        register(SPIRIT_ALTAR_BLOCK_ENTITY, rendererDispatcherIn -> new SpiritAltarRenderer());
    }
}
