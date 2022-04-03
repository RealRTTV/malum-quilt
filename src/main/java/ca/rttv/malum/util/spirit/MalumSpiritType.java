package ca.rttv.malum.util.spirit;

import ca.rttv.malum.item.spirit.MalumSpiritItem;
import ca.rttv.malum.util.DataHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;

import java.awt.*;

import static ca.rttv.malum.Malum.MODID;

public class MalumSpiritType {
    public final Color color;
    public final Color endColor;

    public final String identifier;
    protected Item splinterItem;

    public MalumSpiritType(String identifier, Color color, Item splinterItem) {
        this.identifier = identifier;
        this.color = color;
        this.endColor = createEndColor(color);
        this.splinterItem = splinterItem;
    }

    public MalumSpiritType(String identifier, Color color, Color endColor, Item splinterItem) {
        this.identifier = identifier;
        this.color = color;
        this.endColor = endColor;
        this.splinterItem = splinterItem;
    }

    public Component getComponent(int count) {
        return (Component) new LiteralText(" " + count + " ").append(new TranslatableText(getDescription())).fillStyle(Style.EMPTY.withColor(color.getRGB()));
    }

    public String getDescription() {
        return "malum.spirit.description." + identifier;
    }

    public Color createEndColor(Color color) {
        return new Color(color.getGreen(), color.getBlue(), color.getRed());
    }

    public MalumSpiritItem getSplinterItem() {
        return (MalumSpiritItem) splinterItem;
    }

    public Identifier getOverlayTexture() {
        return new Identifier(MODID, "block/totem/" + identifier + "_glow");
    }

//TODO: ALL OF THIS

//    public BlockState getBlockState(boolean isCorrupt, BlockHitResult hit) {
//        //need to make these
//        Block base = isCorrupt ? BlockRegistry.SOULWOOD_TOTEM_POLE.get() : BlockRegistry.RUNEWOOD_TOTEM_POLE.get();
//        return base.getDefaultState().setValue(BlockStateProperties.HORIZONTAL_FACING, hit.getDirection()).setValue(TotemPoleBlock.SPIRIT_TYPE, SpiritTypeRegistry.SPIRITS.indexOf(this));
//    }
}
