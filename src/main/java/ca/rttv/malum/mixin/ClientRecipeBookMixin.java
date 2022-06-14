package ca.rttv.malum.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.client.recipebook.ClientRecipeBook;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientRecipeBook.class)
final class ClientRecipeBookMixin {
    @WrapWithCondition(method = "getGroupForRecipe", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V", ordinal = 0, remap = false))
    private static boolean malum$disableAnnoyingLogMessage(Logger LOGGER, String format, Object arg1, Object arg2) {
        return false;
    }
}
