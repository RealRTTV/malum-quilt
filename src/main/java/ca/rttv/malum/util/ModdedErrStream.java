package ca.rttv.malum.util;

import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.OutputStream;
import java.io.PrintStream;

public class ModdedErrStream extends PrintStream {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final String name;
    private final boolean debug;

    public ModdedErrStream(String name, OutputStream out, boolean debug) {
        super(out);
        this.name = name;
        this.debug = debug;
    }

    public void println(@Nullable String string) {
        this.log(string);
    }

    public void println(Object object) {
        this.log(String.valueOf(object));
    }

    protected void log(@Nullable String message) {
        if (message != null && message.replaceAll("Could not find code source for magic-at:.+\\.class: java\\.net\\.MalformedURLException: unknown protocol: magic-at", "").equals("")) {
            return;
        }
        if (debug) {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            StackTraceElement stackTraceElement = stackTraceElements[Math.min(3, stackTraceElements.length)];
            LOGGER.info("[{}]@.({}:{}): {}", this.name, stackTraceElement.getFileName(), stackTraceElement.getLineNumber(), message);
        } else {
            LOGGER.info("[{}]: {}", this.name, message);
        }
    }
}
