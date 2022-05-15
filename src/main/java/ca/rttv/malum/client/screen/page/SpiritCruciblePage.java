package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.recipe.SpiritFocusingRecipe;
import ca.rttv.malum.util.helper.DataHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class SpiritCruciblePage extends BookPage {
    private final SpiritFocusingRecipe recipe;

    public SpiritCruciblePage(Predicate<SpiritFocusingRecipe> predicate) {
        super(DataHelper.prefix("textures/gui/book/pages/spirit_crucible_page.png"));
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) { // this is null during datagen
            this.recipe = null;
            return;
        }
        this.recipe = SpiritFocusingRecipe.getRecipe(client.world, predicate);
    }

    public SpiritCruciblePage(SpiritFocusingRecipe recipe) {
        super(DataHelper.prefix("textures/gui/book/pages/spirit_crucible_page.png"));
        this.recipe = recipe;
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        ItemStack[] inputStacks = recipe.input().getMatchingStacks();
        ItemStack inputStack;
        if (inputStacks.length == 1) {
            inputStack = inputStacks[0];
        } else {
            inputStack = inputStacks[(int) ((client.world.getTime() / 20L) % inputStacks.length)];
        }
        ProgressionBookScreen.renderItem(matrices, inputStack, guiLeft + 67, guiTop + 59, mouseX, mouseY);
//        ProgressionBookScreen.renderComponents(matrices, recipe.output(), guiLeft + 67, guiTop + 126, mouseX, mouseY);
//        ProgressionBookScreen.renderComponents(matrices, recipe.spirits(), guiLeft + 65, guiTop + 16, mouseX, mouseY, false);
    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        ItemStack[] inputStacks = recipe.input().getMatchingStacks();
        ItemStack inputStack;
        if (inputStacks.length == 1) {
            inputStack = inputStacks[0];
        } else {
            inputStack = inputStacks[(int) ((client.world.getTime() / 20L) % inputStacks.length)];
        }
        ProgressionBookScreen.renderItem(matrices, inputStack, guiLeft + 209, guiTop + 59, mouseX, mouseY);
//        ProgressionBookScreen.renderComponent(matrices, recipe.output(), guiLeft + 209, guiTop + 126, mouseX, mouseY);
//        ProgressionBookScreen.renderComponents(matrices, recipe.spirits(), guiLeft + 207, guiTop + 16, mouseX, mouseY, false);
    }

    public static SpiritCruciblePage fromInput(Item inputItem) {
        return new SpiritCruciblePage(recipe -> recipe.input().test(new ItemStack(inputItem)));
    }

    public static SpiritCruciblePage fromOutput(Item outputItem) {
        return new SpiritCruciblePage(recipe -> recipe.output().isOf(outputItem));
    }
}