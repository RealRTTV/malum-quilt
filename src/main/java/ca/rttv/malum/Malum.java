package ca.rttv.malum;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.RegistryEntries.ENCYCLOPEDIA_ARCANA;

public final class Malum implements ModInitializer {

    public static final String MODID = "malum"; // 1 character faster than MOD_ID!
    public static final ItemGroup MALUM = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(ENCYCLOPEDIA_ARCANA));

    @Override
    public void onInitialize() {
        RegistryEntries.init();
    }
}
