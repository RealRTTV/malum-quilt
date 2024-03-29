package ca.rttv.malum.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.CraftingCategory;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.registry.Registry;
import org.quiltmc.qsl.recipe.api.serializer.QuiltRecipeSerializer;

import java.util.Map;

import static ca.rttv.malum.registry.MalumRecipeSerializerRegistry.SAVED_NBT_RECIPE_SERIALIZER;

public class SavedNbtRecipe extends ShapedRecipe {
    private final Item savedItem;

    public SavedNbtRecipe(Identifier identifier, String string, int i, int j, DefaultedList<Ingredient> defaultedList, ItemStack itemStack, Item savedItem) {
        super(identifier, string, CraftingCategory.MISC, i, j, defaultedList, itemStack);
        this.savedItem = savedItem;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        ItemStack savedItem = ItemStack.EMPTY;

        for(int i = 0; i < inventory.size(); ++i) {
            ItemStack indexStack = inventory.getStack(i);
            if (indexStack.getItem() == this.savedItem) {
                savedItem = indexStack;
                break;
            }
        }

        ItemStack output = this.getOutput().copy();
        output.setNbt(savedItem.getNbt());
        return savedItem.isEmpty() ? ItemStack.EMPTY : output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SAVED_NBT_RECIPE_SERIALIZER;
    }

    public static class Serializer implements RecipeSerializer<SavedNbtRecipe>, QuiltRecipeSerializer<SavedNbtRecipe> {
        public SavedNbtRecipe read(Identifier identifier, JsonObject json) {
            String string = JsonHelper.getString(json, "group", "");
            Map<String, Ingredient> map = ShapedRecipe.readSymbols(JsonHelper.getObject(json, "key"));
            String[] strings = ShapedRecipe.removePadding(ShapedRecipe.getPattern(JsonHelper.getArray(json, "pattern")));
            int i = strings[0].length();
            int j = strings.length;
            DefaultedList<Ingredient> defaultedList = ShapedRecipe.createPatternMatrix(strings, map, i, j);
            ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
            Item savedItem = JsonHelper.getItem(json.get("nbtCarry").getAsJsonObject(), "item");
            return new SavedNbtRecipe(identifier, string, i, j, defaultedList, itemStack, savedItem);
        }

        public SavedNbtRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            int i = packetByteBuf.readVarInt();
            int j = packetByteBuf.readVarInt();
            String string = packetByteBuf.readString();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i * j, Ingredient.EMPTY);

            defaultedList.replaceAll(ignored -> Ingredient.fromPacket(packetByteBuf));

            ItemStack itemStack = packetByteBuf.readItemStack();
            Item savedItem = Registries.ITEM.get(new Identifier(packetByteBuf.readString()));
            return new SavedNbtRecipe(identifier, string, i, j, defaultedList, itemStack, savedItem);
        }

        public void write(PacketByteBuf packetByteBuf, SavedNbtRecipe savedNbtRecipe) {
            packetByteBuf.writeVarInt(savedNbtRecipe.getWidth());
            packetByteBuf.writeVarInt(savedNbtRecipe.getHeight());
            packetByteBuf.writeString(savedNbtRecipe.getGroup());

            for(Ingredient ingredient : savedNbtRecipe.getIngredients()) {
                ingredient.write(packetByteBuf);
            }

            packetByteBuf.writeItemStack(savedNbtRecipe.getOutput());
            packetByteBuf.writeString(Registries.ITEM.getId(savedNbtRecipe.savedItem).toString());
        }

        @Override
        public JsonObject toJson(SavedNbtRecipe recipe) {
            JsonObject json = new JsonObject();
            json.add("Unable to deserialize malum:saved_nbt_recipe to a json, Reason: Incapable of converting SavedNbtRecipe object to JsonObject", null);
            return json;
        }
    }
}
