package ca.rttv.malum.emi;

import ca.rttv.malum.MalumEmiPlugin;
import ca.rttv.malum.client.screen.EntryScreen;
import ca.rttv.malum.recipe.SpiritFocusingRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static ca.rttv.malum.Malum.MODID;

public class SpiritFocusingEmiRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(MODID, "textures/gui/emi/spirit_focusing.png");

    private final Identifier id;
    private final EmiIngredient input;
    private final EmiStack output;
    private final List<EmiIngredient> spirits;

    public SpiritFocusingEmiRecipe(SpiritFocusingRecipe recipe) {
        id = recipe.id();
        input = EmiIngredient.of(recipe.input());
        output = EmiStack.of(recipe.output());
        spirits = Arrays.stream(recipe.spirits().getEntries()).map(entry -> EmiIngredient.of(Ingredient.ofEntries(Stream.of(entry.toIngredientEntry())), entry.getCount())).toList();
    }

    @Override
    public List<EmiIngredient> getCatalysts() {
        return List.of(input);
    }

    @Override
    public boolean supportsRecipeTree() {
        return true;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return MalumEmiPlugin.SPIRIT_FOCUSING;
    }

    @Override
    @Nullable
    public Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return spirits;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
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

        widgets.addSlot(input, 62, 56).drawBack(false).catalyst(true);

        widgets.addSlot(output, 62, 123).drawBack(false);

        for (int i = 0; i < spirits.size(); i++) {
            int xOffset = 71 - 18 * spirits.size() / 2 + i * 18;
            widgets.addTexture(new EmiTexture(EntryScreen.BOOK_TEXTURE, 75, 192, 20, 20, 20, 20, 512, 512), xOffset - 1, 12);
            widgets.addSlot(spirits.get(i), xOffset, 13).drawBack(false);
            // todo, add crowns @arathain, you do this
        }
    }
}
