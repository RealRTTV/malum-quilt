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

public class HeadlineTextPage extends BookPage {
    public static final Codec<HeadlineTextPage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("headline_translation_key").forGetter(HeadlineTextPage::headlineTranslationKey),
        Codec.STRING.fieldOf("description_translation_key").forGetter(HeadlineTextPage::descriptionTranslationKey)
    ).apply(instance, HeadlineTextPage::new));

    private final String headlineTranslationKey;
    private final String descriptionTranslationKey;

    public HeadlineTextPage(String headlineTranslationKey, String descriptionTranslationKey) {
        super(new Identifier(MODID, "textures/gui/book/pages/headline_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.descriptionTranslationKey = descriptionTranslationKey;
    }

    public HeadlineTextPage(JsonObject json) {
        super(new Identifier(MODID, "textures/gui/book/pages/headline_page.png"));
        headlineTranslationKey = json.get("headline_translation_key").getAsString();
        descriptionTranslationKey = json.get("description_translation_key").getAsString();
    }

    public String headlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
    }

    public String descriptionTranslationKey() {
        return "malum.gui.book.entry.page.text." + descriptionTranslationKey;
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        Text text = Text.translatable(this.headlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 75 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 10);
        ProgressionBookScreen.renderWrappingText(matrices, descriptionTranslationKey(), guiLeft + 16, guiTop + 31, 120);
    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        Text text = Text.translatable(this.headlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 218 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 10);
        ProgressionBookScreen.renderWrappingText(matrices, descriptionTranslationKey(), guiLeft + 158, guiTop + 31, 120);
    }

    @Override
    public MalumPageTypeRegistry.PageType type() {
        return MalumPageTypeRegistry.HEADLINE_PAGE_TYPE;
    }
}
