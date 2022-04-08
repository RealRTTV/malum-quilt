package ca.rttv.malum.registry;

import ca.rttv.malum.block.*;
import ca.rttv.malum.block.entity.EtherBlockEntity;
import ca.rttv.malum.block.entity.ItemPedestalBlockEntity;
import ca.rttv.malum.block.entity.ItemStandBlockEntity;
import ca.rttv.malum.block.entity.SpiritAltarBlockEntity;
import ca.rttv.malum.block.sapling.RunewoodSaplingGenerator;
import ca.rttv.malum.block.sapling.SoulwoodSaplingGenerator;
import ca.rttv.malum.item.EncyclopediaArcanaItem;
import ca.rttv.malum.item.HolySyrupItem;
import ca.rttv.malum.item.ScytheItem;
import ca.rttv.malum.item.spirit.MalumSpiritItem;
import ca.rttv.malum.recipe.SpiritInfusionRecipe;
import ca.rttv.malum.world.gen.feature.GradientTreeFeature;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.SignType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.ConfiguredFeatureUtil;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;

import java.util.*;

import static ca.rttv.malum.Malum.*;
import static ca.rttv.malum.registry.MalumSoundRegistry.*;
import static net.minecraft.world.gen.feature.OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.world.gen.feature.OreConfiguredFeatures.STONE_ORE_REPLACEABLES;

@SuppressWarnings({"unused", "SameParameterValue"})
public final class MalumRegistry { // maps make stuff look cooler ok?
    public static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();
    public static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    public static final Set<ScytheItem> SCYTHES = new ReferenceOpenHashSet<>();
    public static final Map<Identifier, BlockEntityType<?>> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();
    public static final Map<Identifier, Feature<? extends FeatureConfig>> FEATURES = new LinkedHashMap<>();
    public static final Map<Identifier, RecipeType<? extends Recipe<?>>> RECIPE_TYPES = new LinkedHashMap<>();
    public static final Map<Identifier, RecipeSerializer<? extends Recipe<?>>> RECIPE_SERIALIZER = new LinkedHashMap<>();
    public static final ArrayList<SignType> SIGN_TYPES = new ArrayList<>();
    public static final Map<Identifier, SoundEvent> SOUND_EVENTS = new LinkedHashMap<>();
    public static final Map<Identifier, ConfiguredFeature<? extends FeatureConfig, ?>> CONFIGURED_FEATURES = new LinkedHashMap<>();

    // sign types
    public static final SignType                                      RUNEWOOD_SIGN_TYPE                        = registerSignType         (new SignType("runewood"));
    public static final SignType                                      SOULWOOD_SIGN_TYPE                        = registerSignType         (new SignType("soulwood"));

    // sound groups
    public static final BlockSoundGroup                               BLOCK_ARCANE_CHARCOAL_SOUNDS              = new BlockSoundGroup      (1.0f, 1.0f, ARCANE_CHARCOAL_BREAK, ARCANE_CHARCOAL_STEP, ARCANE_CHARCOAL_PLACE, ARCANE_CHARCOAL_HIT, ARCANE_CHARCOAL_STEP);
    public static final BlockSoundGroup                               BLOCK_SOULSTONE_SOUNDS                    = new BlockSoundGroup      (1.0f, 1.0f, SOULSTONE_BREAK, SOULSTONE_STEP, SOULSTONE_PLACE, SOULSTONE_HIT, SOULSTONE_STEP);
    public static final BlockSoundGroup                               BLOCK_TAINTED_ROCK_SOUNDS                 = new BlockSoundGroup      (1.0f, 1.0f, TAINTED_ROCK_BREAK, TAINTED_ROCK_STEP, TAINTED_ROCK_PLACE, TAINTED_ROCK_HIT, TAINTED_ROCK_STEP);

    // items & blocks, sorted [Malum]
    public static final Item                                          ENCYCLOPEDIA_ARCANA                       = registerItem             ("encyclopedia_arcana",                       new EncyclopediaArcanaItem(new Item.Settings().rarity(Rarity.UNCOMMON).group(MALUM)));
    public static final Block                                         SOULSTONE_ORE                             = registerBlockItem        ("soulstone_ore",                             new OreBlock(Settings.of(Material.STONE, MapColor.BLACK).strength(5.0f, 3.0f).sounds(BLOCK_SOULSTONE_SOUNDS)), MALUM);
    public static final Block                                         DEEPSLATE_SOULSTONE_ORE                   = registerBlockItem        ("deepslate_soulstone_ore",                   new OreBlock(Settings.of(Material.STONE, MapColor.BLACK).strength(7.0f, 6.0f).sounds(BLOCK_SOULSTONE_SOUNDS)), MALUM);
    public static final Item                                          RAW_SOULSTONE                             = registerItem             ("raw_soulstone",                             new Item(new Item.Settings().group(MALUM)));
    public static final Item                                          PROCESSED_SOULSTONE                       = registerItem             ("processed_soulstone",                       new Item(new Item.Settings().group(MALUM)));
    public static final Block                                         BLOCK_OF_RAW_SOULSTONE                    = registerBlockItem        ("block_of_raw_soulstone",                    new Block(Settings.of(Material.STONE, MapColor.BLACK).strength(5.0f, 3.0f).sounds(BLOCK_SOULSTONE_SOUNDS)), MALUM);
    public static final Block                                         BLOCK_OF_SOULSTONE                        = registerBlockItem        ("block_of_soulstone",                        new Block(Settings.of(Material.STONE, MapColor.BLACK).strength(5.0f, 3.0f).sounds(BLOCK_SOULSTONE_SOUNDS)), MALUM);
    public static final Block                                         SPIRIT_ALTAR                              = registerBlockItem        ("spirit_altar",                              new SpiritAltarBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).sounds(BlockSoundGroup.WOOD).strength(2.0f)), MALUM);
    // spirit jar
    // soul vial
    // runewood obelisk
    // brilliant obelisk
    // spirit crucible
    // runewood totem base
    // soulwood totem base
    // soulwood plinth
    // soulwood fusion plate
    public static final Item                                          HEX_ASH                                   = registerItem             ("hex_ash",                                   new Item(new Item.Settings().group(MALUM)));
    public static final Item                                          SPIRIT_FABRIC                             = registerItem             ("spirit_fabric",                             new Item(new Item.Settings().group(MALUM)));
    // spectral lens
    // poppet
    // hallowed gold ingot
    // hallowed gold nugget
    // block of hallowed gold
    // hallowed spirit resonator
    // soul stained steel ingot
    // soul stained steel nugget
    // block of soul stained steel
    // stained spirit resonator
    // cracked alchemical impetus
    // alchemical impetus
    // cracked vitric impetus
    // vitric impetus
    // cracked iron impetus
    // iron impetus
    // cracked copper impetus
    // copper impetus
    // cracked gold impetus
    // gold impetus
    public static final Block                                         ETHER                                     = registerBlockItem        ("ether",                                     new EtherBlock(Settings.of(Material.WOOL).noCollision().breakInstantly().luminance(state -> 14).sounds(BlockSoundGroup.WOOD) /* todo: particle */), MALUM);
    public static final Block                                         ETHER_TORCH                               = registerBlock            ("ether_torch",                               new EtherTorchBlock(Settings.of(Material.DECORATION).noCollision().breakInstantly().luminance(state -> 14).sounds(BlockSoundGroup.WOOD) /* todo: particle */));
    public static final Block                                         WALL_ETHER_TORCH                          = registerBlock            ("wall_ether_torch",                          new EtherWallTorchBlock(Settings.of(Material.DECORATION).noCollision().breakInstantly().luminance(state -> 14).sounds(BlockSoundGroup.WOOD) /* todo: particle */));
    public static final Item                                          ETHER_TORCH_ITEM                          = registerItem             ("ether_torch",                               new WallStandingBlockItem(ETHER_TORCH, WALL_ETHER_TORCH, new Item.Settings().group(MALUM)));
    // tainted ether brazier
    // twisted ether brazier
    // iridescent ether
    // iridescent ether torch
    // tainted iridescent ether brazier
    // twisted iridescent ether brazier
    // spirit pouch
    public static final Item                                          CRUDE_SCYTHE                              = registerScytheItem       ("crude_scythe",                              new ScytheItem(ToolMaterials.IRON, 3, -3.1f, new Item.Settings().group(MALUM)));
    // soul stained steel scythe
    // soulwood stave
    // soul stained steel sword
    // soul stained steel pickaxe
    // soul stained steel axe
    // soul stained steel shovel
    // soul stained steel hoe
    // soul stained steel helmet
    // soul stained steel chestplate
    // soul stained steel leggings
    // soul stained steel boots
    // soul hunter cloak
    // soul hunter robe
    // soul hunter leggings
    // soul hunter boots
    // tyrving
    // gilded ring
    // ornate ring
    // ornate necklace
    // gilded belt
    // ring of arcane reach
    // ring of arcane spoil
    // ring of prowess
    // ring of curative talent
    // ring of wicket intent
    // necklace of the mystic mirror
    // necklace of the narrow edge
    // warded belt
    // magebane belt
    // cracked ceaseless impetus
    // ceaseless impetus

    // items & blocks, sorted [Malum: Arcane Rocks]
    public static final Block                                         TAINTED_ROCK                              = registerBlockItem        ("tainted_rock",                              new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         SMOOTH_TAINTED_ROCK                       = registerBlockItem        ("smooth_tainted_rock",                       new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         POLISHED_TAINTED_ROCK                     = registerBlockItem        ("polished_tainted_rock",                     new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_BRICKS                       = registerBlockItem        ("tainted_rock_bricks",                       new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TAINTED_ROCK_BRICKS               = registerBlockItem        ("cracked_tainted_rock_bricks",               new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_TILES                        = registerBlockItem        ("tainted_rock_tiles",                        new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TAINTED_ROCK_TILES                = registerBlockItem        ("cracked_tainted_rock_tiles",                new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         SMALL_TAINTED_ROCK_BRICKS                 = registerBlockItem        ("small_tainted_rock_bricks",                 new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_SMALL_TAINTED_ROCK_BRICKS         = registerBlockItem        ("cracked_small_tainted_rock_bricks",         new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_PILLAR                       = registerBlockItem        ("tainted_rock_pillar",                       new PillarBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_PILLAR_CAP                   = registerBlockItem        ("tainted_rock_pillar_cap",                   new PillarBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_COLUMN                       = registerBlockItem        ("tainted_rock_column",                       new PillarBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_COLUMN_CAP                   = registerBlockItem        ("tainted_rock_column_cap",                   new PillarBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CUT_TAINTED_ROCK                          = registerBlockItem        ("cut_tainted_rock",                          new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CHISELED_TAINTED_ROCK                     = registerBlockItem        ("chiseled_tainted_rock",                     new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_PRESSURE_PLATE               = registerBlockItem        ("tainted_rock_pressure_plate",               new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_WALL                         = registerBlockItem        ("tainted_rock_wall",                         new WallBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_BRICKS_WALL                  = registerBlockItem        ("tainted_rock_bricks_wall",                  new WallBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TAINTED_ROCK_BRICKS_WALL          = registerBlockItem        ("cracked_tainted_rock_bricks_wall",          new WallBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_TILES_WALL                   = registerBlockItem        ("tainted_rock_tiles_wall",                   new WallBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TAINTED_ROCK_TILES_WALL           = registerBlockItem        ("cracked_tainted_rock_tiles_wall",           new WallBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         SMALL_TAINTED_ROCK_BRICKS_WALL            = registerBlockItem        ("small_tainted_rock_bricks_wall",            new WallBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL    = registerBlockItem        ("cracked_small_tainted_rock_bricks_wall",    new WallBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_SLAB                         = registerBlockItem        ("tainted_rock_slab",                         new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         SMOOTH_TAINTED_ROCK_SLAB                  = registerBlockItem        ("smooth_tainted_rock_slab",                  new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         POLISHED_TAINTED_ROCK_SLAB                = registerBlockItem        ("polished_tainted_rock_slab",                new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_BRICKS_SLAB                  = registerBlockItem        ("tainted_rock_bricks_slab",                  new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TAINTED_ROCK_BRICKS_SLAB          = registerBlockItem        ("cracked_tainted_rock_bricks_slab",          new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_TILES_SLAB                   = registerBlockItem        ("tainted_rock_tiles_slab",                   new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TAINTED_ROCK_TILES_SLAB           = registerBlockItem        ("cracked_tainted_rock_tiles_slab",           new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         SMALL_TAINTED_ROCK_BRICKS_SLAB            = registerBlockItem        ("small_tainted_rock_bricks_slab",            new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB    = registerBlockItem        ("cracked_small_tainted_rock_bricks_slab",    new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_STAIRS                       = registerBlockItem        ("tainted_rock_stairs",                       new StairsBlock(TAINTED_ROCK.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         SMOOTH_TAINTED_ROCK_STAIRS                = registerBlockItem        ("smooth_tainted_rock_stairs",                new StairsBlock(SMOOTH_TAINTED_ROCK.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         POLISHED_TAINTED_ROCK_STAIRS              = registerBlockItem        ("polished_tainted_rock_stairs",              new StairsBlock(POLISHED_TAINTED_ROCK.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_BRICKS_STAIRS                = registerBlockItem        ("tainted_rock_bricks_stairs",                new StairsBlock(TAINTED_ROCK_BRICKS.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TAINTED_ROCK_BRICKS_STAIRS        = registerBlockItem        ("cracked_tainted_rock_bricks_stairs",        new StairsBlock(CRACKED_TAINTED_ROCK_BRICKS.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_TILES_STAIRS                 = registerBlockItem        ("tainted_rock_tiles_stairs",                 new StairsBlock(TAINTED_ROCK_TILES.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TAINTED_ROCK_TILES_STAIRS         = registerBlockItem        ("cracked_tainted_rock_tiles_stairs",         new StairsBlock(CRACKED_TAINTED_ROCK_TILES.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         SMALL_TAINTED_ROCK_BRICKS_STAIRS          = registerBlockItem        ("small_tainted_rock_bricks_stairs",          new StairsBlock(SMALL_TAINTED_ROCK_BRICKS.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS  = registerBlockItem        ("cracked_small_tainted_rock_bricks_stairs",  new StairsBlock(CRACKED_SMALL_TAINTED_ROCK_BRICKS.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_ITEM_STAND                   = registerBlockItem        ("tainted_rock_item_stand",                   new ItemStandBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TAINTED_ROCK_ITEM_PEDESTAL                = registerBlockItem        ("tainted_rock_item_pedestal",                new StoneItemPedestalBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK                              = registerBlockItem        ("twisted_rock",                              new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         SMOOTH_TWISTED_ROCK                       = registerBlockItem        ("smooth_twisted_rock",                       new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         POLISHED_TWISTED_ROCK                     = registerBlockItem        ("polished_twisted_rock",                     new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_BRICKS                       = registerBlockItem        ("twisted_rock_bricks",                       new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TWISTED_ROCK_BRICKS               = registerBlockItem        ("cracked_twisted_rock_bricks",               new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_TILES                        = registerBlockItem        ("twisted_rock_tiles",                        new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TWISTED_ROCK_TILES                = registerBlockItem        ("cracked_twisted_rock_tiles",                new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         SMALL_TWISTED_ROCK_BRICKS                 = registerBlockItem        ("small_twisted_rock_bricks",                 new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_SMALL_TWISTED_ROCK_BRICKS         = registerBlockItem        ("cracked_small_twisted_rock_bricks",         new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_PILLAR                       = registerBlockItem        ("twisted_rock_pillar",                       new PillarBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_PILLAR_CAP                   = registerBlockItem        ("twisted_rock_pillar_cap",                   new PillarBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_COLUMN                       = registerBlockItem        ("twisted_rock_column",                       new PillarBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_COLUMN_CAP                   = registerBlockItem        ("twisted_rock_column_cap",                   new PillarBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CUT_TWISTED_ROCK                          = registerBlockItem        ("cut_twisted_rock",                          new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CHISELED_TWISTED_ROCK                     = registerBlockItem        ("chiseled_twisted_rock",                     new Block(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_PRESSURE_PLATE               = registerBlockItem        ("twisted_rock_pressure_plate",               new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_WALL                         = registerBlockItem        ("twisted_rock_wall",                         new WallBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_BRICKS_WALL                  = registerBlockItem        ("twisted_rock_bricks_wall",                  new WallBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TWISTED_ROCK_BRICKS_WALL          = registerBlockItem        ("cracked_twisted_rock_bricks_wall",          new WallBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_TILES_WALL                   = registerBlockItem        ("twisted_rock_tiles_wall",                   new WallBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TWISTED_ROCK_TILES_WALL           = registerBlockItem        ("cracked_twisted_rock_tiles_wall",           new WallBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         SMALL_TWISTED_ROCK_BRICKS_WALL            = registerBlockItem        ("small_twisted_rock_bricks_wall",            new WallBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL    = registerBlockItem        ("cracked_small_twisted_rock_bricks_wall",    new WallBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_SLAB                         = registerBlockItem        ("twisted_rock_slab",                         new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         SMOOTH_TWISTED_ROCK_SLAB                  = registerBlockItem        ("smooth_twisted_rock_slab",                  new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         POLISHED_TWISTED_ROCK_SLAB                = registerBlockItem        ("polished_twisted_rock_slab",                new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_BRICKS_SLAB                  = registerBlockItem        ("twisted_rock_bricks_slab",                  new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TWISTED_ROCK_BRICKS_SLAB          = registerBlockItem        ("cracked_twisted_rock_bricks_slab",          new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_TILES_SLAB                   = registerBlockItem        ("twisted_rock_tiles_slab",                   new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TWISTED_ROCK_TILES_SLAB           = registerBlockItem        ("cracked_twisted_rock_tiles_slab",           new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         SMALL_TWISTED_ROCK_BRICKS_SLAB            = registerBlockItem        ("small_twisted_rock_bricks_slab",            new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB    = registerBlockItem        ("cracked_small_twisted_rock_bricks_slab",    new SlabBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_STAIRS                       = registerBlockItem        ("twisted_rock_stairs",                       new StairsBlock(TWISTED_ROCK.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         SMOOTH_TWISTED_ROCK_STAIRS                = registerBlockItem        ("smooth_twisted_rock_stairs",                new StairsBlock(SMOOTH_TWISTED_ROCK.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         POLISHED_TWISTED_ROCK_STAIRS              = registerBlockItem        ("polished_twisted_rock_stairs",              new StairsBlock(POLISHED_TWISTED_ROCK.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_BRICKS_STAIRS                = registerBlockItem        ("twisted_rock_bricks_stairs",                new StairsBlock(TWISTED_ROCK_BRICKS.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TWISTED_ROCK_BRICKS_STAIRS        = registerBlockItem        ("cracked_twisted_rock_bricks_stairs",        new StairsBlock(CRACKED_TWISTED_ROCK_BRICKS.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_TILES_STAIRS                 = registerBlockItem        ("twisted_rock_tiles_stairs",                 new StairsBlock(TWISTED_ROCK_TILES.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_TWISTED_ROCK_TILES_STAIRS         = registerBlockItem        ("cracked_twisted_rock_tiles_stairs",         new StairsBlock(CRACKED_TWISTED_ROCK_TILES.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         SMALL_TWISTED_ROCK_BRICKS_STAIRS          = registerBlockItem        ("small_twisted_rock_bricks_stairs",          new StairsBlock(SMALL_TWISTED_ROCK_BRICKS.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS  = registerBlockItem        ("cracked_small_twisted_rock_bricks_stairs",  new StairsBlock(CRACKED_SMALL_TWISTED_ROCK_BRICKS.getDefaultState(), Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_ITEM_STAND                   = registerBlockItem        ("twisted_rock_item_stand",                   new ItemStandBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);
    public static final Block                                         TWISTED_ROCK_ITEM_PEDESTAL                = registerBlockItem        ("twisted_rock_item_pedestal",                new StoneItemPedestalBlock(Settings.of(Material.STONE, MapColor.STONE_GRAY).sounds(BLOCK_TAINTED_ROCK_SOUNDS).strength(1.25f, 9.0f)), MALUM_ARCANE_ROCKS);

    // items & blocks, sorted [Malum: Natural Wonders]
    public static final Item                                          HOLY_SAP                                  = registerItem             ("holy_sap",                                  new Item(new Item.Settings().group(MALUM)));
    public static final Item                                          HOLY_SAPBALL                              = registerItem             ("holy_sapball",                              new Item(new Item.Settings().group(MALUM)));
    public static final Item                                          HOLY_SYRUP                                = registerItem             ("holy_syrup",                                new HolySyrupItem(new Item.Settings().group(MALUM).food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0), 1.0f).alwaysEdible().hunger(8).saturationModifier(0.4f).build())));
    public static final Block                                         RUNEWOOD_LEAVES                           = registerBlockItem        ("runewood_leaves",                           new GradientLeavesBlock(Settings.of(Material.LEAVES).strength(0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque()), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_SAPLING                          = registerBlockItem        ("runewood_sapling",                          new SaplingBlock(new RunewoodSaplingGenerator(), Settings.of(Material.LEAVES).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_LOG                              = registerBlockItem        ("runewood_log",                              new PillarBlock(Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         STRIPPED_RUNEWOOD_LOG                     = registerBlockItem        ("stripped_runewood_log",                     new PillarBlock(Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD                                  = registerBlockItem        ("runewood",                                  new PillarBlock(Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         STRIPPED_RUNEWOOD                         = registerBlockItem        ("stripped_runewood",                         new PillarBlock(Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         EXPOSED_RUNEWOOD_LOG                      = registerBlockItem        ("exposed_runewood_log",                      new PillarBlock(Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         REVEALED_RUNEWOOD_LOG                     = registerBlockItem        ("revealed_runewood_log",                     new RevealedPillarBlock(Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD), HOLY_SAP, STRIPPED_RUNEWOOD_LOG), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_PLANKS                           = registerBlockItem        ("runewood_planks",                           new Block(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         VERTICAL_RUNEWOOD_PLANKS                  = registerBlockItem        ("vertical_runewood_planks",                  new Block(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_PANEL                            = registerBlockItem        ("runewood_panel",                            new Block(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_TILES                            = registerBlockItem        ("runewood_tiles",                            new Block(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_PLANKS_SLAB                      = registerBlockItem        ("runewood_planks_slab",                      new SlabBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         VERTICAL_RUNEWOOD_PLANKS_SLAB             = registerBlockItem        ("vertical_runewood_planks_slab",             new SlabBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_PANEL_SLAB                       = registerBlockItem        ("runewood_panel_slab",                       new SlabBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_TILES_SLAB                       = registerBlockItem        ("runewood_tiles_slab",                       new SlabBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_PLANKS_STAIRS                    = registerBlockItem        ("runewood_planks_stairs",                    new StairsBlock(RUNEWOOD_PLANKS.getDefaultState(), Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         VERTICAL_RUNEWOOD_PLANKS_STAIRS           = registerBlockItem        ("vertical_runewood_planks_stairs",           new StairsBlock(VERTICAL_RUNEWOOD_PLANKS.getDefaultState(), Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_PANEL_STAIRS                     = registerBlockItem        ("runewood_panel_stairs",                     new StairsBlock(RUNEWOOD_PANEL.getDefaultState(), Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_TILES_STAIRS                     = registerBlockItem        ("runewood_tiles_stairs",                     new StairsBlock(RUNEWOOD_TILES.getDefaultState(), Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         CUT_RUNEWOOD_PLANKS                       = registerBlockItem        ("cut_runewood_planks",                       new Block(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_BEAM                             = registerBlockItem        ("runewood_beam",                             new PillarBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_DOOR                             = registerBlock            ("runewood_door",                             new DoorBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static final Item                                          RUNEWOOD_DOOR_ITEM                        = registerItem             ("runewood_door",                             new TallBlockItem(RUNEWOOD_DOOR, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    public static final Block                                         RUNEWOOD_TRAPDOOR                         = registerBlockItem        ("runewood_trapdoor",                         new TrapdoorBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD).nonOpaque()), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOLID_RUNEWOOD_TRAPDOOR                   = registerBlockItem        ("solid_runewood_trapdoor",                   new TrapdoorBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_PLANKS_BUTTON                    = registerBlockItem        ("runewood_planks_button",                    new WoodenButtonBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_PLANKS_PRESSURE_PLATE            = registerBlockItem        ("runewood_planks_pressure_plate",            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_PLANKS_FENCE                     = registerBlockItem        ("runewood_planks_fence",                     new FenceBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_PLANKS_FENCE_GATE                = registerBlockItem        ("runewood_planks_fence_gate",                new FenceGateBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_ITEM_STAND                       = registerBlockItem        ("runewood_item_stand",                       new ItemStandBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_ITEM_PEDESTAL                    = registerBlockItem        ("runewood_item_pedestal",                    new WoodenItemPedestalBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         RUNEWOOD_SIGN                             = registerBlock            ("runewood_sign",                             new SignBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).noCollision().sounds(BlockSoundGroup.WOOD), RUNEWOOD_SIGN_TYPE));
    public static final Block                                         RUNEWOOD_WALL_SIGN                        = registerBlock            ("runewood_wall_sign",                        new WallSignBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).noCollision().sounds(BlockSoundGroup.WOOD), RUNEWOOD_SIGN_TYPE));
    public static final Item                                          RUNEWOOD_SIGN_ITEM                        = registerItem             ("runewood_sign",                             new SignItem(new Item.Settings().maxCount(16).group(MALUM_NATURAL_WONDERS), RUNEWOOD_SIGN, RUNEWOOD_WALL_SIGN));
    public static final Item                                          RUNEWOOD_BOAT                             = registerItem             ("runewood_boat",                             new BoatItem(MalumBoatTypes.RUNEWOOD, new Item.Settings().maxCount(1).group(MALUM_NATURAL_WONDERS)));
    public static final Item                                          UNHOLY_SAP                                = registerItem             ("unholy_sap",                                new Item(new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    public static final Item                                          UNHOLY_SAPBALL                            = registerItem             ("unholy_sapball",                            new Item(new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    public static final Item                                          UNHOLY_SYRUP                              = registerItem             ("unholy_syrup",                              new HolySyrupItem(new Item.Settings().group(MALUM_NATURAL_WONDERS).food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 300, 0), 1.0f).alwaysEdible().hunger(8).saturationModifier(0.4f).build())));
    public static final Block                                         SOULWOOD_LEAVES                           = registerBlockItem        ("soulwood_leaves",                           new GradientLeavesBlock(Settings.of(Material.LEAVES).strength(0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_SAPLING                          = registerBlockItem        ("soulwood_sapling",                          new SaplingBlock(new SoulwoodSaplingGenerator(), Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_LOG                              = registerBlockItem        ("soulwood_log",                              new PillarBlock(Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         STRIPPED_SOULWOOD_LOG                     = registerBlockItem        ("stripped_soulwood_log",                     new PillarBlock(Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD                                  = registerBlockItem        ("soulwood",                                  new PillarBlock(Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         STRIPPED_SOULWOOD                         = registerBlockItem        ("stripped_soulwood",                         new PillarBlock(Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         EXPOSED_SOULWOOD_LOG                      = registerBlockItem        ("exposed_soulwood_log",                      new PillarBlock(Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         REVEALED_SOULWOOD_LOG                     = registerBlockItem        ("revealed_soulwood_log",                     new RevealedPillarBlock(Settings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD), UNHOLY_SAP, STRIPPED_SOULWOOD_LOG), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_PLANKS                           = registerBlockItem        ("soulwood_planks",                           new Block(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         VERTICAL_SOULWOOD_PLANKS                  = registerBlockItem        ("vertical_soulwood_planks",                  new Block(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_PANEL                            = registerBlockItem        ("soulwood_panel",                            new Block(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_TILES                            = registerBlockItem        ("soulwood_tiles",                            new Block(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_PLANKS_SLAB                      = registerBlockItem        ("soulwood_planks_slab",                      new SlabBlock(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         VERTICAL_SOULWOOD_PLANKS_SLAB             = registerBlockItem        ("vertical_soulwood_planks_slab",             new SlabBlock(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_PANEL_SLAB                       = registerBlockItem        ("soulwood_panel_slab",                       new SlabBlock(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_TILES_SLAB                       = registerBlockItem        ("soulwood_tiles_slab",                       new SlabBlock(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_PLANKS_STAIRS                    = registerBlockItem        ("soulwood_planks_stairs",                    new StairsBlock(SOULWOOD_PLANKS.getDefaultState(), Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         VERTICAL_SOULWOOD_PLANKS_STAIRS           = registerBlockItem        ("vertical_soulwood_planks_stairs",           new StairsBlock(VERTICAL_SOULWOOD_PLANKS.getDefaultState(), Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_PANEL_STAIRS                     = registerBlockItem        ("soulwood_panel_stairs",                     new StairsBlock(SOULWOOD_PANEL.getDefaultState(), Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_TILES_STAIRS                     = registerBlockItem        ("soulwood_tiles_stairs",                     new StairsBlock(SOULWOOD_TILES.getDefaultState(), Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         CUT_SOULWOOD_PLANKS                       = registerBlockItem        ("cut_soulwood_planks",                       new Block(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_BEAM                             = registerBlockItem        ("soulwood_beam",                             new PillarBlock(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_DOOR                             = registerBlock            ("soulwood_door",                             new DoorBlock(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static final Item                                          SOULWOOD_DOOR_ITEM                        = registerItem             ("soulwood_door",                             new TallBlockItem(SOULWOOD_DOOR, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    public static final Block                                         SOULWOOD_TRAPDOOR                         = registerBlockItem        ("soulwood_trapdoor",                         new TrapdoorBlock(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD).nonOpaque()), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOLID_SOULWOOD_TRAPDOOR                   = registerBlockItem        ("solid_soulwood_trapdoor",                   new TrapdoorBlock(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD).nonOpaque()), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_PLANKS_BUTTON                    = registerBlockItem        ("soulwood_planks_button",                    new WoodenButtonBlock(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_PLANKS_PRESSURE_PLATE            = registerBlockItem        ("soulwood_planks_pressure_plate",            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_PLANKS_FENCE                     = registerBlockItem        ("soulwood_planks_fence",                     new FenceBlock(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_PLANKS_FENCE_GATE                = registerBlockItem        ("soulwood_planks_fence_gate",                new FenceGateBlock(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_ITEM_STAND                       = registerBlockItem        ("soulwood_item_stand",                       new ItemStandBlock(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_ITEM_PEDESTAL                    = registerBlockItem        ("soulwood_item_pedestal",                    new WoodenItemPedestalBlock(Settings.of(Material.WOOD, MapColor.TERRACOTTA_PURPLE).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), MALUM_NATURAL_WONDERS);
    public static final Block                                         SOULWOOD_SIGN                             = registerBlock            ("soulwood_sign",                             new SignBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).noCollision().sounds(BlockSoundGroup.WOOD), SOULWOOD_SIGN_TYPE));
    public static final Block                                         SOULWOOD_WALL_SIGN                        = registerBlock            ("soulwood_wall_sign",                        new WallSignBlock(Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).noCollision().sounds(BlockSoundGroup.WOOD), SOULWOOD_SIGN_TYPE));
    public static final Item                                          SOULWOOD_SIGN_ITEM                        = registerItem             ("soulwood_sign",                             new SignItem(new Item.Settings().maxCount(16).group(MALUM_NATURAL_WONDERS), SOULWOOD_SIGN, SOULWOOD_WALL_SIGN));
    public static final Item                                          SOULWOOD_BOAT                             = registerItem             ("soulwood_boat",                             new BoatItem(MalumBoatTypes.SOULWOOD, new Item.Settings().maxCount(1).group(MALUM_NATURAL_WONDERS)));

    // items & blocks, sorted [Malum: Spirits]
    public static final Item                                          SACRED_SPIRIT                             = registerItem             ("sacred_spirit",                             new MalumSpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritTypeRegistry.SACRED_SPIRIT));
    public static final Item                                          WICKED_SPIRIT                             = registerItem             ("wicked_spirit",                             new MalumSpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritTypeRegistry.WICKED_SPIRIT));
    public static final Item                                          ARCANE_SPIRIT                             = registerItem             ("arcane_spirit",                             new MalumSpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritTypeRegistry.ARCANE_SPIRIT));
    public static final Item                                          ELDRITCH_SPIRIT                           = registerItem             ("eldritch_spirit",                           new MalumSpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritTypeRegistry.ELDRITCH_SPIRIT));
    public static final Item                                          EARTHEN_SPIRIT                            = registerItem             ("earthen_spirit",                            new MalumSpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritTypeRegistry.EARTHEN_SPIRIT));
    public static final Item                                          INFERNAL_SPIRIT                           = registerItem             ("infernal_spirit",                           new MalumSpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritTypeRegistry.INFERNAL_SPIRIT));
    public static final Item                                          AERIAL_SPIRIT                             = registerItem             ("aerial_spirit",                             new MalumSpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritTypeRegistry.AERIAL_SPIRIT));
    public static final Item                                          AQUEOUS_SPIRIT                            = registerItem             ("aqueous_spirit",                            new MalumSpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritTypeRegistry.AQUEOUS_SPIRIT));

    public static final Block                                         BLOCK_OF_ARCANE_CHARCOAL                  = registerBlockItem        ("block_of_arcane_charcoal",                  new Block(Settings.of(Material.STONE, MapColor.BLACK).strength(5.0f, 6.0f).sounds(BLOCK_ARCANE_CHARCOAL_SOUNDS)), MALUM);
    public static final Item                                          ARCANE_CHARCOAL                           = registerItem             ("arcane_charcoal",                           new Item(new Item.Settings().group(MALUM)));

    // block entities
    public static final BlockEntityType<ItemStandBlockEntity>         ITEM_STAND_BLOCK_ENTITY                   = registerBlockEntity      ("item_stand",                                BlockEntityType.Builder.create(ItemStandBlockEntity::new, RUNEWOOD_ITEM_STAND, SOULWOOD_ITEM_STAND, TAINTED_ROCK_ITEM_STAND, TWISTED_ROCK_ITEM_STAND).build(null));
    public static final BlockEntityType<ItemPedestalBlockEntity>      ITEM_PEDESTAL_BLOCK_ENTITY                = registerBlockEntity      ("item_pedestal",                             BlockEntityType.Builder.create(ItemPedestalBlockEntity::new, RUNEWOOD_ITEM_PEDESTAL, SOULWOOD_ITEM_PEDESTAL, TAINTED_ROCK_ITEM_PEDESTAL, TWISTED_ROCK_ITEM_PEDESTAL).build(null));
    public static final BlockEntityType<SpiritAltarBlockEntity>       SPIRIT_ALTAR_BLOCK_ENTITY                 = registerBlockEntity      ("spirit_altar",                              BlockEntityType.Builder.create(SpiritAltarBlockEntity::new, SPIRIT_ALTAR).build(null));
    public static final BlockEntityType<EtherBlockEntity>             ETHER_BLOCK_ENTITY                        = registerBlockEntity      ("ether",                                     BlockEntityType.Builder.create(EtherBlockEntity::new, ETHER, ETHER_TORCH, WALL_ETHER_TORCH).build(null));

    public static final RecipeType<SpiritInfusionRecipe>              SPIRIT_INFUSION                           = registerRecipeType       ("spirit_infusion",                           new RecipeType<>() { public String toString() { return "spirit_infusion"; } });
    public static final RecipeSerializer<SpiritInfusionRecipe>        SPIRIT_INFUSION_SERIALIZER                = registerRecipeSerializer ("spirit_infusion",                           new SpiritInfusionRecipe.Serializer<>(SpiritInfusionRecipe::new));

    public static final Feature<DefaultFeatureConfig>                 RUNEWOOD_TREE_FEATURE                     = registerFeature          ("runewood_tree",                             new GradientTreeFeature(EXPOSED_RUNEWOOD_LOG, RUNEWOOD_LEAVES, RUNEWOOD_LOG, RUNEWOOD_SAPLING));
    public static final Feature<DefaultFeatureConfig>                 SOULWOOD_TREE_FEATURE                     = registerFeature          ("soulwood_tree",                             new GradientTreeFeature(EXPOSED_SOULWOOD_LOG, SOULWOOD_LEAVES, SOULWOOD_LOG, SOULWOOD_SAPLING));

    public static final List<OreFeatureConfig.Target>                 SOULSTONE_ORE_TARGETS                     = List.of                  (OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, SOULSTONE_ORE.getDefaultState()),
                                                                                                                                            OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, DEEPSLATE_SOULSTONE_ORE.getDefaultState()));
    public static final Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> CONFIGURED_RUNEWOOD_TREE_FEATURE     = registerConfiguredFeature("runewood_tree",       RUNEWOOD_TREE_FEATURE);
    public static final Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> CONFIGURED_SOULWOOD_TREE_FEATURE     = registerConfiguredFeature("soulwood_tree",       SOULWOOD_TREE_FEATURE);

    public static final Holder<ConfiguredFeature<OreFeatureConfig, ?>>     LOWER_ORE_SOULSTONE_CONFIGURED       = registerConfiguredFeature("lower_ore_soulstone", Feature.ORE, new OreFeatureConfig(SOULSTONE_ORE_TARGETS, 12)); // i == maxSize
    public static final Holder<PlacedFeature>                              LOWER_ORE_SOULSTONE_PLACED           = registerPlacedFeature    ("lower_ore_soulstone", LOWER_ORE_SOULSTONE_CONFIGURED, OrePlacedFeatures.commonOrePlacementModifiers(8, HeightRangePlacementModifier.createUniform(YOffset.getBottom(), YOffset.fixed(30)))); // bottom of world and y 30

    public static final Holder<ConfiguredFeature<OreFeatureConfig, ?>>     UPPER_ORE_SOULSTONE_CONFIGURED       = registerConfiguredFeature("upper_ore_soulstone", Feature.ORE, new OreFeatureConfig(SOULSTONE_ORE_TARGETS, 6)); // i == maxSize
    public static final Holder<PlacedFeature>                              UPPER_ORE_SOULSTONE_PLACED           = registerPlacedFeature    ("upper_ore_soulstone", UPPER_ORE_SOULSTONE_CONFIGURED, OrePlacedFeatures.commonOrePlacementModifiers(5, HeightRangePlacementModifier.createUniform(YOffset.fixed(60), YOffset.fixed(100)))); // y 60 to y 100


    static Holder<PlacedFeature> registerPlacedFeature(String id, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers) {
        return PlacedFeatureUtil.register(id, configuredFeature, modifiers);
    }

    static <T extends Block> T registerBlock(String id, T block) {
        BLOCKS.put(new Identifier(MODID, id), block);
        return block;
    }

    static <T extends Block> T registerBlockItem(String id, T block, ItemGroup itemGroup) {
        BLOCKS.put(new Identifier(MODID, id), block);
        ITEMS.put(new Identifier(MODID, id), new BlockItem(block, new Item.Settings().group(itemGroup)));
        return block;
    }

    static <T extends ScytheItem> T registerScytheItem(String id, T item) {
        SCYTHES.add(item);
        return registerItem(id, item);
    }

    static <T extends Item> T registerItem(String id, T item) {
        ITEMS.put(new Identifier(MODID, id), item);
        return item;
    }

    static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String id, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(new Identifier(MODID, id), type);
        return type;
    }

    static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String id, F feature) {
        FEATURES.put(new Identifier(MODID, id), feature);
        return feature;
    }

    static Holder<ConfiguredFeature<DefaultFeatureConfig, ?>> registerConfiguredFeature(String id, Feature<DefaultFeatureConfig> feature) {
        return ConfiguredFeatureUtil.register(new Identifier(MODID, id).toString(), feature);
    }

    static <FC extends FeatureConfig, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> registerConfiguredFeature(String id, F feature, FC featureConfig) {
        return ConfiguredFeatureUtil.register(new Identifier(MODID, id).toString(), feature, featureConfig);
    }

    static SoundEvent registerSoundEvent(String id) {
        SoundEvent soundEvent = new SoundEvent(new Identifier(MODID, id));
        SOUND_EVENTS.put(new Identifier(MODID, id), soundEvent);
        return soundEvent;
    }

    static <T extends Recipe<?>> RecipeType<T> registerRecipeType(String id, RecipeType<T> type) {
        RECIPE_TYPES.put(new Identifier(MODID, id), type);
        return type;
    }

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S registerRecipeSerializer(String id, S serializer) {
        RECIPE_SERIALIZER.put(new Identifier(MODID, id), serializer);
        return serializer;
    }

    static SignType registerSignType(SignType signType) {
        SIGN_TYPES.add(signType);
        return signType;
    }

    public static void init() {
        ITEMS.forEach((id, item) -> Registry.register(Registry.ITEM, id, item));
        BLOCKS.forEach((id, block) -> Registry.register(Registry.BLOCK, id, block));
        BLOCK_ENTITY_TYPES.forEach((id, entityType) -> Registry.register(Registry.BLOCK_ENTITY_TYPE, id, entityType));
        FEATURES.forEach((id, feature) -> Registry.register(Registry.FEATURE, id, feature));
        RECIPE_TYPES.forEach((id, type) -> Registry.register(Registry.RECIPE_TYPE, id, type));
        RECIPE_SERIALIZER.forEach((id, serializer) -> Registry.register(Registry.RECIPE_SERIALIZER, id, serializer));
        SIGN_TYPES.forEach(SignType::register);
        SOUND_EVENTS.forEach((id, soundEvent) -> Registry.register(Registry.SOUND_EVENT, id, soundEvent));
        MalumEnchantments.init();
        MalumSoundRegistry.init();
        MalumEntityRegistry.init();
    }
}
