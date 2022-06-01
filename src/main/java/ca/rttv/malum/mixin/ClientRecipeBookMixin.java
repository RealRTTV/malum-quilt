package ca.rttv.malum.mixin;

import net.minecraft.client.recipebook.ClientRecipeBook;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientRecipeBook.class)
public final class ClientRecipeBookMixin {
    @Redirect(method = "getGroupForRecipe", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V", ordinal = 0, remap = false))
    private static void disableAnnoyingLogMessage(Logger LOGGER, String format, Object arg1, Object arg2) {}
}
