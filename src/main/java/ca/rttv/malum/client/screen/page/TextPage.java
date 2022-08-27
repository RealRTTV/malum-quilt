package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class TextPage extends BookPage {
    public static final Codec<TextPage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("translation_key").forGetter(TextPage::translationKey)
    ).apply(instance, TextPage::new));

    public final String translationKey;

    public TextPage(String translationKey) {
        this.translationKey = translationKey;
    }

    public String translationKey() {
        return translationKey;
    }

    @Override
    public MalumPageTypeRegistry.PageType<TextPage> type() {
        return MalumPageTypeRegistry.TEXT_PAGE_TYPE;
    }
}
