package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class BookObject {
    public boolean isHovering;
    public float hover;
    public final int posX;
    public final int posY;
    public final int width;
    public final int height;

    public BookObject(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public int hoverCap() {
        return 20;
    }

    public void render(MinecraftClient client, MatrixStack matrices, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {

    }

    public void lateRender(MinecraftClient client, MatrixStack matrices, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {

    }

    public void click(float xOffset, float yOffset, double mouseX, double mouseY) {

    }

    public void exit() {

    }

    public boolean isHovering(float xOffset, float yOffset, double mouseX, double mouseY) {
        return ProgressionBookScreen.isHovering(mouseX, mouseY, offsetPosX(xOffset), offsetPosY(yOffset), width, height);
    }

    public int offsetPosX(float xOffset) {
        int guiLeft = (width - ProgressionBookScreen.screen.bookWidth) / 2;
        return (int) (guiLeft + this.posX + xOffset);
    }

    public int offsetPosY(float yOffset) {
        int guiTop = (height - ProgressionBookScreen.screen.bookHeight) / 2;
        return (int) (guiTop + this.posY + yOffset);
    }
}
