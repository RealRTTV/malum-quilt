package ca.rttv.malum.item.spirit;

import ca.rttv.malum.item.interfaces.IFloatingGlowItem;
import ca.rttv.malum.util.spirit.MalumSpiritType;
import net.minecraft.item.Item;

import java.awt.*;

public class MalumSpiritItem extends Item implements IFloatingGlowItem {
    // , ItemParticleEmitter
    public final MalumSpiritType type;

    public MalumSpiritItem(Item.Settings settings, MalumSpiritType type) {
        super(settings);
        this.type = type;
    }

    @Override
    public Color getColor() {
        return type.color;
    }

    @Override
    public Color getEndColor() {
        return type.endColor;
    }

//    @Override
//    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
//        spawnSpiritScreenParticles(type.color, type.endColor, stack, x, y, renderOrder);
//    }
}
