package ca.rttv.malum.util.listener;

import ca.rttv.malum.client.screen.page.BookEntry;
import ca.rttv.malum.client.screen.page.TextPage;
import ca.rttv.malum.registry.MalumEntryObjectTypeRegistry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import net.minecraft.item.Items;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.quiltmc.qsl.resource.loader.api.reloader.IdentifiableResourceReloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;

public class ProgressionBookEntriesReloadListener extends JsonDataLoader implements IdentifiableResourceReloader {
    private static final Gson GSON = new GsonBuilder().create();
    public static final List<BookEntry> SERVER_ENTRIES = new ArrayList<>();

    public ProgressionBookEntriesReloadListener() {
        super(GSON, "book_entries");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        SERVER_ENTRIES.clear();
        prepared.forEach((id, element) -> SERVER_ENTRIES.add(BookEntry.CODEC.decode(JsonOps.INSTANCE, element)
                                                                            .result()
                                                                            .orElseGet(() -> Pair.of(new BookEntry(new Identifier("null"), List.of(Items.AIR.getDefaultStack()), 0, 0, MalumEntryObjectTypeRegistry.ENTRY_OBJECT, List.of(new TextPage("null")), "null", "null"), JsonNull.INSTANCE))
                                                                            .getFirst()));
    }

    @Override
    public Identifier getQuiltId() {
        return new Identifier(MODID, "book_entries");
    }
}
