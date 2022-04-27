package ca.rttv.malum.util.spirit;

import ca.rttv.malum.item.spirit.MalumSpiritItem;
import ca.rttv.malum.registry.MalumRegistry;
import net.minecraft.item.Item;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;

import java.awt.*;
import java.util.function.Supplier;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.SpiritTypeRegistry.SPIRITS;

public class MalumSpiritType implements StringIdentifiable {
    public static final MalumSpiritType SACRED_SPIRIT = new MalumSpiritType("sacred", new Color(243, 65, 107));
    public static final MalumSpiritType WICKED_SPIRIT = new MalumSpiritType("wicked", new Color(178, 29, 232));
    public static final MalumSpiritType ARCANE_SPIRIT = new MalumSpiritType("arcane", new Color(212, 55, 255));
    public static final MalumSpiritType ELDRITCH_SPIRIT = new MalumSpiritType("eldritch", new Color(148, 45, 245), new Color(39, 201, 103));
    public static final MalumSpiritType AERIAL_SPIRIT = new MalumSpiritType("aerial", new Color(75, 243, 218));
    public static final MalumSpiritType AQUEOUS_SPIRIT = new MalumSpiritType("aqueous", new Color(42, 114, 232));
    public static final MalumSpiritType INFERNAL_SPIRIT = new MalumSpiritType("infernal", new Color(210, 134, 39));
    public static final MalumSpiritType EARTHEN_SPIRIT = new MalumSpiritType("earthen", new Color(73, 234, 27));

    public final Color color;
    public final Color endColor;

    public final String id;
    private final Supplier<Item> splinterItem;

    public MalumSpiritType(String id, Color color) {
        this(id, color, createEndColor(color));
    }

    public MalumSpiritType(String id, Color color, Color endColor) {
        this.id = id;
        this.color = color;
        this.endColor = endColor;
        switch(id) {
            case "wicked" -> this.splinterItem = () -> MalumRegistry.WICKED_SPIRIT;
            case "arcane" -> this.splinterItem = () -> MalumRegistry.ARCANE_SPIRIT;
            case "eldritch" -> this.splinterItem = () -> MalumRegistry.ELDRITCH_SPIRIT;
            case "aerial" -> this.splinterItem = () -> MalumRegistry.AERIAL_SPIRIT;
            case "aqueous" -> this.splinterItem = () -> MalumRegistry.AQUEOUS_SPIRIT;
            case "infernal" -> this.splinterItem = () -> MalumRegistry.INFERNAL_SPIRIT;
            case "earthen" -> this.splinterItem = () -> MalumRegistry.EARTHEN_SPIRIT;
            default -> this.splinterItem = () -> MalumRegistry.SACRED_SPIRIT;
        }
        SPIRITS.add(this);
    }

    public Text getComponent(int count) {
        return new LiteralText(" " + count + " ").append(new TranslatableText(getDescription())).fillStyle(Style.EMPTY.withColor(color.getRGB()));
    }

    public String getDescription() {
        return "malum.spirit.description." + id;
    }

    public static Color createEndColor(Color color) {
        return new Color(color.getGreen(), color.getBlue(), color.getRed());
    }

    public MalumSpiritItem getSplinterItem() {
        return (MalumSpiritItem) splinterItem.get();
    }

    public Identifier getOverlayTexture() {
        return new Identifier(MODID, "block/totem/" + id + "_glow");
    }

    @Override
    public String asString() {
        return this.id;
    }

//TODO: ALL OF THIS

//    public BlockState getBlockState(boolean isCorrupt, BlockHitResult hit) {
//        //need to make these
//        Block base = isCorrupt ? BlockRegistry.SOULWOOD_TOTEM_POLE.get() : BlockRegistry.RUNEWOOD_TOTEM_POLE.get();
//        return base.getDefaultState().setValue(BlockStateProperties.HORIZONTAL_FACING, hit.getDirection()).setValue(TotemPoleBlock.SPIRIT_TYPE, SpiritTypeRegistry.SPIRITS.indexOf(this));
//    }
}
