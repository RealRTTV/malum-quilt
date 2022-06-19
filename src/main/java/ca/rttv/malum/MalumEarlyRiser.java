package ca.rttv.malum;

import ca.rttv.malum.util.ModdedErrStream;
import com.chocohead.mm.api.ClassTinkerers;
import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import com.mojang.logging.LogUtils;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvents;
import org.quiltmc.loader.api.MappingResolver;
import org.quiltmc.loader.api.QuiltLoader;

import java.util.function.Supplier;

import static ca.rttv.malum.registry.MalumPlanksRegistry.RUNEWOOD_PLANKS;
import static ca.rttv.malum.registry.MalumPlanksRegistry.SOULWOOD_PLANKS;
import static ca.rttv.malum.registry.MalumItemRegistry.*;

public final class MalumEarlyRiser implements Runnable {
    @SuppressWarnings("rawtypes")
    @Override
    public void run() {
        MixinExtrasBootstrap.init();
        MappingResolver mappings = QuiltLoader.getMappingResolver();
        final String type = mappings.mapClassName("intermediary", "net.minecraft.class_1690$class_1692");
        final String enchantmentTarget = mappings.mapClassName("intermediary", "net.minecraft.class_1886");
        final String armorMaterialsTarget = mappings.mapClassName("intermediary", "net.minecraft.class_1740");
        final String toolMaterialsTarget = mappings.mapClassName("intermediary", "net.minecraft.class_1834");
        final String boatParam1 = "L" + mappings.mapClassName("intermediary", "net.minecraft.class_2248") + ";";
        final String armorParam5 = "L" + mappings.mapClassName("intermediary", "net.minecraft.class_3414") + ";";
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
                              () -> (new Object[]{"soul_cloak", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, (Supplier) () -> Ingredient.ofItems(SPIRIT_FABRIC)})
                ).addEnumSubclass(
                        "SOUL_STAINED_STEEL",
                     "ca.rttv.malum.item.SoulStainedSteelArmorMaterial",
                              () -> (new Object[]{"soul_stained_steel", 22, new int[]{2, 6, 7, 3}, 13, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0f, 0.0f, (Supplier) () -> Ingredient.ofItems(SOUL_STAINED_STEEL_INGOT)})
                ).build();

        // toolMaterials
        ClassTinkerers.enumBuilder(toolMaterialsTarget, int.class, int.class, float.class, float.class, int.class, Supplier.class)
                .addEnum("SOUL_STAINED_STEEL", 3, 1250, 7.5f, 2.5f, 16, (Supplier) () -> Ingredient.ofItems(SOUL_STAINED_STEEL_INGOT))
                .addEnum("TWISTED_ROCK", 3, 850, 8.0f, 1.0f, 12, (Supplier) () -> Ingredient.ofItems(TWISTED_ROCK)).build();
    }

    static {
        System.setErr(new ModdedErrStream("STDERR", System.err, LogUtils.getLogger().isDebugEnabled()));
    }
}
