package ca.rttv.malum.registry;

import ca.rttv.malum.enchantment.ReboundEnchantment;
import ca.rttv.malum.enchantment.SpiritPlunderEnchantment;
import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;

public interface MalumEnchantments {
     Map<Identifier, Enchantment> ENCHANTMENTS = new LinkedHashMap<>();
     EnchantmentTarget SCYTHE = ClassTinkerers.getEnum(EnchantmentTarget.class, "SCYTHE");
     EnchantmentTarget REBOUND_SCYTHE = ClassTinkerers.getEnum(EnchantmentTarget.class, "REBOUND_SCYTHE");
     EnchantmentTarget SOUL_HUNTER_WEAPON = ClassTinkerers.getEnum(EnchantmentTarget.class, "SOUL_HUNTER");
     Enchantment REBOUND = create("rebound", new ReboundEnchantment(Enchantment.Rarity.UNCOMMON, REBOUND_SCYTHE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
//     Enchantment HAUNTED = create("haunted", HauntedEnchantment::new);
     Enchantment SPIRIT_PLUNDER = create("spirit_plunder", new SpiritPlunderEnchantment());

    static <T extends Enchantment> T create(String id, T enchantment) {
        ENCHANTMENTS.put(new Identifier(MODID, id), enchantment);
        return enchantment;
    }

    static void init() {
        ENCHANTMENTS.forEach((id, enchantment) -> Registry.register(Registry.ENCHANTMENT, id, enchantment));
    }
}
