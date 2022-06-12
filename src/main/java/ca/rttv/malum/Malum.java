package ca.rttv.malum;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.enchantment.ReboundEnchantment;
import ca.rttv.malum.registry.*;
import ca.rttv.malum.util.listener.SpiritDataReloadListener;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.random.RandomGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;
import org.quiltmc.qsl.resource.loader.api.reloader.IdentifiableResourceReloader;

import java.util.Random;

import static ca.rttv.malum.registry.MalumItemRegistry.*;

public final class Malum implements ModInitializer {
    public static final RandomGenerator RANDOM = RandomGenerator.createLegacy();
    public static final String MODID = "malum"; // 1 character faster than MOD_ID!
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final ItemGroup MALUM = QuiltItemGroup.builder(new Identifier(MODID, MODID)).icon(() -> new ItemStack(SPIRIT_ALTAR)).build();
    public static final ItemGroup MALUM_ARCANE_ROCKS = QuiltItemGroup.builder(new Identifier(MODID, "malum_shaped_stones")).icon(() -> new ItemStack(TAINTED_ROCK)).build(); // do **not** use .getDefaultStack();
    public static final ItemGroup MALUM_NATURAL_WONDERS = QuiltItemGroup.builder(new Identifier(MODID, "malum_natural_wonders")).icon(() -> new ItemStack(RUNEWOOD_SAPLING)).build();
    public static final ItemGroup MALUM_SPIRITS = QuiltItemGroup.builder(new Identifier(MODID, "malum_spirits")).icon(() -> new ItemStack(ARCANE_SPIRIT)).build();
    public static final ItemGroup MALUM_METALLURGIC_MAGIC = QuiltItemGroup.builder(new Identifier(MODID, "malum_metallurgic_magic")).icon(() -> new ItemStack(ALCHEMICAL_IMPETUS)).build();

    @Override
    public void onInitialize(ModContainer mod) {
        // todo, haunted enchantment
        MalumAttributeRegistry.init();
        MalumParticleRegistry.init();
        MalumBlockRegistry.init();
        MalumItemRegistry.init();
        MalumEnchantments.init();
        MalumSoundRegistry.init();
        MalumEntityRegistry.init();
        MalumBlockEntityRegistry.init();
        MalumRiteRegistry.init();
        MalumSignTypeRegistry.init();
        MalumBlockSoundGroupRegistry.init();
        MalumRecipeTypeRegistry.init();
        MalumRecipeSerializerRegistry.init();
        MalumFeatureRegistry.init();
        MalumConfiguredFeatureRegistry.init();
        MalumPlacedFeatureRegistry.init();
        MalumScreenHandlerRegistry.init();
        MalumAcceleratorTypeRegistry.init();
        MalumStatusEffectRegistry.init();
        MalumTags.init();
        UseItemCallback.EVENT.register(ReboundEnchantment::onRightClickItem);

        ResourceLoader.get(ResourceType.SERVER_DATA).registerReloader(new SpiritDataReloadListenerFabricImpl());
    }

    public static class SpiritDataReloadListenerFabricImpl extends SpiritDataReloadListener implements IdentifiableResourceReloader {
        @Override
        public Identifier getQuiltId() {
            return new Identifier(MODID, "spirit_data");
        }
    }
}
