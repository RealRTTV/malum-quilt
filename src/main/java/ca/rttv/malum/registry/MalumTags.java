package ca.rttv.malum.registry;

import ca.rttv.malum.Malum;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public interface MalumTags {
    TagKey<Item> SCYTHE             = TagKey.of(RegistryKeys.ITEM, new Identifier(Malum.MODID, "scythe"));
    TagKey<Item> SOUL_HUNTER_WEAPON = TagKey.of(RegistryKeys.ITEM, new Identifier(Malum.MODID, "soul_hunter_weapon"));

    static void init() {

    }
}
