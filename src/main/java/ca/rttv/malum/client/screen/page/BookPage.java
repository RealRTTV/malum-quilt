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

    public abstract void renderLeft(MinecraftClient minecraft, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta);

    public abstract void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta);

    public void renderBackgroundLeft(MinecraftClient client, MatrixStack matrices, int guiLeft, int guiTop, int mouseX, int mouseY, float tickDelta) {
        ProgressionBookScreen.renderTexture(TEXTURE, matrices, guiLeft, guiTop, 1, 1, 146, 189, 512, 512);
    }

    public void renderBackgroundRight(MinecraftClient client, MatrixStack matrices, int guiLeft, int guiTop, int mouseX, int mouseY, float tickDelta) {
        ProgressionBookScreen.renderTexture(TEXTURE, matrices, guiLeft + 147, guiTop, 147, 1, 146, 189, 512, 512);
    }

}
