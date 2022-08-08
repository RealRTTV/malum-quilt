package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.registry.MalumEntryObjectTypeRegistry;
import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.QuiltLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookEntry {
    public static final Codec<BookEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("name").forGetter(page -> page.id),
        ItemStack.CODEC.listOf().fieldOf("icon_stacks").forGetter(page -> Arrays.asList(page.iconStacks)),
        Codec.INT.fieldOf("x_offset").forGetter(page -> page.xOffset),
        Codec.INT.fieldOf("y_offset").forGetter(page -> page.yOffset),
        MalumEntryObjectTypeRegistry.CODEC.fieldOf("object_supplier").forGetter(page -> page.objectSupplier),
        MalumPageTypeRegistry.CODEC.listOf().fieldOf("pages").forGetter(page -> page.pages),
        Codec.STRING.fieldOf("translation_key").forGetter(page -> page.translationKey),
        Codec.STRING.fieldOf("description_translation_key").forGetter(page -> page.descriptionTranslationKey)
    ).apply(instance, BookEntry::new));

    public final ItemStack[] iconStacks;
    public final String id;
    public final int xOffset;
    public final int yOffset;
    public final List<BookPage> pages;
    public EntryObjectSupplier objectSupplier;
    public String translationKey;
    public String descriptionTranslationKey;

    public BookEntry(String id, Item item, int xOffset, int yOffset) {
        this.iconStacks = new ItemStack[]{item.getDefaultStack()};
        this.id = id;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.pages = new ArrayList<>();
        this.objectSupplier = MalumEntryObjectTypeRegistry.ENTRY_OBJECT;
        this.translationKey = "malum.gui.book.entry." + id;
        this.descriptionTranslationKey = "malum.gui.book.entry." + id + ".description";
    }

    public BookEntry(String id, Item[] stacks, int xOffset, int yOffset) {
        this.iconStacks = Arrays.stream(stacks).map(Item::getDefaultStack).toArray(ItemStack[]::new);
        this.id = id;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.pages = new ArrayList<>();
        this.objectSupplier = MalumEntryObjectTypeRegistry.ENTRY_OBJECT;
        this.translationKey = "malum.gui.book.entry." + id;
        this.descriptionTranslationKey = "malum.gui.book.entry." + id + ".description";
    }

    public BookEntry(String id, List<ItemStack> iconStacks, int xOffset, int yOffset, EntryObjectSupplier objectSupplier, List<BookPage> pages, String translationKey, String descriptionTranslationKey) {
        this.id = id;
        this.iconStacks = iconStacks.toArray(ItemStack[]::new);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.objectSupplier = objectSupplier;
        this.pages = pages;
        this.translationKey = translationKey;
        this.descriptionTranslationKey = descriptionTranslationKey;
    }

    public String translationKey() {
        return translationKey;
    }

    public String descriptionTranslationKey() {
        return descriptionTranslationKey;
    }

    public BookEntry addPage(BookPage page) {
        if (page.isValid()) {
            pages.add(page);
        }
        return this;
    }

    public BookEntry addModCompatPage(BookPage page, String modId) {
        if (QuiltLoader.isModLoaded(modId)) {
            pages.add(page);
        }
        return this;
    }

    public BookEntry setObjectSupplier(EntryObjectSupplier objectSupplier) {
        this.objectSupplier = objectSupplier;
        return this;
    }

    public interface EntryObjectSupplier {
        EntryObject getBookObject(BookEntry entry, int x, int y);
    }
}
