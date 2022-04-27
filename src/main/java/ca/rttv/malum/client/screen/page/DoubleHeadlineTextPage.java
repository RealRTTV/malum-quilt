package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Malum.MODID;

public class DoubleHeadlineTextPage extends BookPage {
    private final String headlineTranslationKey;
    private final String translationKey;
    private final String secondHeadlineTranslationKey;
    private final String secondTranslationKey;

    public DoubleHeadlineTextPage(String headlineTranslationKey, String translationKey, String secondHeadlineTranslationKey, String secondTranslationKey) {
        super(new Identifier(MODID, "textures/gui/book/pages/spirit_rite_text_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.translationKey = translationKey;
        this.secondHeadlineTranslationKey = secondHeadlineTranslationKey;
        this.secondTranslationKey = secondTranslationKey;
    }

    public DoubleHeadlineTextPage(String headlineTranslationKey, String translationKey) {
        this(headlineTranslationKey, translationKey, "corrupted_" + headlineTranslationKey, "corrupted_" + translationKey);
    }

    public String getHeadlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
    }

    public String getTranslationKey() {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    public String getSecondHeadlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + secondHeadlineTranslationKey;
    }

    public String getCorruptedTranslationKey() {
        return "malum.gui.book.entry.page.text." + secondTranslationKey;
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        Text text = new TranslatableText(this.getHeadlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 75 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 10);
        ProgressionBookScreen.renderWrappingText(matrices, getTranslationKey(), guiLeft + 16, guiTop + 31, 120);

        text = new TranslatableText(this.getSecondHeadlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 75 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 87);
        ProgressionBookScreen.renderWrappingText(matrices, getCorruptedTranslationKey(), guiLeft + 16, guiTop + 108, 120);
    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        Text text = new TranslatableText(this.getHeadlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 218 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 10);
        ProgressionBookScreen.renderWrappingText(matrices, getTranslationKey(), guiLeft + 158, guiTop + 31, 120);

        text = new TranslatableText(this.getSecondHeadlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 218 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 87);
        ProgressionBookScreen.renderWrappingText(matrices, getCorruptedTranslationKey(), guiLeft + 158, guiTop + 108, 120);
    }
}
