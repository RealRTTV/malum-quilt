package ca.rttv.malum.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientRecipeBook.class)
public abstract class ClientRecipeBookMixin {
    @WrapWithCondition(method = "getGroupForRecipe", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"))
    private boolean disableAnnoyingLogMessage(Recipe<?> recipe) {
        return false;
    }
}
