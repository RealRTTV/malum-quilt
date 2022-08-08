package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.registry.MalumPageTypeRegistry;
import ca.rttv.malum.registry.MalumRiteRegistry;
import ca.rttv.malum.rite.Rite;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Malum.MODID;

public class SpiritRitePage extends BookPage {
    public static final Codec<SpiritRitePage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        MalumRiteRegistry.RITE.getCodec().fieldOf("rite").forGetter(page -> page.rite)
    ).apply(instance, SpiritRitePage::new));

    private final Rite rite;

    public SpiritRitePage(Rite rite) {
        super(new Identifier(MODID, "textures/gui/book/pages/spirit_rite_page.png"));
        this.rite = rite;
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        renderRite(matrices, guiLeft + 67, guiTop + 123, mouseX, mouseY, rite.items());
    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        renderRite(matrices, guiLeft + 209, guiTop + 123, mouseX, mouseY, rite.items());
    }

    @Override
    public MalumPageTypeRegistry.PageType type() {
        return MalumPageTypeRegistry.SPIRIT_RITE_PAGE_TYPE;
    }

    public void renderRite(MatrixStack matrices, int left, int top, int mouseX, int mouseY, Item[] spirits) {
        for (int i = 0; i < spirits.length; i++) {
            ItemStack stack = spirits[i].getDefaultStack();
            ProgressionBookScreen.renderItem(matrices, stack, left, top - 20 * i, mouseX, mouseY);
        }
    }
}
