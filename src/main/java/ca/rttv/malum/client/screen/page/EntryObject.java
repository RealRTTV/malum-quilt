package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.EntryScreen;
import ca.rttv.malum.client.screen.ProgressionBookScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Arrays;

public class EntryObject extends BookObject {
    public final BookEntry entry;

    public EntryObject(BookEntry entry, int posX, int posY) {
        super(posX, posY, 32, 32);
        this.entry = entry;
    }

    @Override
    public void click(float xOffset, float yOffset, double mouseX, double mouseY) {
        EntryScreen.openScreen(this);
    }

    @Override
    public void render(MinecraftClient client, MatrixStack matrices, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        ProgressionBookScreen.renderTexture(ProgressionBookScreen.FRAME_TEXTURE, matrices, posX, posY, 1, 252, width, height, 512, 512);
        client.getItemRenderer().renderInGuiWithOverrides(entry.iconStacks[(int) ((client.world.getTime() / 20) % entry.iconStacks.length)], posX + 8, posY + 8);
    }

    @Override
    public void lateRender(MinecraftClient client, MatrixStack matrices, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (isHovering) {
            ProgressionBookScreen.screen.renderTooltip(matrices, Arrays.asList(Text.translatable(entry.translationKey()), Text.translatable(entry.descriptionTranslationKey()).styled(style -> style.withColor(Formatting.GRAY))), mouseX, mouseY);
        }
    }
}
