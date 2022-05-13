package ca.rttv.malum.screen;

import ca.rttv.malum.item.MalumSpiritItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

import static ca.rttv.malum.registry.MalumScreenHandlerRegistry.SPIRIT_POUCH_SCREEN_HANDLER;

public class SpiritPouchScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final ItemStack stack;

    public SpiritPouchScreenHandler(@Nullable ScreenHandlerType<?> screenHandlerType, int syncId, PlayerInventory playerInventory, Inventory inventory, ItemStack stack) {
        super(screenHandlerType, syncId);
        this.inventory = inventory;
        this.stack = stack;

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9, 8 + j * 18, 18 + i * 18) { public boolean canInsert(ItemStack stack) { return stack.getItem() instanceof MalumSpiritItem; } } );
            }
        }

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public SpiritPouchScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(SPIRIT_POUCH_SCREEN_HANDLER, syncId, playerInventory, new SimpleInventory(27), new ItemStack((ItemConvertible) null));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index < this.inventory.size()) {
                if (!this.insertItem(itemStack2, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemStack;
    }

    @Override
    public void close(PlayerEntity player) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) {
            nbt = new NbtCompound();
        }
        Inventories.writeNbt(nbt, ((SimpleInventory) this.inventory).stacks);
        stack.setNbt(nbt);
        super.close(player);
    }
}
