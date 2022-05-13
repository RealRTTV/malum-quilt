package ca.rttv.malum;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.registry.MalumAttributeRegistry;
import ca.rttv.malum.registry.MalumRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;

import java.util.Random;

import static ca.rttv.malum.registry.MalumRegistry.*;

public final class Malum implements ModInitializer {
    public static final Random RANDOM = new Random();
    public static final String MODID = "malum"; // 1 character faster than MOD_ID!
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final ItemGroup MALUM = QuiltItemGroup.builder(new Identifier(MODID, MODID)).icon(() -> new ItemStack(SPIRIT_ALTAR)).build();
    public static final ItemGroup MALUM_ARCANE_ROCKS = QuiltItemGroup.builder(new Identifier(MODID, "malum_shaped_stones")).icon(() -> new ItemStack(TAINTED_ROCK)).build();
    public static final ItemGroup MALUM_NATURAL_WONDERS = QuiltItemGroup.builder(new Identifier(MODID, "malum_natural_wonders")).icon(() -> new ItemStack(RUNEWOOD_SAPLING)).build();
    public static final ItemGroup MALUM_SPIRITS = QuiltItemGroup.builder(new Identifier(MODID, "malum_spirits")).icon(() -> new ItemStack(ARCANE_SPIRIT)).build();

    @Override
    public void onInitialize(ModContainer mod) {
        MalumAttributeRegistry.init();
        MalumParticleRegistry.init();
        MalumRegistry.init();
    }
}
