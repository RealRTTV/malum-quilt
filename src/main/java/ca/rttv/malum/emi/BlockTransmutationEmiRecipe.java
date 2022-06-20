package ca.rttv.malum.emi;

import ca.rttv.malum.MalumEmiPlugin;
import ca.rttv.malum.recipe.BlockTransmutationRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static ca.rttv.malum.Malum.MODID;

public class BlockTransmutationEmiRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(MODID, "textures/gui/emi/block_transmutation.png");

    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public BlockTransmutationEmiRecipe(BlockTransmutationRecipe recipe) {
        id = recipe.id();
        input = List.of(EmiIngredient.of(recipe.input()));
        output = List.of(EmiStack.of(recipe.output()));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return MalumEmiPlugin.BLOCK_TRANSMUTATION;
    }

    @Override
    public boolean supportsRecipeTree() {
        return true;
    }

    @Override
    @Nullable
    public Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 140;
    }

    @Override
    public int getDisplayHeight() {
        return 81;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(new EmiTexture(TEXTURE, 0, 0, getDisplayWidth(), getDisplayHeight()), 0, 0);

        widgets.addSlot(input.get(0), 27, 26).drawBack(false);

        widgets.addSlot(output.get(0), 92, 25).drawBack(false);
    }
}
