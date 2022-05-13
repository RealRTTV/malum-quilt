package ca.rttv.malum.item;

import ca.rttv.malum.screen.SpiritPouchScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import static ca.rttv.malum.registry.MalumScreenHandlerRegistry.SPIRIT_POUCH_SCREEN_HANDLER;

public class SpiritPouchItem extends Item {
    public SpiritPouchItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) {
            player.swingHand(hand);
            return TypedActionResult.success(player.getStackInHand(hand));
        } else {
            player.openHandledScreen(new NamedScreenHandlerFactory() {
                @Override
                public Text getDisplayName() {
                    return Text.of("");
                }

                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                    DefaultedList<ItemStack> stacks = DefaultedList.ofSize(27, ItemStack.EMPTY);
                    NbtCompound nbt = playerEntity.getStackInHand(hand).getNbt();
                    if (nbt != null) {
                        Inventories.readNbt(nbt, stacks);
                    }
                    return new SpiritPouchScreenHandler(SPIRIT_POUCH_SCREEN_HANDLER, syncId, playerInventory, new SimpleInventory(stacks.toArray(ItemStack[]::new)), playerEntity.getStackInHand(hand));
                }
            });
            return super.use(world, player, hand);
        }
    }
}
