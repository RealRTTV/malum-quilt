package ca.rttv.malum.client.recipe;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.client.screen.page.SpiritCruciblePage;
import ca.rttv.malum.recipe.SpiritFocusingRecipe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Malum.MODID;

public class SpiritCruciblePageRenderer extends BookPageRenderer<SpiritCruciblePage> {
    private final SpiritFocusingRecipe recipe;

    public SpiritCruciblePageRenderer(SpiritCruciblePage page) {
        super(page, new Identifier(MODID, "textures/gui/book/pages/spirit_crucible_page.png"));
        this.recipe = (SpiritFocusingRecipe) MinecraftClient.getInstance().world.getRecipeManager().get(page.recipe).orElseThrow();

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
        ProgressionBookScreen.renderItem(matrices, recipe.output(), guiLeft + 67, guiTop + 126, mouseX, mouseY);
        ProgressionBookScreen.renderComponents(matrices, recipe.spirits(), guiLeft + 65, guiTop + 16, mouseX, mouseY, false);
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
        ProgressionBookScreen.renderItem(matrices, recipe.output(), guiLeft + 209, guiTop + 126, mouseX, mouseY);
        ProgressionBookScreen.renderComponents(matrices, recipe.spirits(), guiLeft + 207, guiTop + 16, mouseX, mouseY, false);
    }
}
