package ca.rttv.malum;

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

public class SpiritInfusionRecipe implements Recipe<Inventory> {
    public final Identifier id;
    public final String group;
    public final Ingredient input;
    public final ItemStack output;
    public final Ingredient extraItems;
    public final Ingredient spirits;
    public final RecipeSerializer<?> serializer;

    public SpiritInfusionRecipe(Identifier id, String group, Ingredient input, ItemStack output, Ingredient extraItems, Ingredient spirits, RecipeSerializer<?> serializer) {
        this.id = id;
        this.group = group;
        this.input = input;
        this.output = output;
        this.extraItems = extraItems;
        this.spirits = spirits;
        this.serializer = serializer;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.input.test(inventory.getStack(0));
    }

    public static SpiritInfusionRecipe getRecipe(World world, Predicate<SpiritInfusionRecipe> predicate) {
        List<SpiritInfusionRecipe> recipes = getRecipes(world);
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
        defaultedList.add(this.input);
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
        return this.serializer;
    }

    @Override
    public RecipeType<?> getType() {
        return SPIRIT_INFUSION;
    }

    public static class Serializer<T extends SpiritInfusionRecipe> implements RecipeSerializer<T> {
        final RecipeFactory<T> recipeFactory;

        protected Serializer(RecipeFactory<T> recipeFactory) {
            this.recipeFactory = recipeFactory;
        }

        public T read(Identifier id, JsonObject jsonObject) {
            String group = JsonHelper.getString(jsonObject, "group", "");
            Ingredient input = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "output"));
            ItemStack output = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "input")).getMatchingStacks()[0];
            Ingredient extraItems = Ingredient.fromJson(JsonHelper.getArray(jsonObject, "extra_items"));
            Ingredient spirits = Ingredient.fromJson(JsonHelper.getArray(jsonObject, "spirits"));
            return this.recipeFactory.create(id, group, input, output, extraItems, spirits);
        }

        public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
            String group = packetByteBuf.readString();
            Ingredient input = Ingredient.fromPacket(packetByteBuf);
            ItemStack output = packetByteBuf.readItemStack();
            Ingredient extraItems = Ingredient.fromPacket(packetByteBuf);
            Ingredient spirits = Ingredient.fromPacket(packetByteBuf);
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
            T create(Identifier id, String group, Ingredient input, ItemStack output, Ingredient extraItems, Ingredient spirits);
        }
    }
}
