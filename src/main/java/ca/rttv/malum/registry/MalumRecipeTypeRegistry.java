package ca.rttv.malum.registry;

import ca.rttv.malum.recipe.*;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;

@SuppressWarnings("unused")
public interface MalumRecipeTypeRegistry {
    Map<Identifier, RecipeType<? extends Recipe<?>>> RECIPE_TYPES = new LinkedHashMap<>();

        RecipeType<SpiritInfusionRecipe> SPIRIT_INFUSION     = register("spirit_infusion");
              RecipeType<SavedNbtRecipe> SAVED_NBT           = register("nbt_carry");
    RecipeType<BlockTransmutationRecipe> BLOCK_TRANSMUTATION = register("block_transmutation");
        RecipeType<SpiritFocusingRecipe> SPIRIT_FOCUSING     = register("spirit_focusing");
          RecipeType<SpiritRepairRecipe> SPIRIT_REPAIR       = register("spirit_repair");

    static <T extends Recipe<?>> RecipeType<T> register(String id) {
        RecipeType<T> type = new RecipeType<>(){ public String toString() { return id; }};
        RECIPE_TYPES.put(new Identifier(MODID, id), type);
        return type;
    }

    static void init() {
        RECIPE_TYPES.forEach((id, type) -> Registry.register(Registries.RECIPE_TYPE, id, type));
    }
}
