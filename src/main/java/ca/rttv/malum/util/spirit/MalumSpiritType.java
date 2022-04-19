package ca.rttv.malum.util.spirit;

import ca.rttv.malum.item.spirit.MalumSpiritItem;
import net.minecraft.item.Item;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.function.Supplier;

import static ca.rttv.malum.Main.MODID;
import static ca.rttv.malum.registry.MalumRegistry.*;

public class MalumSpiritType {
    public final Color color;
    public final Color endColor;

    public final String identifier;
    protected final Supplier<Item> splinterItem;

    public MalumSpiritType(String identifier, Color color, Item splinterItem) {
        this.identifier = identifier;
        this.color = color;
        this.endColor = createEndColor(color);
        switch(identifier) {
            case "wicked" -> {
                this.splinterItem = () -> WICKED_SPIRIT;
            }
            case "arcane" -> {
                this.splinterItem = () -> ARCANE_SPIRIT;
            }
            case "eldritch" -> {
                this.splinterItem = () -> ELDRITCH_SPIRIT;
            }
            case "aerial" -> {
                this.splinterItem = () -> AERIAL_SPIRIT;
            }
            case "aqueous" -> {
                this.splinterItem = () -> AQUEOUS_SPIRIT;
            }
            case "infernal" -> {
                this.splinterItem = () -> INFERNAL_SPIRIT;
            }
            case "earthen" -> {
                this.splinterItem = () -> EARTHEN_SPIRIT;
            }
            default -> {
                this.splinterItem = () -> SACRED_SPIRIT;
            }
        }
    }

    public MalumSpiritType(String identifier, Color color, Color endColor, Item splinterItem) {
        this.identifier = identifier;
        this.color = color;
        this.endColor = endColor;
        switch(identifier) {
            case "wicked" -> {
                this.splinterItem = () -> WICKED_SPIRIT;
            }
            case "arcane" -> {
                this.splinterItem = () -> ARCANE_SPIRIT;
            }
            case "eldritch" -> {
                this.splinterItem = () -> ELDRITCH_SPIRIT;
            }
            case "aerial" -> {
                this.splinterItem = () -> AERIAL_SPIRIT;
            }
            case "aqueous" -> {
                this.splinterItem = () -> AQUEOUS_SPIRIT;
            }
            case "infernal" -> {
                this.splinterItem = () -> INFERNAL_SPIRIT;
            }
            case "earthen" -> {
                this.splinterItem = () -> EARTHEN_SPIRIT;
            }
            default -> {
                this.splinterItem = () -> SACRED_SPIRIT;
            }
        }
    }

    public Text getComponent(int count) {
        return new LiteralText(" " + count + " ").append(new TranslatableText(getDescription())).fillStyle(Style.EMPTY.withColor(color.getRGB()));
    }

    public String getDescription() {
        return "malum.spirit.description." + identifier;
    }

    public Color createEndColor(Color color) {
        return new Color(color.getGreen(), color.getBlue(), color.getRed());
    }

    public MalumSpiritItem getSplinterItem() {
        return (MalumSpiritItem) splinterItem.get();
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
