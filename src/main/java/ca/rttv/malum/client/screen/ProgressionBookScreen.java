package ca.rttv.malum.client.screen;

import ca.rttv.malum.api.event.ProgressionBookEntriesSetEvent;
import ca.rttv.malum.client.screen.page.BookEntry;
import ca.rttv.malum.client.screen.page.BookObject;
import ca.rttv.malum.recipe.IngredientWithCount;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.lodestone.handlers.ScreenParticleHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.ColorUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ca.rttv.malum.Malum.MODID;
import static com.sammy.lodestone.systems.rendering.particle.screen.base.ScreenParticle.RenderOrder.BEFORE_TOOLTIPS;
import static org.lwjgl.opengl.GL11C.GL_SCISSOR_TEST;

public class ProgressionBookScreen extends Screen {
    public static final Identifier FRAME_TEXTURE = new Identifier(MODID, "textures/gui/book/frame.png");
    public static final Identifier FADE_TEXTURE = new Identifier(MODID, "textures/gui/book/fade.png");
    public static final Identifier BACKGROUND_TEXTURE = new Identifier(MODID, "textures/gui/book/background.png");
    public static ProgressionBookScreen screen;
    public static List<BookEntry> entries = new ArrayList<>();
    public static List<BookObject> objects;
    public final int parallax_width = 1024;
    public final int parallax_height = 2560;
    public final int bookWidth = 378;
    public final int bookHeight = 250;
    public final int bookInsideWidth = 344;
    public final int bookInsideHeight = 218;
    public float xOffset;
    public float yOffset;
    public float cachedXOffset;
    public float cachedYOffset;
    public boolean ignoreNextMouseInput;

    public ProgressionBookScreen() {
        super(Text.literal("malum.gui.book.title"));
        client = MinecraftClient.getInstance();
        ProgressionBookEntriesSetEvent.EVENT.invoker().addExtraEntry(entries);
        setupObjects();
    }

    public static boolean isHovering(double mouseX, double mouseY, int posX, int posY, int width, int height) {
        if (!isInView(mouseX, mouseY)) {
            return false;
        }
        return mouseX > posX && mouseX < posX + width && mouseY > posY && mouseY < posY + height;
    }

    public static boolean isInView(double mouseX, double mouseY) {
        int guiLeft = (screen.width - screen.bookWidth) / 2;
        int guiTop = (screen.height - screen.bookHeight) / 2;
        return !(mouseX < guiLeft + 17) && !(mouseY < guiTop + 14) && !(mouseX > guiLeft + (screen.bookWidth - 17)) && !(mouseY > (guiTop + screen.bookHeight - 14));
    }

    public static void renderTexture(Identifier texture, MatrixStack matrices, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);
        DrawableHelper.drawTexture(matrices, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
//        RenderHelper.blit(matrices, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
    }

    public static void renderTransparentTexture(Identifier texture, MatrixStack matrices, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        renderTexture(texture, matrices, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void renderItem(MatrixStack matrices, ItemStack stack, int posX, int posY, int mouseX, int mouseY) {
        final MinecraftClient client = MinecraftClient.getInstance();
        client.getItemRenderer().renderInGuiWithOverrides(stack, posX, posY);
        client.getItemRenderer().renderGuiItemOverlay(screen.textRenderer, stack, posX, posY, null);
        if (isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            screen.renderTooltip(matrices, stack.getTooltip(client.player, TooltipContext.Default.f_yceowjjc), mouseX, mouseY);
        }
    }

    public static void renderWrappingText(MatrixStack matrices, String text, int x, int y, int w) {
        final MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        text = Text.translatable(text).getString();
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        String line = "";

        for (String s : words) {
            if (textRenderer.getWidth(line) + textRenderer.getWidth(s) > w) {
                lines.add(line);
                line = s + " ";
            } else line += s + " ";
        }

        if (!line.isEmpty()) {
            lines.add(line);
        }

        for (int i = 0; i < lines.size(); i++) {
            String currentLine = lines.get(i);
            renderRawText(matrices, currentLine, x, y + i * (textRenderer.fontHeight + 1), glow(i / 4f));
        }
    }

    public static void renderText(MatrixStack stack, Text text, int x, int y) {
        String str = text.getString();
        renderRawText(stack, str, x, y, glow(0));
    }

    private static void renderRawText(MatrixStack matrices, String text, int x, int y, float glow) {
        TextRenderer textRenderer = screen.textRenderer;
        //182, 61, 183   227, 39, 228
        int r = (int) MathHelper.lerp(glow, 182, 227);
        int g = (int) MathHelper.lerp(glow, 61, 39);
        int b = (int) MathHelper.lerp(glow, 183, 228);

        textRenderer.draw(matrices, text, x - 1, y, ColorUtil.ARGB32.getArgb(96, 255, 210, 243));
        textRenderer.draw(matrices, text, x + 1, y, ColorUtil.ARGB32.getArgb(128, 240, 131, 232));
        textRenderer.draw(matrices, text, x, y - 1, ColorUtil.ARGB32.getArgb(128, 255, 183, 236));
        textRenderer.draw(matrices, text, x, y + 1, ColorUtil.ARGB32.getArgb(96, 236, 110, 226));

        textRenderer.draw(matrices, text, x, y, ColorUtil.ARGB32.getArgb(255, r, g, b));
    }

    public static float glow(float offset) {
        final MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return 0.0f;
        return MathHelper.sin(offset + client.player.world.getTime() / 40f) / 2f + 0.5f;
    }

    public static void openScreen(boolean ignoreNextMouseClick) {
        final MinecraftClient client = MinecraftClient.getInstance();
        client.setScreen(getInstance());
        screen.playSound();
        screen.ignoreNextMouseInput = ignoreNextMouseClick;
    }

    public static ProgressionBookScreen getInstance() {
        if (screen == null) {
            screen = new ProgressionBookScreen();
        }
        return screen;
    }

    public static void renderComponents(MatrixStack matrices, IngredientWithCount components, int posX, int posY, int mouseX, int mouseY, boolean vertical) {
        List<ItemStack> items = Arrays.stream(components.getEntries())
                                      .flatMap(entry -> entry.getStacks().stream())
                                      .distinct()
                                      .toList();
        ProgressionBookScreen.renderItemList(matrices, items, posX, posY, mouseX, mouseY, vertical);
    }

    private static void renderItemList(MatrixStack matrices, List<ItemStack> items, int posX, int posY, int mouseX, int mouseY, boolean vertical) {
        int slots = items.size();
        renderItemFrames(matrices, posX, posY, vertical, slots);
        if (vertical) {
            posX -= 10 * (slots - 1);
        } else {
            posY -= 10 * (slots - 1);
        }
        posX -= 19; // I don't know why
        posY += 21; // I don't know why
        for (ItemStack stack : items) {
            ProgressionBookScreen.renderItem(matrices, stack, posX, posY, mouseX, mouseY);
            if (vertical) {
                posY -= 20;
            } else {
                posX += 20;
            }
        }
    }

    public static void renderItemFrames(MatrixStack matrices, int posX, int posY, boolean vertical, int slots) {
        if (vertical) {
            posY -= 10 * (slots - 1);
        } else {
            posX -= 10 * (slots - 1);
        }
        //item slot
        for (int i = 0; i < slots; i++) {
            int offset = i * 20;
            int dx = posX + (vertical ? 0 : offset);
            int dy = posY + (vertical ? offset : 0);
            renderTexture(EntryScreen.BOOK_TEXTURE, matrices, dx, dy, 75, 192, 20, 20, 512, 512);

            if (vertical) {
                //bottom fade
                if (slots > 1 && i != slots - 1) {
                    renderTexture(EntryScreen.BOOK_TEXTURE, matrices, posX + 1, dy + 19, 75, 213, 18, 2, 512, 512);
                }
                //bottommost fade
                if (i == slots - 1) {
                    renderTexture(EntryScreen.BOOK_TEXTURE, matrices, dx + 1, dy + 19, 75, 216, 18, 2, 512, 512);
                }
            } else {
                //bottom fade
                renderTexture(EntryScreen.BOOK_TEXTURE, matrices, dx + 1, posY + 19, 75, 216, 18, 2, 512, 512);
                if (slots > 1 && i != slots - 1) {
                    //side fade
                    renderTexture(EntryScreen.BOOK_TEXTURE, matrices, dx + 19, posY, 96, 192, 2, 20, 512, 512);
                }
            }
        }

        //crown
        int crownLeft = posX + 5 + (vertical ? 0 : 10 * (slots - 1));
        renderTexture(EntryScreen.BOOK_TEXTURE, matrices, crownLeft, posY - 5, 128, 192, 10, 6, 512, 512);

        //side bars
        if (vertical) {
            renderTexture(EntryScreen.BOOK_TEXTURE, matrices, posX - 4, posY - 4, 99, 200, 28, 7, 512, 512);
            renderTexture(EntryScreen.BOOK_TEXTURE, matrices, posX - 4, posY + 17 + 20 * (slots - 1), 99, 192, 28, 7, 512, 512);
        }
        // top bars
        else {
            renderTexture(EntryScreen.BOOK_TEXTURE, matrices, posX - 4, posY - 4, 59, 192, 7, 28, 512, 512);
            renderTexture(EntryScreen.BOOK_TEXTURE, matrices, posX + 17 + 20 * (slots - 1), posY - 4, 67, 192, 7, 28, 512, 512);
        }
    }

    public void setupObjects() {
        if (client == null) return;
        objects = new ArrayList<>();
        this.width = client.getWindow().getScaledWidth();
        this.height = client.getWindow().getScaledHeight();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int coreX = guiLeft + bookInsideWidth;
        int coreY = guiTop + bookInsideHeight;
        int width = 40;
        int height = 48;
        for (BookEntry entry : entries) {
            BookObject object = entry.objectSupplier.getBookObject(entry, coreX + entry.xOffset * width, coreY - entry.yOffset * height);
            objects.add(object);
            if (entry.id.equals(new Identifier("malum", "introduction"))) {
                faceObject(object);
            }
        }
    }

    public void faceObject(BookObject object) {
        if (client == null) return;
        this.width = client.getWindow().getScaledWidth();
        this.height = client.getWindow().getScaledHeight();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        xOffset = -object.posX + guiLeft + bookInsideWidth;
        yOffset = -object.posY + guiTop + bookInsideHeight;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, partialTicks);
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;

        renderBackground(BACKGROUND_TEXTURE, matrices, 0.1f, 0.4f);
        GL11.glEnable(GL_SCISSOR_TEST);
        cut();

        renderEntries(matrices, mouseX, mouseY, partialTicks);
        ScreenParticleHandler.renderParticles(BEFORE_TOOLTIPS);
        GL11.glDisable(GL_SCISSOR_TEST);

        renderTransparentTexture(FADE_TEXTURE, matrices, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        renderTexture(FRAME_TEXTURE, matrices, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        lateEntryRender(matrices, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        xOffset += dragX;
        yOffset += dragY;
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        cachedXOffset = xOffset;
        cachedYOffset = yOffset;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (ignoreNextMouseInput) {
            ignoreNextMouseInput = false;
            return super.mouseReleased(mouseX, mouseY, button);
        }
        if (xOffset != cachedXOffset || yOffset != cachedYOffset) {
            return super.mouseReleased(mouseX, mouseY, button);
        }
        for (BookObject object : objects) {
            if (object.isHovering(xOffset, yOffset, mouseX, mouseY)) {
                object.click(xOffset, yOffset, mouseX, mouseY);
                break;
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (client == null) return false;
        if (keyCode == client.options.inventoryKey.getDefaultKey().getKeyCode()) {
            closeScreen();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void renderEntries(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        for (int i = objects.size() - 1; i >= 0; i--) {
            BookObject object = objects.get(i);
            boolean isHovering = object.isHovering(xOffset, yOffset, mouseX, mouseY);
            object.isHovering = isHovering;
            object.hover = isHovering ? Math.min(object.hover++, object.hoverCap()) : Math.max(object.hover - 1, 0);
            object.render(client, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
        }
    }

    public void lateEntryRender(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        for (int i = objects.size() - 1; i >= 0; i--) {
            BookObject object = objects.get(i);
            object.lateRender(client, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
        }
    }

    public void renderBackground(Identifier texture, MatrixStack matrices, float xModifier, float yModifier) {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 17;
        int insideTop = guiTop + 14;
        float uOffset = (parallax_width - xOffset) * xModifier;
        float vOffset = Math.min(parallax_height - bookInsideHeight, (parallax_height - bookInsideHeight - yOffset * yModifier));
        if (vOffset <= parallax_height / 2f) {
            vOffset = parallax_height / 2f;
        }
        if (uOffset <= 0) {
            uOffset = 0;
        }
        if (uOffset > (bookInsideWidth - 8) / 2f) {
            uOffset = (bookInsideWidth - 8) / 2f;
        }
        renderTexture(texture, matrices, insideLeft, insideTop, uOffset, vOffset, bookInsideWidth, bookInsideHeight, parallax_width / 2, parallax_height / 2);
    }

    public void cut() {
        if (client == null) return;
        int scale = (int) client.getWindow().getScaleFactor();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 17;
        int insideTop = guiTop + 18;
        GL11.glScissor(insideLeft * scale, insideTop * scale, bookInsideWidth * scale, (bookInsideHeight + 1) * scale); // do not ask why the 1 is needed please
    }

    public void playSound() {
        if (client == null || client.player == null) return;
        PlayerEntity playerEntity = client.player;
        playerEntity.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }
}
