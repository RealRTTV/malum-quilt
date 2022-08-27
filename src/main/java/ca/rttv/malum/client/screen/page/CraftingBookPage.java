package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CraftingBookPage extends BookPage {
    public static final Codec<CraftingBookPage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ItemStack.CODEC.fieldOf("output").forGetter(page -> page.outputStack),
        ItemStack.CODEC.listOf().fieldOf("input").forGetter(page -> Arrays.asList(page.inputStacks))
    ).apply(instance, CraftingBookPage::new));

    public final ItemStack outputStack;
    public final ItemStack[] inputStacks;

    public CraftingBookPage(ItemStack outputStack, ItemStack... inputStacks) {
        this.outputStack = outputStack;
        this.inputStacks = inputStacks;
    }

    public CraftingBookPage(ItemStack output, List<ItemStack> input) {
        this(output, input.toArray(ItemStack[]::new));
    }

    @Override
    public MalumPageTypeRegistry.PageType<CraftingBookPage> type() {
        return MalumPageTypeRegistry.CRAFTING_PAGE_TYPE;
    }
}
