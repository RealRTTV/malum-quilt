package ca.rttv.malum.client.recipe;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.client.screen.page.SpiritTextPage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Malum.MODID;

public class SpiritTextPageRenderer extends BookPageRenderer<SpiritTextPage> {
    public SpiritTextPageRenderer(SpiritTextPage page) {
        super(page, new Identifier(MODID, "textures/gui/book/pages/spirit_page.png"));
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        Text text = Text.translatable(page.headlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 75 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 10);
        ProgressionBookScreen.renderWrappingText(matrices, page.descriptionTranslationKey(), guiLeft + 16, guiTop + 79, 125);
        ProgressionBookScreen.renderItem(matrices, page.spiritStack, guiLeft + 67, guiTop + 44, mouseX, mouseY);
    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        Text text = Text.translatable(page.headlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 218 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 10);
        ProgressionBookScreen.renderWrappingText(matrices, page.descriptionTranslationKey(), guiLeft + 158, guiTop + 79, 125);
        ProgressionBookScreen.renderItem(matrices, page.spiritStack, guiLeft + 209, guiTop + 44, mouseX, mouseY);
    }
}
