package ca.rttv.malum.emi;

import ca.rttv.malum.MalumEmiPlugin;
import ca.rttv.malum.client.screen.EntryScreen;
import ca.rttv.malum.recipe.IngredientWithCount;
import ca.rttv.malum.recipe.SpiritInfusionRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static ca.rttv.malum.Malum.MODID;

public class SpiritInfusionEmiRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(MODID, "textures/gui/emi/spirit_infusion.png");

    private final Identifier id;
    private final IngredientWithCount input;
    private final ItemStack output;
    private final IngredientWithCount extraItems;
    private final IngredientWithCount spirits;

    public SpiritInfusionEmiRecipe(SpiritInfusionRecipe recipe) {
        id = recipe.id();
        input = recipe.input();
        output = recipe.output();
        extraItems = recipe.extraItems();
        spirits = recipe.spirits();
    }

    @Override
    public boolean supportsRecipeTree() {
        return true;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return MalumEmiPlugin.SPIRIT_INFUSION;
    }

    @Override
    @Nullable
    public Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        List<EmiIngredient> list = Lists.newArrayList();
        Stream.concat(
            Stream.concat(
                   Arrays.stream(extraItems.getEntries()),
                   Arrays.stream(spirits.getEntries())
            ),
               Arrays.stream(input.getEntries())
        )
        .map(entry -> EmiIngredient.of(Ingredient.ofEntries(Stream.of(entry.toIngredientEntry())), entry.getCount()))
        .forEach(list::add);
        return list;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(EmiStack.of(output));
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

        widgets.addSlot(Arrays.stream(input.getEntries())
                              .map(entry -> EmiIngredient.of(Ingredient.ofEntries(Stream.of(entry.toIngredientEntry())), entry.getCount()))
                              .findFirst()
                              .orElseThrow(),
                        62, 56).drawBack(false);

        widgets.addSlot(EmiStack.of(output), 62, 123).drawBack(false);

        List<EmiIngredient> spirits = Arrays.stream(this.spirits.getEntries()).map(entry -> EmiIngredient.of(Ingredient.ofEntries(Stream.of(entry.toIngredientEntry())), entry.getCount())).toList();

        for (int i = 0; i < spirits.size(); i++) {
            int yOffset = 59 - 18 * spirits.size() / 2 + i * 18;
            widgets.addTexture(new EmiTexture(EntryScreen.BOOK_TEXTURE, 75, 192, 20, 20, 20, 20, 512, 512), 18, yOffset - 2);
            widgets.addSlot(spirits.get(i), 20, yOffset).drawBack(false);
            // todo, add crowns @arathain, you do this
        }

        List<EmiIngredient> extraItems = Arrays.stream(this.extraItems.getEntries()).map(entry -> EmiIngredient.of(Ingredient.ofEntries(Stream.of(entry.toIngredientEntry())), entry.getCount())).toList();

        for (int i = 0; i < extraItems.size(); i++) {
            int yOffset = 59 - 18 * extraItems.size() / 2 + i * 18;
            widgets.addTexture(new EmiTexture(EntryScreen.BOOK_TEXTURE, 75, 192, 20, 20, 20, 20, 512, 512), 104, yOffset - 2);
            widgets.addSlot(extraItems.get(i), 106, yOffset).drawBack(false);
        }
    }
}
