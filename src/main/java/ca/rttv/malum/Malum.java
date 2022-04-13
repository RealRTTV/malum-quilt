package ca.rttv.malum;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.enchantment.ReboundEnchantment;
import ca.rttv.malum.registry.MalumAttributeRegistry;
import ca.rttv.malum.registry.MalumRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static ca.rttv.malum.registry.MalumRegistry.*;

public final class Malum implements ModInitializer {
    public static final Random RANDOM = new Random();
    public static final String MODID = "malum"; // 1 character faster than MOD_ID!
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final ItemGroup MALUM = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(SPIRIT_ALTAR));
    public static final ItemGroup MALUM_ARCANE_ROCKS = FabricItemGroupBuilder.build(new Identifier(MODID, "malum_shaped_stones"), () -> new ItemStack(TAINTED_ROCK));
    public static final ItemGroup MALUM_NATURAL_WONDERS = FabricItemGroupBuilder.build(new Identifier(MODID, "malum_natural_wonders"), () -> new ItemStack(RUNEWOOD_SAPLING));
    public static final ItemGroup MALUM_SPIRITS = FabricItemGroupBuilder.build(new Identifier(MODID, "malum_spirits"), () -> new ItemStack(ARCANE_SPIRIT));

    @Override
    public void onInitialize() {
        MalumParticleRegistry.init();
        MalumAttributeRegistry.init();
        MalumRegistry.init();
        UseItemCallback.EVENT.register(ReboundEnchantment::onRightClickItem);
    }
}
