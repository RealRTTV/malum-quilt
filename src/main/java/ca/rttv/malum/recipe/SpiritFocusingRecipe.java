package ca.rttv.malum.recipe;

import ca.rttv.malum.block.entity.SpiritCrucibleBlockEntity;
import ca.rttv.malum.item.SpiritItem;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.recipe.api.serializer.QuiltRecipeSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static ca.rttv.malum.registry.MalumRecipeSerializerRegistry.SPIRIT_FOCUSING_SERIALIZER;
import static ca.rttv.malum.registry.MalumRecipeTypeRegistry.SPIRIT_FOCUSING;

public record SpiritFocusingRecipe(Identifier id, String group, int time, int durabilityCost, Ingredient input, ItemStack output, IngredientWithCount spirits) implements Recipe<Inventory> {
    @Nullable
    public static SpiritFocusingRecipe getRecipe(World world, Predicate<SpiritFocusingRecipe> predicate) {
        List<SpiritFocusingRecipe> recipes = getRecipes(world);
        for (SpiritFocusingRecipe recipe : recipes) {
            if (predicate.test(recipe)) {
                return recipe;
            }
        }
        return null;
    }

    public static SpiritFocusingRecipe getRecipe(World world, ItemStack stack, List<ItemStack> spirits) {
        return getRecipe(world, recipe -> recipe.input.test(stack) && recipe.doSpiritsMatch(spirits));
    }

    private boolean doSpiritsMatch(List<ItemStack> spirits) {
        if (Arrays.stream(spirits().getEntries()).anyMatch(entry -> entry instanceof IngredientWithCount.TagEntry || !(((IngredientWithCount.StackEntry) entry).getStack().getItem() instanceof SpiritItem))) {
            throw new IllegalStateException("spirits cannot hold tags or non-spirit items");
        }
        List<ItemStack> newSpirits = new ArrayList<>(spirits);
        for (int i = 0; i < newSpirits.size(); i++) {
            if (newSpirits.get(i).isEmpty()) {
                newSpirits.remove(i--);
            }
        }
        for (IngredientWithCount.Entry entry : spirits().getEntries()) {
            IngredientWithCount.StackEntry stackEntry = (IngredientWithCount.StackEntry) entry;
            boolean foundMatch = false;
            for (int i = 0; i < newSpirits.size(); i++) {
                if (stackEntry.isValidItem(newSpirits.get(i))) {
                    foundMatch = true;
                    newSpirits.remove(i);
                    break;
                }
            }
            if (!foundMatch) return false;
        }
        return true;
    }

    public static List<SpiritFocusingRecipe> getRecipes(World world) {
        return world.getRecipeManager().listAllOfType(SPIRIT_FOCUSING);
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.input.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        if (!(inventory instanceof SpiritCrucibleBlockEntity blockEntity)) {
            throw new IllegalStateException("Parameter 'inventory' must be an instanceof SpiritCrucibleBlockEntity");
        }

        if (blockEntity.focusingRecipe == null) {
            throw new IllegalStateException("Spirit Crucible recipe must not be null");
        }

        if (blockEntity.getWorld() == null) {
            throw new IllegalStateException("Spirit Crucible world must not be null");
        }

        // spirits
        for (int[] i = {0}; i[0] < blockEntity.spiritSlots.size(); i[0]++) {
            if (blockEntity.spiritSlots.get(i[0]).isEmpty()) break;
            blockEntity.spiritSlots.get(i[0]).decrement(Arrays.stream(spirits.getEntries()).filter(spirit -> spirit.isValidItem(blockEntity.spiritSlots.get(i[0]))).findFirst().orElseGet(() -> new IngredientWithCount.StackEntry(ItemStack.EMPTY)).getCount());
        }

        BlockPos pos = blockEntity.getPos();
        ItemStack stack = output.copy(); // this has to be copied since these recipes are stored statically-ish
        ItemScatterer.spawn(blockEntity.getWorld(), pos.up(), DefaultedList.ofSize(1, stack));
        return stack;
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
        return SPIRIT_FOCUSING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return SPIRIT_FOCUSING;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(input);
        return defaultedList;
    }

    @Override
    public String getGroup() {
        return group;
    }

    public record Serializer<T extends SpiritFocusingRecipe>(SpiritFocusingRecipe.Serializer.RecipeFactory<T> recipeFactory) implements RecipeSerializer<T>, QuiltRecipeSerializer<T> {
        @Override
        public T read(Identifier id, JsonObject json) {
            String group = JsonHelper.getString(json, "group", "");
            int time = json.get("time").getAsInt();
            int durabilityCost = json.get("durabilityCost").getAsInt();
            Ingredient input = Ingredient.fromJson(json.get("input"));
            ItemStack output = IngredientWithCount.fromJson(json.get("output")).getMatchingStacks()[0];
            IngredientWithCount spirits = IngredientWithCount.fromJson(json.get("spirits"));
            return recipeFactory.create(id, group, time, durabilityCost, input, output, spirits);
        }

        @Override
        public T read(Identifier id, PacketByteBuf buf) {
            return recipeFactory.create(id, buf.readString(), buf.readInt(), buf.readInt(), Ingredient.fromPacket(buf), buf.readItemStack(), IngredientWithCount.fromPacket(buf));
        }

        @Override
        public void write(PacketByteBuf buf, T recipe) {
            buf.writeString(recipe.group());
            buf.writeInt(recipe.time());
            buf.writeInt(recipe.durabilityCost());
            recipe.input().write(buf);
            buf.writeItemStack(recipe.output());
            recipe.spirits().write(buf);
        }

        @Override
        public JsonObject toJson(T recipe) {
            JsonObject json = new JsonObject();

            json.addProperty("type", "malum:spirit_focusing");

            if (!recipe.group().equals("")) {
                json.addProperty("group", recipe.group());
            }

            json.addProperty("time", recipe.time());

            json.addProperty("durabilityCost", recipe.durabilityCost());

            json.add("input", recipe.input().entries[0].toJson());

            JsonObject output = new JsonObject();
            output.addProperty("item", Registry.ITEM.getId(recipe.output().getItem()).toString());
            output.addProperty("count", recipe.output().getCount());
            json.add("output", output);

            json.add("spirits", recipe.spirits().toJson());

            return json;
        }

        public interface RecipeFactory<T> {
            T create(Identifier id, String group, int time, int durabilityCost, Ingredient input, ItemStack output, IngredientWithCount spirits);
        }
    }
}
