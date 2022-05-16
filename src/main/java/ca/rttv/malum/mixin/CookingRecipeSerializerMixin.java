package ca.rttv.malum.mixin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(CookingRecipeSerializer.class)
public abstract class CookingRecipeSerializerMixin {
    @Redirect(method = "read(Lnet/minecraft/util/Identifier;Lcom/google/gson/JsonObject;)Lnet/minecraft/recipe/AbstractCookingRecipe;", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/JsonHelper;getString(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;", ordinal = 0))
    private String getString(JsonObject json, String key) {
        JsonElement element = json.get(key);
        return element.isJsonPrimitive() ? element.getAsString() : element.getAsJsonObject().get("item").getAsString();
    }

    @ModifyArgs(method = "read(Lnet/minecraft/util/Identifier;Lcom/google/gson/JsonObject;)Lnet/minecraft/recipe/AbstractCookingRecipe;", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/CookingRecipeSerializer$RecipeFactory;create(Lnet/minecraft/util/Identifier;Ljava/lang/String;Lnet/minecraft/recipe/Ingredient;Lnet/minecraft/item/ItemStack;FI)Lnet/minecraft/recipe/AbstractCookingRecipe;", ordinal = 0))
    private void create(Args args, Identifier identifier, JsonObject jsonObject) {
        if (jsonObject.get("result").isJsonObject()) {
            ItemStack stack = args.get(3);
            stack.setCount(jsonObject.getAsJsonObject("result").get("count").getAsInt());
        }
    }
}
