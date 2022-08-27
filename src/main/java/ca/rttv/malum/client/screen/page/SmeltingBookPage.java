package ca.rttv.malum.client.screen.page;

import ca.rttv.malum.registry.MalumPageTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;

public class SmeltingBookPage extends BookPage {
    public static final Codec<SmeltingBookPage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ItemStack.CODEC.fieldOf("input").forGetter(page -> page.inputStack),
        ItemStack.CODEC.fieldOf("output").forGetter(page -> page.outputStack)
    ).apply(instance, SmeltingBookPage::new));

    public final ItemStack inputStack;
    public final ItemStack outputStack;

    public SmeltingBookPage(ItemStack inputStack, ItemStack outputStack) {
        this.inputStack = inputStack;
        this.outputStack = outputStack;
    }

    @Override
    public boolean isValid() {
        return !inputStack.isEmpty() && !outputStack.isEmpty();
    }

    @Override
    public MalumPageTypeRegistry.PageType<SmeltingBookPage> type() {
        return MalumPageTypeRegistry.SMELTING_PAGE_TYPE;
    }
}
