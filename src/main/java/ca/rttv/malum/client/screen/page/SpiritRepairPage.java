package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

public class SpiritRepairPage extends BookPage {
    public static final Codec<SpiritRepairPage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Identifier.CODEC.fieldOf("recipe").forGetter(page -> page.recipe)
    ).apply(instance, SpiritRepairPage::new));

    public final Identifier recipe;

    public SpiritRepairPage(Identifier id) {
        recipe = id;
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    @Override
    public MalumPageTypeRegistry.PageType<SpiritRepairPage> type() {
        return MalumPageTypeRegistry.SPIRIT_REPAIR_PAGE_TYPE;
    }
}
