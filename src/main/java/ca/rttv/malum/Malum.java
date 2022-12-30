package ca.rttv.malum;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.config.ClientConfig;
import ca.rttv.malum.config.CommonConfig;
import ca.rttv.malum.enchantment.ReboundEnchantment;
import ca.rttv.malum.network.packet.s2c.play.ProgressionBookEntriesS2CPacket;
import ca.rttv.malum.registry.*;
import ca.rttv.malum.util.listener.ProgressionBookEntriesReloadListener;
import ca.rttv.malum.util.listener.SpiritDataReloadListener;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.random.RandomGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;
import org.quiltmc.qsl.resource.loader.api.reloader.IdentifiableResourceReloader;

import static ca.rttv.malum.registry.MalumItemRegistry.*;

public final class Malum implements ModInitializer {
    //TODO: WAIT UNTIL QUILT ADDS ENCH. BOOSTER FOR THE BRILLIANCE PYLON THINGS
    public static final RandomGenerator RANDOM = RandomGenerator.createLegacy();
    public static final String MODID = "malum"; // 1 character faster than MOD_ID!
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final ItemGroup MALUM = FabricItemGroup.builder(new Identifier(MODID, MODID)).icon(() -> new ItemStack(SPIRIT_ALTAR)).build();
    public static final ItemGroup MALUM_ARCANE_ROCKS = FabricItemGroup.builder(new Identifier(MODID, "malum_shaped_stones")).icon(() -> new ItemStack(TAINTED_ROCK)).build();
    public static final ItemGroup MALUM_NATURAL_WONDERS = FabricItemGroup.builder(new Identifier(MODID, "malum_natural_wonders")).icon(() -> new ItemStack(RUNEWOOD_SAPLING)).build();
    public static final ItemGroup MALUM_SPIRITS = FabricItemGroup.builder(new Identifier(MODID, "malum_spirits")).icon(() -> new ItemStack(ARCANE_SPIRIT)).entries((f, e, b) -> {
        e.addItem(AERIAL_SPIRIT);
        e.addItem(AQUEOUS_SPIRIT);
        e.addItem(INFERNAL_SPIRIT);
        e.addItem(ARCANE_SPIRIT);
        e.addItem(ELDRITCH_SPIRIT);
        e.addItem(EARTHEN_SPIRIT);
        e.addItem(WICKED_SPIRIT);
        e.addItem(SACRED_SPIRIT);
    }).build();
    public static final ItemGroup MALUM_METALLURGIC_MAGIC = FabricItemGroup.builder(new Identifier(MODID, "malum_metallurgic_magic")).icon(() -> new ItemStack(ALCHEMICAL_IMPETUS)).entries((f, e, b) -> {
        e.addItem(ALCHEMICAL_IMPETUS);
        e.addItem(CRACKED_ALCHEMICAL_IMPETUS);
        e.addItem(GOLD_NODE);
        e.addItem(GOLD_IMPETUS);
        e.addItem(CRACKED_GOLD_IMPETUS);
        e.addItem(IRON_NODE);
        e.addItem(IRON_IMPETUS);
        e.addItem(CRACKED_IRON_IMPETUS);
        e.addItem(COPPER_NODE);
        e.addItem(COPPER_IMPETUS);
        e.addItem(CRACKED_COPPER_IMPETUS);
        e.addItem(SILVER_NODE);
        e.addItem(SILVER_IMPETUS);
        e.addItem(CRACKED_SILVER_IMPETUS);
        e.addItem(LEAD_NODE);
        e.addItem(LEAD_IMPETUS);
        e.addItem(CRACKED_LEAD_IMPETUS);
    }).build();

    @Override
    public void onInitialize(ModContainer mod) {
        ClientConfig.LOGGER.info("Finished Class-Load of Malum's ClientConfig");
        CommonConfig.LOGGER.info("Finished Class-Load of Malum's CommonConfig");

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
        MalumScreenHandlerRegistry.init();
        MalumAcceleratorTypeRegistry.init();
        MalumStatusEffectRegistry.init();
        MalumTags.init();
        MalumPageTypeRegistry.init();
        MalumEntryObjectTypeRegistry.initater();
        UseItemCallback.EVENT.register(ReboundEnchantment::onRightClickItem);

        ResourceLoader.get(ResourceType.SERVER_DATA).registerReloader(new SpiritDataReloadListenerFabricImpl());
        ResourceLoader.get(ResourceType.SERVER_DATA).registerReloader(new ProgressionBookEntriesReloadListener());
        ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> ServerPlayNetworking.send(handler.player, new Identifier(MODID, "progressionbookentriess2cpacket"), new ProgressionBookEntriesS2CPacket(ProgressionBookEntriesReloadListener.SERVER_ENTRIES).toBuf())));
    }

    public static class SpiritDataReloadListenerFabricImpl extends SpiritDataReloadListener implements IdentifiableResourceReloader {
        @Override
        public Identifier getQuiltId() {
            return new Identifier(MODID, "spirit_data");
        }
    }
}
