package ca.rttv.malum.client.recipe;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.client.screen.page.TextPage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Malum.MODID;

public class TextPageRenderer extends BookPageRenderer<TextPage> {
    public TextPageRenderer(TextPage page) {
        super(page, new Identifier(MODID, "textures/gui/book/pages/blank_page.png"));
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float partialTicks) {
        ProgressionBookScreen.renderWrappingText(matrices, page.translationKey(), guiLeft + 16, guiTop + 10, 120);
    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float partialTicks) {
        ProgressionBookScreen.renderWrappingText(matrices, page.translationKey(), guiLeft + 158, guiTop + 10, 120);
    }
}
