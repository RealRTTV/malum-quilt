package ca.rttv.malum.client.screen;

import ca.rttv.malum.client.screen.page.*;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.BackgroundHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumRegistry.*;
import static net.minecraft.item.Items.*;
import static org.lwjgl.opengl.GL11C.GL_SCISSOR_TEST;

public class ProgressionBookScreen extends Screen {
    public static final Identifier FRAME_TEXTURE = new Identifier(MODID, "textures/gui/book/frame.png");
    public static final Identifier FADE_TEXTURE = new Identifier(MODID, "textures/gui/book/fade.png");
    public static final Identifier BACKGROUND_TEXTURE = new Identifier(MODID, "textures/gui/book/background.png");
    public static ProgressionBookScreen screen;
    public static ArrayList<BookEntry> entries;
    public static ArrayList<BookObject> objects;
    public final int parallax_width = 1024;
    public final int parallax_height = 2560;
    public final int bookWidth = 378;
    public final int bookHeight = 250;
    public final int bookInsideWidth = 344;
    public final int bookInsideHeight = 218;
    public float xOffset;
    public float yOffset;
    public float cachedXOffset;
    public float cachedYOffset;
    public boolean ignoreNextMouseInput;

    protected ProgressionBookScreen() {
        super(new TranslatableText("malum.gui.book.title"));
        this.client = MinecraftClient.getInstance();
        setupEntries();
        setupObjects();
    }

    public static void setupEntries() {
        entries = new ArrayList<>();
        final Item EMPTY = ItemStack.EMPTY.getItem();

        entries.add(new BookEntry(
                "introduction", ENCYCLOPEDIA_ARCANA, 0, 0)
                .setObjectSupplier(ImportantEntryObject::new)
                .addPage(new HeadlineTextPage("introduction", "introduction_a"))
                .addPage(new TextPage("introduction_b"))
                .addPage(new TextPage("introduction_c"))
                .addPage(new TextPage("introduction_d"))
        );


        entries.add(new BookEntry(
                "spirit_magics", SOUL_SAND, 0, 1)
                .addPage(new HeadlineTextPage("spirit_magics", "spirit_magics_a"))
                .addPage(new TextPage("spirit_magics_b"))
                .addPage(new TextPage("spirit_magics_c"))
        );

        entries.add(new BookEntry(
                "runewood", RUNEWOOD_SAPLING.asItem(), 1, 2)
                .addPage(new HeadlineTextPage("runewood", "runewood_a"))
                .addPage(new TextPage("runewood_b"))
                .addPage(CraftingBookPage.itemPedestalPage(RUNEWOOD_ITEM_PEDESTAL.asItem(), RUNEWOOD_PLANKS.asItem(), RUNEWOOD_PLANKS_SLAB.asItem()))
                .addPage(CraftingBookPage.itemStandPage(RUNEWOOD_ITEM_STAND.asItem(), RUNEWOOD_PLANKS.asItem(), RUNEWOOD_PLANKS_SLAB.asItem()))
                .addPage(new HeadlineTextPage("arcane_charcoal", "arcane_charcoal"))
                .addPage(new SmeltingBookPage(RUNEWOOD_LOG.asItem(), ARCANE_CHARCOAL))
                .addPage(CraftingBookPage.fullPage(BLOCK_OF_ARCANE_CHARCOAL.asItem(), ARCANE_CHARCOAL))
                .addPage(new HeadlineTextPage("holy_sap", "holy_sap_a"))
                .addPage(new TextPage("holy_sap_b"))
                .addPage(new CraftingBookPage(new ItemStack(HOLY_SAPBALL.asItem(), 3), SLIME_BALL, HOLY_SAP.asItem()))
                .addPage(new TextPage("holy_sap_c"))
                .addPage(new SmeltingBookPage(HOLY_SAP.asItem(), HOLY_SYRUP.asItem()))
                .addModCompatPage(new TextPage("holy_sap_d"), "thermal_expansion")
        );

        entries.add(new BookEntry(
                "soulstone", PROCESSED_SOULSTONE, -1, 2)
                .addPage(new HeadlineTextPage("soulstone", "soulstone_a"))
                .addPage(new TextPage("soulstone_b"))
                .addPage(new TextPage("soulstone_c"))
                .addPage(CraftingBookPage.fullPage(BLOCK_OF_SOULSTONE.asItem(), PROCESSED_SOULSTONE))
        );

        entries.add(new BookEntry(
                "scythes", CRUDE_SCYTHE, 0, 3)
                .addPage(new HeadlineTextPage("scythes", "scythes_a"))
                .addPage(new TextPage("scythes_b"))
                .addPage(new TextPage("scythes_c"))
                .addPage(CraftingBookPage.scythePage(CRUDE_SCYTHE, IRON_INGOT, PROCESSED_SOULSTONE))
                .addPage(new HeadlineTextPage("haunted", "haunted"))
                .addPage(new HeadlineTextPage("spirit_plunder", "spirit_plunder"))
                .addPage(new HeadlineTextPage("rebound", "rebound"))
        );

        entries.add(new BookEntry(
                "spirit_infusion", SPIRIT_ALTAR.asItem(), 0, 5)
                .setObjectSupplier(ImportantEntryObject::new)
                .addPage(new HeadlineTextPage("spirit_infusion", "spirit_infusion_a"))
                .addPage(new TextPage("spirit_infusion_b"))
                .addPage(new TextPage("spirit_infusion_c"))
                .addPage(new CraftingBookPage(SPIRIT_ALTAR.asItem(), AIR, PROCESSED_SOULSTONE.asItem(), AIR, GOLD_INGOT.asItem(), RUNEWOOD.asItem(), GOLD_INGOT.asItem(), RUNEWOOD.asItem(), RUNEWOOD.asItem(), RUNEWOOD.asItem()))
                .addPage(CraftingBookPage.itemPedestalPage(RUNEWOOD_ITEM_PEDESTAL.asItem(), RUNEWOOD_PLANKS.asItem(), RUNEWOOD_PLANKS_SLAB.asItem()))
                .addPage(CraftingBookPage.itemStandPage(RUNEWOOD_ITEM_STAND.asItem(), RUNEWOOD_PLANKS.asItem(), RUNEWOOD_PLANKS_SLAB.asItem()))
                .addPage(new HeadlineTextPage("hex_ash", "hex_ash"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/hex_ash")))
        );

        entries.add(new BookEntry(
                "simple_spirits", ARCANE_SPIRIT, -2, 4)
                .addPage(new SpiritTextPage("sacred_spirit", "sacred_spirit_a", SACRED_SPIRIT))
                .addPage(new TextPage("sacred_spirit_b"))
                .addPage(new SpiritTextPage("wicked_spirit", "wicked_spirit_a", WICKED_SPIRIT))
                .addPage(new TextPage("wicked_spirit_b"))
                .addPage(new SpiritTextPage("arcane_spirit", "arcane_spirit_a", ARCANE_SPIRIT))
                .addPage(new TextPage("arcane_spirit_b"))
                .addPage(new TextPage("arcane_spirit_c"))
        );

        entries.add(new BookEntry(
                "elemental_spirits", EARTHEN_SPIRIT, 2, 4)
                .addPage(new SpiritTextPage("earthen_spirit", "earthen_spirit_a", EARTHEN_SPIRIT))
                .addPage(new TextPage("earthen_spirit_b"))
                .addPage(new SpiritTextPage("infernal_spirit", "infernal_spirit_a", INFERNAL_SPIRIT))
                .addPage(new TextPage("infernal_spirit_b"))
                .addPage(new SpiritTextPage("aerial_spirit", "aerial_spirit_a", AERIAL_SPIRIT))
                .addPage(new TextPage("aerial_spirit_b"))
                .addPage(new SpiritTextPage("aqueous_spirit", "aqueous_spirit_a", AQUEOUS_SPIRIT))
                .addPage(new TextPage("aqueous_spirit_b"))
        );

        entries.add(new BookEntry(
                "eldritch_spirit", ELDRITCH_SPIRIT, 0, 7)
                .addPage(new SpiritTextPage("eldritch_spirit", "eldritch_spirit_a", ELDRITCH_SPIRIT))
                .addPage(new TextPage("eldritch_spirit_b"))
        );

        entries.add(new BookEntry(
                "arcane_rock", TAINTED_ROCK.asItem(), 3, 6)
                .addPage(new HeadlineTextPage("tainted_rock", "tainted_rock"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/tainted_rock")))
                .addPage(CraftingBookPage.itemPedestalPage(TAINTED_ROCK_ITEM_PEDESTAL.asItem(), TAINTED_ROCK.asItem(), TAINTED_ROCK_SLAB.asItem()))
                .addPage(CraftingBookPage.itemStandPage(TAINTED_ROCK_ITEM_STAND.asItem(), TAINTED_ROCK.asItem(), TAINTED_ROCK_SLAB.asItem()))
                .addPage(new HeadlineTextPage("twisted_rock", "twisted_rock"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/twisted_rock")))
                .addPage(CraftingBookPage.itemPedestalPage(TWISTED_ROCK_ITEM_PEDESTAL.asItem(), TWISTED_ROCK.asItem(), TWISTED_ROCK_SLAB.asItem()))
                .addPage(CraftingBookPage.itemStandPage(TWISTED_ROCK_ITEM_STAND.asItem(), TWISTED_ROCK.asItem(), TWISTED_ROCK_SLAB.asItem()))
        );

        entries.add(new BookEntry(
                "ether", ETHER.asItem(), 5, 6)
                .addPage(new HeadlineTextPage("ether", "ether_a"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/ether")))
                .addPage(new TextPage("ether_b"))
                .addPage(new CraftingBookPage(ETHER_TORCH_ITEM, EMPTY, EMPTY, EMPTY, EMPTY, ETHER.asItem(), EMPTY, EMPTY, STICK, EMPTY))
                .addPage(new CraftingBookPage(TAINTED_ETHER_BRAZIER_ITEM, EMPTY, EMPTY, EMPTY, TAINTED_ROCK.asItem(), ETHER.asItem(), TAINTED_ROCK.asItem(), STICK, TAINTED_ROCK.asItem(), STICK))
                .addPage(new CraftingBookPage(TWISTED_ETHER_BRAZIER_ITEM, EMPTY, EMPTY, EMPTY, TWISTED_ROCK.asItem(), ETHER.asItem(), TWISTED_ROCK.asItem(), STICK, TWISTED_ROCK.asItem(), STICK))
                .addPage(new HeadlineTextPage("iridescent_ether", "iridescent_ether_a"))
                .addPage(new TextPage("iridescent_ether_b"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/iridescent_ether")))
                .addPage(new CraftingBookPage(IRIDESCENT_ETHER_TORCH_ITEM, EMPTY, EMPTY, EMPTY, EMPTY, IRIDESCENT_ETHER_ITEM, EMPTY, EMPTY, STICK, EMPTY))
                .addPage(new CraftingBookPage(TAINTED_IRIDESCENT_ETHER_BRAZIER_ITEM, EMPTY, EMPTY, EMPTY, TAINTED_ROCK.asItem(), IRIDESCENT_ETHER.asItem(), TAINTED_ROCK.asItem(), STICK, TAINTED_ROCK.asItem(), STICK))
                .addPage(new CraftingBookPage(TWISTED_IRIDESCENT_ETHER_BRAZIER_ITEM, EMPTY, EMPTY, EMPTY, TWISTED_ROCK.asItem(), IRIDESCENT_ETHER.asItem(), TWISTED_ROCK.asItem(), STICK, TWISTED_ROCK.asItem(), STICK))
        );

        entries.add(new BookEntry(
                "spirit_fabric", SPIRIT_FABRIC, 4, 5)
                .addPage(new HeadlineTextPage("spirit_fabric", "spirit_fabric"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/spirit_fabric")))
                .addPage(new HeadlineTextPage("spirit_pouch", "spirit_pouch"))
                .addPage(new CraftingBookPage(SPIRIT_POUCH, EMPTY, STRING, EMPTY, SPIRIT_FABRIC, SOUL_SAND, SPIRIT_FABRIC, EMPTY, SPIRIT_FABRIC, EMPTY))
        );

        entries.add(new BookEntry(
                "soul_hunter_gear", SPIRIT_HUNTER_CLOAK, 4, 7)
                .addPage(new HeadlineTextPage("soul_hunter_armor", "soul_hunter_armor"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/soul_hunter_cloak")))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/soul_hunter_robe")))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/soul_hunter_leggings")))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/soul_hunter_boots")))
        );
/*
        entries.add(new BookEntry(
                "spirit_focusing", SPIRIT_CRUCIBLE.asItem(), 7, 6)
                .addPage(new HeadlineTextPage("spirit_focusing", "spirit_focusing_a"))
                .addPage(new TextPage("spirit_focusing_b"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_crucible")))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "alchemical_impetus")))
        );

        entries.add(new BookEntry(
                "working_with_ashes", GUNPOWDER, 6, 5)
                .addPage(new HeadlineTextPage("working_with_ashes", "working_with_ashes"))
                .addPage(SpiritCruciblePage.fromOutput(GUNPOWDER))
                .addPage(SpiritCruciblePage.fromOutput(GLOWSTONE_DUST))
                .addPage(SpiritCruciblePage.fromOutput(REDSTONE))
        );

        entries.add(new BookEntry(
                "metallurgic_magic", IRON_NODE.get(), 8, 7)
                .addPage(new HeadlineTextPage("metallurgic_magic", "metallurgic_magic_a"))
                .addPage(new TextPage("metallurgic_magic_b"))
                .addPage(SpiritInfusionPage.fromOutput(IRON_IMPETUS))
                .addPage(SpiritCruciblePage.fromOutput(IRON_NODE))
                .addPage(SpiritInfusionPage.fromOutput(GOLD_IMPETUS))
                .addPage(SpiritCruciblePage.fromOutput(GOLD_NODE))
                .addPage(SpiritInfusionPage.fromOutput(COPPER_IMPETUS))
                .addPage(SpiritCruciblePage.fromOutput(COPPER_NODE))
                .addPage(SpiritInfusionPage.fromOutput(LEAD_IMPETUS))
                .addPage(SpiritCruciblePage.fromOutput(LEAD_NODE))
                .addPage(SpiritInfusionPage.fromOutput(SILVER_IMPETUS))
                .addPage(SpiritCruciblePage.fromOutput(SILVER_NODE))
                .addPage(SpiritInfusionPage.fromOutput(ALUMINUM_IMPETUS))
                .addPage(SpiritCruciblePage.fromOutput(ALUMINUM_NODE))
                .addPage(SpiritInfusionPage.fromOutput(NICKEL_IMPETUS))
                .addPage(SpiritCruciblePage.fromOutput(NICKEL_NODE))
                .addPage(SpiritInfusionPage.fromOutput(URANIUM_IMPETUS))
                .addPage(SpiritCruciblePage.fromOutput(URANIUM_NODE))
                .addPage(SpiritInfusionPage.fromOutput(OSMIUM_IMPETUS))
                .addPage(SpiritCruciblePage.fromOutput(OSMIUM_NODE))
                .addPage(SpiritInfusionPage.fromOutput(ZINC_IMPETUS))
                .addPage(SpiritCruciblePage.fromOutput(ZINC_NODE))
                .addPage(SpiritInfusionPage.fromOutput(TIN_IMPETUS))
                .addPage(SpiritCruciblePage.fromOutput(TIN_NODE))
        );

        entries.add(new BookEntry(
                "crucible_acceleration", SPIRIT_CATALYZER.get(), 7, 4)
                .addPage(new HeadlineTextPage("crucible_acceleration", "crucible_acceleration_a"))
                .addPage(new TextPage("crucible_acceleration_b"))
                .addPage(new TextPage("crucible_acceleration_c"))
                .addPage(SpiritInfusionPage.fromOutput(SPIRIT_CATALYZER))
        );
*/
        entries.add(new BookEntry(
                "spirit_metallurgy", SOUL_STAINED_STEEL_INGOT, -3, 6)
                .addPage(new HeadlineTextPage("hallowed_gold", "hallowed_gold_a"))
                .addPage(new TextPage("hallowed_gold_b"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/hallowed_gold_ingot")))
                .addPage(CraftingBookPage.resonatorPage(HALLOWED_SPIRIT_RESONATOR, QUARTZ, HALLOWED_GOLD_INGOT, RUNEWOOD_PLANKS.asItem()))
                .addPage(new HeadlineTextPage("spirit_jar", "spirit_jar"))
                .addPage(new CraftingBookPage(SPIRIT_JAR.asItem(), GLASS_PANE, HALLOWED_GOLD_INGOT, GLASS_PANE, GLASS_PANE, EMPTY, GLASS_PANE, GLASS_PANE, GLASS_PANE, GLASS_PANE))
                .addPage(new HeadlineTextPage("soul_stained_steel", "soul_stained_steel_a"))
                .addPage(new TextPage("soul_stained_steel_b"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/soul_stained_steel_ingot")))
                .addPage(CraftingBookPage.resonatorPage(STAINED_SPIRIT_RESONATOR, QUARTZ, SOUL_STAINED_STEEL_INGOT, RUNEWOOD_PLANKS.asItem()))
                .addPage(CraftingBookPage.toolPage(SOUL_STAINED_STEEL_PICKAXE, SOUL_STAINED_STEEL_INGOT))
                .addPage(CraftingBookPage.toolPage(SOUL_STAINED_STEEL_AXE, SOUL_STAINED_STEEL_INGOT))
                .addPage(CraftingBookPage.toolPage(SOUL_STAINED_STEEL_HOE, SOUL_STAINED_STEEL_INGOT))
                .addPage(CraftingBookPage.toolPage(SOUL_STAINED_STEEL_SHOVEL, SOUL_STAINED_STEEL_INGOT))
                .addPage(CraftingBookPage.toolPage(SOUL_STAINED_STEEL_SWORD, SOUL_STAINED_STEEL_INGOT))
//                .addModCompatPage(new CraftingBookPage(SOUL_STAINED_STEEL_KNIFE, EMPTY, EMPTY, EMPTY, EMPTY, SOUL_STAINED_STEEL_INGOT, EMPTY, STICK), "farmersdelight")
        );

        entries.add(new BookEntry(
                "soul_stained_gear", SOUL_STAINED_STEEL_SCYTHE, -4, 5)
                .addPage(new HeadlineTextPage("soul_stained_scythe", "soul_stained_scythe"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/soul_stained_steel_scythe")))
                .addPage(new HeadlineTextPage("soul_stained_armor", "soul_stained_armor_a"))
                .addPage(new TextPage("soul_stained_armor_b"))
                .addPage(new TextPage("soul_stained_armor_c"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/soul_stained_steel_helmet")))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/soul_stained_steel_chestplate")))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/soul_stained_steel_leggings")))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/soul_stained_steel_boots")))
        );

        entries.add(new BookEntry(
                "spirit_trinkets", ORNATE_RING, -4, 7)
                .addPage(new HeadlineTextPage("spirit_trinkets", "spirit_trinkets_a"))
                .addPage(new TextPage("spirit_trinkets_b"))
                .addPage(CraftingBookPage.ringPage(GILDED_RING, LEATHER, HALLOWED_GOLD_INGOT))
                .addPage(new CraftingBookPage(GILDED_BELT, LEATHER, LEATHER, LEATHER, HALLOWED_GOLD_INGOT, PROCESSED_SOULSTONE, HALLOWED_GOLD_INGOT, EMPTY, HALLOWED_GOLD_INGOT, EMPTY))
                .addPage(CraftingBookPage.ringPage(ORNATE_RING, LEATHER, SOUL_STAINED_STEEL_INGOT))
                .addPage(new CraftingBookPage(ORNATE_NECKLACE, EMPTY, STRING, EMPTY, STRING, EMPTY, STRING, EMPTY, SOUL_STAINED_STEEL_INGOT, EMPTY))
        );

        entries.add(new BookEntry(
                "soul_hunter_trinkets", RING_OF_ARCANE_REACH, -5, 6)
                .addPage(new HeadlineTextPage("arcane_reach", "arcane_reach"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/ring_of_arcane_reach")))
                .addPage(new HeadlineTextPage("arcane_spoil", "arcane_spoil"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/ring_of_arcane_spoil")))
        );

        entries.add(new BookEntry(
                "ring_of_prowess", RING_OF_PROWESS, -7, 6)
                .addPage(new HeadlineTextPage("ring_of_prowess", "ring_of_prowess_a"))
                .addPage(new TextPage("ring_of_prowess_b"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/ring_of_prowess")))
        );

        entries.add(new BookEntry(
                "ring_of_wicked_intent", RING_OF_WICKED_INTENT, -7, 8)
                .addPage(new HeadlineTextPage("ring_of_wicked_intent", "ring_of_wicked_intent"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/ring_of_wicked_intent")))
        );

        entries.add(new BookEntry(
                "ring_of_curative_talent", RING_OF_CURATIVE_TALENT, -7, 4)
                .addPage(new HeadlineTextPage("ring_of_curative_talent", "ring_of_curative_talent"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/ring_of_curative_talent")))
        );

        entries.add(new BookEntry(
                "necklace_of_the_mystic_mirror", NECKLACE_OF_THE_MYSTIC_MIRROR, -6, 5)
                .addPage(new HeadlineTextPage("necklace_of_the_mystic_mirror", "necklace_of_the_mystic_mirror"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/necklace_of_the_mystic_mirror")))
        );

        entries.add(new BookEntry(
                "necklace_of_the_narrow_edge", NECKLACE_OF_THE_NARROW_EDGE, -8, 7)
                .addPage(new HeadlineTextPage("necklace_of_the_narrow_edge", "necklace_of_the_narrow_edge"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/necklace_of_the_narrow_edge")))
        );

        entries.add(new BookEntry(
                "warded_belt", WARDED_BELT, -9, 5)
                .addPage(new HeadlineTextPage("warded_belt", "warded_belt"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/warded_belt")))
        );

        entries.add(new BookEntry(
                "mirror_magic", SPECTRAL_LENS, -6, 10)
                .setObjectSupplier(ImportantEntryObject::new)
                .addPage(new HeadlineTextPage("mirror_magic", "mirror_magic"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/spectral_lens")))
        );

        entries.add(new BookEntry(
                "voodoo_magic", POPPET, 6, 10)
                .setObjectSupplier(ImportantEntryObject::new)
                .addPage(new HeadlineTextPage("voodoo_magic", "voodoo_magic"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/poppet")))
        );

        entries.add(new BookEntry(
                "altar_acceleration", RUNEWOOD_OBELISK.asItem(), -1, 8)
                .addPage(new HeadlineTextPage("runewood_obelisk", "runewood_obelisk"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/runewood_obelisk")))
                .addPage(new HeadlineTextPage("brilliant_obelisk", "brilliant_obelisk"))
                .addPage(SpiritInfusionPage.fromId(new Identifier(MODID, "spirit_infusion/brilliant_obelisk")))
        );
/*
        entries.add(new BookEntry(
                "totem_magic", RUNEWOOD_TOTEM_BASE.get(), 0, 9)
                .setObjectSupplier(ImportantEntryObject::new)
                .addPage(new HeadlineTextPage("totem_magic", "totem_magic_a"))
                .addPage(new TextPage("totem_magic_b"))
                .addPage(new TextPage("totem_magic_c"))
                .addPage(SpiritInfusionPage.fromOutput(RUNEWOOD_TOTEM_BASE))
        );

        entries.add(new BookEntry(
                "arcane_rite", ARCANE_SPIRIT.get(), 0, 11)
                .addPage(new HeadlineTextPage("totem_corruption", "totem_corruption_a"))
                .addPage(new TextPage("totem_corruption_b"))
                .addPage(new DoubleHeadlineTextPage("rite_effect", "arcane_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.ARCANE_RITE))
                .addPage(new TextPage("totem_corruption_c"))
                .addPage(SpiritInfusionPage.fromOutput(SOULWOOD_TOTEM_BASE))
        );

        entries.add(new BookEntry(
                "sacred_rite", SACRED_SPIRIT.get(), -2, 11)
                .addPage(new DoubleHeadlineTextPage("rite_effect", "sacred_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.SACRED_RITE))
                .addPage(new DoubleHeadlineTextPage("rite_effect", "eldritch_sacred_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.ELDRITCH_SACRED_RITE))
        );

        entries.add(new BookEntry(
                "wicked_rite", WICKED_SPIRIT.get(), 2, 11)
                .addPage(new DoubleHeadlineTextPage("rite_effect", "wicked_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.WICKED_RITE))
                .addPage(new DoubleHeadlineTextPage("rite_effect", "eldritch_wicked_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.ELDRITCH_WICKED_RITE))
        );

        entries.add(new BookEntry(
                "earthen_rite", EARTHEN_SPIRIT.get(), -1, 12)
                .addPage(new DoubleHeadlineTextPage("rite_effect", "earthen_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.EARTHEN_RITE))
                .addPage(new DoubleHeadlineTextPage("rite_effect", "eldritch_earthen_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE))
        );

        entries.add(new BookEntry(
                "infernal_rite", INFERNAL_SPIRIT.get(), 1, 12)
                .addPage(new DoubleHeadlineTextPage("rite_effect", "infernal_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.INFERNAL_RITE))
                .addPage(new DoubleHeadlineTextPage("rite_effect", "eldritch_infernal_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE))
        );

        entries.add(new BookEntry(
                "aerial_rite", AERIAL_SPIRIT.get(), -1, 10)
                .addPage(new DoubleHeadlineTextPage("rite_effect", "aerial_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.AERIAL_RITE))
                .addPage(new DoubleHeadlineTextPage("rite_effect", "eldritch_aerial_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE))
        );

        entries.add(new BookEntry(
                "aqueous_rite", AQUEOUS_SPIRIT.get(), 1, 10)
                .addPage(new DoubleHeadlineTextPage("rite_effect", "aqueous_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.AQUEOUS_RITE))
                .addPage(new DoubleHeadlineTextPage("rite_effect", "eldritch_aqueous_rite"))
                .addPage(new SpiritRitePage(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE))
        );

        entries.add(new BookEntry(
                "soulwood", SOULWOOD_SAPLING.get(), 0, 13)
                .addPage(new HeadlineTextPage("soulwood", "soulwood_a"))
                .addPage(new TextPage("soulwood_b"))
                .addPage(CraftingBookPage.itemPedestalPage(SOULWOOD_ITEM_PEDESTAL.get(), SOULWOOD_PLANKS.get(), SOULWOOD_PLANKS_SLAB))
                .addPage(CraftingBookPage.itemStandPage(SOULWOOD_ITEM_STAND.get(), SOULWOOD_PLANKS.get(), SOULWOOD_PLANKS_SLAB))
                .addPage(new SmeltingBookPage(SOULWOOD_LOG.get(), ARCANE_CHARCOAL))
                .addPage(CraftingBookPage.fullPage(BLOCK_OF_ARCANE_CHARCOAL.get(), ARCANE_CHARCOAL))
                .addPage(new CraftingBookPage(new ItemStack(UNHOLY_SAPBALL.get(), 3), Items.SLIME_BALL, UNHOLY_SAP))
                .addPage(new SmeltingBookPage(UNHOLY_SAP.get(), UNHOLY_SYRUP))
        );

        entries.add(new BookEntry(
                "magebane_belt", MAGEBANE_BELT.get(), 1, 15)
                .addPage(new HeadlineTextPage("magebane_belt", "magebane_belt"))
                .addPage(SpiritInfusionPage.fromOutput(MAGEBANE_BELT))
        );

        entries.add(new BookEntry(
                "tyrving", TYRVING.get(), -1, 15)
                .addPage(new HeadlineTextPage("tyrving", "tyrving"))
                .addPage(SpiritInfusionPage.fromOutput(TYRVING))
        );

        entries.add(new BookEntry(
                "ceaseless_impetus", CEASELESS_IMPETUS.get(), 0, 16)
                .addPage(new HeadlineTextPage("ceaseless_impetus", "ceaseless_impetus_a"))
                .addPage(new TextPage("ceaseless_impetus_b"))
                .addPage(SpiritInfusionPage.fromOutput(CEASELESS_IMPETUS))
        );


        entries.add(new BookEntry(
                "huh", THE_DEVICE.asItem(), 0, -10)
                .setObjectSupplier(VanishingEntryObject::new)
                .addPage(new HeadlineTextPage("the_device", "the_device"))
                .addPage(new CraftingBookPage(THE_DEVICE.asItem(), TWISTED_ROCK.asItem(), TAINTED_ROCK.asItem(), TWISTED_ROCK.asItem(), TAINTED_ROCK.asItem(), TWISTED_ROCK.asItem(), TAINTED_ROCK.asItem(), TWISTED_ROCK.asItem(), TAINTED_ROCK.asItem(), TWISTED_ROCK.asItem()))
        ); */
    }

    public static boolean isHovering(double mouseX, double mouseY, int posX, int posY, int width, int height) {
        if (!isInView(mouseX, mouseY)) {
            return false;
        }
        return mouseX > posX && mouseX < posX + width && mouseY > posY && mouseY < posY + height;
    }

    public static boolean isInView(double mouseX, double mouseY) {
        int guiLeft = (screen.width - screen.bookWidth) / 2;
        int guiTop = (screen.height - screen.bookHeight) / 2;
        return !(mouseX < guiLeft + 17) && !(mouseY < guiTop + 14) && !(mouseX > guiLeft + (screen.bookWidth - 17)) && !(mouseY > (guiTop + screen.bookHeight - 14));
    }

    public static void renderTexture(Identifier texture, MatrixStack matrices, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);
        DrawableHelper.drawTexture(matrices, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
//        RenderHelper.blit(matrices, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
    }

    public static void renderTransparentTexture(Identifier texture, MatrixStack matrices, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        renderTexture(texture, matrices, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void renderItem(MatrixStack matrices, ItemStack stack, int posX, int posY, int mouseX, int mouseY) {
        final MinecraftClient client = MinecraftClient.getInstance();
        client.getItemRenderer().renderInGuiWithOverrides(stack, posX, posY);
        client.getItemRenderer().renderGuiItemOverlay(screen.textRenderer, stack, posX, posY, null);
        if (isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            screen.renderTooltip(matrices, new TranslatableText(stack.getTranslationKey()), mouseX, mouseY);
        }
    }

    public static void renderWrappingText(MatrixStack matrices, String text, int x, int y, int w) {
        final MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        text = new TranslatableText(text).getString();
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        String line = "";

        for (String s : words) {
            if (textRenderer.getWidth(line) + textRenderer.getWidth(s) > w) {
                lines.add(line);
                line = s + " ";
            } else line += s + " ";
        }

        if (!line.isEmpty()) {
            lines.add(line);
        }

        for (int i = 0; i < lines.size(); i++) {
            String currentLine = lines.get(i);
            renderRawText(matrices, currentLine, x, y + i * (textRenderer.fontHeight + 1), glow(i / 4f));
        }
    }

    public static void renderText(MatrixStack stack, Text text, int x, int y) {
        String str = text.getString();
        renderRawText(stack, str, x, y, glow(0));
    }

    private static void renderRawText(MatrixStack matrices, String text, int x, int y, float glow) {
        TextRenderer textRenderer = screen.textRenderer;
        //182, 61, 183   227, 39, 228
        int r = (int) MathHelper.lerp(glow, 182, 227);
        int g = (int) MathHelper.lerp(glow, 61, 39);
        int b = (int) MathHelper.lerp(glow, 183, 228);

        textRenderer.draw(matrices, text, x - 1, y, BackgroundHelper.ColorMixer.getArgb(96, 255, 210, 243));
        textRenderer.draw(matrices, text, x + 1, y, BackgroundHelper.ColorMixer.getArgb(128, 240, 131, 232));
        textRenderer.draw(matrices, text, x, y - 1, BackgroundHelper.ColorMixer.getArgb(128, 255, 183, 236));
        textRenderer.draw(matrices, text, x, y + 1, BackgroundHelper.ColorMixer.getArgb(96, 236, 110, 226));

        textRenderer.draw(matrices, text, x, y, BackgroundHelper.ColorMixer.getArgb(255, r, g, b));
    }

    public static float glow(float offset) {
        final MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return 0.0f;
        return MathHelper.sin(offset + client.player.world.getTime() / 40f) / 2f + 0.5f;
    }

    public static void openScreen(boolean ignoreNextMouseClick) {
        final MinecraftClient client = MinecraftClient.getInstance();
        client.setScreen(getInstance());
        screen.playSound();
        screen.ignoreNextMouseInput = ignoreNextMouseClick;
    }

    public static ProgressionBookScreen getInstance() {
        if (screen == null) {
            screen = new ProgressionBookScreen();
        }
        return screen;
    }

    public void setupObjects() {
        if (client == null) return;
        objects = new ArrayList<>();
        this.width = client.getWindow().getScaledWidth();
        this.height = client.getWindow().getScaledHeight();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int coreX = guiLeft + bookInsideWidth;
        int coreY = guiTop + bookInsideHeight;
        int width = 40;
        int height = 48;
        for (BookEntry entry : entries) {
            objects.add(entry.objectSupplier.getBookObject(entry, coreX + entry.xOffset * width, coreY - entry.yOffset * height));
        }
        faceObject(objects.get(0));
    }

    public void faceObject(BookObject object) {
        if (client == null) return;
        this.width = client.getWindow().getScaledWidth();
        this.height = client.getWindow().getScaledHeight();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        xOffset = -object.posX + guiLeft + bookInsideWidth;
        yOffset = -object.posY + guiTop + bookInsideHeight;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, partialTicks);
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;

        renderBackground(BACKGROUND_TEXTURE, matrices, 0.1f, 0.4f);
        GL11.glEnable(GL_SCISSOR_TEST);
        cut();

        renderEntries(matrices, mouseX, mouseY, partialTicks);
//        ScreenParticleHandler.renderParticles(BEFORE_TOOLTIPS); todo: screen particles
        GL11.glDisable(GL_SCISSOR_TEST);

        renderTransparentTexture(FADE_TEXTURE, matrices, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        renderTexture(FRAME_TEXTURE, matrices, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        lateEntryRender(matrices, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        xOffset += dragX;
        yOffset += dragY;
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        cachedXOffset = xOffset;
        cachedYOffset = yOffset;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (ignoreNextMouseInput) {
            ignoreNextMouseInput = false;
            return super.mouseReleased(mouseX, mouseY, button);
        }
        if (xOffset != cachedXOffset || yOffset != cachedYOffset) {
            return super.mouseReleased(mouseX, mouseY, button);
        }
        for (BookObject object : objects) {
            if (object.isHovering(xOffset, yOffset, mouseX, mouseY)) {
                object.click(xOffset, yOffset, mouseX, mouseY);
                break;
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (client == null) return false;
        if (keyCode == client.options.inventoryKey.getDefaultKey().getKeyCode()) {
            onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void renderEntries(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        for (int i = objects.size() - 1; i >= 0; i--) {
            BookObject object = objects.get(i);
            boolean isHovering = object.isHovering(xOffset, yOffset, mouseX, mouseY);
            object.isHovering = isHovering;
            object.hover = isHovering ? Math.min(object.hover++, object.hoverCap()) : Math.max(object.hover - 1, 0);
            object.render(client, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
        }
    }

    public void lateEntryRender(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        for (int i = objects.size() - 1; i >= 0; i--) {
            BookObject object = objects.get(i);
            object.lateRender(client, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
        }
    }

    public void renderBackground(Identifier texture, MatrixStack matrices, float xModifier, float yModifier) {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 17;
        int insideTop = guiTop + 14;
        float uOffset = (parallax_width - xOffset) * xModifier;
        float vOffset = Math.min(parallax_height - bookInsideHeight, (parallax_height - bookInsideHeight - yOffset * yModifier));
        if (vOffset <= parallax_height / 2f) {
            vOffset = parallax_height / 2f;
        }
        if (uOffset <= 0) {
            uOffset = 0;
        }
        if (uOffset > (bookInsideWidth - 8) / 2f) {
            uOffset = (bookInsideWidth - 8) / 2f;
        }
        renderTexture(texture, matrices, insideLeft, insideTop, uOffset, vOffset, bookInsideWidth, bookInsideHeight, parallax_width / 2, parallax_height / 2);
    }

    public void cut() {
        if (client == null) return;
        int scale = (int) client.getWindow().getScaleFactor();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 17;
        int insideTop = guiTop + 18;
        GL11.glScissor(insideLeft * scale, insideTop * scale, bookInsideWidth * scale, (bookInsideHeight + 1) * scale); // do not ask why the 1 is needed please
    }

    public void playSound() {
        if (client == null || client.player == null) return;
        PlayerEntity playerEntity = client.player;
        playerEntity.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }
}