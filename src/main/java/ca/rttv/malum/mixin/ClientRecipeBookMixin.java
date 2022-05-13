package ca.rttv.malum.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.client.recipebook.ClientRecipeBook;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientRecipeBook.class)
public final class ClientRecipeBookMixin {
    @WrapWithCondition(method = "getGroupForRecipe", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"))
    private static boolean disableAnnoyingLogMessage(Logger LOGGER, String format, Object arg1, Object arg2) {
        return false;
    }
}
