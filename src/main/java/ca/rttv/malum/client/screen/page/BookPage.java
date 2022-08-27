package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.mojang.serialization.Codec;

public abstract class BookPage {
    public static final Codec<BookPage> CODEC = MalumPageTypeRegistry.PAGE_TYPE.getCodec().dispatch(BookPage::type, MalumPageTypeRegistry.PageType::codec);

    public boolean isValid() {
        return true;
    }

    public abstract MalumPageTypeRegistry.PageType<?> type();
}
