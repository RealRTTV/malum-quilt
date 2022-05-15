package ca.rttv.malum.registry;

import ca.rttv.malum.recipe.*;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;

@SuppressWarnings("unused")
public interface MalumRecipeTypeRegistry {
    Map<Identifier, RecipeType<? extends Recipe<?>>> RECIPE_TYPES = new LinkedHashMap<>();

        RecipeType<SpiritInfusionRecipe> SPIRIT_INFUSION     = registerRecipeType("spirit_infusion",     new RecipeType<>() { public String toString() { return "spirit_infusion"; } });
              RecipeType<SavedNbtRecipe> SAVED_NBT_RECIPE    = registerRecipeType("nbt_carry",           new RecipeType<>() { public String toString() { return "nbt_carry"; } });
    RecipeType<BlockTransmutationRecipe> BLOCK_TRANSMUTATION = registerRecipeType("block_transmutation", new RecipeType<>() { public String toString() { return "block_transmutation"; } });
        RecipeType<SpiritFocusingRecipe> SPIRIT_FOCUSING     = registerRecipeType("spirit_focusing",     new RecipeType<>() { public String toString() { return "spirit_focusing"; }});
          RecipeType<SpiritRepairRecipe> SPIRIT_REPAIR       = registerRecipeType("spirit_repair",       new RecipeType<>() { public String toString() { return "spirit_repair"; }});

    static <T extends Recipe<?>> RecipeType<T> registerRecipeType(String id, RecipeType<T> type) {
        RECIPE_TYPES.put(new Identifier(MODID, id), type);
        return type;
    }

    static void init() {
        RECIPE_TYPES.forEach((id, type) -> Registry.register(Registry.RECIPE_TYPE, id, type));
    }
}
