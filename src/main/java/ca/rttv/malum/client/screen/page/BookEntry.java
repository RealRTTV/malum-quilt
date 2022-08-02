package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.registry.MalumEntryObjectTypeRegistry;
import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.QuiltLoader;

import java.util.ArrayList;
import java.util.Arrays;

public class BookEntry {
    public final ItemStack[] iconStacks;
    public final String id;
    public final int xOffset;
    public final int yOffset;
    public final ArrayList<BookPage> pages = new ArrayList<>();
    public EntryObjectSupplier objectSupplier = MalumEntryObjectTypeRegistry.ENTRY_OBJECT;

    public BookEntry(String id, Item item, int xOffset, int yOffset) {
        this.iconStacks = new ItemStack[]{item.getDefaultStack()};
        this.id = id;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public BookEntry(String id, Item[] stacks, int xOffset, int yOffset) {
        this.iconStacks = Arrays.stream(stacks).map(Item::getDefaultStack).toArray(ItemStack[]::new);
        this.id = id;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public BookEntry(JsonObject json) {
        id = json.get("name").getAsString();
        JsonElement icons = json.get("icon");
        if (icons instanceof JsonPrimitive) {
            iconStacks = new ItemStack[]{Registry.ITEM.get(new Identifier(icons.getAsString())).getDefaultStack()};
        } else if (icons instanceof JsonArray jsonArray) {
            iconStacks = new ItemStack[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                iconStacks[i] = Registry.ITEM.get(new Identifier(jsonArray.get(i).getAsString())).getDefaultStack();
            }
        } else {
            throw new IllegalStateException("'icon' element must be of type string or an array of strings");
        }
        xOffset = json.get("x_offset").getAsInt();
        yOffset = json.get("y_offset").getAsInt();
        objectSupplier = MalumEntryObjectTypeRegistry.ENTRY_OBJECT_SUPPLIER.get(new Identifier(json.get("entry_type").getAsString()));
        JsonArray pageArray = json.get("pages").getAsJsonArray();
        pages.ensureCapacity(pageArray.size());
        pageArray.forEach(pageJson -> {
            String type = pageJson.getAsJsonObject().get("type").getAsString();
            MalumPageTypeRegistry.PageType<?> pageType = MalumPageTypeRegistry.PAGE_TYPE.get(new Identifier(type));
            if (pageType == null) throw new NullPointerException("PageType is not in page registry (the input 'type' " + type + " is not a valid page type)");
            BookPage page = pageType.create(pageJson.getAsJsonObject());
            pages.add(page);
        });
    }

    public String translationKey() {
        return "malum.gui.book.entry." + id; // todo, fix
    }

    public String descriptionTranslationKey() {
        return "malum.gui.book.entry." + id + ".description"; // todo, fix
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

    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("name", id);

        if (iconStacks.length == 1) {
            json.addProperty("icon", Registry.ITEM.getId(iconStacks[0].getItem()).toString());
        } else {
            JsonArray icons = new JsonArray();
            for (ItemStack stack : iconStacks) {
                icons.add(Registry.ITEM.getId(stack.getItem()).toString());
            }
            json.add("icon", icons);
        }

        json.addProperty("x_offset", xOffset);
        json.addProperty("y_offset", yOffset);
        json.addProperty("entry_type", MalumEntryObjectTypeRegistry.ENTRY_OBJECT_SUPPLIER.getId(objectSupplier).toString());

        JsonArray pages = new JsonArray();
        this.pages.forEach(page -> pages.add(page.serialize()));

        json.add("pages", pages);
        return json;
    }

    public interface EntryObjectSupplier {
        EntryObject getBookObject(BookEntry entry, int x, int y);
    }
}
