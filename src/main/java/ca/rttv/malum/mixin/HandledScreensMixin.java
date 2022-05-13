package ca.rttv.malum.mixin;

import ca.rttv.malum.client.screen.SpiritPouchScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static ca.rttv.malum.registry.MalumScreenHandlerRegistry.SPIRIT_POUCH_SCREEN_HANDLER;

@Mixin(HandledScreens.class)
public final class HandledScreensMixin {
    @Shadow
    public static <M extends ScreenHandler, U extends Screen & ScreenHandlerProvider<M>> void register(ScreenHandlerType<? extends M> type, HandledScreens.Provider<M, U> provider) {}

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void clinit(CallbackInfo ci) {
        register(SPIRIT_POUCH_SCREEN_HANDLER, SpiritPouchScreen::new);
    }
}
