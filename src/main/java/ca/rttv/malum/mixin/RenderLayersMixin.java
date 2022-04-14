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
        BLOCKS.put(TAINTED_ETHER_BRAZIER, RenderLayer.getCutout());
        BLOCKS.put(TWISTED_ETHER_BRAZIER, RenderLayer.getCutout());
        BLOCKS.put(IRIDESCENT_ETHER_TORCH, RenderLayer.getCutout());
        BLOCKS.put(IRIDESCENT_WALL_ETHER_TORCH, RenderLayer.getCutout());
        BLOCKS.put(TAINTED_IRIDESCENT_ETHER_BRAZIER, RenderLayer.getCutout());
        BLOCKS.put(TWISTED_IRIDESCENT_ETHER_BRAZIER, RenderLayer.getCutout());
        BLOCKS.put(BRILLIANT_DEEPSLATE, RenderLayer.getCutout());
        BLOCKS.put(BRILLIANT_STONE, RenderLayer.getCutout());
        BLOCKS.put(BLAZING_QUARTZ_ORE, RenderLayer.getCutout());
        BLOCKS.put(SPIRIT_JAR, RenderLayer.getCutout());
    }
}
