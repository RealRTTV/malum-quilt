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
public class ClientConfig {
    public static final Logger LOGGER;
    public static final boolean DELAYED_PARTICLE_RENDERING;
    public static final String BOOK_THEME;

    static {
        LOGGER = LogUtils.getLogger();

        JsonObject json = null;

        File dir = new File(".", "config");
        File file = new File(dir, "malum-client.json");
        if (dir.exists() && dir.isDirectory() || dir.mkdirs()) {
            if (!file.exists()) {
                try {
                    Files.writeString(file.toPath(), defaultConfig(), StandardOpenOption.WRITE);
                } catch (Exception e) {
                    LOGGER.error("Failed writing malum-client.json config file", e);
                }
            }
        }

        if (file.exists() && file.isFile() && file.canRead()) {
            try {
                json = JsonParser.parseString(new String(Files.readAllBytes(file.toPath())).replaceAll("( )+//.+\\n", "")).getAsJsonObject();
            } catch (Exception e) {
                LOGGER.error("Failed reading malum-client.json config file", e);
            }
        }

        Objects.requireNonNull(json, "Never read(past tense) malum-client.json file, please fix this!");

        DELAYED_PARTICLE_RENDERING = json.get("delayed_particle_rendering").getAsBoolean();
        BOOK_THEME = json.get("book_theme").getAsString();
    }

    private static String defaultConfig() {
        // just don't use utf 16, and we'll be ok
        return """
               {
                 // responsible for moving all rendering to a separate framebuffer, ensuring better rendering & compatibility with things like Sodium.
                 "delayed_particle_rendering": true,
                 
                 // 'default' or 'easy_read' are acceptable, changes the progression book's theme
                 "book_theme": "default"
               }
               """;
    }
}
