package ca.rttv.malum.registry;

import ca.rttv.malum.client.recipe.*;
import ca.rttv.malum.client.screen.page.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

@SuppressWarnings("unused")
public interface MalumPageRendererRegistry {
    RegistryKey<Registry<PageRendererFactory<?>>> PAGE_RENDERER_FACTORY_KEY = RegistryKey.ofRegistry(new Identifier("malum", "page_renderer_factory"));
    Registry<PageRendererFactory<?>> PAGE_RENDERER_FACTORY = Registry.registerSimple(PAGE_RENDERER_FACTORY_KEY, registry -> MalumPageRendererRegistry.CRAFTING);

    interface PageRendererFactory<T extends BookPage> {
        BookPageRenderer<T> create(T page);

        static <T extends BookPage> BookPageRenderer<T> create(PageRendererFactory<T> instance, BookPage page) {
            //noinspection unchecked -- checked, all uses of this have been checked
            return instance.create((T) page);
        }
    }

    PageRendererFactory<CraftingBookPage> CRAFTING = register(MalumPageTypeRegistry.CRAFTING_PAGE_TYPE, CraftingPageRenderer::new);
    PageRendererFactory<DoubleHeadlineTextPage> DOUBLE_HEADLINE_TEXT = register(MalumPageTypeRegistry.DOUBLE_HEADLINE_PAGE_TYPE, DoubleHeadlineTextPageRenderer::new);
    PageRendererFactory<HeadlineTextPage> HEADLINE_TEXT = register(MalumPageTypeRegistry.HEADLINE_PAGE_TYPE, HeadlineTextPageRenderer::new);
    PageRendererFactory<SmeltingBookPage> SMELTING = register(MalumPageTypeRegistry.SMELTING_PAGE_TYPE, SmeltingBookPageRenderer::new);
    PageRendererFactory<SpiritCruciblePage> SPIRIT_CRUCIBLE = register(MalumPageTypeRegistry.SPIRIT_CRUCIBLE_PAGE_TYPE, SpiritCruciblePageRenderer::new);
    PageRendererFactory<SpiritInfusionPage> SPIRIT_INFUSION = register(MalumPageTypeRegistry.SPIRIT_INFUSION_PAGE_TYPE, SpiritInfusionPageRenderer::new);
    PageRendererFactory<SpiritRepairPage> SPIRIT_REPAIR = register(MalumPageTypeRegistry.SPIRIT_REPAIR_PAGE_TYPE, SpiritRepairPageRenderer::new);
    PageRendererFactory<SpiritRitePage> SPIRIT_RITE = register(MalumPageTypeRegistry.SPIRIT_RITE_PAGE_TYPE, SpiritRitePageRenderer::new);
    PageRendererFactory<SpiritTextPage> SPIRIT_TEXT = register(MalumPageTypeRegistry.SPIRIT_TEXT_PAGE_TYPE, SpiritTextPageRenderer::new);
    PageRendererFactory<TextPage> TEXT = register(MalumPageTypeRegistry.TEXT_PAGE_TYPE, TextPageRenderer::new);

    static <T extends BookPage> PageRendererFactory<T> register(MalumPageTypeRegistry.PageType<T> type, PageRendererFactory<T> factory) {
        Registry.register(PAGE_RENDERER_FACTORY, MalumPageTypeRegistry.PAGE_TYPE.getId(type), factory);
        return factory;
    }
}
