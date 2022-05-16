package ca.rttv.malum.recipe;

import ca.rttv.malum.item.SpiritItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import static ca.rttv.malum.registry.MalumRecipeSerializerRegistry.SPIRIT_REPAIR_SERIALIZER;
import static ca.rttv.malum.registry.MalumRecipeTypeRegistry.SPIRIT_REPAIR;

public record SpiritRepairRecipe(Identifier id, String group, String inputLookup, double durabilityPercentage, Ingredient input, IngredientWithCount spirits, IngredientWithCount repairMaterial) implements Recipe<Inventory> {
    @Nullable
    public static SpiritRepairRecipe getRecipe(World world, Predicate<SpiritRepairRecipe> predicate) {
        List<SpiritRepairRecipe> recipes = getRecipes(world);
        for (SpiritRepairRecipe recipe : recipes) {
            if (predicate.test(recipe)) {
                return recipe;
            }
        }
        return null;
    }

    public static SpiritRepairRecipe getRecipe(World world, ItemStack stack, List<ItemStack> spirits, List<ItemStack> tables) {
        return getRecipe(world, recipe -> recipe.input.test(stack) && recipe.doSpiritsMatch(spirits));
    }
    
    private boolean doSpiritsMatch(List<ItemStack> spirits) {
        if (Arrays.stream(spirits().getEntries()).anyMatch(entry -> entry instanceof IngredientWithCount.TagEntry || !(((IngredientWithCount.StackEntry) entry).getStack().getItem() instanceof SpiritItem))) {
            throw new IllegalStateException("spirits cannot hold tags or non-spirit items");
        }
        List<ItemStack> newSpirits = new ArrayList<>(spirits);
        for (IngredientWithCount.Entry entry : spirits().getEntries()) {
            IngredientWithCount.StackEntry stackEntry = (IngredientWithCount.StackEntry) entry;
            boolean foundMatch = false;
            for (int i = 0; i < newSpirits.size(); i++) {
                if (stackEntry.isValidItem(spirits.get(i))) {
                    foundMatch = true;
                    newSpirits.remove(i);
                    break;
                }
            }
            if (!foundMatch) return false;
        }
        return true;
    }

    public static List<SpiritRepairRecipe> getRecipes(World world) {
        return world.getRecipeManager().listAllOfType(SPIRIT_REPAIR);
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return input.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return null; // todo
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return input.getMatchingStacks()[0];
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SPIRIT_REPAIR_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return SPIRIT_REPAIR;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(input);
        return defaultedList;
    }

    public record Serializer<T extends SpiritRepairRecipe>(SpiritRepairRecipe.Serializer.RecipeFactory<T> recipeFactory) implements RecipeSerializer<T> {
        @Override
        public T read(Identifier id, JsonObject json) {
            System.out.println(id);
            String group = JsonHelper.getString(json, "group", "");
            String inputLookup = json.get("inputLookup").getAsString();
            double durabilityPercentage = json.get("durabilityPercentage").getAsDouble();
            Ingredient inputs = SpiritRepairRecipe.parseItems(json.getAsJsonArray("inputs"));
            IngredientWithCount spirits = IngredientWithCount.fromJson(json.getAsJsonArray("spirits"));
            IngredientWithCount repairMaterial = IngredientWithCount.fromJson(json.getAsJsonObject("repairMaterial"));
            return recipeFactory.create(id, group, inputLookup, durabilityPercentage, inputs, spirits, repairMaterial);
        }

        @Override
        public T read(Identifier id, PacketByteBuf buf) {
            return recipeFactory.create(id, buf.readString(), buf.readString(), buf.readDouble(), Ingredient.fromPacket(buf), IngredientWithCount.fromPacket(buf), IngredientWithCount.fromPacket(buf));
        }

        @Override
        public void write(PacketByteBuf buf, T recipe) {
            buf.writeString(recipe.group());
            buf.writeString(recipe.inputLookup());
            buf.writeDouble(recipe.durabilityPercentage());
            recipe.input().write(buf);
            recipe.spirits().write(buf);
            recipe.repairMaterial().write(buf);
        }

        public interface RecipeFactory<T> {
            T create(Identifier id, String group, String inputLookup, double durabilityPercentage, Ingredient input, IngredientWithCount spirits, IngredientWithCount repairMaterial);
        }
    }

    private static Ingredient parseItems(JsonArray input) {
        return Ingredient.ofEntries(StreamSupport.stream(input.spliterator(), false).map(JsonElement::getAsString).map(string -> string.startsWith("#") ? new Ingredient.TagEntry(TagKey.of(Registry.ITEM_KEY, new Identifier(string.substring(1)))) : new Ingredient.StackEntry(new ItemStack(Registry.ITEM.get(new Identifier(string))))));
    }
}
