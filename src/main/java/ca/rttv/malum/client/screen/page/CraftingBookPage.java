package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.client.screen.ProgressionBookScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Main.MODID;

public class CraftingBookPage extends BookPage {
    private final ItemStack outputStack;
    private final ItemStack[] inputStacks;

    public CraftingBookPage(ItemStack outputStack, ItemStack... inputStacks) {
        super(new Identifier(MODID, "textures/gui/book/pages/crafting_page.png"));
        this.outputStack = outputStack;
        this.inputStacks = inputStacks;
    }

    public CraftingBookPage(Item outputItem, Item... inputItems) {
        this(outputItem.getDefaultStack(), inputItems);
    }

    public CraftingBookPage(ItemStack outputStack, Item... inputItems) {
        super(new Identifier(MODID, "textures/gui/book/pages/crafting_page.png"));
        this.outputStack = outputStack;

        ItemStack[] inputStacks = new ItemStack[inputItems.length];
        for (int i = 0; i < inputItems.length; i++) {
            inputStacks[i] = inputItems[i].getDefaultStack();
        }
        this.inputStacks = inputStacks;
    }

    public static CraftingBookPage fullPage(Item output, Item input) {
        return fullPage(output.getDefaultStack(), input.getDefaultStack());
    }

    public static CraftingBookPage fullPage(ItemStack output, ItemStack input) {
        return new CraftingBookPage(output, input, input, input, input, input, input, input, input, input);
    }

    public static CraftingBookPage scythePage(Item scythe, Item metal, Item reagent) {
        return scythePage(scythe.getDefaultStack(), metal.getDefaultStack(), reagent.getDefaultStack());
    }

    public static CraftingBookPage scythePage(ItemStack scythe, ItemStack metal, ItemStack reagent) {
        ItemStack stick = Items.STICK.getDefaultStack();
        ItemStack empty = Items.AIR.getDefaultStack();
        return new CraftingBookPage(scythe, metal, metal, reagent, empty, stick, metal, stick, empty, empty);
    }

    public static CraftingBookPage resonatorPage(Item resonator, Item gem, Item metal, Item reagent) {
        return resonatorPage(resonator.getDefaultStack(), gem.getDefaultStack(), metal.getDefaultStack(), reagent.getDefaultStack());
    }

    public static CraftingBookPage resonatorPage(ItemStack resonator, ItemStack gem, ItemStack metal, ItemStack reagent) {
        ItemStack empty = Items.AIR.getDefaultStack();
        return new CraftingBookPage(resonator, empty, reagent, empty, metal, gem, metal, empty, reagent, empty);
    }

    public static CraftingBookPage ringPage(Item ring, Item material, Item reagent) {
        return ringPage(ring.getDefaultStack(), material.getDefaultStack(), reagent.getDefaultStack());
    }

    public static CraftingBookPage ringPage(ItemStack ring, ItemStack material, ItemStack reagent) {
        ItemStack empty = Items.AIR.getDefaultStack();
        return new CraftingBookPage(ring, empty, material, reagent, material, empty, material, empty, material, empty);
    }

    public static CraftingBookPage itemPedestalPage(Item pedestal, Item fullBlock, Item slab) {
        return itemPedestalPage(pedestal.getDefaultStack(), fullBlock.getDefaultStack(), slab.getDefaultStack());
    }

    public static CraftingBookPage itemPedestalPage(ItemStack pedestal, ItemStack fullBlock, ItemStack slab) {
        ItemStack empty = Items.AIR.getDefaultStack();
        return new CraftingBookPage(pedestal, slab, slab, slab, empty, fullBlock, empty, slab, slab, slab);
    }

    public static CraftingBookPage itemStandPage(Item stand, Item fullBlock, Item slab) {
        return itemStandPage(stand.getDefaultStack(), fullBlock.getDefaultStack(), slab.getDefaultStack());
    }

    public static CraftingBookPage itemStandPage(ItemStack stand, ItemStack fullBlock, ItemStack slab) {
        ItemStack empty = Items.AIR.getDefaultStack();
        return new CraftingBookPage(stand, empty, empty, empty, slab, slab, slab, fullBlock, fullBlock, fullBlock);
    }

    public static CraftingBookPage toolPage(Item tool, Item metal) {
        return toolPage(tool.getDefaultStack(), metal.getDefaultStack());
    }

    public static CraftingBookPage toolPage(ItemStack tool, ItemStack metal) {
        ItemStack stick = Items.STICK.getDefaultStack();
        ItemStack empty = Items.AIR.getDefaultStack();
        if (tool.getItem() instanceof SwordItem) {
            return new CraftingBookPage(tool, empty, metal, empty, empty, metal, empty, empty, stick, empty);
        }
        if (tool.getItem() instanceof AxeItem) {
            return new CraftingBookPage(tool, metal, metal, empty, metal, stick, empty, empty, stick, empty);
        }
        if (tool.getItem() instanceof HoeItem) {
            return new CraftingBookPage(tool, metal, metal, empty, empty, stick, empty, empty, stick, empty);
        }
        if (tool.getItem() instanceof ShovelItem) {
            return new CraftingBookPage(tool, empty, metal, empty, empty, stick, empty, empty, stick, empty);
        }
        if (tool.getItem() instanceof PickaxeItem) {
            return new CraftingBookPage(tool, metal, metal, metal, empty, stick, empty, empty, stick, empty);
        }
        return null;
    }

    @Override
    public void renderLeft(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float partialTicks) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < inputStacks.length && !inputStacks[index].isEmpty()) {
                    ItemStack itemStack = inputStacks[index];
                    int itemPosX = guiLeft + 45 + j * 22;
                    int itemPosY = guiTop + 34 + i * 22;
                    ProgressionBookScreen.renderItem(matrices, itemStack, itemPosX, itemPosY, mouseX, mouseY);
                }
            }
        }

        ProgressionBookScreen.renderItem(matrices, outputStack, guiLeft + 67, guiTop + 126, mouseX, mouseY);

    }

    @Override
    public void renderRight(MinecraftClient client, MatrixStack matrices, int guiTop, int guiLeft, int mouseX, int mouseY, float partialTicks) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < inputStacks.length && !inputStacks[index].isEmpty()) {
                    ItemStack itemStack = inputStacks[index];
                    int itemPosX = guiLeft + 187 + j * 22;
                    int itemPosY = guiTop + 34 + i * 22;
                    ProgressionBookScreen.renderItem(matrices, itemStack, itemPosX, itemPosY, mouseX, mouseY);
                }
            }
        }

        ProgressionBookScreen.renderItem(matrices, outputStack, guiLeft + 209, guiTop + 126, mouseX, mouseY);
    }
}
