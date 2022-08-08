package ca.rttv.malum.registry;

import ca.rttv.malum.client.screen.page.*;
import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import static ca.rttv.malum.Malum.MODID;

@SuppressWarnings("unused")
public interface MalumPageTypeRegistry {
    RegistryKey<Registry<PageType>> PAGE_TYPE_KEY = RegistryKey.ofRegistry(new Identifier(MODID, "page_type"));
    Registry<PageType> PAGE_TYPE = Registry.registerSimple(PAGE_TYPE_KEY, registry -> MalumPageTypeRegistry.CRAFTING_PAGE_TYPE);
    Codec<BookPage> CODEC = PAGE_TYPE.getCodec().dispatch(BookPage::type, PageType::codec);

    @SuppressWarnings("ClassCanBeRecord") // what if a frendo wants to make their own page type which works differently?
    class PageType {
        private final Codec<? extends BookPage> codec;

        public PageType(Codec<? extends BookPage> codec) {
            this.codec = codec;
        }

        public Codec<? extends BookPage> codec() {
            return codec;
        }
    }

    PageType CRAFTING_PAGE_TYPE        = register("crafting",        new PageType(CraftingBookPage.CODEC));
    PageType DOUBLE_HEADLINE_PAGE_TYPE = register("double_headline", new PageType(DoubleHeadlineTextPage.CODEC));
    PageType HEADLINE_PAGE_TYPE        = register("headline",        new PageType(HeadlineTextPage.CODEC));
    PageType SMELTING_PAGE_TYPE        = register("smelting",        new PageType(SmeltingBookPage.CODEC));
    PageType SPIRIT_CRUCIBLE_PAGE_TYPE = register("spirit_crucible", new PageType(SpiritCruciblePage.CODEC));
    PageType SPIRIT_INFUSION_PAGE_TYPE = register("spirit_infusion", new PageType(SpiritInfusionPage.CODEC));
    PageType SPIRIT_REPAIR_PAGE_TYPE   = register("spirit_repair",   new PageType(SpiritRepairPage.CODEC));
    PageType SPIRIT_RITE_PAGE_TYPE     = register("rite",            new PageType(SpiritRitePage.CODEC));
    PageType SPIRIT_TEXT_PAGE_TYPE     = register("item",            new PageType(SpiritTextPage.CODEC));
    PageType TEXT_PAGE_TYPE            = register("text",            new PageType(TextPage.CODEC));

    static PageType register(String id, PageType type) {
        return Registry.register(PAGE_TYPE, new Identifier(MODID, id), type);
    }

    static void init() {

    }
}
