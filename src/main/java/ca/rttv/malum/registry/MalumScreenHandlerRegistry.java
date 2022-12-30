package ca.rttv.malum.registry;

import ca.rttv.malum.screen.SpiritPouchScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;

@SuppressWarnings("unused")
public interface MalumScreenHandlerRegistry {
    Map<Identifier, ScreenHandlerType<? extends ScreenHandler>> SCREEN_HANDLERS = new LinkedHashMap<>();

    ScreenHandlerType<SpiritPouchScreenHandler> SPIRIT_POUCH_SCREEN_HANDLER = register("spirit_pouch", SpiritPouchScreenHandler::new);

    static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        ScreenHandlerType<T> screenHandlerType = new ScreenHandlerType<>(factory);
        SCREEN_HANDLERS.put(new Identifier(MODID, id), screenHandlerType);
        return screenHandlerType;
    }

    static void init() {
        SCREEN_HANDLERS.forEach((id, handler) -> Registry.register(Registries.SCREEN_HANDLER_TYPE, id, handler));
    }
}
