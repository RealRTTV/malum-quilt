package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.registry.MalumPageTypeRegistry;
import ca.rttv.malum.registry.MalumRiteRegistry;
import ca.rttv.malum.rite.Rite;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class SpiritRitePage extends BookPage {
    public static final Codec<SpiritRitePage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        MalumRiteRegistry.RITE.getCodec().fieldOf("rite").forGetter(page -> page.rite)
    ).apply(instance, SpiritRitePage::new));

    public final Rite rite;

    public SpiritRitePage(Rite rite) {
        this.rite = rite;
    }

    @Override
    public MalumPageTypeRegistry.PageType<SpiritRitePage> type() {
        return MalumPageTypeRegistry.SPIRIT_RITE_PAGE_TYPE;
    }
}
