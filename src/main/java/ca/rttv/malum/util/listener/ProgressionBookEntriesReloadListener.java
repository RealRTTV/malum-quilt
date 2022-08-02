package ca.rttv.malum.util.listener;

import ca.rttv.malum.client.screen.page.BookEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProgressionBookEntriesReloadListener extends JsonDataLoader {
    private static final Gson GSON = new GsonBuilder().create();
    public static final List<BookEntry> SERVER_ENTRIES = new ArrayList<>();

    public ProgressionBookEntriesReloadListener() {
        super(GSON, "book_entries");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        SERVER_ENTRIES.clear();
//        prepared.forEach((id, element) -> SERVER_ENTRIES.add(new BookEntry(element.getAsJsonObject())));
    }
}
