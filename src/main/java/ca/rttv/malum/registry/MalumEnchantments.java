package ca.rttv.malum.registry;

import ca.rttv.malum.enchantment.ReboundEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;

public class MalumEnchantments {
    private static final Map<Enchantment, Identifier> ENCHANTMENTS = new LinkedHashMap<>();
//    public static final EnchantmentTarget SOUL_HUNTER_WEAPON = EnchantmentTarget.create(MalumMod.MODID + ":soul_hunter_only", i -> i.getDefaultInstance().is(ItemTagRegistry.SOUL_HUNTER_WEAPON));
//    public static final EnchantmentTarget SCYTHE = EnchantmentTarget.create(MalumMod.MODID + ":scythe_only", i -> i.getDefaultInstance().is(ItemTagRegistry.SCYTHE));
//    public static final EnchantmentTarget REBOUND_SCYTHE = EnchantmentTarget.create(MalumMod.MODID + ":rebound_scythe_only", i -> i.getDefaultInstance().is(ItemTagRegistry.SCYTHE) || (CommonConfig.ULTIMATE_REBOUND.get() && i instanceof TieredItem));

    public static final Enchantment REBOUND = create("rebound", new ReboundEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
//    public static final RegistryObject<Enchantment> HAUNTED = ENCHANTMENTS.register("haunted", HauntedEnchantment::new);
//    public static final RegistryObject<Enchantment> SPIRIT_PLUNDER = ENCHANTMENTS.register("spirit_plunder", SpiritPlunderEnchantment::new);
    private static <T extends Enchantment> Enchantment create(String id, Enchantment enchantment) {
        ENCHANTMENTS.put(enchantment, new Identifier(MODID, id));
        return enchantment;
    }
    public static void init() {
        ENCHANTMENTS.keySet().forEach(effect -> Registry.register(Registry.ENCHANTMENT, ENCHANTMENTS.get(effect), effect));
    }
}
