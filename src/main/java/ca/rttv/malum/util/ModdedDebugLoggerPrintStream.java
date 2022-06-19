package ca.rttv.malum.util;

import net.minecraft.util.logging.DebugLoggerPrintStream;

import java.io.OutputStream;

public class ModdedDebugLoggerPrintStream extends DebugLoggerPrintStream {
    public ModdedDebugLoggerPrintStream(String string, OutputStream stream) {
        super(string, stream);
    }

    @Override
    protected void log(String message) {
        if (message == null || !message.matches("Could not find code source for magic-at:.+\\.class: java\\.net\\.MalformedURLException: unknown protocol: magic-at")) {
            super.log(message);
        }
    }
}
