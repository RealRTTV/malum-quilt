package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TextPage extends BookPage {

    public final String translationKey;

    public TextPage(String translationKey) {
        super(new Identifier("malum", "textures/gui/book/pages/blank_page.png"));
        this.translationKey = translationKey;
    }

    public String translationKey() {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ProgressionBookScreen.renderWrappingText(matrices, translationKey(), guiLeft + 16, guiTop + 10, 120);
    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ProgressionBookScreen.renderWrappingText(matrices, translationKey(), guiLeft + 158, guiTop + 10, 120);
    }
}
