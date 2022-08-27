package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

public class SpiritInfusionPage extends BookPage {
    public static final Codec<SpiritInfusionPage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Identifier.CODEC.fieldOf("recipe").forGetter(page -> page.recipe)
    ).apply(instance, SpiritInfusionPage::new));

    public final Identifier recipe;

    public SpiritInfusionPage(Identifier id) {
        recipe = id;
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    @Override
    public MalumPageTypeRegistry.PageType<SpiritInfusionPage> type() {
        return MalumPageTypeRegistry.SPIRIT_INFUSION_PAGE_TYPE;
    }
}
