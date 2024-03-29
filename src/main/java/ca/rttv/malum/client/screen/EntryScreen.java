package ca.rttv.malum.client.screen;

import ca.rttv.malum.client.recipe.BookPageRenderer;
import ca.rttv.malum.client.screen.page.BookEntry;
import ca.rttv.malum.client.screen.page.BookPage;
import ca.rttv.malum.client.screen.page.EntryObject;
import ca.rttv.malum.registry.MalumPageRendererRegistry;
import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.sammy.lodestone.handlers.ScreenParticleHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import static ca.rttv.malum.Malum.MODID;
import static com.sammy.lodestone.systems.rendering.particle.screen.base.ScreenParticle.RenderOrder.BEFORE_TOOLTIPS;

public class EntryScreen extends Screen {
    public static final Identifier BOOK_TEXTURE = new Identifier(MODID, "textures/gui/book/entry.png");

    public static EntryScreen screen;
    public static EntryObject openObject;

    public final int bookWidth = 292;
    public final int bookHeight = 190;

    public int grouping;

    public EntryScreen() {
        super(Text.literal("malum.gui.entry.title"));
    }

    public static void openScreen(EntryObject newObject) {
        final MinecraftClient client = MinecraftClient.getInstance();
        client.setScreen(getInstance(newObject));
        screen.playSound();
    }

    public static EntryScreen getInstance(EntryObject newObject) {
        if (screen == null || !newObject.equals(openObject)) {
            screen = new EntryScreen();
            openObject = newObject;
        }
        return screen;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
        BookEntry openEntry = openObject.entry;
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, tickDelta);
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        ProgressionBookScreen.renderTexture(BOOK_TEXTURE, matrices, guiLeft, guiTop, 1, 1, bookWidth - 1, bookHeight - 1, 512, 512);
        if (!openEntry.pages.isEmpty()) {
            int openPages = grouping * 2;
            for (int i = openPages; i < openPages + 2; i++) {
                if (i < openEntry.pages.size()) {
                    BookPage page = openEntry.pages.get(i);
                    //noinspection ConstantConditions -- should be ok
                    BookPageRenderer<?> renderer = MalumPageRendererRegistry.PageRendererFactory.create(MalumPageRendererRegistry.PAGE_RENDERER_FACTORY.get(MalumPageTypeRegistry.PAGE_TYPE.getId(page.type())), page);
                    if (i % 2 == 0) {
                        renderer.renderBackgroundLeft(client, matrices, guiLeft, guiTop, mouseX, mouseY, tickDelta);
                    } else {
                        renderer.renderBackgroundRight(client, matrices, guiLeft, guiTop, mouseX, mouseY, tickDelta);
                    }
                }
            }
        }

        // buttons
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
                    BookPageRenderer<?> renderer = openEntry.pageRenderers().get(i);
                    if (i % 2 == 0) {
                        renderer.renderLeft(client, matrices, guiTop, guiLeft, mouseX, mouseY, tickDelta);
                    } else {
                        renderer.renderRight(client, matrices, guiTop, guiLeft, mouseX, mouseY, tickDelta);
                    }
                }
            }
        }
        ScreenParticleHandler.renderParticles(BEFORE_TOOLTIPS);
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
    public boolean isPauseScreen() {
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
    public void closeScreen() {
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
        if (client == null) {
            return;
        }
        PlayerEntity player = client.player;
        if (player == null) {
            return;
        }
        player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 1.0f, player.world.random.nextFloat() * 0.4F + 0.8F);
    }
}
