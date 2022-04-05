package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.SpiritInfusionRecipe;
import ca.rttv.malum.client.screen.ProgressionBookScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumRegistry.SPIRIT_INFUSION;

public class SpiritInfusionPage extends BookPage {
    private final SpiritInfusionRecipe recipe;

    public SpiritInfusionPage(Predicate<SpiritInfusionRecipe> predicate) {
        super(new Identifier(MODID, "textures/gui/book/pages/spirit_infusion_page.png"));
        final MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            this.recipe = null;
            return;
        }
        this.recipe = SpiritInfusionRecipe.getRecipe(client.world, predicate);
        System.out.println(this.recipe);
        client.world.getRecipeManager().getAllOfType(SPIRIT_INFUSION).forEach((id, recipee) -> System.out.println(recipee.getId()));
    }

    public SpiritInfusionPage(SpiritInfusionRecipe recipe) {
        super(new Identifier(MODID, "textures/gui/book/pages/spirit_infusion_page.png"));
        this.recipe = recipe;
    }

    public static SpiritInfusionPage fromInput(Item inputItem) {
        return new SpiritInfusionPage(s -> s.input.test(inputItem.getDefaultStack()));
    }

    public static SpiritInfusionPage fromOutput(Item outputItem) {
        return new SpiritInfusionPage(s -> s.output.isItemEqual(outputItem.getDefaultStack()));
    }

    public static int[] uOffset() {
        return new int[]{360, 393, 393, 360, 327, 294, 294, 327};
    }

    public static int[] vOffset() {
        return new int[]{1, 1, 53, 34, 1, 1, 129, 110};
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        ItemStack inputStack = recipe.input.getMatchingStacks()[0];
        ItemStack outputStack = recipe.output;
        if (!recipe.extraItems.isEmpty()) {
            renderIngredients(matrices, guiLeft + 105, guiTop + 51, mouseX, mouseY, recipe.extraItems);
        }
        ProgressionBookScreen.renderItem(matrices, inputStack, guiLeft + 67, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(matrices, outputStack, guiLeft + 67, guiTop + 126, mouseX, mouseY);
        renderItems(matrices, guiLeft + 15, guiTop + 51, mouseX, mouseY, List.of(recipe.spirits.getMatchingStacks()));
    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        ItemStack inputStack = recipe.input.getMatchingStacks()[0];
        ItemStack outputStack = recipe.output;
        if (!recipe.extraItems.isEmpty()) {
            renderIngredients(matrices, guiLeft + 247, guiTop + 51, mouseX, mouseY, recipe.extraItems);
        }
        ProgressionBookScreen.renderItem(matrices, inputStack, guiLeft + 209, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(matrices, outputStack, guiLeft + 209, guiTop + 126, mouseX, mouseY);
        renderItems(matrices, guiLeft + 157, guiTop + 51, mouseX, mouseY, List.of(recipe.spirits.getMatchingStacks()));
    }

    public void renderIngredients(MatrixStack matrices, int left, int top, int mouseX, int mouseY, Ingredient ingredients) {
        List<ItemStack> items = List.of(ingredients.getMatchingStacks());

        renderItems(matrices, left, top, mouseX, mouseY, items);
    }

    public void renderItems(MatrixStack matrices, int left, int top, int mouseX, int mouseY, List<ItemStack> extraItems) {
        int index = extraItems.size() - 1;
        int textureHeight = 32 + index * 19;
        int offset = (int) (6.5f * index);
        top -= offset;
        int uOffset = uOffset()[index];
        int vOffset = vOffset()[index];
        ProgressionBookScreen.renderTexture(TEXTURE, matrices, left, top, uOffset, vOffset, 32, textureHeight, 512, 512);

        for (int i = 0; i < extraItems.size(); i++) {
            ItemStack stack = extraItems.get(i);
            ProgressionBookScreen.renderItem(matrices, stack, left + 8, top + 8 + 19 * i, mouseX, mouseY);
        }
    }
}
