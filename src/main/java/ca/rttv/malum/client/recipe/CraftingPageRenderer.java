package ca.rttv.malum.client.recipe;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.client.screen.page.CraftingBookPage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Malum.MODID;

public class CraftingPageRenderer extends BookPageRenderer<CraftingBookPage> {
    public CraftingPageRenderer(CraftingBookPage page) {
        super(page, new Identifier(MODID, "textures/gui/book/pages/crafting_page.png"));
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float partialTicks) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < page.inputStacks.length && !page.inputStacks[index].isEmpty()) {
                    ItemStack itemStack = page.inputStacks[index];
                    int itemPosX = guiLeft + 45 + j * 22;
                    int itemPosY = guiTop + 34 + i * 22;
                    ProgressionBookScreen.renderItem(matrices, itemStack, itemPosX, itemPosY, mouseX, mouseY);
                }
            }
        }

        ProgressionBookScreen.renderItem(matrices, page.outputStack, guiLeft + 67, guiTop + 126, mouseX, mouseY);

    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float partialTicks) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < page.inputStacks.length && !page.inputStacks[index].isEmpty()) {
                    ItemStack itemStack = page.inputStacks[index];
                    int itemPosX = guiLeft + 187 + j * 22;
                    int itemPosY = guiTop + 34 + i * 22;
                    ProgressionBookScreen.renderItem(matrices, itemStack, itemPosX, itemPosY, mouseX, mouseY);
                }
            }
        }

        ProgressionBookScreen.renderItem(matrices, page.outputStack, guiLeft + 209, guiTop + 126, mouseX, mouseY);
    }
}
