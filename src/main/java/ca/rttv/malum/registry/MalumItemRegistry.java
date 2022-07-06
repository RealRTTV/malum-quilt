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
import static ca.rttv.malum.registry.MalumBlockRegistry.IRIDESCENT_WALL_ETHER_TORCH;
import static ca.rttv.malum.registry.MalumBlockRegistry.WALL_ETHER_TORCH;
import static ca.rttv.malum.registry.MalumSignRegistry.RUNEWOOD_WALL_SIGN;
import static ca.rttv.malum.registry.MalumSignRegistry.SOULWOOD_WALL_SIGN;
import static net.minecraft.item.ItemGroup.BUILDING_BLOCKS;
import static net.minecraft.item.ItemGroup.MISC;

@SuppressWarnings("unused")
public interface MalumItemRegistry {
    Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    Set<ScytheItem> SCYTHES = new ReferenceOpenHashSet<>();

    // items & blocks, sorted [Main: Natural Wonders]
    Item HOLY_SAP                                   = register("holy_sap",                                  new Item(new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item HOLY_SAPBALL                               = register("holy_sapball",                              new Item(new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item HOLY_SYRUP                                 = register("holy_syrup",                                new HolySyrupItem(new Item.Settings().group(MALUM_NATURAL_WONDERS).food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0), 1.0f).alwaysEdible().hunger(8).saturationModifier(0.4f).build())));
    Item RUNEWOOD_LEAVES                            = register("runewood_leaves",                           new BlockItem(MalumBlockRegistry.RUNEWOOD_LEAVES, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_SAPLING                           = register("runewood_sapling",                          new BlockItem(MalumBlockRegistry.RUNEWOOD_SAPLING, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_LOG                               = register("runewood_log",                              new BlockItem(MalumBlockRegistry.RUNEWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item STRIPPED_RUNEWOOD_LOG                      = register("stripped_runewood_log",                     new BlockItem(MalumBlockRegistry.STRIPPED_RUNEWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD                                   = register("runewood",                                  new BlockItem(MalumBlockRegistry.RUNEWOOD, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item STRIPPED_RUNEWOOD                          = register("stripped_runewood",                         new BlockItem(MalumBlockRegistry.STRIPPED_RUNEWOOD, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item EXPOSED_RUNEWOOD_LOG                       = register("exposed_runewood_log",                      new BlockItem(MalumBlockRegistry.EXPOSED_RUNEWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item REVEALED_RUNEWOOD_LOG                      = register("revealed_runewood_log",                     new BlockItem(MalumBlockRegistry.REVEALED_RUNEWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PLANKS                            = register("runewood_planks",                           new BlockItem(MalumPlanksRegistry.RUNEWOOD_PLANKS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item VERTICAL_RUNEWOOD_PLANKS                   = register("vertical_runewood_planks",                  new BlockItem(MalumBlockRegistry.VERTICAL_RUNEWOOD_PLANKS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PANEL                             = register("runewood_panel",                            new BlockItem(MalumBlockRegistry.RUNEWOOD_PANEL, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_TILES                             = register("runewood_tiles",                            new BlockItem(MalumBlockRegistry.RUNEWOOD_TILES, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PLANKS_SLAB                       = register("runewood_planks_slab",                      new BlockItem(MalumBlockRegistry.RUNEWOOD_PLANKS_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item VERTICAL_RUNEWOOD_PLANKS_SLAB              = register("vertical_runewood_planks_slab",             new BlockItem(MalumBlockRegistry.VERTICAL_RUNEWOOD_PLANKS_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PANEL_SLAB                        = register("runewood_panel_slab",                       new BlockItem(MalumBlockRegistry.RUNEWOOD_PANEL_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_TILES_SLAB                        = register("runewood_tiles_slab",                       new BlockItem(MalumBlockRegistry.RUNEWOOD_TILES_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PLANKS_STAIRS                     = register("runewood_planks_stairs",                    new BlockItem(MalumBlockRegistry.RUNEWOOD_PLANKS_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item VERTICAL_RUNEWOOD_PLANKS_STAIRS            = register("vertical_runewood_planks_stairs",           new BlockItem(MalumBlockRegistry.VERTICAL_RUNEWOOD_PLANKS_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PANEL_STAIRS                      = register("runewood_panel_stairs",                     new BlockItem(MalumBlockRegistry.RUNEWOOD_PANEL_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_TILES_STAIRS                      = register("runewood_tiles_stairs",                     new BlockItem(MalumBlockRegistry.RUNEWOOD_TILES_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item CUT_RUNEWOOD_PLANKS                        = register("cut_runewood_planks",                       new BlockItem(MalumBlockRegistry.CUT_RUNEWOOD_PLANKS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_BEAM                              = register("runewood_beam",                             new BlockItem(MalumBlockRegistry.RUNEWOOD_BEAM, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_DOOR                              = register("runewood_door",                             new TallBlockItem(MalumBlockRegistry.RUNEWOOD_DOOR, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_TRAPDOOR                          = register("runewood_trapdoor",                         new BlockItem(MalumBlockRegistry.RUNEWOOD_TRAPDOOR, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOLID_RUNEWOOD_TRAPDOOR                    = register("solid_runewood_trapdoor",                   new BlockItem(MalumBlockRegistry.SOLID_RUNEWOOD_TRAPDOOR, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PLANKS_BUTTON                     = register("runewood_planks_button",                    new BlockItem(MalumBlockRegistry.RUNEWOOD_PLANKS_BUTTON, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PLANKS_PRESSURE_PLATE             = register("runewood_planks_pressure_plate",            new BlockItem(MalumBlockRegistry.RUNEWOOD_PLANKS_PRESSURE_PLATE, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PLANKS_FENCE                      = register("runewood_planks_fence",                     new BlockItem(MalumBlockRegistry.RUNEWOOD_PLANKS_FENCE, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_PLANKS_FENCE_GATE                 = register("runewood_planks_fence_gate",                new BlockItem(MalumBlockRegistry.RUNEWOOD_PLANKS_FENCE_GATE, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_ITEM_STAND                        = register("runewood_item_stand",                       new BlockItem(MalumBlockRegistry.RUNEWOOD_ITEM_STAND, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_ITEM_PEDESTAL                     = register("runewood_item_pedestal",                    new BlockItem(MalumBlockRegistry.RUNEWOOD_ITEM_PEDESTAL, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_SIGN                              = register("runewood_sign",                             new SignItem(new Item.Settings().maxCount(16).group(MALUM_NATURAL_WONDERS), MalumSignRegistry.RUNEWOOD_SIGN, RUNEWOOD_WALL_SIGN));
    Item RUNEWOOD_BOAT                              = register("runewood_boat",                             new BoatItem(false, MalumBoatTypes.RUNEWOOD, new Item.Settings().maxCount(1).group(MALUM_NATURAL_WONDERS)));
    Item RUNEWOOD_CHEST_BOAT                        = register("runewood_chest_boat",                       new BoatItem(true,  MalumBoatTypes.RUNEWOOD, new Item.Settings().maxCount(1).group(MALUM_NATURAL_WONDERS)));
    Item UNHOLY_SAP                                 = register("unholy_sap",                                new Item(new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item UNHOLY_SAPBALL                             = register("unholy_sapball",                            new Item(new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item UNHOLY_SYRUP                               = register("unholy_syrup",                              new HolySyrupItem(new Item.Settings().group(MALUM_NATURAL_WONDERS).food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 300, 0), 1.0f).alwaysEdible().hunger(8).saturationModifier(0.4f).build())));
    Item SOULWOOD_LEAVES                            = register("soulwood_leaves",                           new BlockItem(MalumBlockRegistry.SOULWOOD_LEAVES, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_SAPLING                           = register("soulwood_sapling",                          new BlockItem(MalumBlockRegistry.SOULWOOD_SAPLING, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_LOG                               = register("soulwood_log",                              new BlockItem(MalumBlockRegistry.SOULWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item STRIPPED_SOULWOOD_LOG                      = register("stripped_soulwood_log",                     new BlockItem(MalumBlockRegistry.STRIPPED_SOULWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD                                   = register("soulwood",                                  new BlockItem(MalumBlockRegistry.SOULWOOD, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item STRIPPED_SOULWOOD                          = register("stripped_soulwood",                         new BlockItem(MalumBlockRegistry.STRIPPED_SOULWOOD, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item EXPOSED_SOULWOOD_LOG                       = register("exposed_soulwood_log",                      new BlockItem(MalumBlockRegistry.EXPOSED_SOULWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item REVEALED_SOULWOOD_LOG                      = register("revealed_soulwood_log",                     new BlockItem(MalumBlockRegistry.REVEALED_SOULWOOD_LOG, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PLANKS                            = register("soulwood_planks",                           new BlockItem(MalumPlanksRegistry.SOULWOOD_PLANKS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item VERTICAL_SOULWOOD_PLANKS                   = register("vertical_soulwood_planks",                  new BlockItem(MalumBlockRegistry.VERTICAL_SOULWOOD_PLANKS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PANEL                             = register("soulwood_panel",                            new BlockItem(MalumBlockRegistry.SOULWOOD_PANEL, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_TILES                             = register("soulwood_tiles",                            new BlockItem(MalumBlockRegistry.SOULWOOD_TILES, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PLANKS_SLAB                       = register("soulwood_planks_slab",                      new BlockItem(MalumBlockRegistry.SOULWOOD_PLANKS_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item VERTICAL_SOULWOOD_PLANKS_SLAB              = register("vertical_soulwood_planks_slab",             new BlockItem(MalumBlockRegistry.VERTICAL_SOULWOOD_PLANKS_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PANEL_SLAB                        = register("soulwood_panel_slab",                       new BlockItem(MalumBlockRegistry.SOULWOOD_PANEL_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_TILES_SLAB                        = register("soulwood_tiles_slab",                       new BlockItem(MalumBlockRegistry.SOULWOOD_TILES_SLAB, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PLANKS_STAIRS                     = register("soulwood_planks_stairs",                    new BlockItem(MalumBlockRegistry.SOULWOOD_PLANKS_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item VERTICAL_SOULWOOD_PLANKS_STAIRS            = register("vertical_soulwood_planks_stairs",           new BlockItem(MalumBlockRegistry.VERTICAL_SOULWOOD_PLANKS_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PANEL_STAIRS                      = register("soulwood_panel_stairs",                     new BlockItem(MalumBlockRegistry.SOULWOOD_PANEL_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_TILES_STAIRS                      = register("soulwood_tiles_stairs",                     new BlockItem(MalumBlockRegistry.SOULWOOD_TILES_STAIRS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item CUT_SOULWOOD_PLANKS                        = register("cut_soulwood_planks",                       new BlockItem(MalumBlockRegistry.CUT_SOULWOOD_PLANKS, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_BEAM                              = register("soulwood_beam",                             new BlockItem(MalumBlockRegistry.SOULWOOD_BEAM, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_DOOR                              = register("soulwood_door",                             new TallBlockItem(MalumBlockRegistry.SOULWOOD_DOOR, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_TRAPDOOR                          = register("soulwood_trapdoor",                         new BlockItem(MalumBlockRegistry.SOULWOOD_TRAPDOOR, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOLID_SOULWOOD_TRAPDOOR                    = register("solid_soulwood_trapdoor",                   new BlockItem(MalumBlockRegistry.SOLID_SOULWOOD_TRAPDOOR, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PLANKS_BUTTON                     = register("soulwood_planks_button",                    new BlockItem(MalumBlockRegistry.SOULWOOD_PLANKS_BUTTON, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PLANKS_PRESSURE_PLATE             = register("soulwood_planks_pressure_plate",            new BlockItem(MalumBlockRegistry.SOULWOOD_PLANKS_PRESSURE_PLATE, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PLANKS_FENCE                      = register("soulwood_planks_fence",                     new BlockItem(MalumBlockRegistry.SOULWOOD_PLANKS_FENCE, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_PLANKS_FENCE_GATE                 = register("soulwood_planks_fence_gate",                new BlockItem(MalumBlockRegistry.SOULWOOD_PLANKS_FENCE_GATE, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_ITEM_STAND                        = register("soulwood_item_stand",                       new BlockItem(MalumBlockRegistry.SOULWOOD_ITEM_STAND, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_ITEM_PEDESTAL                     = register("soulwood_item_pedestal",                    new BlockItem(MalumBlockRegistry.SOULWOOD_ITEM_PEDESTAL, new Item.Settings().group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_SIGN                              = register("soulwood_sign",                             new SignItem(new Item.Settings().maxCount(16).group(MALUM_NATURAL_WONDERS), MalumSignRegistry.SOULWOOD_SIGN, SOULWOOD_WALL_SIGN));
    Item SOULWOOD_BOAT                              = register("soulwood_boat",                             new BoatItem(false, MalumBoatTypes.SOULWOOD, new Item.Settings().maxCount(1).group(MALUM_NATURAL_WONDERS)));
    Item SOULWOOD_CHEST_BOAT                        = register("soulwood_chest_boat",                       new BoatItem(true, MalumBoatTypes.SOULWOOD, new Item.Settings().maxCount(1).group(MALUM_NATURAL_WONDERS)));

// items & blocks, sorted [Main]
    Item ENCYCLOPEDIA_ARCANA                        = register("encyclopedia_arcana",                       new EncyclopediaArcanaItem(new Item.Settings().rarity(Rarity.UNCOMMON).group(MALUM).maxCount(1)));
    Item SOULSTONE_ORE                              = register("soulstone_ore",                             new BlockItem(MalumBlockRegistry.SOULSTONE_ORE, new Item.Settings().group(MALUM)));
    Item DEEPSLATE_SOULSTONE_ORE                    = register("deepslate_soulstone_ore",                   new BlockItem(MalumBlockRegistry.DEEPSLATE_SOULSTONE_ORE, new Item.Settings().group(MALUM)));
    Item PROCESSED_SOULSTONE                        = register("processed_soulstone",                       new Item(new Item.Settings().group(MALUM)));
    Item BLOCK_OF_SOULSTONE                         = register("block_of_soulstone",                        new BlockItem(MalumBlockRegistry.BLOCK_OF_SOULSTONE, new Item.Settings().group(MALUM)));
    Item SPIRIT_ALTAR                               = register("spirit_altar",                              new BlockItem(MalumBlockRegistry.SPIRIT_ALTAR, new Item.Settings().group(MALUM)));
    Item SPIRIT_JAR                                 = register("spirit_jar",                                new BlockItem(MalumBlockRegistry.SPIRIT_JAR, new Item.Settings().group(MALUM)));
    // soul vial
    Item RUNEWOOD_OBELISK                           = register("runewood_obelisk",                          new TallBlockItem(MalumBlockRegistry.RUNEWOOD_OBELISK, new Item.Settings().group(MALUM)));
    Item BRILLIANT_OBELISK                          = register("brilliant_obelisk",                         new TallBlockItem(MalumBlockRegistry.BRILLIANT_OBELISK, new Item.Settings().group(MALUM)));
    Item SPIRIT_CRUCIBLE                            = register("spirit_crucible",                           new TallBlockItem(MalumBlockRegistry.SPIRIT_CRUCIBLE, new Item.Settings().group(MALUM)));
    Item RUNEWOOD_TOTEM_BASE                        = register("runewood_totem_base",                       new BlockItem(MalumBlockRegistry.RUNEWOOD_TOTEM_BASE, new Item.Settings().group(MALUM)));
    Item SOULWOOD_TOTEM_BASE                        = register("soulwood_totem_base",                       new BlockItem(MalumBlockRegistry.SOULWOOD_TOTEM_BASE, new Item.Settings().group(MALUM)));
    // soulwood plinth
    // soulwood fusion plate
    Item HEX_ASH                                    = register("hex_ash",                                   new Item(new Item.Settings().group(MALUM)));
    Item SPIRIT_FABRIC                              = register("spirit_fabric",                             new Item(new Item.Settings().group(MALUM)));
    Item SPECTRAL_LENS                              = register("spectral_lens",                             new Item(new Item.Settings().group(MALUM)));
    Item POPPET                                     = register("poppet",                                    new Item(new Item.Settings().group(MALUM)));
    Item HALLOWED_GOLD_INGOT                        = register("hallowed_gold_ingot",                       new Item(new Item.Settings().group(MALUM)));
    Item HALLOWED_GOLD_NUGGET                       = register("hallowed_gold_nugget",                      new Item(new Item.Settings().group(MALUM)));
    Item BLOCK_OF_HALLOWED_GOLD                     = register("block_of_hallowed_gold",                    new BlockItem(MalumBlockRegistry.BLOCK_OF_HALLOWED_GOLD, new Item.Settings().group(MALUM)));
    Item HALLOWED_SPIRIT_RESONATOR                  = register("hallowed_spirit_resonator",                 new Item(new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_INGOT                   = register("soul_stained_steel_ingot",                  new Item(new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_NUGGET                  = register("soul_stained_steel_nugget",                 new Item(new Item.Settings().group(MALUM)));
    Item BLOCK_OF_SOUL_STAINED_STEEL                = register("block_of_soul_stained_steel",               new BlockItem(MalumBlockRegistry.BLOCK_OF_SOUL_STAINED_STEEL, new Item.Settings().group(MALUM)));
    Item STAINED_SPIRIT_RESONATOR                   = register("stained_spirit_resonator",                  new Item(new Item.Settings().group(MALUM)));
    Item ETHER                                      = register("ether",                                     new EtherBlockItem(MalumBlockRegistry.ETHER, new Item.Settings().group(MALUM)));
    Item ETHER_TORCH                                = register("ether_torch",                               new EtherWallStandingBlockItem(MalumBlockRegistry.ETHER_TORCH, WALL_ETHER_TORCH, new Item.Settings().group(MALUM)));
    Item TAINTED_ETHER_BRAZIER                      = register("tainted_ether_brazier",                     new EtherBlockItem(MalumBlockRegistry.TAINTED_ETHER_BRAZIER, new Item.Settings().group(MALUM)));
    Item TWISTED_ETHER_BRAZIER                      = register("twisted_ether_brazier",                     new EtherBlockItem(MalumBlockRegistry.TWISTED_ETHER_BRAZIER, new Item.Settings().group(MALUM)));
    Item IRIDESCENT_ETHER                           = register("iridescent_ether",                          new IridescentEtherBlockItem(MalumBlockRegistry.IRIDESCENT_ETHER, new Item.Settings().group(MALUM)));
    Item IRIDESCENT_ETHER_TORCH                     = register("iridescent_ether_torch",                    new IridescentEtherWallStandingBlockItem(MalumBlockRegistry.IRIDESCENT_ETHER_TORCH, IRIDESCENT_WALL_ETHER_TORCH, new Item.Settings().group(MALUM)));
    Item TAINTED_IRIDESCENT_ETHER_BRAZIER           = register("tainted_iridescent_ether_brazier",          new IridescentEtherBlockItem(MalumBlockRegistry.TAINTED_IRIDESCENT_ETHER_BRAZIER, new Item.Settings().group(MALUM)));
    Item TWISTED_IRIDESCENT_ETHER_BRAZIER           = register("twisted_iridescent_ether_brazier",          new IridescentEtherBlockItem(MalumBlockRegistry.TWISTED_IRIDESCENT_ETHER_BRAZIER, new Item.Settings().group(MALUM)));
    Item SPIRIT_POUCH                               = register("spirit_pouch",                              new SpiritPouchItem(new Item.Settings().group(MALUM).maxCount(1)));
    Item CRUDE_SCYTHE                               = registerScytheItem("crude_scythe",                    new ScytheItem(ToolMaterials.IRON, 3, -3.1f, 0.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_SCYTHE                  = registerScytheItem("soul_stained_steel_scythe",       new ScytheItem(MalumToolMaterials.SOUL_STAINED_STEEL, 0.5f, -3.1f, 4.0f, new Item.Settings().group(MALUM)));
    Item CREATIVE_SCYTHE                            = registerScytheItem("creative_scythe",                 new ScytheItem(ToolMaterials.IRON, 9993, 9.1f, 999f, new Item.Settings()));

    // soulwood stave
    Item SOUL_STAINED_STEEL_SWORD                   = register("soul_stained_steel_sword",                  new MagicSwordItem(MalumToolMaterials.SOUL_STAINED_STEEL, 0.0f, -2.4f, 3.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_PICKAXE                 = register("soul_stained_steel_pickaxe",                new MagicPickaxeItem(MalumToolMaterials.SOUL_STAINED_STEEL, -1.0f, -2.8f, 2.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_AXE                     = register("soul_stained_steel_axe",                    new MagicAxeItem(MalumToolMaterials.SOUL_STAINED_STEEL, 3.0f, -3.2f, 4.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_SHOVEL                  = register("soul_stained_steel_shovel",                 new MagicShovelItem(MalumToolMaterials.SOUL_STAINED_STEEL, -0.5f, -3.0f, 2.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_HOE                     = register("soul_stained_steel_hoe",                    new MagicHoeItem(MalumToolMaterials.SOUL_STAINED_STEEL, -2.0f, 0.0f, 2.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_HELMET                  = register("soul_stained_steel_helmet",                 new MagicArmorItem(MalumArmorMaterials.SOUL_STAINED_STEEL, EquipmentSlot.HEAD, 1.0f, 3.0f, 0.0f, 0.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_CHESTPLATE              = register("soul_stained_steel_chestplate",             new MagicArmorItem(MalumArmorMaterials.SOUL_STAINED_STEEL, EquipmentSlot.CHEST, 1.0f, 3.0f, 0.0f, 0.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_LEGGINGS                = register("soul_stained_steel_leggings",               new MagicArmorItem(MalumArmorMaterials.SOUL_STAINED_STEEL, EquipmentSlot.LEGS, 1.0f, 3.0f, 0.0f, 0.0f, new Item.Settings().group(MALUM)));
    Item SOUL_STAINED_STEEL_BOOTS                   = register("soul_stained_steel_boots",                  new MagicArmorItem(MalumArmorMaterials.SOUL_STAINED_STEEL, EquipmentSlot.FEET, 1.0f, 3.0f, 0.0f, 0.0f, new Item.Settings().group(MALUM)));
    Item SOUL_HUNTER_CLOAK                          = register("soul_hunter_cloak",                         new MagicArmorItem(SOUL_CLOAK, EquipmentSlot.HEAD, 0.0f, 0.0f, 1.0f, 1.0f, new Item.Settings().group(MALUM)));
    Item SOUL_HUNTER_ROBE                           = register("soul_hunter_robe",                          new MagicArmorItem(SOUL_CLOAK, EquipmentSlot.CHEST,0.0f, 0.0f, 1.0f, 1.0f,  new Item.Settings().group(MALUM)));
    Item SOUL_HUNTER_LEGGINGS                       = register("soul_hunter_leggings",                      new MagicArmorItem(SOUL_CLOAK, EquipmentSlot.LEGS, 0.0f, 0.0f, 1.0f, 1.0f, new Item.Settings().group(MALUM)));
    Item SOUL_HUNTER_BOOTS                          = register("soul_hunter_boots",                         new MagicArmorItem(SOUL_CLOAK, EquipmentSlot.FEET, 0.0f, 0.0f, 1.0f, 1.0f, new Item.Settings().group(MALUM)));
    Item TYRVING                                    = register("tyrving",                                   new TyrvingItem(MalumToolMaterials.TWISTED_ROCK, 3.0f, -2.7f, new Item.Settings().group(MALUM)));
    Item GILDED_RING                                = register("gilded_ring",                               new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> modifiers.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uuid, "Trinket modifier", 2.0f, EntityAttributeModifier.Operation.ADDITION))));
    Item ORNATE_RING                                = register("ornate_ring",                               new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> modifiers.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(uuid, "Trinket modifier", 2.0f, EntityAttributeModifier.Operation.ADDITION))));
    Item ORNATE_NECKLACE                            = register("ornate_necklace",                           new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> modifiers.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(uuid, "Trinket modifier", 4.0f, EntityAttributeModifier.Operation.ADDITION))));
    Item GILDED_BELT                                = register("gilded_belt",                               new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> modifiers.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uuid, "Trinket modifier", 4.0f, EntityAttributeModifier.Operation.ADDITION))));
    Item RING_OF_ARCANE_REACH                       = register("ring_of_arcane_reach",                      new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> modifiers.put(SPIRIT_REACH, new EntityAttributeModifier(uuid, "Trinket modifier", 8.0f, EntityAttributeModifier.Operation.ADDITION))));
    Item RING_OF_ARCANE_SPOIL                       = register("ring_of_arcane_spoil",                      new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> modifiers.put(SPIRIT_SPOILS, new EntityAttributeModifier(uuid, "Trinket modifier", 1.0f, EntityAttributeModifier.Operation.ADDITION))));
    Item RING_OF_PROWESS                            = register("ring_of_prowess",                           new RingOfProwessItem(new Item.Settings().group(MALUM).maxCount(1)));
    Item RING_OF_CURATIVE_TALENT                    = register("ring_of_curative_talent",                   new RingOfCurativeTalentItem(new Item.Settings().group(MALUM).maxCount(1)));
    Item RING_OF_WICKED_INTENT                      = register("ring_of_wicked_intent",                     new RingOfWickedIntentItem(new Item.Settings().group(MALUM).maxCount(1)));
    Item NECKLACE_OF_THE_MYSTIC_MIRROR              = register("necklace_of_the_mystic_mirror",             new TrinketItem(new Item.Settings().group(MALUM).maxCount(1)));
    Item NECKLACE_OF_THE_NARROW_EDGE                = register("necklace_of_the_narrow_edge",               new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> modifiers.put(SCYTHE_PROFICIENCY, new EntityAttributeModifier(uuid, "Trinket modifier", 4.0f, EntityAttributeModifier.Operation.ADDITION))));
    Item WARDED_BELT                                = register("warded_belt",                               new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> { modifiers.put(MAGIC_RESISTANCE, new EntityAttributeModifier(uuid, "Trinket modifier", 1.0f, EntityAttributeModifier.Operation.ADDITION)); modifiers.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uuid, "Trinket modifier", 2.0f, EntityAttributeModifier.Operation.ADDITION)); modifiers.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(uuid, "Trinket modifier", 0.2f, EntityAttributeModifier.Operation.ADDITION)); modifiers.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(uuid, "Trinket modifier", 1.0f, EntityAttributeModifier.Operation.ADDITION)); }));
    Item MAGEBANE_BELT                              = register("magebane_belt",                             new AttributeTrinketItem(new Item.Settings().group(MALUM).maxCount(1), (modifiers, uuid) -> { modifiers.put(MAGIC_RESISTANCE, new EntityAttributeModifier(uuid, "Trinket modifier", 2.0f, EntityAttributeModifier.Operation.ADDITION)); modifiers.put(SOUL_WARD_CAP, new EntityAttributeModifier(uuid, "Trinket modifier", 3.0f, EntityAttributeModifier.Operation.ADDITION)); }));
    Item CRACKED_CEASELESS_IMPETUS                  = register("cracked_ceaseless_impetus",                 new Item(new Item.Settings().maxCount(1).group(MALUM).rarity(Rarity.UNCOMMON)));
    Item CEASELESS_IMPETUS                          = register("ceaseless_impetus",                         new Item(new Item.Settings().maxCount(1).maxDamage(2).group(MALUM).rarity(Rarity.UNCOMMON)));
    Item RAW_SOULSTONE                              = register("raw_soulstone",                             new Item(new Item.Settings().group(MALUM)));
    Item CRUSHED_SOULSTONE                          = register("crushed_soulstone",                         new Item(new Item.Settings().group(MALUM)));
    Item BLOCK_OF_RAW_SOULSTONE                     = register("block_of_raw_soulstone",                    new BlockItem(MalumBlockRegistry.BLOCK_OF_RAW_SOULSTONE, new Item.Settings().group(MALUM)));
    Item TWISTED_TABLET                             = register("twisted_tablet",                            new BlockItem(MalumBlockRegistry.TWISTED_TABLET, new Item.Settings().group(MALUM)));
    Item SPIRIT_CATALYZER                           = register("spirit_catalyzer",                          new TallBlockItem(MalumBlockRegistry.SPIRIT_CATALYZER, new Item.Settings().group(MALUM)));

    // items & blocks, sorted [Main: Arcane Rocks]
    Item TAINTED_ROCK                               = register("tainted_rock",                              new BlockItem(MalumBlockRegistry.TAINTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMOOTH_TAINTED_ROCK                        = register("smooth_tainted_rock",                       new BlockItem(MalumBlockRegistry.SMOOTH_TAINTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item POLISHED_TAINTED_ROCK                      = register("polished_tainted_rock",                     new BlockItem(MalumBlockRegistry.POLISHED_TAINTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_BRICKS                        = register("tainted_rock_bricks",                       new BlockItem(MalumBlockRegistry.TAINTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_BRICKS                = register("cracked_tainted_rock_bricks",               new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_TILES                         = register("tainted_rock_tiles",                        new BlockItem(MalumBlockRegistry.TAINTED_ROCK_TILES, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_TILES                 = register("cracked_tainted_rock_tiles",                new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_TILES, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TAINTED_ROCK_BRICKS                  = register("small_tainted_rock_bricks",                 new BlockItem(MalumBlockRegistry.SMALL_TAINTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TAINTED_ROCK_BRICKS          = register("cracked_small_tainted_rock_bricks",         new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_COLUMN                        = register("tainted_rock_column",                       new BlockItem(MalumBlockRegistry.TAINTED_ROCK_COLUMN, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_COLUMN_CAP                    = register("tainted_rock_column_cap",                   new BlockItem(MalumBlockRegistry.TAINTED_ROCK_COLUMN_CAP, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CUT_TAINTED_ROCK                           = register("cut_tainted_rock",                          new BlockItem(MalumBlockRegistry.CUT_TAINTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CHISELED_TAINTED_ROCK                      = register("chiseled_tainted_rock",                     new BlockItem(MalumBlockRegistry.CHISELED_TAINTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_PRESSURE_PLATE                = register("tainted_rock_pressure_plate",               new BlockItem(MalumBlockRegistry.TAINTED_ROCK_PRESSURE_PLATE, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_WALL                          = register("tainted_rock_wall",                         new BlockItem(MalumBlockRegistry.TAINTED_ROCK_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_BRICKS_WALL                   = register("tainted_rock_bricks_wall",                  new BlockItem(MalumBlockRegistry.TAINTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_BRICKS_WALL           = register("cracked_tainted_rock_bricks_wall",          new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_TILES_WALL                    = register("tainted_rock_tiles_wall",                   new BlockItem(MalumBlockRegistry.TAINTED_ROCK_TILES_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_TILES_WALL            = register("cracked_tainted_rock_tiles_wall",           new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_TILES_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TAINTED_ROCK_BRICKS_WALL             = register("small_tainted_rock_bricks_wall",            new BlockItem(MalumBlockRegistry.SMALL_TAINTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL     = register("cracked_small_tainted_rock_bricks_wall",    new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_SLAB                          = register("tainted_rock_slab",                         new BlockItem(MalumBlockRegistry.TAINTED_ROCK_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMOOTH_TAINTED_ROCK_SLAB                   = register("smooth_tainted_rock_slab",                  new BlockItem(MalumBlockRegistry.SMOOTH_TAINTED_ROCK_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item POLISHED_TAINTED_ROCK_SLAB                 = register("polished_tainted_rock_slab",                new BlockItem(MalumBlockRegistry.POLISHED_TAINTED_ROCK_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_BRICKS_SLAB                   = register("tainted_rock_bricks_slab",                  new BlockItem(MalumBlockRegistry.TAINTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_BRICKS_SLAB           = register("cracked_tainted_rock_bricks_slab",          new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_TILES_SLAB                    = register("tainted_rock_tiles_slab",                   new BlockItem(MalumBlockRegistry.TAINTED_ROCK_TILES_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_TILES_SLAB            = register("cracked_tainted_rock_tiles_slab",           new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_TILES_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TAINTED_ROCK_BRICKS_SLAB             = register("small_tainted_rock_bricks_slab",            new BlockItem(MalumBlockRegistry.SMALL_TAINTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB     = register("cracked_small_tainted_rock_bricks_slab",    new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_STAIRS                        = register("tainted_rock_stairs",                       new BlockItem(MalumBlockRegistry.TAINTED_ROCK_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMOOTH_TAINTED_ROCK_STAIRS                 = register("smooth_tainted_rock_stairs",                new BlockItem(MalumBlockRegistry.SMOOTH_TAINTED_ROCK_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item POLISHED_TAINTED_ROCK_STAIRS               = register("polished_tainted_rock_stairs",              new BlockItem(MalumBlockRegistry.POLISHED_TAINTED_ROCK_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_BRICKS_STAIRS                 = register("tainted_rock_bricks_stairs",                new BlockItem(MalumBlockRegistry.TAINTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_BRICKS_STAIRS         = register("cracked_tainted_rock_bricks_stairs",        new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_TILES_STAIRS                  = register("tainted_rock_tiles_stairs",                 new BlockItem(MalumBlockRegistry.TAINTED_ROCK_TILES_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TAINTED_ROCK_TILES_STAIRS          = register("cracked_tainted_rock_tiles_stairs",         new BlockItem(MalumBlockRegistry.CRACKED_TAINTED_ROCK_TILES_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TAINTED_ROCK_BRICKS_STAIRS           = register("small_tainted_rock_bricks_stairs",          new BlockItem(MalumBlockRegistry.SMALL_TAINTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS   = register("cracked_small_tainted_rock_bricks_stairs",  new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_ITEM_STAND                    = register("tainted_rock_item_stand",                   new BlockItem(MalumBlockRegistry.TAINTED_ROCK_ITEM_STAND, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_ITEM_PEDESTAL                 = register("tainted_rock_item_pedestal",                new BlockItem(MalumBlockRegistry.TAINTED_ROCK_ITEM_PEDESTAL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK                               = register("twisted_rock",                              new BlockItem(MalumBlockRegistry.TWISTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMOOTH_TWISTED_ROCK                        = register("smooth_twisted_rock",                       new BlockItem(MalumBlockRegistry.SMOOTH_TWISTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item POLISHED_TWISTED_ROCK                      = register("polished_twisted_rock",                     new BlockItem(MalumBlockRegistry.POLISHED_TWISTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_BRICKS                        = register("twisted_rock_bricks",                       new BlockItem(MalumBlockRegistry.TWISTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_BRICKS                = register("cracked_twisted_rock_bricks",               new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_TILES                         = register("twisted_rock_tiles",                        new BlockItem(MalumBlockRegistry.TWISTED_ROCK_TILES, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_TILES                 = register("cracked_twisted_rock_tiles",                new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_TILES, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TWISTED_ROCK_BRICKS                  = register("small_twisted_rock_bricks",                 new BlockItem(MalumBlockRegistry.SMALL_TWISTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TWISTED_ROCK_BRICKS          = register("cracked_small_twisted_rock_bricks",         new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_COLUMN                        = register("twisted_rock_column",                       new BlockItem(MalumBlockRegistry.TWISTED_ROCK_COLUMN, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_COLUMN_CAP                    = register("twisted_rock_column_cap",                   new BlockItem(MalumBlockRegistry.TWISTED_ROCK_COLUMN_CAP, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CUT_TWISTED_ROCK                           = register("cut_twisted_rock",                          new BlockItem(MalumBlockRegistry.CUT_TWISTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CHISELED_TWISTED_ROCK                      = register("chiseled_twisted_rock",                     new BlockItem(MalumBlockRegistry.CHISELED_TWISTED_ROCK, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_PRESSURE_PLATE                = register("twisted_rock_pressure_plate",               new BlockItem(MalumBlockRegistry.TWISTED_ROCK_PRESSURE_PLATE, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_WALL                          = register("twisted_rock_wall",                         new BlockItem(MalumBlockRegistry.TWISTED_ROCK_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_BRICKS_WALL                   = register("twisted_rock_bricks_wall",                  new BlockItem(MalumBlockRegistry.TWISTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_BRICKS_WALL           = register("cracked_twisted_rock_bricks_wall",          new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_TILES_WALL                    = register("twisted_rock_tiles_wall",                   new BlockItem(MalumBlockRegistry.TWISTED_ROCK_TILES_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_TILES_WALL            = register("cracked_twisted_rock_tiles_wall",           new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_TILES_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TWISTED_ROCK_BRICKS_WALL             = register("small_twisted_rock_bricks_wall",            new BlockItem(MalumBlockRegistry.SMALL_TWISTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL     = register("cracked_small_twisted_rock_bricks_wall",    new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_SLAB                          = register("twisted_rock_slab",                         new BlockItem(MalumBlockRegistry.TWISTED_ROCK_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMOOTH_TWISTED_ROCK_SLAB                   = register("smooth_twisted_rock_slab",                  new BlockItem(MalumBlockRegistry.SMOOTH_TWISTED_ROCK_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item POLISHED_TWISTED_ROCK_SLAB                 = register("polished_twisted_rock_slab",                new BlockItem(MalumBlockRegistry.POLISHED_TWISTED_ROCK_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_BRICKS_SLAB                   = register("twisted_rock_bricks_slab",                  new BlockItem(MalumBlockRegistry.TWISTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_BRICKS_SLAB           = register("cracked_twisted_rock_bricks_slab",          new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_TILES_SLAB                    = register("twisted_rock_tiles_slab",                   new BlockItem(MalumBlockRegistry.TWISTED_ROCK_TILES_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_TILES_SLAB            = register("cracked_twisted_rock_tiles_slab",           new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_TILES_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TWISTED_ROCK_BRICKS_SLAB             = register("small_twisted_rock_bricks_slab",            new BlockItem(MalumBlockRegistry.SMALL_TWISTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB     = register("cracked_small_twisted_rock_bricks_slab",    new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_STAIRS                        = register("twisted_rock_stairs",                       new BlockItem(MalumBlockRegistry.TWISTED_ROCK_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMOOTH_TWISTED_ROCK_STAIRS                 = register("smooth_twisted_rock_stairs",                new BlockItem(MalumBlockRegistry.SMOOTH_TWISTED_ROCK_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item POLISHED_TWISTED_ROCK_STAIRS               = register("polished_twisted_rock_stairs",              new BlockItem(MalumBlockRegistry.POLISHED_TWISTED_ROCK_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_BRICKS_STAIRS                 = register("twisted_rock_bricks_stairs",                new BlockItem(MalumBlockRegistry.TWISTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_BRICKS_STAIRS         = register("cracked_twisted_rock_bricks_stairs",        new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_TILES_STAIRS                  = register("twisted_rock_tiles_stairs",                 new BlockItem(MalumBlockRegistry.TWISTED_ROCK_TILES_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_TWISTED_ROCK_TILES_STAIRS          = register("cracked_twisted_rock_tiles_stairs",         new BlockItem(MalumBlockRegistry.CRACKED_TWISTED_ROCK_TILES_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item SMALL_TWISTED_ROCK_BRICKS_STAIRS           = register("small_twisted_rock_bricks_stairs",          new BlockItem(MalumBlockRegistry.SMALL_TWISTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS   = register("cracked_small_twisted_rock_bricks_stairs",  new BlockItem(MalumBlockRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_ITEM_STAND                    = register("twisted_rock_item_stand",                   new BlockItem(MalumBlockRegistry.TWISTED_ROCK_ITEM_STAND, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_ITEM_PEDESTAL                 = register("twisted_rock_item_pedestal",                new BlockItem(MalumBlockRegistry.TWISTED_ROCK_ITEM_PEDESTAL, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TAINTED_ROCK_BUTTON                        = register("tainted_rock_button",                       new BlockItem(MalumBlockRegistry.TAINTED_ROCK_BUTTON, new Item.Settings().group(MALUM_ARCANE_ROCKS)));
    Item TWISTED_ROCK_BUTTON                        = register("twisted_rock_button",                       new BlockItem(MalumBlockRegistry.TWISTED_ROCK_BUTTON, new Item.Settings().group(MALUM_ARCANE_ROCKS)));

    // items & blocks, sorted [Main: Spirits]
    Item SACRED_SPIRIT                              = register("sacred_spirit",                             new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.SACRED_SPIRIT));
    Item WICKED_SPIRIT                              = register("wicked_spirit",                             new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.WICKED_SPIRIT));
    Item ARCANE_SPIRIT                              = register("arcane_spirit",                             new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.ARCANE_SPIRIT));
    Item ELDRITCH_SPIRIT                            = register("eldritch_spirit",                           new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.ELDRITCH_SPIRIT));
    Item EARTHEN_SPIRIT                             = register("earthen_spirit",                            new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.EARTHEN_SPIRIT));
    Item INFERNAL_SPIRIT                            = register("infernal_spirit",                           new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.INFERNAL_SPIRIT));
    Item AERIAL_SPIRIT                              = register("aerial_spirit",                             new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.AERIAL_SPIRIT));
    Item AQUEOUS_SPIRIT                             = register("aqueous_spirit",                            new SpiritItem(new Item.Settings().group(MALUM_SPIRITS), SpiritType.AQUEOUS_SPIRIT));

    // items & blocks, sorted [Main: Metallurgic Magic]
    Item CRACKED_ALCHEMICAL_IMPETUS                 = register("cracked_alchemical_impetus",                new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC)));
    Item ALCHEMICAL_IMPETUS                         = register("alchemical_impetus",                        new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item CRACKED_IRON_IMPETUS                       = register("cracked_iron_impetus",                      new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC)));
    Item IRON_IMPETUS                               = register("iron_impetus",                              new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item CRACKED_COPPER_IMPETUS                     = register("cracked_copper_impetus",                    new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC)));
    Item COPPER_IMPETUS                             = register("copper_impetus",                            new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item CRACKED_GOLD_IMPETUS                       = register("cracked_gold_impetus",                      new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC)));
    Item GOLD_IMPETUS                               = register("gold_impetus",                              new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item CRACKED_LEAD_IMPETUS                       = register("cracked_lead_impetus",                      new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC)));
    Item LEAD_IMPETUS                               = register("lead_impetus",                              new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item CRACKED_SILVER_IMPETUS                     = register("cracked_silver_impetus",                    new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC)));
    Item SILVER_IMPETUS                             = register("silver_impetus",                            new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item IRON_NODE                                  = register("iron_node",                                 new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item COPPER_NODE                                = register("copper_node",                               new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item GOLD_NODE                                  = register("gold_node",                                 new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item LEAD_NODE                                  = register("lead_node",                                 new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item SILVER_NODE                                = register("silver_node",                               new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item CRACKED_ALUMINUM_IMPETUS                   = register("cracked_aluminum_impetus",                  new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC)));
    Item ALUMINUM_IMPETUS                           = register("aluminum_impetus",                          new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item ALUMINUM_NODE                              = register("aluminum_node",                             new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item CRACKED_NICKEL_IMPETUS                     = register("cracked_nickel_impetus",                    new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC)));
    Item NICKEL_IMPETUS                             = register("nickel_impetus",                            new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item NICKEL_NODE                                = register("nickel_node",                               new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item CRACKED_URANIUM_IMPETUS                    = register("cracked_uranium_impetus",                   new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC)));
    Item URANIUM_IMPETUS                            = register("uranium_impetus",                           new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item URANIUM_NODE                               = register("uranium_node",                              new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item CRACKED_OSMIUM_IMPETUS                     = register("cracked_osmium_impetus",                    new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC)));
    Item OSMIUM_IMPETUS                             = register("osmium_impetus",                            new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item OSMIUM_NODE                                = register("osmium_node",                               new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item CRACKED_ZINC_IMPETUS                       = register("cracked_zinc_impetus",                      new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC)));
    Item ZINC_IMPETUS                               = register("zinc_impetus",                              new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item ZINC_NODE                                  = register("zinc_node",                                 new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item CRACKED_TIN_IMPETUS                        = register("cracked_tin_impetus",                       new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC)));
    Item TIN_IMPETUS                                = register("tin_impetus",                               new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));
    Item TIN_NODE                                   = register("tin_node",                                  new Item(new Item.Settings().maxCount(1).group(MALUM_METALLURGIC_MAGIC).maxDamage(100)));

    // items & blocks, sorted [Building Blocks]
    Item BLOCK_OF_ARCANE_CHARCOAL                   = register("block_of_arcane_charcoal",                  new BlockItem(MalumBlockRegistry.BLOCK_OF_ARCANE_CHARCOAL, new Item.Settings().group(BUILDING_BLOCKS)));
    Item BLAZING_QUARTZ_ORE                         = register("blazing_quartz_ore",                        new BlockItem(MalumBlockRegistry.BLAZING_QUARTZ_ORE, new Item.Settings().group(BUILDING_BLOCKS)));
    Item BLOCK_OF_BLAZING_QUARTZ                    = register("block_of_blazing_quartz",                   new BlockItem(MalumBlockRegistry.BLOCK_OF_BLAZING_QUARTZ, new Item.Settings().group(BUILDING_BLOCKS)));
    Item BRILLIANT_STONE                            = register("brilliant_stone",                           new BlockItem(MalumBlockRegistry.BRILLIANT_STONE, new Item.Settings().group(BUILDING_BLOCKS)));
    Item BRILLIANT_DEEPSLATE                        = register("brilliant_deepslate",                       new BlockItem(MalumBlockRegistry.BRILLIANT_DEEPSLATE, new Item.Settings().group(BUILDING_BLOCKS)));
    Item BLOCK_OF_BRILLIANCE                        = register("block_of_brilliance",                       new BlockItem(MalumBlockRegistry.BLOCK_OF_BRILLIANCE, new Item.Settings().group(BUILDING_BLOCKS)));

    // items & blocks, sorted [Miscellaneous]
    Item COPPER_NUGGET                              = register("copper_nugget",                             new Item(new Item.Settings().group(MISC)));
    Item COAL_FRAGMENT                              = register("coal_fragment",                             new Item(new Item.Settings().group(MISC)));
    Item CHARCOAL_FRAGMENT                          = register("charcoal_fragment",                         new Item(new Item.Settings().group(MISC)));
    Item ARCANE_CHARCOAL                            = register("arcane_charcoal",                           new Item(new Item.Settings().group(MISC)));
    Item ARCANE_CHARCOAL_FRAGMENT                   = register("arcane_charcoal_fragment",                  new Item(new Item.Settings().group(MISC)));
    Item BLAZING_QUARTZ                             = register("blazing_quartz",                            new Item(new Item.Settings().group(MISC)));
    Item BLAZING_QUARTZ_FRAGMENT                    = register("blazing_quartz_fragment",                   new Item(new Item.Settings().group(MISC)));
    Item CLUSTER_OF_BRILLIANCE                      = register("cluster_of_brilliance",                     new Item(new Item.Settings().group(MISC)));
    Item CHUNK_OF_BRILLIANCE                        = register("chunk_of_brilliance",                       new Item(new Item.Settings().group(MISC)));

    // the device
    Item THE_DEVICE                                 = register("the_device",                                new BlockItem(MalumBlockRegistry.THE_DEVICE, new Item.Settings()));

    static <T extends Item> T register(String id, T item) {
        ITEMS.put(new Identifier(MODID, id), item);
        return item;
    }

    static <T extends ScytheItem> T registerScytheItem(String id, T item) {
        SCYTHES.add(item);
        return register(id, item);
    }

    static void init() {
        ITEMS.forEach((id, item) -> Registry.register(Registry.ITEM, id, item));
    }
}
