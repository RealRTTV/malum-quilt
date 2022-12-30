package ca.rttv.malum.registry;

import ca.rttv.malum.enchantment.HauntedEnchantment;
import ca.rttv.malum.enchantment.ReboundEnchantment;
import ca.rttv.malum.enchantment.SpiritPlunderEnchantment;
import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;

public interface MalumEnchantments {
     Map<Identifier, Enchantment> ENCHANTMENTS = new LinkedHashMap<>();
     EnchantmentTarget SCYTHE = ClassTinkerers.getEnum(EnchantmentTarget.class, "SCYTHE");
     EnchantmentTarget REBOUND_SCYTHE = ClassTinkerers.getEnum(EnchantmentTarget.class, "REBOUND_SCYTHE");
     EnchantmentTarget SOUL_HUNTER_WEAPON = ClassTinkerers.getEnum(EnchantmentTarget.class, "SOUL_HUNTER");
     Enchantment REBOUND = register("rebound", new ReboundEnchantment(Enchantment.Rarity.UNCOMMON, REBOUND_SCYTHE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
     Enchantment HAUNTED = register("haunted", new HauntedEnchantment(Enchantment.Rarity.UNCOMMON, SOUL_HUNTER_WEAPON, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
     Enchantment SPIRIT_PLUNDER = register("spirit_plunder", new SpiritPlunderEnchantment(Enchantment.Rarity.COMMON, SOUL_HUNTER_WEAPON, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));

    static <T extends Enchantment> T register(String id, T enchantment) {
        ENCHANTMENTS.put(new Identifier(MODID, id), enchantment);
        return enchantment;
    }

    static void init() {
        ENCHANTMENTS.forEach((id, enchantment) -> Registry.register(Registries.ENCHANTMENT, id, enchantment));
    }
}
