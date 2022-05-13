package ca.rttv.malum.mixin;

import ca.rttv.malum.client.render.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import static ca.rttv.malum.registry.MalumBlockEntityRegistry.*;

@Mixin(BlockEntityRendererFactories.class)
public final class BlockEntityRendererFactoriesMixin {
    @Shadow
    private static <T extends BlockEntity> void register(BlockEntityType<? extends T> type, BlockEntityRendererFactory<T> factory) {}

    static {
        register(ITEM_PEDESTAL_BLOCK_ENTITY, rendererDispatcher -> new ItemPedestalRenderer());
        register(ITEM_STAND_BLOCK_ENTITY, renderer -> new ItemStandRenderer());
        register(SPIRIT_ALTAR_BLOCK_ENTITY, renderer -> new SpiritAltarRenderer());
        register(ETHER_BLOCK_ENTITY, renderer -> new EtherRenderer());
        register(SPIRIT_JAR_BLOCK_ENTITY, renderer -> new SpiritJarRenderer());
        register(TOTEM_BASE_BLOCK_ENTITY, renderer -> new TotemBaseRenderer());
        register(TOTEM_POLE_BLOCK_ENTITY, renderer -> new TotemPoleRenderer());
    }
}
