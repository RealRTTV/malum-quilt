package ca.rttv.malum.recipe;

import ca.rttv.malum.block.entity.AbstractItemDisplayBlockEntity;
import ca.rttv.malum.block.entity.SpiritAltarBlockEntity;
import ca.rttv.malum.item.*;
import ca.rttv.malum.util.helper.NbtHelper;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static ca.rttv.malum.registry.MalumRegistry.SPIRIT_INFUSION;
import static ca.rttv.malum.registry.MalumRegistry.SPIRIT_INFUSION_SERIALIZER;

public record SpiritInfusionRecipe(Identifier id, String group,
                                   IngredientWithCount input, ItemStack output,
                                   IngredientWithCount extraItems,
                                   IngredientWithCount spirits) implements Recipe<Inventory> {

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
        for (IngredientWithCount.Entry entry : this.spirits.getEntries()) {
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
        return input.getEntries()[0].isValidItem(stack);
    }

    public static List<SpiritInfusionRecipe> getRecipes(World world) {
        return world.getRecipeManager().listAllOfType(SPIRIT_INFUSION);
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        if (inventory instanceof SpiritAltarBlockEntity blockEntity) {
            if (blockEntity.recipe != null) {
                for (int[] i = {0}; i[0] < blockEntity.spiritSlots.size(); i[0]++) {
                    if (blockEntity.spiritSlots.get(i[0]).isEmpty()) break;
                    blockEntity.spiritSlots.get(i[0]).decrement(Arrays.stream(spirits.getEntries()).filter(spirit -> spirit.isValidItem(blockEntity.spiritSlots.get(i[0]))).findFirst().orElseGet(() -> new IngredientWithCount.StackEntry(ItemStack.EMPTY)).getCount());
                }
                blockEntity.getHeldItem().decrement(input.getEntries()[0].getCount());
                BlockPos pos = blockEntity.getPos();
                IngredientWithCount.Entry[] entries = Arrays.stream(blockEntity.recipe.extraItems.getEntries()).map(entry -> entry instanceof IngredientWithCount.StackEntry stackEntry ? new IngredientWithCount.StackEntry(stackEntry.stack().copy()) : new IngredientWithCount.TagEntry(((IngredientWithCount.TagEntry) entry).tag(), ((IngredientWithCount.TagEntry) entry).count())).toArray(IngredientWithCount.Entry[]::new);
                BlockPos.iterate(pos.getX() - 4, pos.getY() - 2, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 2, pos.getZ() + 4).forEach(reagentPos -> {
                    //noinspection ConstantConditions
                    if (blockEntity.getWorld().getBlockEntity(reagentPos) instanceof AbstractItemDisplayBlockEntity displayBlock) {
                        for (IngredientWithCount.Entry entry : entries) {
                            if (entry.getStacks().stream().anyMatch(stack -> stack.getItem() == displayBlock.getHeldItem().getItem())) {
                                int amountToRemove = Math.min(entry.getCount(), displayBlock.getHeldItem().getCount());
                                entry.decrement(amountToRemove);
                                displayBlock.getHeldItem().decrement(amountToRemove);
                                displayBlock.notifyListeners();
                                break;
                            }
                        }
                    }
                });
                ItemStack stack = output.copy(); // this has to be copied since these recipes are stored statically-ish iirc
                if (stack.getItem() instanceof EtherBlockItem || stack.getItem() instanceof EtherWallStandingBlockItem || stack.getItem() instanceof IridescentEtherBlockItem || stack.getItem() instanceof IridescentEtherWallStandingBlockItem) {
                    NbtCompound nbt = new NbtCompound();
                    NbtCompound displayNbt = new NbtCompound();
                    displayNbt.putInt("FirstColor", NbtHelper.getOrDefaultInt(nbtTag -> NbtHelper.getOrThrowInt(nbtTag.getCompound("display"), "FirstColor"), 15712278, blockEntity.getHeldItem().getNbt()));
                    displayNbt.putInt("SecondColor", NbtHelper.getOrDefaultInt(nbtTag -> NbtHelper.getOrThrowInt(nbtTag.getCompound("display"), "SecondColor"), 4607909, blockEntity.getHeldItem().getNbt()));
                    nbt.put("display", displayNbt);
                    stack.setNbt(nbt);
                }
                ItemScatterer.spawn(blockEntity.getWorld(), pos.up(), DefaultedList.ofSize(1, stack));
                return stack;
            } else {
                throw new IllegalStateException("Spirit Altar recipe must not be null");
            }
        } else {
            throw new IllegalStateException("Parameter 'inventory' must be an instanceof SpiritAltarBlockEntity");
        }
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(input.asIngredient());
        return defaultedList;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SPIRIT_INFUSION_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return SPIRIT_INFUSION;
    }

    public record Serializer<T extends SpiritInfusionRecipe>(SpiritInfusionRecipe.Serializer.RecipeFactory<T> recipeFactory) implements RecipeSerializer<T> {

        public T read(Identifier id, JsonObject jsonObject) {
            String group = JsonHelper.getString(jsonObject, "group", "");
            IngredientWithCount input = IngredientWithCount.fromJson(JsonHelper.getObject(jsonObject, "input"));
            ItemStack output = IngredientWithCount.fromJson(JsonHelper.getObject(jsonObject, "output")).getMatchingStacks()[0];
            IngredientWithCount extraItems = IngredientWithCount.fromJson(JsonHelper.getArray(jsonObject, "extra_items"));
            IngredientWithCount spirits = IngredientWithCount.fromJson(JsonHelper.getArray(jsonObject, "spirits"));
            return recipeFactory.create(id, group, input, output, extraItems, spirits);
        }

        public T read(Identifier identifier, PacketByteBuf buf) {
            String group = buf.readString();
            IngredientWithCount input = IngredientWithCount.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            IngredientWithCount extraItems = IngredientWithCount.fromPacket(buf);
            IngredientWithCount spirits = IngredientWithCount.fromPacket(buf);
            return recipeFactory.create(identifier, group, input, output, extraItems, spirits);
        }

        public void write(PacketByteBuf buf, T spiritInfusionRecipe) {
            buf.writeString(spiritInfusionRecipe.group());
            spiritInfusionRecipe.input().write(buf);
            buf.writeItemStack(spiritInfusionRecipe.output());
            spiritInfusionRecipe.extraItems().write(buf);
            spiritInfusionRecipe.spirits().write(buf);
        }

        public interface RecipeFactory<T extends SpiritInfusionRecipe> {
            T create(Identifier id, String group, IngredientWithCount input, ItemStack output, IngredientWithCount extraItems, IngredientWithCount spirits);
        }
    }
}
