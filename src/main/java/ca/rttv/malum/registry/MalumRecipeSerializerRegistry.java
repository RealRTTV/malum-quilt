package ca.rttv.malum.registry;

import ca.rttv.malum.recipe.*;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;

@SuppressWarnings("unused")
public interface MalumRecipeSerializerRegistry {
    Map<Identifier, RecipeSerializer<? extends Recipe<?>>> RECIPE_SERIALIZER = new LinkedHashMap<>();

        RecipeSerializer<SpiritInfusionRecipe> SPIRIT_INFUSION_SERIALIZER     = registerRecipeSerializer("spirit_infusion",     new SpiritInfusionRecipe.Serializer<>(SpiritInfusionRecipe::new));
              RecipeSerializer<SavedNbtRecipe> SAVED_NBT_RECIPE_SERIALIZER    = registerRecipeSerializer("nbt_carry",           new SavedNbtRecipe.Serializer());
    RecipeSerializer<BlockTransmutationRecipe> BLOCK_TRANSMUTATION_SERIALIZER = registerRecipeSerializer("block_transmutation", new BlockTransmutationRecipe.Serializer<>(BlockTransmutationRecipe::new));
        RecipeSerializer<SpiritFocusingRecipe> SPIRIT_FOCUSING_SERIALIZER     = registerRecipeSerializer("spirit_focusing",     new SpiritFocusingRecipe.Serializer<>(SpiritFocusingRecipe::new));
          RecipeSerializer<SpiritRepairRecipe> SPIRIT_REPAIR_SERIALIZER       = registerRecipeSerializer("spirit_repair",       new SpiritRepairRecipe.Serializer<>(SpiritRepairRecipe::new));

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S registerRecipeSerializer(String id, S serializer) {
        RECIPE_SERIALIZER.put(new Identifier(MODID, id), serializer);
        return serializer;
    }

    static void init() {
        RECIPE_SERIALIZER.forEach((id, serializer)  ->   Registry.register(Registry.RECIPE_SERIALIZER, id, serializer  ));
    }
}
