package ca.rttv.malum.client.recipe;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.client.screen.page.DoubleHeadlineTextPage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Malum.MODID;

public class DoubleHeadlineTextPageRenderer extends BookPageRenderer<DoubleHeadlineTextPage> {
    public DoubleHeadlineTextPageRenderer(DoubleHeadlineTextPage page) {
        super(page, new Identifier(MODID, "textures/gui/book/pages/spirit_rite_text_page.png"));
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        Text text = Text.translatable(page.firstHeadlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 75 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 10);
        ProgressionBookScreen.renderWrappingText(matrices, page.firstDescriptionTranslationKey(), guiLeft + 16, guiTop + 31, 120);

        text = Text.translatable(page.secondHeadlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 75 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 87);
        ProgressionBookScreen.renderWrappingText(matrices, page.secondDescriptionTranslationKey(), guiLeft + 16, guiTop + 108, 120);
    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        Text text = Text.translatable(page.firstHeadlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 218 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 10);
        ProgressionBookScreen.renderWrappingText(matrices, page.firstDescriptionTranslationKey(), guiLeft + 158, guiTop + 31, 120);

        text = Text.translatable(page.secondHeadlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 218 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 87);
        ProgressionBookScreen.renderWrappingText(matrices, page.secondDescriptionTranslationKey(), guiLeft + 158, guiTop + 108, 120);
    }
}
