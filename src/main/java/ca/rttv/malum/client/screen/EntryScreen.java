package ca.rttv.malum.client.screen;

import ca.rttv.malum.client.screen.page.BookEntry;
import ca.rttv.malum.client.screen.page.BookPage;
import ca.rttv.malum.client.screen.page.EntryObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class EntryScreen extends Screen {
    public static final Identifier BOOK_TEXTURE = new Identifier("malum", "textures/gui/book/entry.png");

    public static EntryScreen screen;
    public static EntryObject openObject;

    public final int bookWidth = 292;
    public final int bookHeight = 190;

    public int grouping;

    public EntryScreen() {
        super(new TranslatableText("malum.gui.entry.title"));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
        BookEntry openEntry = openObject.entry;
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, partialTicks);
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        ProgressionBookScreen.renderTexture(BOOK_TEXTURE, matrices, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        if (!openEntry.pages.isEmpty()) {
            int openPages = grouping * 2;
            for (int i = openPages; i < openPages + 2; i++) {
                if (i < openEntry.pages.size()) {
                    BookPage page = openEntry.pages.get(i);
                    if (i % 2 == 0) {
                        page.renderBackgroundLeft(client, matrices, ProgressionBookScreen.screen.xOffset, ProgressionBookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
                    } else {
                        page.renderBackgroundRight(client, matrices, ProgressionBookScreen.screen.xOffset, ProgressionBookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
                    }
                }
            }
        }
        ProgressionBookScreen.renderTexture(BOOK_TEXTURE, matrices, guiLeft - 13, guiTop + 150, 1, 193, 28, 18, 512, 512);
        if (ProgressionBookScreen.isHovering(mouseX, mouseY, guiLeft - 13, guiTop + 150, 28, 18)) {
            ProgressionBookScreen.renderTexture(BOOK_TEXTURE, matrices, guiLeft - 13, guiTop + 150, 1, 232, 28, 18, 512, 512);
        } else {
            ProgressionBookScreen.renderTexture(BOOK_TEXTURE, matrices, guiLeft - 13, guiTop + 150, 1, 213, 28, 18, 512, 512);
        }
        if (grouping < openEntry.pages.size() / 2f - 1) {
            ProgressionBookScreen.renderTexture(BOOK_TEXTURE, matrices, guiLeft + bookWidth - 15, guiTop + 150, 30, 193, 28, 18, 512, 512);
            if (ProgressionBookScreen.isHovering(mouseX, mouseY, guiLeft + bookWidth - 15, guiTop + 150, 28, 18)) {
                ProgressionBookScreen.renderTexture(BOOK_TEXTURE, matrices, guiLeft + bookWidth - 15, guiTop + 150, 30, 232, 28, 18, 512, 512);
            } else {
                ProgressionBookScreen.renderTexture(BOOK_TEXTURE, matrices, guiLeft + bookWidth - 15, guiTop + 150, 30, 213, 28, 18, 512, 512);
            }
        }
        if (!openEntry.pages.isEmpty()) {
            int openPages = grouping * 2;
            for (int i = openPages; i < openPages + 2; i++) {
                if (i < openEntry.pages.size()) {
                    BookPage page = openEntry.pages.get(i);
                    if (i % 2 == 0) {
                        page.renderLeft(client, matrices, ProgressionBookScreen.screen.xOffset, ProgressionBookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
                    } else {
                        page.renderRight(client, matrices, ProgressionBookScreen.screen.xOffset, ProgressionBookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
                    }
                }
            }
        }
//        ScreenParticleHandler.renderParticles(BEFORE_TOOLTIPS); todo: screen particles
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        if (ProgressionBookScreen.isHovering(mouseX, mouseY, guiLeft - 13, guiTop + 150, 28, 18)) {
            previousPage(true);
            return true;
        }
        if (ProgressionBookScreen.isHovering(mouseX, mouseY, guiLeft + bookWidth - 15, guiTop + 150, 28, 18)) {
            nextPage();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        if (scroll > 0) {
            nextPage();
        } else {
            previousPage(false);
        }
        return super.mouseScrolled(mouseX, mouseY, scroll);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_E) {
            close(false);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void close() {
        close(false);
    }

    public void nextPage() {
        if (grouping < openObject.entry.pages.size() / 2f - 1) {
            grouping += 1;
            screen.playSound();
        }
    }

    public void previousPage(boolean ignore) {
        if (grouping > 0) {
            grouping -= 1;
            screen.playSound();
        } else {
            close(ignore);
        }
    }

    public void close(boolean ignoreNextInput) {
        ProgressionBookScreen.openScreen(ignoreNextInput);
        openObject.exit();
    }

    public void playSound() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 1.0f, player.world.random.nextFloat() * 0.4F + 0.8F);
    }

    public static void openScreen(EntryObject newObject) {
        MinecraftClient.getInstance().setScreen(getInstance(newObject));
        screen.playSound();
    }

    public static EntryScreen getInstance(EntryObject newObject) {
        if (screen == null || !newObject.equals(openObject)) {
            screen = new EntryScreen();
            openObject = newObject;
        }
        return screen;
    }
}