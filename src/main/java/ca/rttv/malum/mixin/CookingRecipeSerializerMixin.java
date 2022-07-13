package ca.rttv.malum.mixin;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(CookingRecipeSerializer.class)
abstract class CookingRecipeSerializerMixin {
    @ModifyArgs(method = "read(Lnet/minecraft/util/Identifier;Lcom/google/gson/JsonObject;)Lnet/minecraft/recipe/AbstractCookingRecipe;", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/JsonHelper;getString(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;", ordinal = 0))
    private void malum$getString(Args args) {
        JsonObject root = args.get(0);
        String originalKey = args.get(1);

        if (root.get(originalKey) instanceof JsonObject result) {
            args.set(0, result);
            args.set(1, "item");
        }
    }

    @ModifyArgs(method = "read(Lnet/minecraft/util/Identifier;Lcom/google/gson/JsonObject;)Lnet/minecraft/recipe/AbstractCookingRecipe;", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/CookingRecipeSerializer$RecipeFactory;create(Lnet/minecraft/util/Identifier;Ljava/lang/String;Lnet/minecraft/recipe/Ingredient;Lnet/minecraft/item/ItemStack;FI)Lnet/minecraft/recipe/AbstractCookingRecipe;", ordinal = 0))
    private void malum$create(Args args, Identifier identifier, JsonObject jsonObject) {
        if (jsonObject.get("result").isJsonObject()) {
            ItemStack stack = args.get(3);
            stack.setCount(jsonObject.getAsJsonObject("result").get("count").getAsInt());
        }
    }
}
