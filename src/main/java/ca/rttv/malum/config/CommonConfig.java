package ca.rttv.malum.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

// MAKE SURE THIS FILE IS UNIX LINE ENDING BASED OR THINGS MAY GO CATASTROPHICALLY WRONG
public class CommonConfig {
    public static final Logger LOGGER;

    public static final boolean GENERATE_RUNEWOOD_TREES; // todo, implement
    public static final double RUNEWOOD_TREES_CHANCE_PLAINS; // todo, implement
    public static final double RUNEWOOD_TREES_CHANCE_FOREST; // todo, implement

    public static final boolean GENERATE_BLAZING_QUARTZ; // todo, implement
    public static final int BLAZING_QUARTZ_VEIN_SIZE; // todo, implement
    public static final int BLAZING_QUARTZ_VEIN_AMOUNT; // todo, implement

    public static final boolean GENERATE_BRILLIANT_STONE; // todo, implement
    public static final int BRILLIANT_STONE_VEIN_SIZE; // todo, implement
    public static final int BRILLIANT_STONE_VEIN_AMOUNT; // todo, implement
    public static final int BRILLIANT_STONE_VEIN_MIN_Y; // todo, implement
    public static final int BRILLIANT_STONE_VEIN_MAX_Y; // todo, implement

    public static final boolean GENERATE_UNDERGROUND_SOULSTONE; // todo, implement
    public static final int UNDERGROUND_SOULSTONE_VEIN_SIZE; // todo, implement
    public static final int UNDERGROUND_SOULSTONE_VEIN_AMOUNT; // todo, implement
    public static final int UNDERGROUND_SOULSTONE_VEIN_MIN_Y; // todo, implement
    public static final int UNDERGROUND_SOULSTONE_VEIN_MAX_Y; // todo, implement

    public static final boolean GENERATE_SURFACE_SOULSTONE; // todo, implement
    public static final int SURFACE_SOULSTONE_VEIN_SIZE; // todo, implement
    public static final int SURFACE_SOULSTONE_VEIN_AMOUNT; // todo, implement
    public static final int SURFACE_SOULSTONE_VEIN_MIN_Y; // todo, implement
    public static final int SURFACE_SOULSTONE_VEIN_MAX_Y; // todo, implement

    public static final boolean ULTIMATE_REBOUND;
    public static final boolean SOULLESS_SPAWNERS;
    public static final int SOUL_WARD_RATE;
    public static final float SOUL_WARD_MAGIC;
    public static final float SOUL_WARD_PHYSICAL;

    public static final double HEART_OF_STONE_COST; // todo, implement
    public static final int HEART_OF_STONE_RATE; // todo, implement

    static {
        LOGGER = LogUtils.getLogger();

        JsonObject json = null;

        File dir = new File(".", "config");
        File file = new File(dir, "malum.json");
        if (dir.exists() && dir.isDirectory() || dir.mkdirs()) {
            if (!file.exists()) {
                try {
                    Files.writeString(file.toPath(), defaultConfig(), StandardOpenOption.WRITE);
                } catch (Exception e) {
                    LOGGER.error("Failed writing malum.json config file", e);
                }
            }
        }

        if (file.exists() && file.isFile() && file.canRead()) {
            try {
                json = JsonParser.parseString(new String(Files.readAllBytes(file.toPath())).replaceAll("( )+//.+\\n", "")).getAsJsonObject();
            } catch (Exception e) {
                LOGGER.error("Failed reading malum.json config file", e);
            }
        }

        Objects.requireNonNull(json, "Never read(past tense) malum.json file, please fix this!");

        GENERATE_RUNEWOOD_TREES = json.get("generate_runewood_trees").getAsBoolean();
        RUNEWOOD_TREES_CHANCE_PLAINS = json.get("runewood_trees_chance_plains").getAsDouble();
        RUNEWOOD_TREES_CHANCE_FOREST = json.get("runewood_trees_chance_forest").getAsDouble();

        GENERATE_BLAZING_QUARTZ = json.get("generate_blazing_quartz").getAsBoolean();
        BLAZING_QUARTZ_VEIN_SIZE = json.get("blazing_quartz_vein_size").getAsInt();
        BLAZING_QUARTZ_VEIN_AMOUNT = json.get("blazing_quartz_vein_amount").getAsInt();

        GENERATE_BRILLIANT_STONE = json.get("generate_brilliant_stone").getAsBoolean();
        BRILLIANT_STONE_VEIN_SIZE = json.get("brilliant_stone_vein_size").getAsInt();
        BRILLIANT_STONE_VEIN_AMOUNT = json.get("brilliant_stone_vein_amount").getAsInt();
        BRILLIANT_STONE_VEIN_MIN_Y = json.get("brilliant_stone_vein_min_y").getAsInt();
        BRILLIANT_STONE_VEIN_MAX_Y = json.get("brilliant_stone_vein_max_y").getAsInt();

        GENERATE_UNDERGROUND_SOULSTONE = json.get("generate_underground_soulstone").getAsBoolean();
        UNDERGROUND_SOULSTONE_VEIN_SIZE = json.get("underground_soulstone_vein_size").getAsInt();
        UNDERGROUND_SOULSTONE_VEIN_AMOUNT = json.get("underground_soulstone_vein_amount").getAsInt();
        UNDERGROUND_SOULSTONE_VEIN_MIN_Y = json.get("underground_soulstone_vein_min_y").getAsInt();
        UNDERGROUND_SOULSTONE_VEIN_MAX_Y = json.get("underground_soulstone_vein_max_y").getAsInt();

        GENERATE_SURFACE_SOULSTONE = json.get("generate_surface_soulstone").getAsBoolean();
        SURFACE_SOULSTONE_VEIN_SIZE = json.get("surface_soulstone_vein_size").getAsInt();
        SURFACE_SOULSTONE_VEIN_AMOUNT = json.get("surface_soulstone_vein_amount").getAsInt();
        SURFACE_SOULSTONE_VEIN_MIN_Y = json.get("surface_soulstone_vein_min_y").getAsInt();
        SURFACE_SOULSTONE_VEIN_MAX_Y = json.get("surface_soulstone_vein_max_y").getAsInt();
        
        ULTIMATE_REBOUND = json.get("ultimate_rebound").getAsBoolean();
        SOULLESS_SPAWNERS = json.get("soulless_spawners").getAsBoolean();


        SOUL_WARD_RATE = json.get("soul_ward_rate").getAsInt();
        SOUL_WARD_MAGIC = json.get("soul_ward_magic").getAsFloat();
        SOUL_WARD_PHYSICAL = json.get("soul_ward_physical").getAsInt();

        HEART_OF_STONE_COST = json.get("heart_of_stone_cost").getAsDouble();
        HEART_OF_STONE_RATE = json.get("heart_of_stone_rate").getAsInt();
    }

    private static String defaultConfig() {
        // just don't use utf 16, and we'll be ok
        return """
                {
                  // toggle to generate runewood trees naturally
                  // default: true
                  "generate_runewood_trees": true,

                  // chance for runewood trees to generate in the plains biome (chance value is internal, idk how it works)
                  // does not change existing generated trees
                  // default: 0.02d
                  "runewood_trees_chance_plains": 0.02d,

                  // chance for runewood trees to generate in the forest biome (chance value is internal, idk how it works)
                  // does not change existing generated trees
                  // default: 0.01d
                  "runewood_trees_chance_forest": 0.01d,



                  // toggle to generate blazing quartz naturally
                  // default: true
                  "generate_blazing_quartz": true,

                  // size of blazing quartz veins (maximum i believe)
                  // does not change existing generated veins
                  // default: 14
                  "blazing_quartz_vein_size": 14,

                  // amount of blazing quartz veins to try to generate per chunk
                  // does not change existing generated veins
                  // default: 16
                  "blazing_quartz_vein_amount": 16,



                  // toggle to generate brilliant stone
                  // does not change existing generated veins
                  // default: true
                  "generate_brilliant_stone": true,

                  // size of brilliant stone veins (maximum i believe)
                  // does not change existing generated veins
                  // default: 4
                  "brilliant_stone_vein_size": 4,

                  // amount of brilliant stone veins to try to generate per chunk
                  // does not change existing generated veins
                  // default: 3
                  "brilliant_stone_vein_amount": 3,

                  // minimum y level which brilliant stone starts generating at
                  // does not change existing generated veins
                  // default: -64
                  "brilliant_stone_vein_min_y": -64,

                  // maximum y level which brilliant stone starts generating at
                  // does not change existing generated veins
                  // default: 30
                  "brilliant_stone_vein_max_y": 30,



                  // toggle to generate underground soulstone
                  // does not change existing generated veins
                  // default: true
                  "generate_underground_soulstone": true,

                  // size of underground soulstone veins (maximum i believe)
                  // does not change existing generated veins
                  // default: 12
                  "underground_soulstone_vein_size": 12,

                  // amount of underground soulstone veins to try to generate per chunk
                  // does not change existing generated veins
                  // default: 8
                  "underground_soulstone_vein_amount": 8,

                  // minimum y level which underground soulstone starts generating at
                  // does not change existing generated veins
                  // default: -64
                  "underground_soulstone_vein_min_y": -64,

                  // maximum y level which underground soulstone starts generating at
                  // does not change existing generated veins
                  // default: 30
                  "underground_soulstone_vein_max_y": 30,



                  // toggle to generate surface soulstone
                  // does not change existing generated veins
                  // default: true
                  "generate_surface_soulstone": true,

                  // size of surface soulstone veins (maximum i believe)
                  // does not change existing generated veins
                  // default: 6
                  "surface_soulstone_vein_size": 6,

                  // amount of surface soulstone veins to try to generate per chunk
                  // does not change existing generated veins
                  // default: 8
                  "surface_soulstone_vein_amount": 8,

                  // minimum y level which surface soulstone starts generating at
                  // does not change existing generated veins
                  // default: 60
                  "surface_soulstone_vein_min_y": 60,

                  // maximum y level which surface soulstone starts generating at
                  // does not change existing generated veins
                  // default: 100
                  "surface_soulstone_vein_max_y": 100,



                  // todo
                  // default: true
                  "ultimate_rebound": true,

                  // todo
                  // default: false
                  "soulless_spawners": false,



                  // how many ticks it takes to regenerate one piece of soul ward
                  // default: 60
                  "soul_ward_rate": 60,

                  // percentage of magic damage absorbed by the soul ward
                  // default: 0.9f
                  "soul_ward_magic": 0.9f,

                  // percentage of non-magic damage absorbed by the soul ward
                  // default: 0.3f
                  "soul_ward_physical": 0.3f,



                  // amount of hunger taken when recovering a point of heart of stone (do note that this will only matter if the player has the earthen affinity)
                  // default: 0.2d
                  "heart_of_stone_cost": 0.2d,

                  // amount of ticks it takes for one unit of hard of stone to regenerate
                  // default: 40
                  "heart_of_stone_rate": 40
                }
                """;
    }
}
