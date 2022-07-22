package ca.rttv.malum.api.event;

import ca.rttv.malum.client.screen.page.BookEntry;
import net.fabricmc.fabric.api.event.Event;

import java.util.ArrayList;

import static net.fabricmc.fabric.api.event.EventFactory.createArrayBacked;


public interface ProgressionBookEntriesSetEvent {
    Event<ProgressionBookEntriesSetEvent> EVENT = createArrayBacked(ProgressionBookEntriesSetEvent.class, listeners -> (entries) -> {
        for (ProgressionBookEntriesSetEvent listener : listeners) {
            listener.addExtraEntry(entries);
        }
    });

    void addExtraEntry(ArrayList<BookEntry> bookEntry);
}
