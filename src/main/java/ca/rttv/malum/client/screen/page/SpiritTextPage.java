package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Main.MODID;

public class SpiritTextPage extends BookPage {
    private final String headlineTranslationKey;
    private final String translationKey;
    private final ItemStack spiritStack;

    public SpiritTextPage(String headlineTranslationKey, String translationKey, ItemStack spiritStack) {
        super(new Identifier(MODID, "textures/gui/book/pages/spirit_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.translationKey = translationKey;
        this.spiritStack = spiritStack;
    }

    public SpiritTextPage(String headlineTranslationKey, String translationKey, Item spirit) {
        this(headlineTranslationKey, translationKey, spirit.getDefaultStack());
    }

    public String headlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
    }

    public String translationKey() {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        Text text = new TranslatableText(this.headlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 75 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 10);
        ProgressionBookScreen.renderWrappingText(matrices, translationKey(), guiLeft + 16, guiTop + 79, 125);
        ProgressionBookScreen.renderItem(matrices, spiritStack, guiLeft + 67, guiTop + 44, mouseX, mouseY);
    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        Text text = new TranslatableText(this.headlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 218 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 10);
        ProgressionBookScreen.renderWrappingText(matrices, translationKey(), guiLeft + 158, guiTop + 79, 125);
        ProgressionBookScreen.renderItem(matrices, spiritStack, guiLeft + 209, guiTop + 44, mouseX, mouseY);
    }
}