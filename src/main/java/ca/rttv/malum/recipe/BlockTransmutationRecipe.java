package ca.rttv.malum.recipe;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
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
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.recipe.api.serializer.QuiltRecipeSerializer;

import java.util.List;
import java.util.function.Predicate;

import static ca.rttv.malum.registry.MalumRecipeSerializerRegistry.BLOCK_TRANSMUTATION_SERIALIZER;
import static ca.rttv.malum.registry.MalumRecipeTypeRegistry.BLOCK_TRANSMUTATION;

public record BlockTransmutationRecipe(Identifier id, String group, Ingredient input, ItemStack output) implements Recipe<Inventory> {

    @Nullable
    public static BlockTransmutationRecipe getRecipe(Block block, World world) {
        return getRecipe(world, recipe -> recipe.input.test(block.asItem().getDefaultStack()));
    }

    @Nullable
    public static BlockTransmutationRecipe getRecipe(World world, Predicate<BlockTransmutationRecipe> predicate) {
        List<BlockTransmutationRecipe> recipes = getRecipes(world);
        for (BlockTransmutationRecipe recipe : recipes) {
            if (predicate.test(recipe)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<BlockTransmutationRecipe> getRecipes(World world) {
        return world.getRecipeManager().listAllOfType(BLOCK_TRANSMUTATION);
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.input.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(@Nullable Inventory inventory) {
        return output;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return DefaultedList.ofSize(1, input);
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BLOCK_TRANSMUTATION_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return BLOCK_TRANSMUTATION;
    }

    public record Serializer<T extends BlockTransmutationRecipe>(BlockTransmutationRecipe.Serializer.RecipeFactory<T> recipeFactory) implements RecipeSerializer<T>, QuiltRecipeSerializer<T> {

        @Override
        public T read(Identifier id, JsonObject json) {
            String group = JsonHelper.getString(json, "group", "");
            Ingredient input = Ingredient.ofItems(JsonHelper.getItem(json, "input"));
            ItemStack output = JsonHelper.getItem(json, "output").getDefaultStack();
            return recipeFactory.create(id, group, input, output);
        }

        @Override
        public T read(Identifier id, PacketByteBuf buf) {
            String group = buf.readString();
            Ingredient input = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            return recipeFactory.create(id, group, input, output);
        }

        @Override
        public void write(PacketByteBuf buf, T recipe) {
            buf.writeString(recipe.group());
            recipe.input().write(buf);
            buf.writeItemStack(recipe.output());
        }

        @Override
        public JsonObject toJson(T recipe) {
            JsonObject json = new JsonObject();

            json.addProperty("type", "malum:block_transmutation");

            if (!recipe.group().equals("")) {
                json.addProperty("group", recipe.group());
            }

            json.addProperty("input", Registry.ITEM.getId(recipe.input().getMatchingStacks()[0].getItem()).toString());

            json.addProperty("output", Registry.ITEM.getId(recipe.output().getItem()).toString());

            return json;
        }

        public interface RecipeFactory<T> {
            T create(Identifier id, String group, Ingredient input, ItemStack output);
        }
    }
}
