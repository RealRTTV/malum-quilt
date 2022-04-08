package ca.rttv.malum.mixin;

import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

import static ca.rttv.malum.registry.MalumRegistry.*;

@Mixin(RenderLayers.class)
public abstract class RenderLayersMixin {
    @Shadow @Final private static Map<Block, RenderLayer> BLOCKS;

    static {
        BLOCKS.put(RUNEWOOD_SAPLING, RenderLayer.getCutout());
        BLOCKS.put(SOULWOOD_SAPLING, RenderLayer.getCutout());
        BLOCKS.put(RUNEWOOD_DOOR, RenderLayer.getCutout());
        BLOCKS.put(SOULWOOD_DOOR, RenderLayer.getCutout());
        BLOCKS.put(RUNEWOOD_TRAPDOOR, RenderLayer.getCutout());
        BLOCKS.put(SOULWOOD_TRAPDOOR, RenderLayer.getCutout());
        BLOCKS.put(ETHER_TORCH, RenderLayer.getCutout());
        BLOCKS.put(WALL_ETHER_TORCH, RenderLayer.getCutout());
    }
}
