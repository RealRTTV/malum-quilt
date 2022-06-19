package ca.rttv.malum.util;

import net.minecraft.util.logging.LoggerPrintStream;
import org.jetbrains.annotations.Nullable;

import java.io.OutputStream;

public class ModdedLoggerPrintStream extends LoggerPrintStream {
    public ModdedLoggerPrintStream(String string, OutputStream stream) {
        super(string, stream);
    }

    @Override
    protected void log(@Nullable String message) {
        if (message == null || !message.matches("Could not find code source for magic-at:.+\\.class: java\\.net\\.MalformedURLException: unknown protocol: magic-at")) {
            super.log(message);
        }
    }
}
