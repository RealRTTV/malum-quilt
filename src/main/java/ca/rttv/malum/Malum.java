package ca.rttv.malum;

import ca.rttv.malum.enchantment.ReboundEnchantment;
import ca.rttv.malum.registry.MalumAttributeRegistry;
import ca.rttv.malum.registry.MalumRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.registry.MalumRegistry.SPIRIT_ALTAR;

public final class Malum implements ModInitializer {

    public static final String MODID = "malum"; // 1 character faster than MOD_ID!
    public static final ItemGroup MALUM = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(SPIRIT_ALTAR));

    @Override
    public void onInitialize() {
        MalumAttributeRegistry.init();
        MalumRegistry.init();
        UseItemCallback.EVENT.register(ReboundEnchantment::onRightClickItem);
    }
}
