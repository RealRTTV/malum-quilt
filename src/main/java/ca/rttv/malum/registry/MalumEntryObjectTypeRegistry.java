package ca.rttv.malum.registry;

import ca.rttv.malum.client.screen.page.BookEntry;
import ca.rttv.malum.client.screen.page.EntryObject;
import ca.rttv.malum.client.screen.page.ImportantEntryObject;
import ca.rttv.malum.client.screen.page.VanishingEntryObject;
import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Malum.MODID;

public interface MalumEntryObjectTypeRegistry {
    RegistryKey<Registry<BookEntry.EntryObjectSupplier>> ENTRY_OBJECT_SUPPLIER_KEY = RegistryKey.ofRegistry(new Identifier(MODID, "entry_object_supplier"));
    Registry<BookEntry.EntryObjectSupplier> ENTRY_OBJECT_SUPPLIER = Registries.registerSimple(ENTRY_OBJECT_SUPPLIER_KEY, registry -> MalumEntryObjectTypeRegistry.ENTRY_OBJECT);
    Codec<BookEntry.EntryObjectSupplier> CODEC = ENTRY_OBJECT_SUPPLIER.getCodec();

    BookEntry.EntryObjectSupplier ENTRY_OBJECT           = register("entry_object",           EntryObject::new);
    BookEntry.EntryObjectSupplier IMPORTANT_ENTRY_OBJECT = register("important_entry_object", ImportantEntryObject::new);
    BookEntry.EntryObjectSupplier VANISHING_ENTRY_OBJECT = register("vanishing_entry_object", VanishingEntryObject::new);

    static void initater() {}

    static BookEntry.EntryObjectSupplier register(String id, BookEntry.EntryObjectSupplier supplier) {
        return Registry.register(ENTRY_OBJECT_SUPPLIER, new Identifier(MODID, id), supplier);
    }
}
