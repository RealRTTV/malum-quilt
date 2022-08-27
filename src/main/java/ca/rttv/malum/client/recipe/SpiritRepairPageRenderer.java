package ca.rttv.malum.client.recipe;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.client.screen.page.SpiritRepairPage;
import ca.rttv.malum.recipe.SpiritRepairRecipe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Malum.MODID;

public class SpiritRepairPageRenderer extends BookPageRenderer<SpiritRepairPage> {
    private final SpiritRepairRecipe recipe;

    public SpiritRepairPageRenderer(SpiritRepairPage page) {
        super(page, new Identifier(MODID, "textures/gui/book/pages/spirit_repair_page.png"));
        this.recipe = (SpiritRepairRecipe) MinecraftClient.getInstance().world.getRecipeManager().get(page.recipe).orElseThrow();
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
        ItemStack[] repairMaterials = recipe.repairMaterial().getMatchingStacks();
        ItemStack repairMaterial;
        if (repairMaterials.length == 1) {
            repairMaterial = repairMaterials[0];
        } else {
            repairMaterial = repairMaterials[(int) ((client.world.getTime() / 15L) % repairMaterials.length)];
        }
        ItemStack outputStack = recipe.getOutput(inputStack);
        inputStack.setDamage((int) (inputStack.getMaxDamage() * recipe.durabilityPercentage()));
        outputStack.setDamage(0); // this is required cause im stupid
        ProgressionBookScreen.renderItem(matrices, repairMaterial, guiLeft + 48, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(matrices, inputStack, guiLeft + 86, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(matrices, outputStack, guiLeft + 67, guiTop + 126, mouseX, mouseY);
        if (recipe.spirits().isPresent()) {
            ProgressionBookScreen.renderComponents(matrices, recipe.spirits(), guiLeft + 65, guiTop + 16, mouseX, mouseY, false);
        }
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
        ItemStack[] repairMaterials = recipe.repairMaterial().getMatchingStacks();
        ItemStack repairMaterial;
        if (repairMaterials.length == 1) {
            repairMaterial = repairMaterials[0];
        } else {
            repairMaterial = repairMaterials[(int) ((client.world.getTime() / 15L) % repairMaterials.length)];
        }
        ItemStack outputStack = recipe.getOutput(inputStack);
        inputStack.setDamage((int) (inputStack.getMaxDamage() * recipe.durabilityPercentage()));
        outputStack.setDamage(0); // this is required cause im stupid
        ProgressionBookScreen.renderItem(matrices, repairMaterial, guiLeft + 190, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(matrices, inputStack, guiLeft + 228, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(matrices, outputStack, guiLeft + 209, guiTop + 126, mouseX, mouseY);
        if (recipe.spirits().isPresent()) {
            ProgressionBookScreen.renderComponents(matrices, recipe.spirits(), guiLeft + 207, guiTop + 16, mouseX, mouseY, false);
        }
    }
}
