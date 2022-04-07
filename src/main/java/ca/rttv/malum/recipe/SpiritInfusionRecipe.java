package ca.rttv.malum.recipe;

import ca.rttv.malum.block.entity.SpiritAltarBlockEntity;
import ca.rttv.malum.item.spirit.MalumSpiritItem;
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
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Nullable
    public static SpiritInfusionRecipe getRecipe(World world, Predicate<SpiritInfusionRecipe> predicate) {
        List<SpiritInfusionRecipe> recipes = getRecipes(world);
        for (SpiritInfusionRecipe recipe : recipes) {
            if (predicate.test(recipe)) {
                return recipe;
            }
        }
        return null;
    }

    public static SpiritInfusionRecipe getRecipe(World world, ItemStack stack, List<ItemStack> spirits) {
        return getRecipe(world, recipe -> recipe.doesInputMatch(stack) && recipe.doSpiritsMatch(spirits));
    }

    public boolean doSpiritsMatch(List<ItemStack> spirits) {
        if (Arrays.stream(this.spirits.getEntries()).anyMatch(entry -> entry instanceof IngredientWithCount.TagEntry || !(((IngredientWithCount.StackEntry) entry).getStack().getItem() instanceof MalumSpiritItem))) {
            throw new IllegalStateException("spirits cannot hold tags or non-spirit items");
        }
        List<ItemStack> newSpirits = new ArrayList<>(spirits);
        for (IngredientWithCount.Entry entry : this.spirits.getEntries()){
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

    private boolean doesInputMatch(ItemStack stack) {
        return this.input.getEntries()[0].isValidItem(stack);
    }

    public static List<SpiritInfusionRecipe> getRecipes(World world) {
        return world.getRecipeManager().listAllOfType(SPIRIT_INFUSION);
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        if (inventory instanceof SpiritAltarBlockEntity blockEntity) {
            for (int i = 0; i < blockEntity.spiritSlots.size(); i++) {
                if (blockEntity.spiritSlots.get(i).isEmpty()) break;
                blockEntity.spiritSlots.get(i).decrement(this.spirits.getEntries()[i].getCount());
            }
            blockEntity.getHeldItem().decrement(this.input.getEntries()[0].getCount());
            ItemScatterer.spawn(blockEntity.getWorld(), blockEntity.getPos(), DefaultedList.ofSize(1, this.output.copy())); // this has to be copied since these recipes are stored statically iirc
            return this.output.copy();
        } else {
            throw new IllegalStateException("Parameter inventory must be an instanceof SpiritAltarBlockEntity");
        }
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

    public record Serializer<T extends SpiritInfusionRecipe>(
            SpiritInfusionRecipe.Serializer.RecipeFactory<T> recipeFactory) implements RecipeSerializer<T> {

        public T read(Identifier id, JsonObject jsonObject) {
            String group = JsonHelper.getString(jsonObject, "group", "");
            IngredientWithCount input = IngredientWithCount.fromJson(JsonHelper.getObject(jsonObject, "input"));
            ItemStack output = IngredientWithCount.fromJson(JsonHelper.getObject(jsonObject, "output")).getMatchingStacks()[0];
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
