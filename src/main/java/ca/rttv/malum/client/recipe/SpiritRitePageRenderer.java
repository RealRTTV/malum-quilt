package ca.rttv.malum.client.recipe;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.client.screen.page.SpiritRitePage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Malum.MODID;

public class SpiritRitePageRenderer extends BookPageRenderer<SpiritRitePage> {
    public SpiritRitePageRenderer(SpiritRitePage page) {
        super(page, new Identifier(MODID, "textures/gui/book/pages/spirit_rite_page.png"));
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        renderRite(matrices, guiLeft + 67, guiTop + 123, mouseX, mouseY, page.rite.items());
    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        renderRite(matrices, guiLeft + 209, guiTop + 123, mouseX, mouseY, page.rite.items());
    }

    public void renderRite(MatrixStack matrices, int left, int top, int mouseX, int mouseY, Item[] spirits) {
        for (int i = 0; i < spirits.length; i++) {
            ItemStack stack = spirits[i].getDefaultStack();
            ProgressionBookScreen.renderItem(matrices, stack, left, top - 20 * i, mouseX, mouseY);
        }
    }
}
