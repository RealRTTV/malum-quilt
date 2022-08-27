package ca.rttv.malum.client.recipe;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.client.screen.page.SmeltingBookPage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Malum.MODID;

public class SmeltingBookPageRenderer extends BookPageRenderer<SmeltingBookPage> {
    public SmeltingBookPageRenderer(SmeltingBookPage page) {
        super(page, new Identifier(MODID, "textures/gui/book/pages/smelting_page.png"));
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack poseStack, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        ProgressionBookScreen.renderItem(poseStack, page.inputStack, guiLeft + 67, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(poseStack, page.outputStack, guiLeft + 67, guiTop + 126, mouseX, mouseY);

    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        ProgressionBookScreen.renderItem(matrices, page.inputStack, guiLeft + 209, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(matrices, page.outputStack, guiLeft + 209, guiTop + 126, mouseX, mouseY);
    }
}
