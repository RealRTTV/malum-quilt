package ca.rttv.malum.registry;

import ca.rttv.malum.item.*;
import ca.rttv.malum.util.spirit.SpiritType;
import dev.emi.trinkets.api.TrinketItem;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static ca.rttv.malum.Malum.*;
import static ca.rttv.malum.registry.MalumArmorMaterials.SOUL_CLOAK;
import static ca.rttv.malum.registry.MalumAttributeRegistry.*;
import static ca.rttv.malum.registry.MalumBlockRegistry.*;
import static net.minecraft.item.ItemGroup.BUILDING_BLOCKS;
import static net.minecraft.item.ItemGroup.MISC;

@SuppressWarnings("unused")
public interface MalumItemRegistry {
    Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    Set<ScytheItem> SCYTHES = new ReferenceOpenHashSet<>();

    // items & blocks, sorted [Main: Natural Wonders]
    Item HOLY_SAP                                    = registerItem             ("holy_sap",                                  new Item(new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item HOLY_SAPBALL                                = registerItem             ("holy_sapball",                              new Item(new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item HOLY_SYRUP                                  = registerItem             ("holy_syrup",                                new HolySyrupItem(new Item.Settings().group(MALUM_NATURAL_WONDERS).food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0), 1.0f).alwaysEdible().hunger(8).saturationModifier(0.4f).build())));
    Item RUNEWOOD_LEAVES                             = registerItem             ("runewood_leaves",                           new BlockItem(MalumBlockRegistry.RUNEWOOD_LEAVES, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_SAPLING                            = registerItem             ("runewood_sapling",                          new BlockItem(MalumBlockRegistry.RUNEWOOD_SAPLING, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_LOG                                = registerItem             ("runewood_log",                              new BlockItem(MalumBlockRegistry.RUNEWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item STRIPPED_RUNEWOOD_LOG                       = registerItem             ("stripped_runewood_log",                     new BlockItem(MalumBlockRegistry.STRIPPED_RUNEWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD                                    = registerItem             ("runewood",                                  new BlockItem(MalumBlockRegistry.RUNEWOOD, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item STRIPPED_RUNEWOOD                           = registerItem             ("stripped_runewood",                         new BlockItem(MalumBlockRegistry.STRIPPED_RUNEWOOD, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item EXPOSED_RUNEWOOD_LOG                        = registerItem             ("exposed_runewood_log",                      new BlockItem(MalumBlockRegistry.EXPOSED_RUNEWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item REVEALED_RUNEWOOD_LOG                       = registerItem             ("revealed_runewood_log",                     new BlockItem(MalumBlockRegistry.REVEALED_RUNEWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PLANKS                             = registerItem             ("runewood_planks",                           new BlockItem(MalumBlockRegistry.RUNEWOOD_PLANKS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item VERTICAL_RUNEWOOD_PLANKS                    = registerItem             ("vertical_runewood_planks",                  new BlockItem(MalumBlockRegistry.VERTICAL_RUNEWOOD_PLANKS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PANEL                              = registerItem             ("runewood_panel",                            new BlockItem(MalumBlockRegistry.RUNEWOOD_PANEL, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_TILES                              = registerItem             ("runewood_tiles",                            new BlockItem(MalumBlockRegistry.RUNEWOOD_TILES, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PLANKS_SLAB                        = registerItem             ("runewood_planks_slab",                      new BlockItem(MalumBlockRegistry.RUNEWOOD_PLANKS_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item VERTICAL_RUNEWOOD_PLANKS_SLAB               = registerItem             ("vertical_runewood_planks_slab",             new BlockItem(MalumBlockRegistry.VERTICAL_RUNEWOOD_PLANKS_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PANEL_SLAB                         = registerItem             ("runewood_panel_slab",                       new BlockItem(MalumBlockRegistry.RUNEWOOD_PANEL_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_TILES_SLAB                         = registerItem             ("runewood_tiles_slab",                       new BlockItem(MalumBlockRegistry.RUNEWOOD_TILES_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PLANKS_STAIRS                      = registerItem             ("runewood_planks_stairs",                    new BlockItem(MalumBlockRegistry.RUNEWOOD_PLANKS_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item VERTICAL_RUNEWOOD_PLANKS_STAIRS             = registerItem             ("vertical_runewood_planks_stairs",           new BlockItem(MalumBlockRegistry.VERTICAL_RUNEWOOD_PLANKS_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PANEL_STAIRS                       = registerItem             ("runewood_panel_stairs",                     new BlockItem(MalumBlockRegistry.RUNEWOOD_PANEL_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_TILES_STAIRS                       = registerItem             ("runewood_tiles_stairs",                     new BlockItem(MalumBlockRegistry.RUNEWOOD_TILES_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item CUT_RUNEWOOD_PLANKS                         = registerItem             ("cut_runewood_planks",                       new BlockItem(MalumBlockRegistry.CUT_RUNEWOOD_PLANKS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_BEAM                               = registerItem             ("runewood_beam",                             new BlockItem(MalumBlockRegistry.RUNEWOOD_BEAM, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_DOOR                               = registerItem             ("runewood_door",                             new TallBlockItem(MalumBlockRegistry.RUNEWOOD_DOOR, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_TRAPDOOR                           = registerItem             ("runewood_trapdoor",                         new BlockItem(MalumBlockRegistry.RUNEWOOD_TRAPDOOR, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOLID_RUNEWOOD_TRAPDOOR                     = registerItem             ("solid_runewood_trapdoor",                   new BlockItem(MalumBlockRegistry.SOLID_RUNEWOOD_TRAPDOOR, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PLANKS_BUTTON                      = registerItem             ("runewood_planks_button",                    new BlockItem(MalumBlockRegistry.RUNEWOOD_PLANKS_BUTTON, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PLANKS_PRESSURE_PLATE              = registerItem             ("runewood_planks_pressure_plate",            new BlockItem(MalumBlockRegistry.RUNEWOOD_PLANKS_PRESSURE_PLATE, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PLANKS_FENCE                       = registerItem             ("runewood_planks_fence",                     new BlockItem(MalumBlockRegistry.RUNEWOOD_PLANKS_FENCE, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PLANKS_FENCE_GATE                  = registerItem             ("runewood_planks_fence_gate",                new BlockItem(MalumBlockRegistry.RUNEWOOD_PLANKS_FENCE_GATE, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_ITEM_STAND                         = registerItem             ("runewood_item_stand",                       new BlockItem(MalumBlockRegistry.RUNEWOOD_ITEM_STAND, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_ITEM_PEDESTAL                      = registerItem             ("runewood_item_pedestal",                    new BlockItem(MalumBlockRegistry.RUNEWOOD_ITEM_PEDESTAL, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_SIGN                               = registerItem             ("runewood_sign",                             new SignItem(new Item.Settings().maxCount(16).group(MALUM_NATURAL_WONDERS), MalumBlockRegistry.RUNEWOOD_SIGN, RUNEWOOD_WALL_SIGN));
    Item RUNEWOOD_BOAT                               = registerItem             ("runewood_boat",                             new BoatItem(MalumBoatTypes.RUNEWOOD, new Item.Settings().maxCount(1).group(MALUM_NATURAL_WONDERS)));
    Item UNHOLY_SAP                                  = registerItem             ("unholy_sap",                                new Item(new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item UNHOLY_SAPBALL                              = registerItem             ("unholy_sapball",                            new Item(new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item UNHOLY_SYRUP                                = registerItem             ("unholy_syrup",                              new HolySyrupItem(new Item.Settings().group(MALUM_NATURAL_WONDERS).food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 300, 0), 1.0f).alwaysEdible().hunger(8).saturationModifier(0.4f).build())));
    Item SOULWOOD_LEAVES                             = registerItem             ("soulwood_leaves",                           new BlockItem(MalumBlockRegistry.SOULWOOD_LEAVES, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_SAPLING                            = registerItem             ("soulwood_sapling",                          new BlockItem(MalumBlockRegistry.SOULWOOD_SAPLING, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_LOG                                = registerItem             ("soulwood_log",                              new BlockItem(MalumBlockRegistry.SOULWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item STRIPPED_SOULWOOD_LOG                       = registerItem             ("stripped_soulwood_log",                     new BlockItem(MalumBlockRegistry.STRIPPED_SOULWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD                                    = registerItem             ("soulwood",                                  new BlockItem(MalumBlockRegistry.SOULWOOD, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item STRIPPED_SOULWOOD                           = registerItem             ("stripped_soulwood",                         new BlockItem(MalumBlockRegistry.STRIPPED_SOULWOOD, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item EXPOSED_SOULWOOD_LOG                        = registerItem             ("exposed_soulwood_log",                      new BlockItem(MalumBlockRegistry.EXPOSED_SOULWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item REVEALED_SOULWOOD_LOG                       = registerItem             ("revealed_soulwood_log",                     new BlockItem(MalumBlockRegistry.REVEALED_SOULWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PLANKS                             = registerItem             ("soulwood_planks",                           new BlockItem(MalumBlockRegistry.SOULWOOD_PLANKS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item VERTICAL_SOULWOOD_PLANKS                    = registerItem             ("vertical_soulwood_planks",                  new BlockItem(MalumBlockRegistry.VERTICAL_SOULWOOD_PLANKS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PANEL                              = registerItem             ("soulwood_panel",                            new BlockItem(MalumBlockRegistry.SOULWOOD_PANEL, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_TILES                              = registerItem             ("soulwood_tiles",                            new BlockItem(MalumBlockRegistry.SOULWOOD_TILES, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PLANKS_SLAB                        = registerItem             ("soulwood_planks_slab",                      new BlockItem(MalumBlockRegistry.SOULWOOD_PLANKS_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item VERTICAL_SOULWOOD_PLANKS_SLAB               = registerItem             ("vertical_soulwood_planks_slab",             new BlockItem(MalumBlockRegistry.VERTICAL_SOULWOOD_PLANKS_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PANEL_SLAB                         = registerItem             ("soulwood_panel_slab",                       new BlockItem(MalumBlockRegistry.SOULWOOD_PANEL_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_TILES_SLAB                         = registerItem             ("soulwood_tiles_slab",                       new BlockItem(MalumBlockRegistry.SOULWOOD_TILES_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PLANKS_STAIRS                      = registerItem             ("soulwood_planks_stairs",                    new BlockItem(MalumBlockRegistry.SOULWOOD_PLANKS_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item VERTICAL_SOULWOOD_PLANKS_STAIRS             = registerItem             ("vertical_soulwood_planks_stairs",           new BlockItem(MalumBlockRegistry.VERTICAL_SOULWOOD_PLANKS_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PANEL_STAIRS                       = registerItem             ("soulwood_panel_stairs",                     new BlockItem(MalumBlockRegistry.SOULWOOD_PANEL_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_TILES_STAIRS                       = registerItem             ("soulwood_tiles_stairs",                     new BlockItem(MalumBlockRegistry.SOULWOOD_TILES_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item CUT_SOULWOOD_PLANKS                         = registerItem             ("cut_soulwood_planks",                       new BlockItem(MalumBlockRegistry.CUT_SOULWOOD_PLANKS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_BEAM                               = registerItem             ("soulwood_beam",                             new BlockItem(MalumBlockRegistry.SOULWOOD_BEAM, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_DOOR                               = registerItem             ("soulwood_door",                             new TallBlockItem(MalumBlockRegistry.SOULWOOD_DOOR, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_TRAPDOOR                           = registerItem             ("soulwood_trapdoor",                         new BlockItem(MalumBlockRegistry.SOULWOOD_TRAPDOOR, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOLID_SOULWOOD_TRAPDOOR                     = registerItem             ("solid_soulwood_trapdoor",                   new BlockItem(MalumBlockRegistry.SOLID_SOULWOOD_TRAPDOOR, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PLANKS_BUTTON                      = registerItem             ("soulwood_planks_button",                    new BlockItem(MalumBlockRegistry.SOULWOOD_PLANKS_BUTTON, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PLANKS_PRESSURE_PLATE              = registerItem             ("soulwood_planks_pressure_plate",            new BlockItem(MalumBlockRegistry.SOULWOOD_PLANKS_PRESSURE_PLATE, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PLANKS_FENCE                       = registerItem             ("soulwood_planks_fence",                     new BlockItem(MalumBlockRegistry.SOULWOOD_PLANKS_FENCE, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PLANKS_FENCE_GATE                  = registerItem             ("soulwood_planks_fence_gate",                new BlockItem(MalumBlockRegistry.SOULWOOD_PLANKS_FENCE_GATE, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_ITEM_STAND                         = registerItem             ("soulwood_item_stand",                       new BlockItem(MalumBlockRegistry.SOULWOOD_ITEM_STAND, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_ITEM_PEDESTAL                      = registerItem             ("soulwood_item_pedestal",                    new BlockItem(MalumBlockRegistry.SOULWOOD_ITEM_PEDESTAL, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_SIGN                               = registerItem             ("soulwood_sign",                             new SignItem(new Item.Settings().maxCount(16).group(MALUM_NATURAL_WONDERS), MalumBlockRegistry.SOULWOOD_SIGN, SOULWOOD_WALL_SIGN));
    Item SOULWOOD_BOAT                               = registerItem             ("soulwood_boat",                             new BoatItem(MalumBoatTypes.SOULWOOD, new Item.Settings().maxCount(1).group(MALUM_NATURAL_WONDERS)));

// items & blocks, sorted [Main]
    Item ENCYCLOPEDIA_ARCANA                        = registerItem             ("encyclopedia_arcana",                       new EncyclopediaArcanaItem(new Item.Settings().rarity(Rarity.UNCOMMON).group(MALUM)));
    Item SOULSTONE_ORE                              = registerItem             ("soulstone_ore",                             new BlockItem(MalumBlockRegistry.SOULSTONE_ORE, new Item.Settings().group(MALUM)));
    Item DEEPSLATE_SOULSTONE_ORE                    = registerItem             ("deepslate_soulstone_ore",                   new BlockItem(MalumBlockRegistry.DEEPSLATE_SOULSTONE_ORE, new Item.Settings().group(MALUM)));
    Item RAW_SOULSTONE                              = registerItem             ("raw_soulstone",                             new Item(new Item.Settings().group(MALUM)));
    Item PROCESSED_SOULSTONE                        = registerItem             ("processed_soulstone",                       new Item(new Item.Settings().group(MALUM)));
    Item BLOCK_OF_RAW_SOULSTONE                     = registerItem             ("block_of_raw_soulstone",                    new BlockItem(MalumBlockRegistry.BLOCK_OF_RAW_SOULSTONE, new Item.Settings().group(MALUM)));
    Item BLOCK_OF_SOULSTONE                         = registerItem             ("block_of_soulstone",                        new BlockItem(MalumBlockRegistry.BLOCK_OF_SOULSTONE, new Item.Settings().group(MALUM)));
    Item SPIRIT_ALTAR                               = registerItem             ("spirit_altar",                              new BlockItem(MalumBlockRegistry.SPIRIT_ALTAR, new Item.Settings().group(MALUM)));
    Item SPIRIT_JAR                                 = registerItem             ("spirit_jar",                                new BlockItem(MalumBlockRegistry.SPIRIT_JAR, new Item.Settings().group(MALUM)));
    // soul vial

    Item RUNEWOOD_OBELISK                           = registerItem             ("runewood_obelisk",                          new TallBlockItem(MalumBlockRegistry.RUNEWOOD_OBELISK, new Item.Settings().group(MALUM)));
    Item BRILLIANT_OBELISK                          = registerItem             ("brilliant_obelisk",                         new TallBlockItem(MalumBlockRegistry.BRILLIANT_OBELISK, new Item.Settings().group(MALUM)));
    Item SPIRIT_CRUCIBLE                            = registerItem             ("spirit_crucible",                           new TallBlockItem(MalumBlockRegistry.SPIRIT_CRUCIBLE, new Item.Settings().group(MALUM)));
    Item RUNEWOOD_TOTEM_BASE                        = registerItem             ("runewood_totem_base",                       new BlockItem(MalumBlockRegistry.RUNEWOOD_TOTEM_BASE, new Item.Settings().group(MALUM)));
    Item SOULWOOD_TOTEM_BASE                        = registerItem             ("soulwood_totem_base",                       new BlockItem(MalumBlockRegistry.SOULWOOD_TOTEM_BASE, new Item.Settings().group(MALUM)));
    // soulwood plinth
    // soulwood fusion plate
    Item HEX_ASH                                    = registerItem             ("hex_ash",                                   new Item(new Item.Settings().group(MALUM)));
    Item SPIRIT_FABRIC                              = registerItem             ("spirit_fabric",                             new Item(new Item.Settings().group(MALUM)));
    Item SPECTRAL_LENS                              = registerItem             ("spectral_lens",                             new Item(new Item.Settings().group(MALUM)));
    Item POPPET                                     = registerItem             ("poppet",                                    new Item(new Item.Settings().group(MALUM)));
    Item HALLOWED_GOLD_INGOT                        = registerItem             ("hallowed_gold_ingot",                       new Item(new Item.Settings().group(MALUM)));
    Item HALLOWED_GOLD_NUGGET                       = registerItem             ("hallowed_gold_nugget",                      new Item(new Item.Settings().group(MALUM)));
    Item BLOCK_OF_HALLOWED_GOLD                     = registerItem             ("block_of_hallowed_gold",                    new BlockItem(MalumBlockRegistry.BLOCK_OF_HALLOWED_GOLD, new Item.Settings().group(MALUM)));
    Item HALLOWED_SPIRIT_RESONATOR                  = registerItem             ("hallowed_spirit_resonator",                 new Item(new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_INGOT                   = registerItem             ("soul_stained_steel_ingot",                  new Item(new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_NUGGET                  = registerItem             ("soul_stained_steel_nugget",                 new Item(new Item.Settings().group(MALUM)));
    Item BLOCK_OF_SOUL_STAINED_STEEL                = registerItem             ("block_of_soul_stained_steel",               new BlockItem(MalumBlockRegistry.BLOCK_OF_SOUL_STAINED_STEEL, new Item.Settings().group(MALUM)));
    Item STAINED_SPIRIT_RESONATOR                   = registerItem             ("stained_spirit_resonator",                  new Item(new Item.Settings().group(MALUM)));
    Item CRACKED_ALCHEMICAL_IMPETUS                 = registerItem             ("cracked_alchemical_impetus",                new Item(new Item.Settings().maxCount(1).group(MALUM)));
    Item ALCHEMICAL_IMPETUS                         = registerItem             ("alchemical_impetus",                        new Item(new Item.Settings().maxCount(1).group(MALUM)));
    // cracked vitric impetus
    // vitric impetus
    // cracked iron impetus
    // iron impetus
    // cracked copper impetus
    // copper impetus
    // cracked gold impetus
    // gold impetus
    Item ETHER                                       = registerItem             ("ether",                                     new ParticleEmittingEtherBlockItem(MalumBlockRegistry.ETHER, new Item.Settings().group(MALUM)));
    Item ETHER_TORCH                                 = registerItem             ("ether_torch",                               new EtherWallStandingBlockItem(MalumBlockRegistry.ETHER_TORCH, WALL_ETHER_TORCH, new Item.Settings().group(MALUM)));
    Item TAINTED_ETHER_BRAZIER                       = registerItem             ("tainted_ether_brazier",                     new EtherBlockItem(MalumBlockRegistry.TAINTED_ETHER_BRAZIER, new Item.Settings().group(MALUM)));
    Item TWISTED_ETHER_BRAZIER                       = registerItem             ("twisted_ether_brazier",                     new EtherBlockItem(MalumBlockRegistry.TWISTED_ETHER_BRAZIER, new Item.Settings().group(MALUM)));
    Item IRIDESCENT_ETHER                            = registerItem             ("iridescent_ether",                          new ParticleEmittingIridescentEtherBlockItem(MalumBlockRegistry.IRIDESCENT_ETHER, new Item.Settings().group(MALUM)));
    Item IRIDESCENT_ETHER_TORCH                      = registerItem             ("iridescent_ether_torch",                    new IridescentEtherWallStandingBlockItem(MalumBlockRegistry.IRIDESCENT_ETHER_TORCH, IRIDESCENT_WALL_ETHER_TORCH, new Item.Settings().group(MALUM)));
    Item TAINTED_IRIDESCENT_ETHER_BRAZIER            = registerItem             ("tainted_iridescent_ether_brazier",          new IridescentEtherBlockItem(MalumBlockRegistry.TAINTED_IRIDESCENT_ETHER_BRAZIER, new Item.Settings().group(MALUM)));
    Item TWISTED_IRIDESCENT_ETHER_BRAZIER            = registerItem             ("twisted_iridescent_ether_brazier",          new IridescentEtherBlockItem(MalumBlockRegistry.TWISTED_IRIDESCENT_ETHER_BRAZIER, new Item.Settings().group(MALUM)));
    Item SPIRIT_POUCH                                = registerItem             ("spirit_pouch",                              new SpiritPouchItem(new Item.Settings().group(MALUM).maxCount(1)));
    Item CRUDE_SCYTHE                                = registerScytheItem       ("crude_scythe",                              new ScytheItem(ToolMaterials.IRON, 3, -3.1f, 0.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_SCYTHE                   = registerScytheItem       ("soul_stained_steel_scythe",                 new ScytheItem(MalumToolMaterials.SOUL_STAINED_STEEL, 0.5f, -3.1f, 4.0f, new Item.Settings().group(MALUM)));
    // soulwood stave
    Item SOUL_STAINED_STEEL_SWORD                    = registerItem             ("soul_stained_steel_sword",                  new MagicSwordItem(MalumToolMaterials.SOUL_STAINED_STEEL, 0.0f, -2.4f, 3.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_PICKAXE                  = registerItem             ("soul_stained_steel_pickaxe",                new MagicPickaxeItem(MalumToolMaterials.SOUL_STAINED_STEEL, -1.0f, -2.8f, 2.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_AXE                      = registerItem             ("soul_stained_steel_axe",                    new MagicAxeItem(MalumToolMaterials.SOUL_STAINED_STEEL, 3.0f, -3.2f, 4.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_SHOVEL                   = registerItem             ("soul_stained_steel_shovel",                 new MagicShovelItem(MalumToolMaterials.SOUL_STAINED_STEEL, -0.5f, -3.0f, 2.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_HOE                      = registerItem             ("soul_stained_steel_hoe",                    new MagicHoeItem(MalumToolMaterials.SOUL_STAINED_STEEL, -2.0f, 0.0f, 2.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_HELMET                   = registerItem             ("soul_stained_steel_helmet",                 new MagicArmorItem(MalumArmorMaterials.SOUL_STAINED_STEEL, EquipmentSlot.HEAD, 1.0f, 3.0f, 0.0f, 0.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_CHESTPLATE               = registerItem             ("soul_stained_steel_chestplate",             new MagicArmorItem(MalumArmorMaterials.SOUL_STAINED_STEEL, EquipmentSlot.CHEST, 1.0f, 3.0f, 0.0f, 0.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_LEGGINGS                 = registerItem             ("soul_stained_steel_leggings",               new MagicArmorItem(MalumArmorMaterials.SOUL_STAINED_STEEL, EquipmentSlot.LEGS, 1.0f, 3.0f, 0.0f, 0.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_BOOTS                    = registerItem             ("soul_stained_steel_boots",                  new MagicArmorItem(MalumArmorMaterials.SOUL_STAINED_STEEL, EquipmentSlot.FEET, 1.0f, 3.0f, 0.0f, 0.0f, new Item.Settings().group(MALUM)));
    Item SOUL_HUNTER_CLOAK                           = registerItem             ("soul_hunter_cloak",                         new MagicArmorItem(SOUL_CLOAK, EquipmentSlot.HEAD, 0.0f, 0.0f, 1.0f, 1.0f, new Item.Settings().group(MALUM)));
    Item SOUL_HUNTER_ROBE                            = registerItem             ("soul_hunter_robe",                          new MagicArmorItem(SOUL_CLOAK, EquipmentSlot.CHEST,0.0f, 0.0f, 1.0f, 1.0f,  new Item.Settings().group(MALUM)));
    Item SOUL_HUNTER_LEGGINGS                        = registerItem             ("soul_hunter_leggings",                      new MagicArmorItem(SOUL_CLOAK, EquipmentSlot.LEGS, 0.0f, 0.0f, 1.0f, 1.0f, new Item.Settings().group(MALUM)));
    Item SOUL_HUNTER_BOOTS                           = registerItem             ("soul_hunter_boots",                         new MagicArmorItem(SOUL_CLOAK, EquipmentSlot.FEET, 0.0f, 0.0f, 1.0f, 1.0f, new Item.Settings().group(MALUM)));
    // tyrving
    Item GILDED_RING                                 = registerItem             ("gilded_ring",                               new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> modifiers.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uuid, "Trinket modifier", 2.0f, EntityAttributeModifier.Operation.ADDITION))));
    Item ORNATE_RING                                 = registerItem             ("ornate_ring",                               new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> modifiers.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(uuid, "Trinket modifier", 2.0f, EntityAttributeModifier.Operation.ADDITION))));
    Item ORNATE_NECKLACE                             = registerItem             ("ornate_necklace",                           new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> modifiers.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(uuid, "Trinket modifier", 4.0f, EntityAttributeModifier.Operation.ADDITION))));
    Item GILDED_BELT                                 = registerItem             ("gilded_belt",                               new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> modifiers.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uuid, "Trinket modifier", 4.0f, EntityAttributeModifier.Operation.ADDITION))));
    Item RING_OF_ARCANE_REACH                        = registerItem             ("ring_of_arcane_reach",                      new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> modifiers.put(SPIRIT_REACH, new EntityAttributeModifier(uuid, "Trinket modifier", 8.0f, EntityAttributeModifier.Operation.ADDITION))));
    Item RING_OF_ARCANE_SPOIL                        = registerItem             ("ring_of_arcane_spoil",                      new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> modifiers.put(SPIRIT_SPOILS, new EntityAttributeModifier(uuid, "Trinket modifier", 1.0f, EntityAttributeModifier.Operation.ADDITION))));
    Item RING_OF_PROWESS                             = registerItem             ("ring_of_prowess",                           new RingOfProwessItem(new Item.Settings().group(MALUM).maxCount(1)));
    Item RING_OF_CURATIVE_TALENT                     = registerItem             ("ring_of_curative_talent",                   new RingOfCurativeTalentItem(new Item.Settings().group(MALUM).maxCount(1)));
    Item RING_OF_WICKED_INTENT                       = registerItem             ("ring_of_wicked_intent",                     new RingOfWickedIntentItem(new Item.Settings().group(MALUM).maxCount(1)));
    Item NECKLACE_OF_THE_MYSTIC_MIRROR               = registerItem             ("necklace_of_the_mystic_mirror",             new TrinketItem(new Item.Settings().group(MALUM).maxCount(1)));
    Item NECKLACE_OF_THE_NARROW_EDGE                 = registerItem             ("necklace_of_the_narrow_edge",               new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> modifiers.put(SCYTHE_PROFICIENCY, new EntityAttributeModifier(uuid, "Trinket modifier", 4.0f, EntityAttributeModifier.Operation.ADDITION))));
    Item WARDED_BELT                                 = registerItem             ("warded_belt",                               new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> { modifiers.put(MAGIC_RESISTANCE, new EntityAttributeModifier(uuid, "Trinket modifier", 1.0f, EntityAttributeModifier.Operation.ADDITION)); modifiers.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uuid, "Trinket modifier", 2.0f, EntityAttributeModifier.Operation.ADDITION)); modifiers.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(uuid, "Trinket modifier", 0.2f, EntityAttributeModifier.Operation.ADDITION)); modifiers.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(uuid, "Trinket modifier", 1.0f, EntityAttributeModifier.Operation.ADDITION)); }));
    Item MAGEBANE_BELT                               = registerItem             ("magebane_belt",                             new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> { modifiers.put(MAGIC_RESISTANCE, new EntityAttributeModifier(uuid, "Trinket modifier", 2.0f, EntityAttributeModifier.Operation.ADDITION)); modifiers.put(SOUL_WARD_CAP, new EntityAttributeModifier(uuid, "Trinket modifier", 3.0f, EntityAttributeModifier.Operation.ADDITION)); }));
    // cracked ceaseless impetus
    // ceaseless impetus

    // items & blocks, sorted [Main: Arcane Rocks]
    Item TAINTED_ROCK                              = registerItem               ("tainted_rock",                              new BlockItem(MalumBlockRegistry.TAINTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMOOTH_TAINTED_ROCK                       = registerItem               ("smooth_tainted_rock",                       new BlockItem(MalumBlockRegistry.SMOOTH_TAINTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item POLISHED_TAINTED_ROCK                     = registerItem               ("polished_tainted_rock",                     new BlockItem(MalumBlockRegistry.POLISHED_TAINTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_BRICKS                       = registerItem               ("tainted_rock_bricks",                       new BlockItem(MalumBlockRegistry.TAINTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_BRICKS               = registerItem               ("cracked_tainted_rock_bricks",               new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_TILES                        = registerItem               ("tainted_rock_tiles",                        new BlockItem(MalumBlockRegistry.TAINTED_ROCK_TILES, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_TILES                = registerItem               ("cracked_tainted_rock_tiles",                new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_TILES, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TAINTED_ROCK_BRICKS                 = registerItem               ("small_tainted_rock_bricks",                 new BlockItem(MalumBlockRegistry.SMALL_TAINTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TAINTED_ROCK_BRICKS         = registerItem               ("cracked_small_tainted_rock_bricks",         new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_PILLAR                       = registerItem               ("tainted_rock_pillar",                       new BlockItem(MalumBlockRegistry.TAINTED_ROCK_PILLAR, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_PILLAR_CAP                   = registerItem               ("tainted_rock_pillar_cap",                   new BlockItem(MalumBlockRegistry.TAINTED_ROCK_PILLAR_CAP, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_COLUMN                       = registerItem               ("tainted_rock_column",                       new BlockItem(MalumBlockRegistry.TAINTED_ROCK_COLUMN, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_COLUMN_CAP                   = registerItem               ("tainted_rock_column_cap",                   new BlockItem(MalumBlockRegistry.TAINTED_ROCK_COLUMN_CAP, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CUT_TAINTED_ROCK                          = registerItem               ("cut_tainted_rock",                          new BlockItem(MalumBlockRegistry.CUT_TAINTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CHISELED_TAINTED_ROCK                     = registerItem               ("chiseled_tainted_rock",                     new BlockItem(MalumBlockRegistry.CHISELED_TAINTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_PRESSURE_PLATE               = registerItem               ("tainted_rock_pressure_plate",               new BlockItem(MalumBlockRegistry.TAINTED_ROCK_PRESSURE_PLATE, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_WALL                         = registerItem               ("tainted_rock_wall",                         new BlockItem(MalumBlockRegistry.TAINTED_ROCK_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_BRICKS_WALL                  = registerItem               ("tainted_rock_bricks_wall",                  new BlockItem(MalumBlockRegistry.TAINTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_BRICKS_WALL          = registerItem               ("cracked_tainted_rock_bricks_wall",          new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_TILES_WALL                   = registerItem               ("tainted_rock_tiles_wall",                   new BlockItem(MalumBlockRegistry.TAINTED_ROCK_TILES_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_TILES_WALL           = registerItem               ("cracked_tainted_rock_tiles_wall",           new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_TILES_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TAINTED_ROCK_BRICKS_WALL            = registerItem               ("small_tainted_rock_bricks_wall",            new BlockItem(MalumBlockRegistry.SMALL_TAINTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL    = registerItem               ("cracked_small_tainted_rock_bricks_wall",    new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_SLAB                         = registerItem               ("tainted_rock_slab",                         new BlockItem(MalumBlockRegistry.TAINTED_ROCK_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMOOTH_TAINTED_ROCK_SLAB                  = registerItem               ("smooth_tainted_rock_slab",                  new BlockItem(MalumBlockRegistry.SMOOTH_TAINTED_ROCK_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item POLISHED_TAINTED_ROCK_SLAB                = registerItem               ("polished_tainted_rock_slab",                new BlockItem(MalumBlockRegistry.POLISHED_TAINTED_ROCK_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_BRICKS_SLAB                  = registerItem               ("tainted_rock_bricks_slab",                  new BlockItem(MalumBlockRegistry.TAINTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_BRICKS_SLAB          = registerItem               ("cracked_tainted_rock_bricks_slab",          new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_TILES_SLAB                   = registerItem               ("tainted_rock_tiles_slab",                   new BlockItem(MalumBlockRegistry.TAINTED_ROCK_TILES_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_TILES_SLAB           = registerItem               ("cracked_tainted_rock_tiles_slab",           new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_TILES_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TAINTED_ROCK_BRICKS_SLAB            = registerItem               ("small_tainted_rock_bricks_slab",            new BlockItem(MalumBlockRegistry.SMALL_TAINTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB    = registerItem               ("cracked_small_tainted_rock_bricks_slab",    new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_STAIRS                       = registerItem               ("tainted_rock_stairs",                       new BlockItem(MalumBlockRegistry.TAINTED_ROCK_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMOOTH_TAINTED_ROCK_STAIRS                = registerItem               ("smooth_tainted_rock_stairs",                new BlockItem(MalumBlockRegistry.SMOOTH_TAINTED_ROCK_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item POLISHED_TAINTED_ROCK_STAIRS              = registerItem               ("polished_tainted_rock_stairs",              new BlockItem(MalumBlockRegistry.POLISHED_TAINTED_ROCK_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_BRICKS_STAIRS                = registerItem               ("tainted_rock_bricks_stairs",                new BlockItem(MalumBlockRegistry.TAINTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_BRICKS_STAIRS        = registerItem               ("cracked_tainted_rock_bricks_stairs",        new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_TILES_STAIRS                 = registerItem               ("tainted_rock_tiles_stairs",                 new BlockItem(MalumBlockRegistry.TAINTED_ROCK_TILES_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_TILES_STAIRS         = registerItem               ("cracked_tainted_rock_tiles_stairs",         new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_TILES_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TAINTED_ROCK_BRICKS_STAIRS          = registerItem               ("small_tainted_rock_bricks_stairs",          new BlockItem(MalumBlockRegistry.SMALL_TAINTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS  = registerItem               ("cracked_small_tainted_rock_bricks_stairs",  new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_ITEM_STAND                   = registerItem               ("tainted_rock_item_stand",                   new BlockItem(MalumBlockRegistry.TAINTED_ROCK_ITEM_STAND, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_ITEM_PEDESTAL                = registerItem               ("tainted_rock_item_pedestal",                new BlockItem(MalumBlockRegistry.TAINTED_ROCK_ITEM_PEDESTAL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK                              = registerItem               ("twisted_rock",                              new BlockItem(MalumBlockRegistry.TWISTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMOOTH_TWISTED_ROCK                       = registerItem               ("smooth_twisted_rock",                       new BlockItem(MalumBlockRegistry.SMOOTH_TWISTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item POLISHED_TWISTED_ROCK                     = registerItem               ("polished_twisted_rock",                     new BlockItem(MalumBlockRegistry.POLISHED_TWISTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_BRICKS                       = registerItem               ("twisted_rock_bricks",                       new BlockItem(MalumBlockRegistry.TWISTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_BRICKS               = registerItem               ("cracked_twisted_rock_bricks",               new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_TILES                        = registerItem               ("twisted_rock_tiles",                        new BlockItem(MalumBlockRegistry.TWISTED_ROCK_TILES, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_TILES                = registerItem               ("cracked_twisted_rock_tiles",                new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_TILES, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TWISTED_ROCK_BRICKS                 = registerItem               ("small_twisted_rock_bricks",                 new BlockItem(MalumBlockRegistry.SMALL_TWISTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TWISTED_ROCK_BRICKS         = registerItem               ("cracked_small_twisted_rock_bricks",         new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_PILLAR                       = registerItem               ("twisted_rock_pillar",                       new BlockItem(MalumBlockRegistry.TWISTED_ROCK_PILLAR, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_PILLAR_CAP                   = registerItem               ("twisted_rock_pillar_cap",                   new BlockItem(MalumBlockRegistry.TWISTED_ROCK_PILLAR_CAP, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_COLUMN                       = registerItem               ("twisted_rock_column",                       new BlockItem(MalumBlockRegistry.TWISTED_ROCK_COLUMN, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_COLUMN_CAP                   = registerItem               ("twisted_rock_column_cap",                   new BlockItem(MalumBlockRegistry.TWISTED_ROCK_COLUMN_CAP, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CUT_TWISTED_ROCK                          = registerItem               ("cut_twisted_rock",                          new BlockItem(MalumBlockRegistry.CUT_TWISTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CHISELED_TWISTED_ROCK                     = registerItem               ("chiseled_twisted_rock",                     new BlockItem(MalumBlockRegistry.CHISELED_TWISTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_PRESSURE_PLATE               = registerItem               ("twisted_rock_pressure_plate",               new BlockItem(MalumBlockRegistry.TWISTED_ROCK_PRESSURE_PLATE, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_WALL                         = registerItem               ("twisted_rock_wall",                         new BlockItem(MalumBlockRegistry.TWISTED_ROCK_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_BRICKS_WALL                  = registerItem               ("twisted_rock_bricks_wall",                  new BlockItem(MalumBlockRegistry.TWISTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_BRICKS_WALL          = registerItem               ("cracked_twisted_rock_bricks_wall",          new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_TILES_WALL                   = registerItem               ("twisted_rock_tiles_wall",                   new BlockItem(MalumBlockRegistry.TWISTED_ROCK_TILES_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_TILES_WALL           = registerItem               ("cracked_twisted_rock_tiles_wall",           new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_TILES_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TWISTED_ROCK_BRICKS_WALL            = registerItem               ("small_twisted_rock_bricks_wall",            new BlockItem(MalumBlockRegistry.SMALL_TWISTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL    = registerItem               ("cracked_small_twisted_rock_bricks_wall",    new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_SLAB                         = registerItem               ("twisted_rock_slab",                         new BlockItem(MalumBlockRegistry.TWISTED_ROCK_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMOOTH_TWISTED_ROCK_SLAB                  = registerItem               ("smooth_twisted_rock_slab",                  new BlockItem(MalumBlockRegistry.SMOOTH_TWISTED_ROCK_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item POLISHED_TWISTED_ROCK_SLAB                = registerItem               ("polished_twisted_rock_slab",                new BlockItem(MalumBlockRegistry.POLISHED_TWISTED_ROCK_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_BRICKS_SLAB                  = registerItem               ("twisted_rock_bricks_slab",                  new BlockItem(MalumBlockRegistry.TWISTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_BRICKS_SLAB          = registerItem               ("cracked_twisted_rock_bricks_slab",          new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_TILES_SLAB                   = registerItem               ("twisted_rock_tiles_slab",                   new BlockItem(MalumBlockRegistry.TWISTED_ROCK_TILES_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_TILES_SLAB           = registerItem               ("cracked_twisted_rock_tiles_slab",           new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_TILES_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TWISTED_ROCK_BRICKS_SLAB            = registerItem               ("small_twisted_rock_bricks_slab",            new BlockItem(MalumBlockRegistry.SMALL_TWISTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB    = registerItem               ("cracked_small_twisted_rock_bricks_slab",    new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_STAIRS                       = registerItem               ("twisted_rock_stairs",                       new BlockItem(MalumBlockRegistry.TWISTED_ROCK_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMOOTH_TWISTED_ROCK_STAIRS                = registerItem               ("smooth_twisted_rock_stairs",                new BlockItem(MalumBlockRegistry.SMOOTH_TWISTED_ROCK_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item POLISHED_TWISTED_ROCK_STAIRS              = registerItem               ("polished_twisted_rock_stairs",              new BlockItem(MalumBlockRegistry.POLISHED_TWISTED_ROCK_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_BRICKS_STAIRS                = registerItem               ("twisted_rock_bricks_stairs",                new BlockItem(MalumBlockRegistry.TWISTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_BRICKS_STAIRS        = registerItem               ("cracked_twisted_rock_bricks_stairs",        new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_TILES_STAIRS                 = registerItem               ("twisted_rock_tiles_stairs",                 new BlockItem(MalumBlockRegistry.TWISTED_ROCK_TILES_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_TILES_STAIRS         = registerItem               ("cracked_twisted_rock_tiles_stairs",         new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_TILES_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TWISTED_ROCK_BRICKS_STAIRS          = registerItem               ("small_twisted_rock_bricks_stairs",          new BlockItem(MalumBlockRegistry.SMALL_TWISTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS  = registerItem               ("cracked_small_twisted_rock_bricks_stairs",  new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_ITEM_STAND                   = registerItem               ("twisted_rock_item_stand",                   new BlockItem(MalumBlockRegistry.TWISTED_ROCK_ITEM_STAND, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_ITEM_PEDESTAL                = registerItem               ("twisted_rock_item_pedestal",                new BlockItem(MalumBlockRegistry.TWISTED_ROCK_ITEM_PEDESTAL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));

    // items & blocks, sorted [Main: Spirits]
    Item SACRED_SPIRIT                             = registerItem               ("sacred_spirit",                             new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.SACRED_SPIRIT));
    Item WICKED_SPIRIT                             = registerItem               ("wicked_spirit",                             new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.WICKED_SPIRIT));
    Item ARCANE_SPIRIT                             = registerItem               ("arcane_spirit",                             new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.ARCANE_SPIRIT));
    Item ELDRITCH_SPIRIT                           = registerItem               ("eldritch_spirit",                           new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.ELDRITCH_SPIRIT));
    Item EARTHEN_SPIRIT                            = registerItem               ("earthen_spirit",                            new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.EARTHEN_SPIRIT));
    Item INFERNAL_SPIRIT                           = registerItem               ("infernal_spirit",                           new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.INFERNAL_SPIRIT));
    Item AERIAL_SPIRIT                             = registerItem               ("aerial_spirit",                             new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.AERIAL_SPIRIT));
    Item AQUEOUS_SPIRIT                            = registerItem               ("aqueous_spirit",                            new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.AQUEOUS_SPIRIT));

    // items & blocks, sorted [Building Blocks]
    Item BLOCK_OF_ARCANE_CHARCOAL                  = registerItem               ("block_of_arcane_charcoal",                  new BlockItem(MalumBlockRegistry.BLOCK_OF_ARCANE_CHARCOAL, new Item.Settings().group(BUILDING_BLOCKS)));
    Item BLAZING_QUARTZ_ORE                        = registerItem               ("blazing_quartz_ore",                        new BlockItem(MalumBlockRegistry.BLAZING_QUARTZ_ORE, new Item.Settings().group(BUILDING_BLOCKS)));
    Item BLOCK_OF_BLAZING_QUARTZ                   = registerItem               ("block_of_blazing_quartz",                   new BlockItem(MalumBlockRegistry.BLOCK_OF_BLAZING_QUARTZ, new Item.Settings().group(BUILDING_BLOCKS)));
    Item BRILLIANT_STONE                           = registerItem               ("brilliant_stone",                           new BlockItem(MalumBlockRegistry.BRILLIANT_STONE, new Item.Settings().group(BUILDING_BLOCKS)));
    Item BRILLIANT_DEEPSLATE                       = registerItem               ("brilliant_deepslate",                       new BlockItem(MalumBlockRegistry.BRILLIANT_DEEPSLATE, new Item.Settings().group(BUILDING_BLOCKS)));
    Item BLOCK_OF_BRILLIANCE                       = registerItem               ("block_of_brilliance",                       new BlockItem(MalumBlockRegistry.BLOCK_OF_BRILLIANCE, new Item.Settings().group(BUILDING_BLOCKS)));

    // items & blocks, sorted [Miscellaneous]
    Item COPPER_NUGGET                             = registerItem               ("copper_nugget",                             new Item(new Item.Settings().group(MISC)));
    Item COAL_FRAGMENT                             = registerItem               ("coal_fragment",                             new Item(new Item.Settings().group(MISC)));
    Item CHARCOAL_FRAGMENT                         = registerItem               ("charcoal_fragment",                         new Item(new Item.Settings().group(MISC)));
    Item ARCANE_CHARCOAL                           = registerItem               ("arcane_charcoal",                           new Item(new Item.Settings().group(MISC)));
    Item ARCANE_CHARCOAL_FRAGMENT                  = registerItem               ("arcane_charcoal_fragment",                  new Item(new Item.Settings().group(MISC)));
    Item BLAZING_QUARTZ                            = registerItem               ("blazing_quartz",                            new Item(new Item.Settings().group(MISC)));
    Item BLAZING_QUARTZ_FRAGMENT                   = registerItem               ("blazing_quartz_fragment",                   new Item(new Item.Settings().group(MISC)));
    Item CLUSTER_OF_BRILLIANCE                     = registerItem               ("cluster_of_brilliance",                     new Item(new Item.Settings().group(MISC)));
    Item CHUNK_OF_BRILLIANCE                       = registerItem               ("chunk_of_brilliance",                       new Item(new Item.Settings().group(MISC)));

    // the device
    Item THE_DEVICE                                = registerItem               ("the_device",                                new BlockItem(MalumBlockRegistry.THE_DEVICE, new Item.Settings()));

    static <T extends Item> T registerItem(String id, T item) {
        ITEMS.put(new Identifier(MODID, id), item);
        return item;
    }

    static <T extends ScytheItem> T registerScytheItem(String id, T item) {
        SCYTHES.add(item);
        return registerItem(id, item);
    }

    static void init() {
        ITEMS.forEach((id, item) -> Registry.register(Registry.ITEM, id, item));
    }
}
