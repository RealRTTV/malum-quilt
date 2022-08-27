package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class HeadlineTextPage extends BookPage {
    public static final Codec<HeadlineTextPage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("headline_translation_key").forGetter(HeadlineTextPage::headlineTranslationKey),
        Codec.STRING.fieldOf("description_translation_key").forGetter(HeadlineTextPage::descriptionTranslationKey)
    ).apply(instance, HeadlineTextPage::new));

    private final String headlineTranslationKey;
    private final String descriptionTranslationKey;

    public HeadlineTextPage(String headlineTranslationKey, String descriptionTranslationKey) {
        this.headlineTranslationKey = headlineTranslationKey;
        this.descriptionTranslationKey = descriptionTranslationKey;
    }

    public String headlineTranslationKey() {
        return headlineTranslationKey;
    }

    public String descriptionTranslationKey() {
        return descriptionTranslationKey;
    }

    @Override
    public MalumPageTypeRegistry.PageType<HeadlineTextPage> type() {
        return MalumPageTypeRegistry.HEADLINE_PAGE_TYPE;
    }
}
