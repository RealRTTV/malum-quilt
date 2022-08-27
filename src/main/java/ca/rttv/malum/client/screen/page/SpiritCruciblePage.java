package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

public class SpiritCruciblePage extends BookPage {
    public static final Codec<SpiritCruciblePage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Identifier.CODEC.fieldOf("recipe").forGetter(page -> page.recipe)
    ).apply(instance, SpiritCruciblePage::new));

    public final Identifier recipe;

    public SpiritCruciblePage(Identifier id) {
        recipe = id;
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    @Override
    public MalumPageTypeRegistry.PageType<SpiritCruciblePage> type() {
        return MalumPageTypeRegistry.SPIRIT_CRUCIBLE_PAGE_TYPE;
    }
}
