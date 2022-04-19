package ca.rttv.malum.registry;

import ca.rttv.malum.Main;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface MalumTags {
    TagKey<Item> SCYTHE = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "scythe"));
    TagKey<Item> SOUL_HUNTER_WEAPON = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "soul_hunter_weapon"));
}
