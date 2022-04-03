package ca.rttv.malum;

import ca.rttv.malum.block.RunewoodLeavesBlock;
import ca.rttv.malum.block.WoodenItemPedestalBlock;
import ca.rttv.malum.block.ItemStandBlock;
import ca.rttv.malum.block.entity.ItemPedestalBlockEntity;
import ca.rttv.malum.block.entity.ItemStandBlockEntity;
import ca.rttv.malum.block.sapling.RunewoodSaplingGenerator;
import ca.rttv.malum.item.EncyclopediaArcanaItem;
import ca.rttv.malum.world.gen.feature.RunewoodTreeFeature;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
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

public final class RegistryEntries { // maps make stuff look cooler ok?
    private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
    private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();

    public static final Item ENCYCLOPEDIA_ARCANA = registerItem("encyclopedia_arcana", new EncyclopediaArcanaItem(new Settings().rarity(Rarity.UNCOMMON).group(MALUM)));

    public static final Block RUNEWOOD_SAPLING       = registerBlockItem("runewood_sapling", new SaplingBlock(new RunewoodSaplingGenerator(), AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)), MALUM);
    public static final Block RUNEWOOD_LOG           = registerBlockItem("runewood_log", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD).strength(2.0F).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block EXPOSED_RUNEWOOD_LOG   = registerBlockItem("exposed_runewood_log", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD).strength(2.0F).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block RUNEWOOD_LEAVES        = registerBlockItem("runewood_leaves", new RunewoodLeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never)), MALUM);
    public static final Block RUNEWOOD_PLANKS        = registerBlockItem("runewood_planks", new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.OAK_TAN).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block RUNEWOOD_PLANKS_SLAB   = registerBlockItem("runewood_planks_slab", new SlabBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.OAK_TAN).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block RUNEWOOD_ITEM_PEDESTAL = registerBlockItem("runewood_item_pedestal", new WoodenItemPedestalBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.OAK_TAN).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), MALUM);
    public static final Block RUNEWOOD_ITEM_STAND    = registerBlockItem("runewood_item_stand", new ItemStandBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.OAK_TAN).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), MALUM);

    public static final BlockEntityType<ItemStandBlockEntity>    ITEM_STAND_BLOCK_ENTITY    = createBlockEntity("item_stand", FabricBlockEntityTypeBuilder.create(ItemStandBlockEntity::new, RUNEWOOD_ITEM_STAND).build(null));
    public static final BlockEntityType<ItemPedestalBlockEntity> ITEM_PEDESTAL_BLOCK_ENTITY = createBlockEntity("item_pedestal", FabricBlockEntityTypeBuilder.create(ItemPedestalBlockEntity::new, RUNEWOOD_ITEM_PEDESTAL).build(null));

    public static final Feature<DefaultFeatureConfig> RUNEWOOD_TREE_FEATURE = registerFeature("runewood_tree", new RunewoodTreeFeature());

    public static final Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> CONFIGURED_RUNEWOOD_TREE_FEATURE = ConfiguredFeatureUtil.register(MODID + ":runewood_tree", RUNEWOOD_TREE_FEATURE);

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
    private static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(String name, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(type, new Identifier(MODID, name));
        return type;
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
        return Registry.register(Registry.FEATURE, new Identifier(MODID, name), feature);
    }

    public static void init() {
        ITEMS.forEach((item, id) -> Registry.register(Registry.ITEM, id, item));
        BLOCKS.forEach((block, id) -> Registry.register(Registry.BLOCK, id, block));
        BLOCK_ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(entityType), entityType));
    }
}
