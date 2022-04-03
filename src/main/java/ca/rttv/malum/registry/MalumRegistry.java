package ca.rttv.malum.registry;

import ca.rttv.malum.block.ItemStandBlock;
import ca.rttv.malum.block.RunewoodLeavesBlock;
import ca.rttv.malum.block.WoodenItemPedestalBlock;
import ca.rttv.malum.block.entity.ItemPedestalBlockEntity;
import ca.rttv.malum.block.entity.ItemStandBlockEntity;
import ca.rttv.malum.block.sapling.RunewoodSaplingGenerator;
import ca.rttv.malum.item.EncyclopediaArcanaItem;
import ca.rttv.malum.item.spirit.MalumSpiritItem;
import ca.rttv.malum.world.gen.feature.RunewoodTreeFeature;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.ConfiguredFeatureUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MALUM;
import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumEntityRegistry.*;

@SuppressWarnings("unused")
public final class MalumRegistry { // maps make stuff look cooler ok?
    private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
    private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();
    private static final Map<Feature<? extends FeatureConfig>, Identifier> FEATURES = new LinkedHashMap<>();

    public static final Item ENCYCLOPEDIA_ARCANA     = registerItem("encyclopedia_arcana", new EncyclopediaArcanaItem(new Settings().rarity(Rarity.UNCOMMON).group(MALUM)));

    // runewood
    public static final Block RUNEWOOD_SAPLING       = registerBlockItem("runewood_sapling", new SaplingBlock(new RunewoodSaplingGenerator(), AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)), MALUM);
    public static final Block RUNEWOOD_LOG           = registerBlockItem("runewood_log", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block STRIPPED_RUNEWOOD_LOG  = registerBlockItem("stripped_runewood_log", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block EXPOSED_RUNEWOOD_LOG   = registerBlockItem("exposed_runewood_log", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block REVEALED_RUNEWOOD_LOG  = registerBlockItem("revealed_runewood_log", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block STRIPPED_RUNEWOOD      = registerBlockItem("stripped_runewood", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block RUNEWOOD               = registerBlockItem("runewood", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block RUNEWOOD_LEAVES        = registerBlockItem("runewood_leaves", new RunewoodLeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never)), MALUM);
    public static final Block RUNEWOOD_PLANKS        = registerBlockItem("runewood_planks", new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.OAK_TAN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block RUNEWOOD_PLANKS_SLAB   = registerBlockItem("runewood_planks_slab", new SlabBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.OAK_TAN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block RUNEWOOD_ITEM_PEDESTAL = registerBlockItem("runewood_item_pedestal", new WoodenItemPedestalBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.OAK_TAN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block RUNEWOOD_ITEM_STAND    = registerBlockItem("runewood_item_stand", new ItemStandBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.OAK_TAN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM);

    // block entities
    public static final BlockEntityType<ItemStandBlockEntity>    ITEM_STAND_BLOCK_ENTITY    = registerBlockEntity("item_stand", BlockEntityType.Builder.create(ItemStandBlockEntity::new, RUNEWOOD_ITEM_STAND).build(null));
    public static final BlockEntityType<ItemPedestalBlockEntity> ITEM_PEDESTAL_BLOCK_ENTITY = registerBlockEntity("item_pedestal", BlockEntityType.Builder.create(ItemPedestalBlockEntity::new, RUNEWOOD_ITEM_PEDESTAL).build(null));

    // spirits
    public static final Item SACRED_SPIRIT   = registerItem("sacred_spirit", new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.SACRED_SPIRIT));
    public static final Item WICKED_SPIRIT   = registerItem("wicked_spirit", new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.WICKED_SPIRIT));
    public static final Item ARCANE_SPIRIT   = registerItem("arcane_spirit", new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.ARCANE_SPIRIT));
    public static final Item ELDRITCH_SPIRIT = registerItem("eldritch_spirit", new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.ELDRITCH_SPIRIT));
    public static final Item EARTHEN_SPIRIT  = registerItem("earthen_spirit", new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.EARTHEN_SPIRIT));
    public static final Item INFERNAL_SPIRIT = registerItem("infernal_spirit", new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.INFERNAL_SPIRIT));
    public static final Item AERIAL_SPIRIT   = registerItem("aerial_spirit", new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.AERIAL_SPIRIT));
    public static final Item AQUEOUS_SPIRIT  = registerItem("aqueous_spirit", new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.AQUEOUS_SPIRIT));


    public static final Feature<DefaultFeatureConfig> RUNEWOOD_TREE_FEATURE = registerFeature("runewood_tree", new RunewoodTreeFeature());

    public static final Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> CONFIGURED_RUNEWOOD_TREE_FEATURE = registerConfiguredFeature("runewood_tree", RUNEWOOD_TREE_FEATURE);

    public static <T extends Block> T registerBlock(String id, T block) {
        BLOCKS.put(block, new Identifier(MODID, id));
        return block;
    }

    public static <T extends Block> T registerBlockItem(String id, T block, ItemGroup itemGroup) {
        BLOCKS.put(block, new Identifier(MODID, id));
        ITEMS.put(new BlockItem(block, new Item.Settings().group(itemGroup)), new Identifier(MODID, id));
        return block;
    }

    public static <T extends Item> T registerItem(String id, T item) {
        ITEMS.put(item, new Identifier(MODID, id));
        return item;
    }
    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(type, new Identifier(MODID, name));
        return type;
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
        FEATURES.put(feature, new Identifier(MODID, name));
        return feature;
    }

    private static Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> registerConfiguredFeature(String name, Feature<DefaultFeatureConfig> feature) {
        return ConfiguredFeatureUtil.register(new Identifier(MODID, name).toString(), feature);
    }

    public static void init() {
        ITEMS.forEach((item, id) -> Registry.register(Registry.ITEM, id, item));
        BLOCKS.forEach((block, id) -> Registry.register(Registry.BLOCK, id, block));
        MalumBoatTypes.init();
        MalumEntityRegistry.init();
        BLOCK_ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(entityType), entityType));
        FEATURES.forEach((feature, id) -> Registry.register(Registry.FEATURE, id, feature));
    }
    public static Item.Settings DEFAULT_PROPERTIES() {
        return new Item.Settings().group(MALUM);
    }

    public static Item.Settings SPLINTER_PROPERTIES() {
        return new Item.Settings().group(MALUM);
    }

    public static Item.Settings BUILDING_PROPERTIES() {
        return new Item.Settings().group(MALUM);
    }

    public static Item.Settings NATURE_PROPERTIES() {
        return new Item.Settings().group(MALUM);
    }

    public static Item.Settings GEAR_PROPERTIES() {
        return new Item.Settings().group(MALUM).maxCount(1);
    }

    public static Item.Settings IMPETUS_PROPERTIES() {
        return new Item.Settings().group(MALUM).maxCount(1);
    }

    public static Item.Settings NODE_PROPERTIES() {
        return new Item.Settings().group(MALUM);
    }

    public static Item.Settings HIDDEN_PROPERTIES() {
        return new Item.Settings().maxCount(1);
    }
}
