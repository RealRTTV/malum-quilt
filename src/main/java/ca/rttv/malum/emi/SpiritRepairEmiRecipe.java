package ca.rttv.malum.emi;

import ca.rttv.malum.MalumEmiPlugin;
import ca.rttv.malum.client.screen.EntryScreen;
import ca.rttv.malum.recipe.SpiritRepairRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static ca.rttv.malum.Malum.MODID;

public class SpiritRepairEmiRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(MODID, "textures/gui/emi/spirit_repair.png");

    private final Identifier id;
    private final EmiIngredient input;
    private final EmiIngredient inputWithDurabilityLost;
    private final List<EmiIngredient> spirits;
    private final EmiIngredient repairMaterial;
    private final double durabilityPercentage;

    public SpiritRepairEmiRecipe(SpiritRepairRecipe recipe) {
        id = recipe.id();
        input = EmiIngredient.of(recipe.input());
        spirits = Arrays.stream(recipe.spirits().getEntries()).map(entry -> EmiIngredient.of(Ingredient.ofEntries(Stream.of(entry.toIngredientEntry())), entry.getCount())).toList();
        repairMaterial = EmiIngredient.of(recipe.repairMaterial().asIngredient(), recipe.repairMaterial().getEntries()[0].getCount());
        durabilityPercentage = recipe.durabilityPercentage();
        inputWithDurabilityLost = EmiIngredient.of(input.getEmiStacks().stream().map(stack -> {
            ItemStack itemStack = stack.getItemStack();
            itemStack.setDamage((int) (itemStack.getMaxDamage() * durabilityPercentage));
            return EmiStack.of(itemStack);
        }).toList());
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return MalumEmiPlugin.SPIRIT_REPAIR;
    }

    @Override
    @Nullable
    public Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        List<EmiIngredient> list = new ArrayList<>();
        list.add(input);
        list.add(repairMaterial);
        list.addAll(spirits);
        return list;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return input.getEmiStacks();
    }

    @Override
    public int getDisplayWidth() {
        return 141;
    }

    @Override
    public int getDisplayHeight() {
        return 183;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(new EmiTexture(TEXTURE, 0, 0, getDisplayWidth(), getDisplayHeight()), 0, 0);

        widgets.addSlot(inputWithDurabilityLost, 81, 56).drawBack(false);

        widgets.addSlot(repairMaterial, 42, 56).drawBack(false);

        widgets.addSlot(input, 62, 123).drawBack(false);

        for (int i = 0; i < spirits.size(); i++) {
            int xOffset = 71 - 18 * spirits.size() / 2 + i * 18;
            widgets.addTexture(new EmiTexture(EntryScreen.BOOK_TEXTURE, 75, 192, 20, 20, 20, 20, 512, 512), xOffset - 1, 12);
            widgets.addSlot(spirits.get(i), xOffset, 13).drawBack(false);
            // todo, add crowns @arathain, you do this
        }
    }

    @Override
    public boolean supportsRecipeTree() {
        return false; // sadly cannot, no issues though since it's a repairing recipe
    }
}
