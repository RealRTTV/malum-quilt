package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Optional;

import static ca.rttv.malum.Malum.MODID;

public class SmeltingBookPage extends BookPage {
    public static final Codec<SmeltingBookPage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ItemStack.CODEC.fieldOf("input").forGetter(page -> page.inputStack),
        ItemStack.CODEC.fieldOf("output").forGetter(page -> page.outputStack)
    ).apply(instance, SmeltingBookPage::new));

    private final ItemStack inputStack;
    private final ItemStack outputStack;

    public SmeltingBookPage(ItemStack inputStack, ItemStack outputStack) {
        super(new Identifier(MODID, "textures/gui/book/pages/smelting_page.png"));
        this.inputStack = inputStack;
        this.outputStack = outputStack;
    }

    public SmeltingBookPage(Item inputItem, Item outputItem) {
        this(inputItem.getDefaultStack(), outputItem.getDefaultStack());
    }

    public SmeltingBookPage(JsonObject json) {
        super(new Identifier(MODID, "textures/gui/book/pages/smelting_page.png"));
        inputStack = Registry.ITEM.get(new Identifier(json.get("input").getAsString())).getDefaultStack();
        outputStack = json.get("output").isJsonObject() ? new ItemStack(Registry.ITEM.get(new Identifier(json.get("output").getAsString())), json.get("count").getAsInt()) : Registry.ITEM.get(new Identifier(json.get("output").getAsString())).getDefaultStack();
    }

    public static SmeltingBookPage fromInput(Item input) {
        final MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            return new SmeltingBookPage(ItemStack.EMPTY, ItemStack.EMPTY);
        }
        Optional<SmeltingRecipe> optional = client.world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, new SimpleInventory(input.getDefaultStack()), client.world);
        if (optional.isPresent()) {
            SmeltingRecipe recipe = optional.get();
            return new SmeltingBookPage(input.getDefaultStack(), recipe.getOutput());
        }
        return new SmeltingBookPage(ItemStack.EMPTY, ItemStack.EMPTY);
    }

    @Override
    public boolean isValid() {
        return !inputStack.isEmpty() && !outputStack.isEmpty();
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack poseStack, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        ProgressionBookScreen.renderItem(poseStack, inputStack, guiLeft + 67, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(poseStack, outputStack, guiLeft + 67, guiTop + 126, mouseX, mouseY);

    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float tickDelta) {
        ProgressionBookScreen.renderItem(matrices, inputStack, guiLeft + 209, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(matrices, outputStack, guiLeft + 209, guiTop + 126, mouseX, mouseY);
    }

    @Override
    public MalumPageTypeRegistry.PageType type() {
        return MalumPageTypeRegistry.SMELTING_PAGE_TYPE;
    }
}
