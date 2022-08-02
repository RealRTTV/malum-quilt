package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Malum.MODID;

public class DoubleHeadlineTextPage extends BookPage {
    public static final Codec<DoubleHeadlineTextPage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("first_headline_translation_key").forGetter(DoubleHeadlineTextPage::firstHeadlineTranslationKey),
        Codec.STRING.fieldOf("first_description_translation_key").forGetter(DoubleHeadlineTextPage::firstDescriptionTranslationKey),
        Codec.STRING.fieldOf("second_headline_translation_key").forGetter(DoubleHeadlineTextPage::secondDescriptionTranslationKey),
        Codec.STRING.fieldOf("second_description_translation_key").forGetter(DoubleHeadlineTextPage::secondDescriptionTranslationKey)
    ).apply(instance, DoubleHeadlineTextPage::new));

    private final String firstHeadlineTranslationKey;
    private final String firstDescriptionTranslationKey;
    private final String secondHeadlineTranslationKey;
    private final String secondDescriptionTranslationKey;

    public DoubleHeadlineTextPage(String firstHeadlineTranslationKey, String firstDescriptionTranslationKey, String secondHeadlineTranslationKey, String secondDescriptionTranslationKey) {
        super(new Identifier(MODID, "textures/gui/book/pages/spirit_rite_text_page.png"));
        this.firstHeadlineTranslationKey = firstHeadlineTranslationKey;
        this.firstDescriptionTranslationKey = firstDescriptionTranslationKey;
        this.secondHeadlineTranslationKey = secondHeadlineTranslationKey;
        this.secondDescriptionTranslationKey = secondDescriptionTranslationKey;
    }

    public DoubleHeadlineTextPage(String firstHeadlineTranslationKey, String firstDescriptionTranslationKey) {
        this(firstHeadlineTranslationKey, firstDescriptionTranslationKey, "corrupted_" + firstHeadlineTranslationKey, "corrupted_" + firstDescriptionTranslationKey);
    }

    public DoubleHeadlineTextPage(JsonObject json) {
        super(new Identifier(MODID, "textures/gui/book/pages/spirit_rite_text_page.png"));
        firstHeadlineTranslationKey = json.get("first_headline_translation_key").getAsString();
        firstDescriptionTranslationKey = json.get("first_description_translation_key").getAsString();
        secondHeadlineTranslationKey = json.get("second_headline_translation_key").getAsString();
        secondDescriptionTranslationKey = json.get("second_description_translation_key").getAsString();
    }

    public String firstHeadlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + firstHeadlineTranslationKey; // todo, fix
    }

    public String firstDescriptionTranslationKey() {
        return "malum.gui.book.entry.page.text." + firstDescriptionTranslationKey; // todo, fix
    }

    public String secondHeadlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + secondHeadlineTranslationKey; // todo, fix
    }

    public String secondDescriptionTranslationKey() {
        return "malum.gui.book.entry.page.text." + secondDescriptionTranslationKey; // todo, fix
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        Text text = Text.translatable(this.firstHeadlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 75 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 10);
        ProgressionBookScreen.renderWrappingText(matrices, firstDescriptionTranslationKey(), guiLeft + 16, guiTop + 31, 120);

        text = Text.translatable(this.secondHeadlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 75 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 87);
        ProgressionBookScreen.renderWrappingText(matrices, secondDescriptionTranslationKey(), guiLeft + 16, guiTop + 108, 120);
    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        Text text = Text.translatable(this.firstHeadlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 218 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 10);
        ProgressionBookScreen.renderWrappingText(matrices, firstDescriptionTranslationKey(), guiLeft + 158, guiTop + 31, 120);

        text = Text.translatable(this.secondHeadlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 218 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 87);
        ProgressionBookScreen.renderWrappingText(matrices, secondDescriptionTranslationKey(), guiLeft + 158, guiTop + 108, 120);
    }

    @Override
    public MalumPageTypeRegistry.PageType type() {
        return MalumPageTypeRegistry.DOUBLE_HEADLINE_PAGE_TYPE;
    }
}
