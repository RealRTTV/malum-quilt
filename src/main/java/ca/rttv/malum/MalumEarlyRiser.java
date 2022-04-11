package ca.rttv.malum;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvents;

import java.util.function.Supplier;

import static ca.rttv.malum.registry.MalumRegistry.RUNEWOOD_PLANKS;
import static ca.rttv.malum.registry.MalumRegistry.SOULWOOD_PLANKS;

public class MalumEarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
        String type = remapper.mapClassName("intermediary", "net.minecraft.class_1690$class_1692");
        String enchantmentTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1886");
        String armorMaterialsTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1740");
        String boatParam1 = "L" + remapper.mapClassName("intermediary", "net.minecraft.class_2248") + ";";
        String armorParam5 = "L" + remapper.mapClassName("intermediary", "net.minecraft.class_3414") + ";";
        ClassTinkerers.enumBuilder(type, boatParam1, String.class).addEnum("RUNEWOOD", () -> new Object[]{RUNEWOOD_PLANKS, "runewood"}).build();
        ClassTinkerers.enumBuilder(type, boatParam1, String.class).addEnum("SOULWOOD", () -> new Object[]{SOULWOOD_PLANKS, "soulwood"}).build();

        //enchantmentTarget
        ClassTinkerers.enumBuilder(enchantmentTarget, new Class[0]).addEnumSubclass("SCYTHE", "ca.rttv.malum.enchantment.ScytheEnchantmentTarget").build();
        ClassTinkerers.enumBuilder(enchantmentTarget, new Class[0]).addEnumSubclass("REBOUND_SCYTHE", "ca.rttv.malum.enchantment.ReboundScytheEnchantmentTarget").build();
        ClassTinkerers.enumBuilder(enchantmentTarget, new Class[0]).addEnumSubclass("SOUL_HUNTER", "ca.rttv.malum.enchantment.SoulHunterWeaponEnchantmentTarget").build();

        // armorMaterials
        ClassTinkerers.enumBuilder(armorMaterialsTarget, String.class, int.class, int[].class, int.class, armorParam5, float.class, float.class, Supplier.class)
                .addEnumSubclass(
                        "SOUL_CLOAK",
                     "ca.rttv.malum.item.SoulCloakArmorMaterial",
                              () -> (new Object[]{"soul_cloak", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, (Supplier<Ingredient>) () -> Ingredient.ofItems(Items.LEATHER)})
                ).build();
    }
}
