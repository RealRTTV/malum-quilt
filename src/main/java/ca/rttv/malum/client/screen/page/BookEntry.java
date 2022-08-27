package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.recipe.BookPageRenderer;
import ca.rttv.malum.registry.MalumEntryObjectTypeRegistry;
import ca.rttv.malum.registry.MalumPageRendererRegistry;
import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookEntry {
    public static final Codec<BookEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Identifier.CODEC.fieldOf("name").forGetter(page -> page.id),
        ItemStack.CODEC.listOf().fieldOf("icon_stacks").forGetter(page -> Arrays.asList(page.iconStacks)),
        Codec.INT.fieldOf("x_offset").forGetter(page -> page.xOffset),
        Codec.INT.fieldOf("y_offset").forGetter(page -> page.yOffset),
        MalumEntryObjectTypeRegistry.CODEC.fieldOf("object_supplier").forGetter(page -> page.objectSupplier),
        MalumPageTypeRegistry.CODEC.listOf().fieldOf("pages").forGetter(page -> page.pages),
        Codec.STRING.fieldOf("translation_key").forGetter(page -> page.translationKey),
        Codec.STRING.fieldOf("description_translation_key").forGetter(page -> page.descriptionTranslationKey)
    ).apply(instance, BookEntry::new));

    public final ItemStack[] iconStacks;
    public final Identifier id;
    public final int xOffset;
    public final int yOffset;
    public final List<BookPage> pages;
    public EntryObjectSupplier objectSupplier;
    public String translationKey;
    public String descriptionTranslationKey;
    private List<BookPageRenderer<?>> pageRenderers; // do not serialize

    public BookEntry(Identifier id, List<ItemStack> iconStacks, int xOffset, int yOffset, EntryObjectSupplier objectSupplier, List<BookPage> pages, String translationKey, String descriptionTranslationKey) {
        this.id = id;
        this.iconStacks = iconStacks.toArray(ItemStack[]::new);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.objectSupplier = objectSupplier;
        this.pages = pages;
        this.translationKey = translationKey;
        this.descriptionTranslationKey = descriptionTranslationKey;
        this.pageRenderers = null;
    }

    public String translationKey() {
        return translationKey;
    }

    public String descriptionTranslationKey() {
        return descriptionTranslationKey;
    }

    public interface EntryObjectSupplier {
        EntryObject getBookObject(BookEntry entry, int x, int y);
    }

    public List<BookPageRenderer<?>> pageRenderers() {
        if (pageRenderers == null) {
            //noinspection ConstantConditions -- checked
            pageRenderers = pages.stream().map(page -> MalumPageRendererRegistry.PageRendererFactory.create(MalumPageRendererRegistry.PAGE_RENDERER_FACTORY.get(MalumPageTypeRegistry.PAGE_TYPE.getId(page.type())), page)).collect(Collectors.toList());
        }
        return pageRenderers;
    }
}
