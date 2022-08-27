package ca.rttv.malum.registry;

import ca.rttv.malum.client.screen.page.*;
import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import static ca.rttv.malum.Malum.MODID;

@SuppressWarnings("unused")
public interface MalumPageTypeRegistry {
    RegistryKey<Registry<PageType<?>>> PAGE_TYPE_KEY = RegistryKey.ofRegistry(new Identifier(MODID, "page_type"));
    Registry<PageType<?>> PAGE_TYPE = Registry.registerSimple(PAGE_TYPE_KEY, registry -> MalumPageTypeRegistry.CRAFTING_PAGE_TYPE);
    Codec<BookPage> CODEC = PAGE_TYPE.getCodec().dispatch(BookPage::type, PageType::codec);

    @SuppressWarnings("ClassCanBeRecord") // what if a frendo wants to make their own page type which works differently?
    class PageType<T extends BookPage> {
        private final Codec<T> codec;

        public PageType(Codec<T> codec) {
            this.codec = codec;
        }

        public Codec<T> codec() {
            return codec;
        }
    }

    PageType<CraftingBookPage> CRAFTING_PAGE_TYPE        = register("crafting",        new PageType<>(CraftingBookPage.CODEC));
    PageType<DoubleHeadlineTextPage> DOUBLE_HEADLINE_PAGE_TYPE = register("double_headline", new PageType<>(DoubleHeadlineTextPage.CODEC));
    PageType<HeadlineTextPage> HEADLINE_PAGE_TYPE        = register("headline",        new PageType<>(HeadlineTextPage.CODEC));
    PageType<SmeltingBookPage> SMELTING_PAGE_TYPE        = register("smelting",        new PageType<>(SmeltingBookPage.CODEC));
    PageType<SpiritCruciblePage> SPIRIT_CRUCIBLE_PAGE_TYPE = register("spirit_crucible", new PageType<>(SpiritCruciblePage.CODEC));
    PageType<SpiritInfusionPage> SPIRIT_INFUSION_PAGE_TYPE = register("spirit_infusion", new PageType<>(SpiritInfusionPage.CODEC));
    PageType<SpiritRepairPage> SPIRIT_REPAIR_PAGE_TYPE   = register("spirit_repair",   new PageType<>(SpiritRepairPage.CODEC));
    PageType<SpiritRitePage> SPIRIT_RITE_PAGE_TYPE     = register("rite",            new PageType<>(SpiritRitePage.CODEC));
    PageType<SpiritTextPage> SPIRIT_TEXT_PAGE_TYPE     = register("item",            new PageType<>(SpiritTextPage.CODEC));
    PageType<TextPage> TEXT_PAGE_TYPE            = register("text",            new PageType<>(TextPage.CODEC));

    static <T extends BookPage> PageType<T> register(String id, PageType<T> type) {
        return Registry.register(PAGE_TYPE, new Identifier(MODID, id), type);
    }

    static void init() {

    }
}
