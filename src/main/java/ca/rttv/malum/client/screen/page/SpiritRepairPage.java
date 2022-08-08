package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import ca.rttv.malum.recipe.SpiritRepairRecipe;
import ca.rttv.malum.registry.MalumPageTypeRegistry;
import ca.rttv.malum.util.helper.DataHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Predicate;

import static ca.rttv.malum.Malum.MODID;

public class SpiritRepairPage extends BookPage {
    public static final Codec<SpiritRepairPage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Identifier.CODEC.fieldOf("recipe").forGetter(page -> page.recipe.id())
    ).apply(instance, SpiritRepairPage::new));

    private final SpiritRepairRecipe recipe;

    public SpiritRepairPage(Predicate<SpiritRepairRecipe> predicate) {
        super(DataHelper.prefix("textures/gui/book/pages/spirit_repair_page.png"));
        final MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) { //this is null during datagen
            this.recipe = null;
            return;
        }
        this.recipe = SpiritRepairRecipe.getRecipe(client.world, predicate);
    }

    public SpiritRepairPage(Identifier id) {
        super(new Identifier(MODID, "textures/gui/book/pages/spirit_repair_page.png"));
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) {
            recipe = null;
        } else {
            Optional<? extends Recipe<?>> optional = client.world.getRecipeManager().get(id);
            recipe = optional.isPresent() && optional.get() instanceof SpiritRepairRecipe spiritRepairRecipe ? spiritRepairRecipe : null;
        }
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    public static SpiritRepairPage fromInput(Item inputItem) {
        return new SpiritRepairPage(recipe -> recipe.input().test(inputItem.getDefaultStack()));
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
        ItemStack[] repairMaterials = recipe.repairMaterial().getMatchingStacks();
        ItemStack repairMaterial;
        if (repairMaterials.length == 1) {
            repairMaterial = repairMaterials[0];
        } else {
            repairMaterial = repairMaterials[(int) ((client.world.getTime() / 15L) % repairMaterials.length)];
        }
        ItemStack outputStack = recipe.getOutput(inputStack);
        inputStack.setDamage((int) (inputStack.getMaxDamage() * recipe.durabilityPercentage()));
        outputStack.setDamage(0); // this is required cause im stupid
        ProgressionBookScreen.renderItem(matrices, repairMaterial, guiLeft + 48, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(matrices, inputStack, guiLeft + 86, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(matrices, outputStack, guiLeft + 67, guiTop + 126, mouseX, mouseY);
        if (recipe.spirits().isPresent()) {
            ProgressionBookScreen.renderComponents(matrices, recipe.spirits(), guiLeft + 65, guiTop + 16, mouseX, mouseY, false);
        }
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
        ItemStack[] repairMaterials = recipe.repairMaterial().getMatchingStacks();
        ItemStack repairMaterial;
        if (repairMaterials.length == 1) {
            repairMaterial = repairMaterials[0];
        } else {
            repairMaterial = repairMaterials[(int) ((client.world.getTime() / 15L) % repairMaterials.length)];
        }
        ItemStack outputStack = recipe.getOutput(inputStack);
        inputStack.setDamage((int) (inputStack.getMaxDamage() * recipe.durabilityPercentage()));
        outputStack.setDamage(0); // this is required cause im stupid
        ProgressionBookScreen.renderItem(matrices, repairMaterial, guiLeft + 190, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(matrices, inputStack, guiLeft + 228, guiTop + 59, mouseX, mouseY);
        ProgressionBookScreen.renderItem(matrices, outputStack, guiLeft + 209, guiTop + 126, mouseX, mouseY);
        if (recipe.spirits().isPresent()) {
            ProgressionBookScreen.renderComponents(matrices, recipe.spirits(), guiLeft + 207, guiTop + 16, mouseX, mouseY, false);
        }
    }

    @Override
    public MalumPageTypeRegistry.PageType type() {
        return MalumPageTypeRegistry.SPIRIT_REPAIR_PAGE_TYPE;
    }
}
