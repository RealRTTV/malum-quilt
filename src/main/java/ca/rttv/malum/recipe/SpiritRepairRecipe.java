package ca.rttv.malum.recipe;

import ca.rttv.malum.block.entity.SpiritCrucibleBlockEntity;
import ca.rttv.malum.block.entity.TabletBlockEntity;
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
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static ca.rttv.malum.registry.MalumRecipeSerializerRegistry.SPIRIT_REPAIR_SERIALIZER;
import static ca.rttv.malum.registry.MalumRecipeTypeRegistry.SPIRIT_REPAIR;

public record SpiritRepairRecipe(Identifier id, String group, String itemIdRegex, String modIdRegex, double durabilityPercentage, Ingredient input, IngredientWithCount spirits, IngredientWithCount repairMaterial) implements Recipe<Inventory> {
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

    public static SpiritRepairRecipe getRecipe(World world, ItemStack stack, List<ItemStack> spirits, List<ItemStack> tablets) {
        return getRecipe(world, recipe -> recipe.input.test(stack)
                && recipe.doSpiritsMatch(spirits)
                && recipe.doTabletsMatch(tablets));
    }

    private boolean doTabletsMatch(List<ItemStack> tablets) {
        for (IngredientWithCount.Entry entry : repairMaterial.getEntries()) {
            boolean found = false;
            for (ItemStack tablet : tablets) {
                if (entry.isValidItem(tablet)) {
                    found = true;
                    // we don't remove the entry since it can cause errors with tags accepting the "wrong" item, thus it can be used multiple times
                    // todo: make a config option for the removal
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    private boolean doSpiritsMatch(List<ItemStack> spirits) {
        if (Arrays.stream(spirits().getEntries())
                                   .anyMatch(entry -> entry.getStacks()
                                                           .stream()
                                                           .anyMatch(stack -> !(stack.getItem() instanceof SpiritItem)))) {
            throw new IllegalStateException("spirits cannot hold non-spirit items (or tags containing non spirit items)");
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

    public static List<SpiritRepairRecipe> getRecipes(World world) {
        return world.getRecipeManager().listAllOfType(SPIRIT_REPAIR);
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return input.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        if (!(inventory instanceof SpiritCrucibleBlockEntity blockEntity)) {
            throw new IllegalStateException("Parameter 'inventory' must be an instanceof SpiritCrucibleBlockEntity");
        }

        if (blockEntity.repairRecipe == null) {
            throw new IllegalStateException("Spirit Crucible recipe must not be null");
        }

        if (blockEntity.getWorld() == null) {
            throw new IllegalStateException("Spirit Crucible world must not be null");
        }

        ItemStack heldItem = blockEntity.getHeldItem();

        // spirits
        for (int[] i = {0}; i[0] < blockEntity.spiritSlots.size(); i[0]++) {
            if (blockEntity.spiritSlots.get(i[0]).isEmpty()) break;
            blockEntity.spiritSlots.get(i[0]).decrement(Arrays.stream(spirits.getEntries())
                                                              .filter(spirit -> spirit.isValidItem(blockEntity.spiritSlots.get(i[0])))
                                                              .findFirst()
                                                              .orElse(new IngredientWithCount.StackEntry(ItemStack.EMPTY))
                                             .getCount());
        }

        BlockPos pos = blockEntity.getPos();

        IngredientWithCount.Entry[] entries = Arrays.stream(blockEntity.repairRecipe.repairMaterial.getEntries())
                                                    .map(entry -> entry instanceof IngredientWithCount.StackEntry stackEntry ? new IngredientWithCount.StackEntry(stackEntry.stack().copy()) : new IngredientWithCount.TagEntry(((IngredientWithCount.TagEntry) entry).tag(), ((IngredientWithCount.TagEntry) entry).count()))
                                                    .toArray(IngredientWithCount.Entry[]::new);

        BlockPos.iterate(pos.getX() - 4, pos.getY() - 2, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 2, pos.getZ() + 4).forEach(reagentPos -> {
            if (blockEntity.getWorld().getBlockEntity(reagentPos) instanceof TabletBlockEntity tabletBlock) {
                for (IngredientWithCount.Entry entry : entries) {
                    if (entry.getStacks().stream()
                                         .anyMatch(stack -> stack.getItem() == tabletBlock.getHeldItem().getItem())) {
                        int amountToRemove = Math.min(entry.getCount(), tabletBlock.getHeldItem().getCount());
                        entry.decrement(amountToRemove);
                        tabletBlock.getHeldItem().decrement(amountToRemove);
                        tabletBlock.notifyListeners();
                        break;
                    }
                }
            }
        });

        heldItem = this.getOutput(heldItem);
        if (!Registry.ITEM.getId(heldItem.getItem()).getPath().endsWith("_impetus")) {
            heldItem.setDamage(Math.max(0, blockEntity.getHeldItem().getDamage() - (int) (blockEntity.getHeldItem().getMaxDamage() * durabilityPercentage)));
        }

        blockEntity.setStack(0, ItemStack.EMPTY);
        ItemScatterer.spawn(blockEntity.getWorld(), pos.up(), DefaultedList.ofSize(1, heldItem));
        return blockEntity.getHeldItem();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return this.getOutput(input.getMatchingStacks()[0]);
    }

    public ItemStack getOutput(ItemStack input) {
        String id = Registry.ITEM.getId(input.getItem()).toString();
        if (id.endsWith("_impetus")) {
            return Registry.ITEM.get(new Identifier(id.replaceAll("cracked_", ""))).getDefaultStack();
        } else {
            return input.copy();
        }
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

    public record Serializer<T extends SpiritRepairRecipe>(SpiritRepairRecipe.Serializer.RecipeFactory<T> recipeFactory) implements RecipeSerializer<T>, QuiltRecipeSerializer<T> {
        @Override
        public T read(Identifier id, JsonObject json) {
            String group = JsonHelper.getString(json, "group", "");
            String itemIdRegex = json.get("itemIdRegex").getAsString();
            String modIdRegex = json.get("modIdRegex").getAsString();
            double durabilityPercentage = json.get("durabilityPercentage").getAsDouble();
            Ingredient inputs = SpiritRepairRecipe.parseItems(json.getAsJsonArray("inputs"), itemIdRegex, modIdRegex);
            IngredientWithCount spirits = IngredientWithCount.fromJson(json.getAsJsonArray("spirits"));
            IngredientWithCount repairMaterial = IngredientWithCount.fromJson(json.getAsJsonObject("repairMaterial"));
            return recipeFactory.create(id, group, itemIdRegex, modIdRegex, durabilityPercentage, inputs, spirits, repairMaterial);
        }

        @Override
        public T read(Identifier id, PacketByteBuf buf) {
            return recipeFactory.create(id, buf.readString(), buf.readString(), buf.readString(), buf.readDouble(), Ingredient.fromPacket(buf), IngredientWithCount.fromPacket(buf), IngredientWithCount.fromPacket(buf));
        }

        @Override
        public void write(PacketByteBuf buf, T recipe) {
            buf.writeString(recipe.group());
            buf.writeString(recipe.itemIdRegex());
            buf.writeString(recipe.modIdRegex());
            buf.writeDouble(recipe.durabilityPercentage());
            recipe.input().write(buf);
            recipe.spirits().write(buf);
            recipe.repairMaterial().write(buf);
        }

        @Override
        public JsonObject toJson(T recipe) {
            JsonObject json = new JsonObject();

            json.addProperty("type", "malum:spirit_repair");

            if (!recipe.group().equals("")) {
                json.addProperty("group", recipe.group());
            }

            json.addProperty("itemIdRegex", recipe.itemIdRegex());

            json.addProperty("modIdRegex", recipe.modIdRegex());

            json.addProperty("durabilityPercentage", recipe.durabilityPercentage());

            json.add("inputs", recipe.input().toJson());

            json.add("repairMaterial", recipe.repairMaterial().toJson());

            json.add("spirits", recipe.spirits().toJson());

            return json;
        }

        public interface RecipeFactory<T> {
            T create(Identifier id, String group, String itemIdRegex, String modIdRegex, double durabilityPercentage, Ingredient input, IngredientWithCount spirits, IngredientWithCount repairMaterial);
        }
    }

    private static Ingredient parseItems(JsonArray input, String itemIdRegex, String modIdRegex) {
        return Ingredient.ofEntries(
            Stream.concat(
                  StreamSupport.stream(input.spliterator(), false)
                               .map(JsonElement::getAsString)
                               .map(string -> string.startsWith("#")
                                            ? new Ingredient.TagEntry(TagKey.of(Registry.ITEM_KEY, new Identifier(string.substring(1))))
                                            : new Ingredient.StackEntry(Registry.ITEM.get(new Identifier(string)).getDefaultStack())
                               ),
                  itemIdRegex.equals("") && modIdRegex.equals("")
                               ? Stream.empty()
                               : Registry.ITEM.stream() // expensive, but there's no better way to do it
                                              .filter(item -> item.isDamageable() && Registry.ITEM.getId(item).getPath().matches(itemIdRegex) && Registry.ITEM.getId(item).getNamespace().matches(modIdRegex))
                                              .map(item -> new Ingredient.StackEntry(item.getDefaultStack()))
            ) // if I care about duplicate entries from the itemIdRegex and entries[] then make an implementation of hashCode on tag entry and stack entry and do a collect(Collectors.toSet()).stream() or something
        );
    }
}
