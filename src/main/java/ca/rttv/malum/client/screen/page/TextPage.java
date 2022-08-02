package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Malum.MODID;

public class TextPage extends BookPage {
    public static final Codec<TextPage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("translation_key").forGetter(TextPage::translationKey)
    ).apply(instance, TextPage::new));

    public final String translationKey;

    public TextPage(String translationKey) {
        super(new Identifier(MODID, "textures/gui/book/pages/blank_page.png"));
        this.translationKey = translationKey;
    }

    public TextPage(JsonObject json) {
        super(new Identifier(MODID, "textures/gui/book/pages/blank_page.png"));
        translationKey = json.get("description").getAsString();
    }

    public String translationKey() {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float partialTicks) {
        ProgressionBookScreen.renderWrappingText(matrices, translationKey(), guiLeft + 16, guiTop + 10, 120);
    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float partialTicks) {
        ProgressionBookScreen.renderWrappingText(matrices, translationKey(), guiLeft + 158, guiTop + 10, 120);
    }

    @Override
    public MalumPageTypeRegistry.PageType type() {
        return MalumPageTypeRegistry.TEXT_PAGE_TYPE;
    }
}
