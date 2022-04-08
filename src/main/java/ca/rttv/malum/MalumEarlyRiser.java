package ca.rttv.malum;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

import static ca.rttv.malum.registry.MalumRegistry.RUNEWOOD_PLANKS;
import static ca.rttv.malum.registry.MalumRegistry.SOULWOOD_PLANKS;

public class MalumEarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
        String type = remapper.mapClassName("intermediary", "net.minecraft.class_1690$class_1692");
        String enchantmentTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1886");
        String param1 = "L" + remapper.mapClassName("intermediary", "net.minecraft.class_2248") + ";";
        ClassTinkerers.enumBuilder(type, param1, String.class).addEnum("RUNEWOOD", () -> new Object[]{RUNEWOOD_PLANKS, "runewood"}).build();
        ClassTinkerers.enumBuilder(type, param1, String.class).addEnum("SOULWOOD", () -> new Object[]{SOULWOOD_PLANKS, "soulwood"}).build();

        //enchantmentTarget
        ClassTinkerers.enumBuilder(enchantmentTarget, new Class[0]).addEnumSubclass("SCYTHE", "ca.rttv.malum.enchantment.ScytheEnchantmentTarget").build();
        ClassTinkerers.enumBuilder(enchantmentTarget, new Class[0]).addEnumSubclass("REBOUND_SCYTHE", "ca.rttv.malum.enchantment.ReboundScytheEnchantmentTarget").build();
        ClassTinkerers.enumBuilder(enchantmentTarget, new Class[0]).addEnumSubclass("SOUL_HUNTER", "ca.rttv.malum.enchantment.SoulHunterWeaponEnchantmentTarget").build();

    }
}
