package ca.rttv.malum.registry;

import ca.rttv.malum.enchantment.ReboundEnchantment;
import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;

public class MalumEnchantments {
    private static final Map<Identifier, Enchantment> ENCHANTMENTS = new LinkedHashMap<>();
    public static final EnchantmentTarget SCYTHE = ClassTinkerers.getEnum(EnchantmentTarget.class, "SCYTHE");
    public static final EnchantmentTarget REBOUND_SCYTHE = ClassTinkerers.getEnum(EnchantmentTarget.class, "REBOUND_SCYTHE");
    public static final EnchantmentTarget SOUL_HUNTER_WEAPON = ClassTinkerers.getEnum(EnchantmentTarget.class, "SOUL_HUNTER");
    public static final Enchantment REBOUND = create("rebound", new ReboundEnchantment(Enchantment.Rarity.UNCOMMON, REBOUND_SCYTHE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
//    public static final Enchantment HAUNTED = create("haunted", HauntedEnchantment::new);
//    public static final Enchantment SPIRIT_PLUNDER = create("spirit_plunder", SpiritPlunderEnchantment::new);
    private static <T extends Enchantment> Enchantment create(String id, Enchantment enchantment) {
        ENCHANTMENTS.put(new Identifier(MODID, id), enchantment);
        return enchantment;
    }
    public static void init() {
        ENCHANTMENTS.forEach((id, enchantment) -> Registry.register(Registry.ENCHANTMENT, id, enchantment));
    }
}
