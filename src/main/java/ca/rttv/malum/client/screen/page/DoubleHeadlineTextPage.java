package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class DoubleHeadlineTextPage extends BookPage {
    public static final Codec<DoubleHeadlineTextPage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("first_headline_translation_key").forGetter(DoubleHeadlineTextPage::firstHeadlineTranslationKey),
        Codec.STRING.fieldOf("first_description_translation_key").forGetter(DoubleHeadlineTextPage::firstDescriptionTranslationKey),
        Codec.STRING.fieldOf("second_headline_translation_key").forGetter(DoubleHeadlineTextPage::secondHeadlineTranslationKey),
        Codec.STRING.fieldOf("second_description_translation_key").forGetter(DoubleHeadlineTextPage::secondDescriptionTranslationKey)
    ).apply(instance, DoubleHeadlineTextPage::new));

    private final String firstHeadlineTranslationKey;
    private final String firstDescriptionTranslationKey;
    private final String secondHeadlineTranslationKey;
    private final String secondDescriptionTranslationKey;

    public DoubleHeadlineTextPage(String firstHeadlineTranslationKey, String firstDescriptionTranslationKey, String secondHeadlineTranslationKey, String secondDescriptionTranslationKey) {
        this.firstHeadlineTranslationKey = firstHeadlineTranslationKey;
        this.firstDescriptionTranslationKey = firstDescriptionTranslationKey;
        this.secondHeadlineTranslationKey = secondHeadlineTranslationKey;
        this.secondDescriptionTranslationKey = secondDescriptionTranslationKey;
    }

    public String firstHeadlineTranslationKey() {
        return firstHeadlineTranslationKey;
    }

    public String firstDescriptionTranslationKey() {
        return firstDescriptionTranslationKey;
    }

    public String secondHeadlineTranslationKey() {
        return secondHeadlineTranslationKey;
    }

    public String secondDescriptionTranslationKey() {
        return secondDescriptionTranslationKey;
    }

    @Override
    public MalumPageTypeRegistry.PageType<DoubleHeadlineTextPage> type() {
        return MalumPageTypeRegistry.DOUBLE_HEADLINE_PAGE_TYPE;
    }
}
