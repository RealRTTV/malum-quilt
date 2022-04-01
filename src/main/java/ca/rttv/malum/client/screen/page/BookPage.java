package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public abstract class BookPage {

    public final Identifier TEXTURE;

    public BookPage(Identifier texture) {
        this.TEXTURE = texture;
    }

    public boolean isValid() {
        return true;
    }

    public abstract void renderLeft(MinecraftClient minecraft, MatrixStack matrices, float xOffset, float yOffset, int mouseX, int mouseY, float tickDelta);

    public abstract void renderRight(MinecraftClient client, MatrixStack matrices, float xOffset, float yOffset, int mouseX, int mouseY, float tickDelta);

    public void renderBackgroundLeft(MinecraftClient client, MatrixStack matrices, float xOffset, float yOffset, int mouseX, int mouseY, float tickDelta) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ProgressionBookScreen.renderTexture(TEXTURE, matrices, guiLeft, guiTop, 1, 1, ProgressionBookScreen.screen.bookWidth - 147, ProgressionBookScreen.screen.bookHeight, 512, 512);
    }

    public void renderBackgroundRight(MinecraftClient client, MatrixStack matrices, float xOffset, float yOffset, int mouseX, int mouseY, float tickDelta) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ProgressionBookScreen.renderTexture(TEXTURE, matrices, guiLeft + 147, guiTop, 148, 1, ProgressionBookScreen.screen.bookWidth - 147, ProgressionBookScreen.screen.bookHeight, 512, 512);
    }

    public int guiLeft() {
        return (ProgressionBookScreen.screen.width - ProgressionBookScreen.screen.bookWidth) / 2;
    }

    public int guiTop() {
        return (ProgressionBookScreen.screen.height - ProgressionBookScreen.screen.bookHeight) / 2;
    }
}
