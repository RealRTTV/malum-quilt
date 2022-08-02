package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.recipe.IngredientWithCount;
import ca.rttv.malum.recipe.SpiritInfusionRecipe;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static ca.rttv.malum.Malum.MODID;

public class SpiritInfusionPage extends BookPage {
    public static final Codec<SpiritInfusionPage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Identifier.CODEC.fieldOf("recipe").forGetter(page -> page.recipe.id())
    ).apply(instance, SpiritInfusionPage::new));

    public static final int[] UOFFSET = {360, 393, 393, 360, 327, 294, 294, 327};
    public static final int[] VOFFSET = {  1,   1,  53,  34,   1,   1, 129, 110};
    private final SpiritInfusionRecipe recipe;

    public SpiritInfusionPage(Predicate<SpiritInfusionRecipe> predicate) {
        super(new Identifier(MODID, "textures/gui/book/pages/spirit_infusion_page.png"));
        final MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            this.recipe = null;
            return;
        }
        this.recipe = SpiritInfusionRecipe.getRecipe(client.world, predicate);
    }

    public SpiritInfusionPage(SpiritInfusionRecipe recipe) {
        super(new Identifier(MODID, "textures/gui/book/pages/spirit_infusion_page.png"));
        this.recipe = recipe;
    }

    public SpiritInfusionPage(JsonObject json) {
        this(new Identifier(json.get("recipe").getAsString()));
    }

    public SpiritInfusionPage(Identifier id) {
        super(new Identifier(MODID, "textures/gui/book/pages/spirit_infusion_page.png"));
        final MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) {
            recipe = null;
        } else {
            Optional<? extends Recipe<?>> optional = client.world.getRecipeManager().get(id);
            recipe = optional.isPresent() && optional.get() instanceof SpiritInfusionRecipe spiritInfusionRecipe ? spiritInfusionRecipe : null;
        }
    }

    public static SpiritInfusionPage fromOutput(Item output) {
        return new SpiritInfusionPage(recipe -> recipe.getOutput().isOf(output));
    }

    public static SpiritInfusionPage fromId(Identifier id) {
        return new SpiritInfusionPage(recipe -> recipe.getId().equals(id));
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        ItemStack[] inputStacks = recipe.input().getMatchingStacks();
        ItemStack inputStack;
        if (inputStacks.length == 1) {
            inputStack = inputStacks[0];
        } else {
            inputStack = inputStacks[(int) ((client.world.getTime() / 20L) % inputStacks.length)];
        }
        ItemStack outputStack = recipe.output();
        if (recipe.extraItems().isPresent()) {
            renderIngredients(matrices, guiLeft + 105, guiTop + 51, mouseX, mouseY, recipe.extraItems());
        }
        renderItems(matrices, guiLeft + 15, guiTop + 51, mouseX, mouseY, List.of(recipe.spirits().getEntries()));
        ProgressionBookScreen.renderItem(matrices, inputStack, guiLeft + 67, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(matrices, outputStack, guiLeft + 67, guiTop + 126, mouseX, mouseY);
    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        ItemStack[] inputStacks = recipe.input().getMatchingStacks();
        ItemStack inputStack;
        if (inputStacks.length == 1) {
            inputStack = inputStacks[0];
        } else {
            inputStack = inputStacks[(int) ((client.world.getTime() / 20L) % inputStacks.length)];
        }
        ItemStack outputStack = recipe.output();
        if (recipe.extraItems().isPresent()) {
            renderIngredients(matrices, guiLeft + 247, guiTop + 51, mouseX, mouseY, recipe.extraItems());
        }
        renderItems(matrices, guiLeft + 157, guiTop + 51, mouseX, mouseY, Arrays.asList(recipe.spirits().getEntries()));
        ProgressionBookScreen.renderItem(matrices, inputStack, guiLeft + 209, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(matrices, outputStack, guiLeft + 209, guiTop + 126, mouseX, mouseY);
    }

    public void renderIngredients(MatrixStack matrices, int left, int top, int mouseX, int mouseY, IngredientWithCount ingredients) {
        renderItems(matrices, left, top, mouseX, mouseY, List.of(ingredients.getEntries()));
    }

    public void renderItems(MatrixStack matrices, int left, int top, int mouseX, int mouseY, List<IngredientWithCount.Entry> extraItems) {
        int index = extraItems.size() - 1;
        int textureHeight = 32 + index * 19;
        int offset = (int) (6.5f * index);
        top -= offset;
        int uOffset = UOFFSET[index];
        int vOffset = VOFFSET[index];
        ProgressionBookScreen.renderTexture(TEXTURE, matrices, left, top, uOffset, vOffset, 32, textureHeight, 512, 512);

        for (int i = 0; i < extraItems.size(); i++) {
            ItemStack stack = extraItems.get(i).getStacks().get(0);
            ProgressionBookScreen.renderItem(matrices, stack, left + 8, top + 8 + 19 * i, mouseX, mouseY);
        }
    }
}
