package ca.rttv.malum.util.spirit;

import ca.rttv.malum.item.SpiritItem;
import ca.rttv.malum.registry.MalumItemRegistry;
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

public class SpiritType implements StringIdentifiable {
    public static final SpiritType SACRED_SPIRIT = new SpiritType("sacred", new Color(243, 65, 107));
    public static final SpiritType WICKED_SPIRIT = new SpiritType("wicked", new Color(178, 29, 232));
    public static final SpiritType ARCANE_SPIRIT = new SpiritType("arcane", new Color(212, 55, 255));
    public static final SpiritType ELDRITCH_SPIRIT = new SpiritType("eldritch", new Color(148, 45, 245), new Color(39, 201, 103));
    public static final SpiritType AERIAL_SPIRIT = new SpiritType("aerial", new Color(75, 243, 218));
    public static final SpiritType AQUEOUS_SPIRIT = new SpiritType("aqueous", new Color(42, 114, 232));
    public static final SpiritType INFERNAL_SPIRIT = new SpiritType("infernal", new Color(210, 134, 39));
    public static final SpiritType EARTHEN_SPIRIT = new SpiritType("earthen", new Color(73, 234, 27));

    public final Color color;
    public final Color endColor;

    public final String id;
    private final Supplier<Item> splinterItem;

    public SpiritType(String id, Color color) {
        this(id, color, createEndColor(color));
    }

    public SpiritType(String id, Color color, Color endColor) {
        this.id = id;
        this.color = color;
        this.endColor = endColor;
        switch(id) {
            case "wicked" -> this.splinterItem = () -> MalumItemRegistry.WICKED_SPIRIT;
            case "arcane" -> this.splinterItem = () -> MalumItemRegistry.ARCANE_SPIRIT;
            case "eldritch" -> this.splinterItem = () -> MalumItemRegistry.ELDRITCH_SPIRIT;
            case "aerial" -> this.splinterItem = () -> MalumItemRegistry.AERIAL_SPIRIT;
            case "aqueous" -> this.splinterItem = () -> MalumItemRegistry.AQUEOUS_SPIRIT;
            case "infernal" -> this.splinterItem = () -> MalumItemRegistry.INFERNAL_SPIRIT;
            case "earthen" -> this.splinterItem = () -> MalumItemRegistry.EARTHEN_SPIRIT;
            default -> this.splinterItem = () -> MalumItemRegistry.SACRED_SPIRIT;
        }
        SPIRITS.put(id, this);
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

    public SpiritItem getSplinterItem() {
        return (SpiritItem) splinterItem.get();
    }

    public Identifier getOverlayTexture() {
        return new Identifier(MODID, "block/totem/" + id + "_glow");
    }

    @Override
    public String asString() {
        return this.id;
    }
}
