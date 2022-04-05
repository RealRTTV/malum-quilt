package ca.rttv.malum;

import ca.rttv.malum.util.IngredientWithCount;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

import static ca.rttv.malum.registry.MalumRegistry.SPIRIT_INFUSION;
import static ca.rttv.malum.registry.MalumRegistry.SPIRIT_INFUSION_SERIALIZER;

public class SpiritInfusionRecipe implements Recipe<Inventory> {
    public final Identifier id;
    public final String group;
    public final IngredientWithCount input;
    public final ItemStack output;
    public final IngredientWithCount extraItems;
    public final IngredientWithCount spirits;

    public SpiritInfusionRecipe(Identifier id, String group, IngredientWithCount input, ItemStack output, IngredientWithCount extraItems, IngredientWithCount spirits) {
        this.id = id;
        this.group = group;
        this.input = input;
        this.output = output;
        this.extraItems = extraItems;
        this.spirits = spirits;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.input.test(inventory.getStack(0));
    }

    public static SpiritInfusionRecipe getRecipe(World world, Predicate<SpiritInfusionRecipe> predicate) {
        List<SpiritInfusionRecipe> recipes = getRecipes(world);
        System.out.println(recipes.size());
        for (SpiritInfusionRecipe recipe : recipes) {
            if (predicate.test(recipe)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<SpiritInfusionRecipe> getRecipes(World world) {
        return world.getRecipeManager().listAllOfType(SPIRIT_INFUSION);
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(this.input.asIngredient());
        return defaultedList;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SPIRIT_INFUSION_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return SPIRIT_INFUSION;
    }

    public static class Serializer<T extends SpiritInfusionRecipe> implements RecipeSerializer<T> {
        final RecipeFactory<T> recipeFactory;

        public Serializer(RecipeFactory<T> recipeFactory) {
            this.recipeFactory = recipeFactory;
        }

        public T read(Identifier id, JsonObject jsonObject) {
            String group = JsonHelper.getString(jsonObject, "group", "");
            IngredientWithCount input = IngredientWithCount.fromJson(JsonHelper.getObject(jsonObject, "output"));
            ItemStack output = IngredientWithCount.fromJson(JsonHelper.getObject(jsonObject, "input")).getMatchingStacks()[0];
            IngredientWithCount extraItems = IngredientWithCount.fromJson(JsonHelper.getArray(jsonObject, "extra_items"));
            IngredientWithCount spirits = IngredientWithCount.fromJson(JsonHelper.getArray(jsonObject, "spirits"));
            return this.recipeFactory.create(id, group, input, output, extraItems, spirits);
        }

        public T read(Identifier identifier, PacketByteBuf buf) {
            String group = buf.readString();
            IngredientWithCount input = IngredientWithCount.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            IngredientWithCount extraItems = IngredientWithCount.fromPacket(buf);
            IngredientWithCount spirits = IngredientWithCount.fromPacket(buf);
            return this.recipeFactory.create(identifier, group, input, output, extraItems, spirits);
        }

        public void write(PacketByteBuf buf, T spiritInfusionRecipe) {
            buf.writeString(spiritInfusionRecipe.group);
            spiritInfusionRecipe.input.write(buf);
            buf.writeItemStack(spiritInfusionRecipe.output);
            spiritInfusionRecipe.extraItems.write(buf);
            spiritInfusionRecipe.spirits.write(buf);
        }

        public interface RecipeFactory<T extends SpiritInfusionRecipe> {
            T create(Identifier id, String group, IngredientWithCount input, ItemStack output, IngredientWithCount extraItems, IngredientWithCount spirits);
        }
    }
}
