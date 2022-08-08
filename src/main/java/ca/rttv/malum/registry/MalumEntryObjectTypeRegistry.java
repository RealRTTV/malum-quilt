package ca.rttv.malum.registry;

import ca.rttv.malum.client.screen.page.BookEntry;
import ca.rttv.malum.client.screen.page.EntryObject;
import ca.rttv.malum.client.screen.page.ImportantEntryObject;
import ca.rttv.malum.client.screen.page.VanishingEntryObject;
import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import static ca.rttv.malum.Malum.MODID;

public interface MalumEntryObjectTypeRegistry {
    RegistryKey<Registry<BookEntry.EntryObjectSupplier>> ENTRY_OBJECT_SUPPLIER_KEY = RegistryKey.ofRegistry(new Identifier(MODID, "entry_object_supplier"));
    Registry<BookEntry.EntryObjectSupplier> ENTRY_OBJECT_SUPPLIER = Registry.registerSimple(ENTRY_OBJECT_SUPPLIER_KEY, registry -> MalumEntryObjectTypeRegistry.ENTRY_OBJECT);
    Codec<BookEntry.EntryObjectSupplier> CODEC = ENTRY_OBJECT_SUPPLIER.getCodec();

    BookEntry.EntryObjectSupplier ENTRY_OBJECT           = register("entry_object",           EntryObject::new);
    BookEntry.EntryObjectSupplier IMPORTANT_ENTRY_OBJECT = register("important_entry_object", ImportantEntryObject::new);
    BookEntry.EntryObjectSupplier VANISHING_ENTRY_OBJECT = register("vanishing_entry_object", VanishingEntryObject::new);

    static BookEntry.EntryObjectSupplier register(String id, BookEntry.EntryObjectSupplier supplier) {
        return Registry.register(ENTRY_OBJECT_SUPPLIER, new Identifier(MODID, id), supplier);
    }
}
