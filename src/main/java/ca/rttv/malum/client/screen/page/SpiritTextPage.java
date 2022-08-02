package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static ca.rttv.malum.Malum.MODID;

public class SpiritTextPage extends BookPage {
    public static final Codec<SpiritTextPage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("headline_translation_key").forGetter(SpiritTextPage::headlineTranslationKey),
        Codec.STRING.fieldOf("description_translation_key").forGetter(SpiritTextPage::descriptionTranslationKey),
        ItemStack.CODEC.fieldOf("item").forGetter(page -> page.spiritStack)
    ).apply(instance, SpiritTextPage::new));

    private final String headlineTranslationKey;
    private final String descriptionTranslationKey;
    private final ItemStack spiritStack;

    public SpiritTextPage(String headlineTranslationKey, String descriptionTranslationKey, ItemStack spiritStack) {
        super(new Identifier(MODID, "textures/gui/book/pages/spirit_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.descriptionTranslationKey = descriptionTranslationKey;
        this.spiritStack = spiritStack;
    }

    public SpiritTextPage(String headlineTranslationKey, String descriptionTranslationKey, Item spirit) {
        this(headlineTranslationKey, descriptionTranslationKey, spirit.getDefaultStack());
    }

    public SpiritTextPage(JsonObject json) {
        super(new Identifier(MODID, "textures/gui/book/pages/spirit_page.png"));
        headlineTranslationKey = json.get("headline_translation_key").getAsString();
        descriptionTranslationKey = json.get("headline_translation_key").getAsString();
        if (json.get("output").isJsonObject()) {
            spiritStack = new ItemStack(Registry.ITEM.get(new Identifier(json.getAsJsonObject("item").get("item").getAsString())), json.getAsJsonObject("item").get("count").getAsInt());
        } else {
            spiritStack = Registry.ITEM.get(new Identifier(json.get("output").getAsString())).getDefaultStack();
        }
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
        ProgressionBookScreen.renderWrappingText(matrices, descriptionTranslationKey(), guiLeft + 16, guiTop + 79, 125);
        ProgressionBookScreen.renderItem(matrices, spiritStack, guiLeft + 67, guiTop + 44, mouseX, mouseY);
    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        Text text = Text.translatable(this.headlineTranslationKey());
        ProgressionBookScreen.renderText(matrices, text, guiLeft + 218 - client.textRenderer.getWidth(text.getString()) / 2, guiTop + 10);
        ProgressionBookScreen.renderWrappingText(matrices, descriptionTranslationKey(), guiLeft + 158, guiTop + 79, 125);
        ProgressionBookScreen.renderItem(matrices, spiritStack, guiLeft + 209, guiTop + 44, mouseX, mouseY);
    }
}
