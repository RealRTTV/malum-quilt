package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class ImportantEntryObject extends EntryObject {

    public ImportantEntryObject(BookEntry entry, int posX, int posY) {
        super(entry, posX, posY);
    }

    @Override
    public void render(MinecraftClient client, MatrixStack matrices, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        ProgressionBookScreen.renderTexture(ProgressionBookScreen.FRAME_TEXTURE, matrices, posX, posY, 34, 252, width, height, 512, 512);
        client.getItemRenderer().renderInGuiWithOverrides(entry.iconStack.apply(client.world), posX + 8, posY + 8);
    }
}