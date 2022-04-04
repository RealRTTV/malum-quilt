package ca.rttv.malum.registry;

import ca.rttv.malum.block.ItemStandBlock;
import ca.rttv.malum.block.RunewoodLeavesBlock;
import ca.rttv.malum.block.WoodenItemPedestalBlock;
import ca.rttv.malum.block.entity.ItemPedestalBlockEntity;
import ca.rttv.malum.block.entity.ItemStandBlockEntity;
import ca.rttv.malum.block.sapling.RunewoodSaplingGenerator;
import ca.rttv.malum.item.EncyclopediaArcanaItem;
import ca.rttv.malum.item.HolySyrupItem;
import ca.rttv.malum.item.ScytheItem;
import ca.rttv.malum.item.spirit.MalumSpiritItem;
import ca.rttv.malum.world.gen.feature.RunewoodTreeFeature;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.item.Item.Settings;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.ConfiguredFeatureUtil;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ca.rttv.malum.Malum.MALUM;
import static ca.rttv.malum.Malum.MODID;
import static net.minecraft.world.gen.feature.OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.world.gen.feature.OreConfiguredFeatures.STONE_ORE_REPLACEABLES;

@SuppressWarnings({"unused", "SameParameterValue"})
public final class MalumRegistry { // maps make stuff look cooler ok?
    private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
    public static final Set<ScytheItem> SCYTHES = new ReferenceOpenHashSet<>();
    private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();
    private static final Map<Feature<? extends FeatureConfig>, Identifier> FEATURES = new LinkedHashMap<>();

    // sound events
    public static final SoundEvent                                    BLOCK_ARCANE_CHARCOAL_BREAK    = registerSoundEvent ("arcane_charcoal_block_break");
    public static final SoundEvent                                    BLOCK_ARCANE_CHARCOAL_STEP     = registerSoundEvent ("arcane_charcoal_block_step");
    public static final SoundEvent                                    BLOCK_ARCANE_CHARCOAL_PLACE    = registerSoundEvent ("arcane_charcoal_block_place");
    public static final SoundEvent                                    BLOCK_ARCANE_CHARCOAL_HIT      = registerSoundEvent ("arcane_charcoal_block_hit");
    public static final SoundEvent                                    BLOCK_SOULSTONE_BREAK          = registerSoundEvent ("soulstone_break");
    public static final SoundEvent                                    BLOCK_SOULSTONE_STEP           = registerSoundEvent ("soulstone_step");
    public static final SoundEvent                                    BLOCK_SOULSTONE_PLACE          = registerSoundEvent ("soulstone_place");
    public static final SoundEvent                                    BLOCK_SOULSTONE_HIT            = registerSoundEvent ("soulstone_hit");

    // sound groups
    public static final BlockSoundGroup                               BLOCK_ARCANE_CHARCOAL_SOUNDS   = new BlockSoundGroup(1.0f, 1.0f, BLOCK_ARCANE_CHARCOAL_BREAK, BLOCK_ARCANE_CHARCOAL_STEP, BLOCK_ARCANE_CHARCOAL_PLACE, BLOCK_ARCANE_CHARCOAL_HIT, BLOCK_ARCANE_CHARCOAL_STEP);
    public static final BlockSoundGroup                               BLOCK_SOULSTONE_SOUNDS         = new BlockSoundGroup(1.0f, 1.0f, BLOCK_SOULSTONE_BREAK, BLOCK_SOULSTONE_STEP, BLOCK_SOULSTONE_PLACE, BLOCK_SOULSTONE_HIT, BLOCK_SOULSTONE_STEP);

    // items
    public static final Item                                          ENCYCLOPEDIA_ARCANA            = registerItem      ("encyclopedia_arcana",               new EncyclopediaArcanaItem(new Settings().rarity(Rarity.UNCOMMON).group(MALUM)));
    public static final Item                                          ARCANE_CHARCOAL                = registerItem      ("arcane_charcoal",                   new Item(new Settings().group(MALUM)));
    public static final Item                                          HOLY_SAP                       = registerItem      ("holy_sap",                          new Item(new Settings().group(MALUM)));
    public static final Item                                          HOLY_SAPBALL                   = registerItem      ("holy_sapball",                      new Item(new Settings().group(MALUM)));
    public static final Item                                          HOLY_SYRUP                     = registerItem      ("holy_syrup",                        new HolySyrupItem(new Settings().group(MALUM).food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0), 1.0f).alwaysEdible().hunger(8).saturationModifier(0.4f).build())));
    public static final Item                                          PROCESSED_SOULSTONE            = registerItem      ("processed_soulstone",               new Item(new Settings().group(MALUM)));
    public static final Item                                          RAW_SOULSTONE                  = registerItem      ("raw_soulstone",                     new Item(new Settings().group(MALUM)));
    public static final Item                                          CRUDE_SCYTHE                   = registerScytheItem      ("crude_scythe",                      new ScytheItem(ToolMaterials.IRON, 3, -3.1f, new Settings().group(MALUM)));

    // blocks
    public static final Block                                         RUNEWOOD_SAPLING               = registerBlockItem ("runewood_sapling",                  new SaplingBlock(new RunewoodSaplingGenerator(), AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)), MALUM);
    public static final Block                                         RUNEWOOD_LOG                   = registerBlockItem ("runewood_log",                      new PillarBlock(AbstractBlock.Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block                                         STRIPPED_RUNEWOOD_LOG          = registerBlockItem ("stripped_runewood_log",             new PillarBlock(AbstractBlock.Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block                                         EXPOSED_RUNEWOOD_LOG           = registerBlockItem ("exposed_runewood_log",              new PillarBlock(AbstractBlock.Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block                                         REVEALED_RUNEWOOD_LOG          = registerBlockItem ("revealed_runewood_log",             new PillarBlock(AbstractBlock.Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block                                         STRIPPED_RUNEWOOD              = registerBlockItem ("stripped_runewood",                 new PillarBlock(AbstractBlock.Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block                                         RUNEWOOD                       = registerBlockItem ("runewood",                          new PillarBlock(AbstractBlock.Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block                                         RUNEWOOD_LEAVES                = registerBlockItem ("runewood_leaves",                   new RunewoodLeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never)), MALUM);
    public static final Block                                         RUNEWOOD_PLANKS                = registerBlockItem ("runewood_planks",                   new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block                                         RUNEWOOD_PLANKS_SLAB           = registerBlockItem ("runewood_planks_slab",              new SlabBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block                                         RUNEWOOD_ITEM_PEDESTAL         = registerBlockItem ("runewood_item_pedestal",            new WoodenItemPedestalBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block                                         RUNEWOOD_ITEM_STAND            = registerBlockItem ("runewood_item_stand",               new ItemStandBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block                                         BLOCK_OF_ARCANE_CHARCOAL       = registerBlockItem ("block_of_arcane_charcoal",          new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.BLACK).strength(5.0f, 6.0f).sounds(BLOCK_ARCANE_CHARCOAL_SOUNDS)), MALUM);
    public static final Block                                         BLOCK_OF_SOULSTONE             = registerBlockItem ("block_of_soulstone",                new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.BLACK).strength(5.0f, 3.0f).sounds(BLOCK_SOULSTONE_SOUNDS)), MALUM);
    public static final Block                                         BLOCK_OF_RAW_SOULSTONE         = registerBlockItem ("block_of_raw_soulstone",            new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.BLACK).strength(5.0f, 3.0f).sounds(BLOCK_SOULSTONE_SOUNDS)), MALUM);
    public static final Block                                         SOULSTONE_ORE                  = registerBlockItem ("soulstone_ore",                     new OreBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.BLACK).strength(5.0f, 3.0f).sounds(BLOCK_SOULSTONE_SOUNDS)), MALUM);
    public static final Block                                         DEEPSLATE_SOULSTONE_ORE        = registerBlockItem ("deepslate_soulstone_ore",           new OreBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.BLACK).strength(7.0f, 6.0f).sounds(BLOCK_SOULSTONE_SOUNDS)), MALUM);

    // block entities
    public static final BlockEntityType<ItemStandBlockEntity>         ITEM_STAND_BLOCK_ENTITY        = registerBlockEntity("item_stand",                       BlockEntityType.Builder.create(ItemStandBlockEntity::new, RUNEWOOD_ITEM_STAND).build(null));
    public static final BlockEntityType<ItemPedestalBlockEntity>      ITEM_PEDESTAL_BLOCK_ENTITY     = registerBlockEntity("item_pedestal",                    BlockEntityType.Builder.create(ItemPedestalBlockEntity::new, RUNEWOOD_ITEM_PEDESTAL).build(null));

    // spirits
    public static final Item                                          SACRED_SPIRIT                  = registerItem       ("sacred_spirit",                    new MalumSpiritItem(new Settings().group(MALUM), SpiritTypeRegistry.SACRED_SPIRIT));
    public static final Item                                          WICKED_SPIRIT                  = registerItem       ("wicked_spirit",                    new MalumSpiritItem(new Settings().group(MALUM), SpiritTypeRegistry.WICKED_SPIRIT));
    public static final Item                                          ARCANE_SPIRIT                  = registerItem       ("arcane_spirit",                    new MalumSpiritItem(new Settings().group(MALUM), SpiritTypeRegistry.ARCANE_SPIRIT));
    public static final Item                                          ELDRITCH_SPIRIT                = registerItem       ("eldritch_spirit",                  new MalumSpiritItem(new Settings().group(MALUM), SpiritTypeRegistry.ELDRITCH_SPIRIT));
    public static final Item                                          EARTHEN_SPIRIT                 = registerItem       ("earthen_spirit",                   new MalumSpiritItem(new Settings().group(MALUM), SpiritTypeRegistry.EARTHEN_SPIRIT));
    public static final Item                                          INFERNAL_SPIRIT                = registerItem       ("infernal_spirit",                  new MalumSpiritItem(new Settings().group(MALUM), SpiritTypeRegistry.INFERNAL_SPIRIT));
    public static final Item                                          AERIAL_SPIRIT                  = registerItem       ("aerial_spirit",                    new MalumSpiritItem(new Settings().group(MALUM), SpiritTypeRegistry.AERIAL_SPIRIT));
    public static final Item                                          AQUEOUS_SPIRIT                 = registerItem       ("aqueous_spirit",                   new MalumSpiritItem(new Settings().group(MALUM), SpiritTypeRegistry.AQUEOUS_SPIRIT));


    public static final Feature<DefaultFeatureConfig>                 RUNEWOOD_TREE_FEATURE          = registerFeature    ("runewood_tree",                    new RunewoodTreeFeature());
    public static final List<OreFeatureConfig.Target> SOULSTONE_ORE_TARGETS = List.of(
            OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, SOULSTONE_ORE.getDefaultState()),
            OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, DEEPSLATE_SOULSTONE_ORE.getDefaultState())
    );

    public static final Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> CONFIGURED_RUNEWOOD_TREE_FEATURE = registerConfiguredFeature("runewood_tree",      RUNEWOOD_TREE_FEATURE);

    public static final Holder<ConfiguredFeature<OreFeatureConfig, ?>> LOWER_ORE_SOULSTONE_CONFIGURED = registerConfiguredFeature("lower_ore_soulstone", Feature.ORE, new OreFeatureConfig(SOULSTONE_ORE_TARGETS, 12)); // i == maxSize
    public static final Holder<PlacedFeature> LOWER_ORE_SOULSTONE_PLACED = registerPlacedFeature("lower_ore_soulstone", LOWER_ORE_SOULSTONE_CONFIGURED, OrePlacedFeatures.commonOrePlacementModifiers(8, HeightRangePlacementModifier.createUniform(YOffset.getBottom(), YOffset.fixed(30)))); // bottom of world and y 30

    public static final Holder<ConfiguredFeature<OreFeatureConfig, ?>> UPPER_ORE_SOULSTONE_CONFIGURED = registerConfiguredFeature("upper_ore_soulstone", Feature.ORE, new OreFeatureConfig(SOULSTONE_ORE_TARGETS, 6)); // i == maxSize
    public static final Holder<PlacedFeature> UPPER_ORE_SOULSTONE_PLACED = registerPlacedFeature("upper_ore_soulstone", UPPER_ORE_SOULSTONE_CONFIGURED, OrePlacedFeatures.commonOrePlacementModifiers(5, HeightRangePlacementModifier.createUniform(YOffset.fixed(60), YOffset.fixed(100)))); // y 60 to y 100

    private static Holder<PlacedFeature> registerPlacedFeature(String id, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers) {
        return PlacedFeatureUtil.register(id, configuredFeature, modifiers);
    }

    public static <T extends Block> T registerBlock(String id, T block) {
        BLOCKS.put(block, new Identifier(MODID, id));
        return block;
    }

    public static <T extends Block> T registerBlockItem(String id, T block, ItemGroup itemGroup) {
        BLOCKS.put(block, new Identifier(MODID, id));
        ITEMS.put(new BlockItem(block, new Settings().group(itemGroup)), new Identifier(MODID, id));
        return block;
    }
    public static <T extends ScytheItem> T registerScytheItem(String id, T item) {
        SCYTHES.add(item);
        return registerItem(id, item);
    }

    public static <T extends Item> T registerItem(String id, T item) {
        ITEMS.put(item, new Identifier(MODID, id));
        return item;
    }

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String id, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(type, new Identifier(MODID, id));
        return type;
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String id, F feature) {
        FEATURES.put(feature, new Identifier(MODID, id));
        return feature;
    }

    private static Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> registerConfiguredFeature(String id, Feature<DefaultFeatureConfig> feature) {
        return ConfiguredFeatureUtil.register(new Identifier(MODID, id).toString(), feature);
    }

    private static <T extends FeatureConfig, U extends Feature<T>> Holder<ConfiguredFeature<T, ?>> registerConfiguredFeature(String id, U feature, T featureConfig) {
        return ConfiguredFeatureUtil.register(id, feature, featureConfig);
    }

    private static SoundEvent registerSoundEvent(String id) {
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(new Identifier(MODID, id)));
    }

    public static void init() {
        ITEMS.forEach((item, id) -> Registry.register(Registry.ITEM, id, item));
        BLOCKS.forEach((block, id) -> Registry.register(Registry.BLOCK, id, block));
        MalumBoatTypes.init();
        MalumEntityRegistry.init();
        BLOCK_ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(entityType), entityType));
        FEATURES.forEach((feature, id) -> Registry.register(Registry.FEATURE, id, feature));
    }
}
