package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;

public class SpiritTextPage extends BookPage {
    public static final Codec<SpiritTextPage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("headline_translation_key").forGetter(SpiritTextPage::headlineTranslationKey),
        Codec.STRING.fieldOf("description_translation_key").forGetter(SpiritTextPage::descriptionTranslationKey),
        ItemStack.CODEC.fieldOf("item").forGetter(page -> page.spiritStack)
    ).apply(instance, SpiritTextPage::new));

    private final String headlineTranslationKey;
    private final String descriptionTranslationKey;
    public final ItemStack spiritStack;

    public SpiritTextPage(String headlineTranslationKey, String descriptionTranslationKey, ItemStack spiritStack) {
        this.headlineTranslationKey = headlineTranslationKey;
        this.descriptionTranslationKey = descriptionTranslationKey;
        this.spiritStack = spiritStack;
    }

    public String headlineTranslationKey() {
        return headlineTranslationKey;
    }

    public String descriptionTranslationKey() {
        return descriptionTranslationKey;
    }

    @Override
    public MalumPageTypeRegistry.PageType<SpiritTextPage> type() {
        return MalumPageTypeRegistry.SPIRIT_TEXT_PAGE_TYPE;
    }
}
