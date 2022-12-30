package ca.rttv.malum.screen;

import ca.rttv.malum.item.SpiritItem;
import ca.rttv.malum.item.SpiritPouchItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

import static ca.rttv.malum.registry.MalumScreenHandlerRegistry.SPIRIT_POUCH_SCREEN_HANDLER;

public class SpiritPouchScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final ItemStack lockedStack;
    public final int lockedSlot;

    public SpiritPouchScreenHandler(@Nullable ScreenHandlerType<?> screenHandlerType, int syncId, PlayerInventory playerInventory, Inventory inventory, ItemStack lockedStack, int lockedSlot) {
        super(screenHandlerType, syncId);
        this.inventory = inventory;
        this.lockedStack = lockedStack;
        this.lockedSlot = lockedSlot;

        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9, 8 + j * 18, 18 + i * 18) { public boolean canInsert(ItemStack stack) { return stack.getItem() instanceof SpiritItem; } } );
            }
        }

        for (int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18) {
                    @Override
                    public boolean canTakeItems(PlayerEntity player) {
                        return lockedSlot != this.getIndex();
                    }
                });
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142) {
                @Override
                public boolean canTakeItems(PlayerEntity player) {
                    return lockedSlot != this.getIndex();
                }
            });
        }
    }

    public SpiritPouchScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(SPIRIT_POUCH_SCREEN_HANDLER, syncId, playerInventory, new SimpleInventory(27), playerInventory.player.getStackInHand(playerInventory.player.getActiveHand()), playerInventory.getSlotWithStack(playerInventory.player.getStackInHand(playerInventory.player.getActiveHand())));
        // how did I not think of using the players active hand, also its fascinating how the client just has a shadow for the inventory and sends and receives packets to update it
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack quickTransfer(PlayerEntity player, int index) {
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
        if (!(lockedStack.getItem() instanceof SpiritPouchItem)) {
            for (int i = 0, stacksSize = inventory.size(); i < stacksSize; i++) {
                ItemStack stack = inventory.getStack(i);
                player.dropItem(stack, true);
            }
            super.close(player);
            return;
        }
        NbtCompound nbt = lockedStack.getNbt();
        if (nbt == null) {
            nbt = new NbtCompound();
        }
        Inventories.writeNbt(nbt, ((SimpleInventory) this.inventory).stacks);
        lockedStack.setNbt(nbt);
        super.close(player);
    }
}
