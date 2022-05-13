package ca.rttv.malum.client.screen.page;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.quiltmc.loader.api.QuiltLoader;

import java.util.ArrayList;

public class BookEntry {
    public final ItemStack iconStack;
    public final String id;
    public final int xOffset;
    public final int yOffset;
    public final ArrayList<BookPage> pages = new ArrayList<>();
    public EntryObjectSupplier objectSupplier = EntryObject::new;

    public BookEntry(String id, Item item, int xOffset, int yOffset) {
        this.iconStack = item.getDefaultStack();
        this.id = id;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public String translationKey() {
        return "malum.gui.book.entry." + id;
    }

    public String descriptionTranslationKey() {
        return "malum.gui.book.entry." + id + ".description";
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