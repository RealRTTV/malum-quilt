package ca.rttv.malum.entity;

import ca.rttv.malum.item.IFloatingGlowItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.awt.*;

import static ca.rttv.malum.registry.MalumItemRegistry.SACRED_SPIRIT;

public class FloatingItemEntity extends FloatingEntity {
    private static final TrackedData<ItemStack> DATA_ITEM_STACK = DataTracker.registerData(FloatingItemEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    public ItemStack stack = ItemStack.EMPTY;

    public FloatingItemEntity(EntityType<? extends FloatingEntity> type, World world) {
        super(type, world);
    }

    public void setItem(ItemStack stack) {
        if (stack.getItem() instanceof IFloatingGlowItem glow) {
            setColor(glow.getColor(), glow.getEndColor());
        }
        if (!stack.isOf(this.getDefaultItem()) || stack.hasNbt()) {
            this.getDataTracker().set(DATA_ITEM_STACK, stack);
        }
    }
    public void setColor(Color color, Color endColor) {
        this.color = color;
        getDataTracker().set(DATA_COLOR, color.getRGB());
        this.endColor = endColor;
        getDataTracker().set(DATA_END_COLOR, endColor.getRGB());
    }
    @Override
    protected void initDataTracker() {
        this.getDataTracker().startTracking(DATA_ITEM_STACK, ItemStack.EMPTY);
        super.initDataTracker();
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (DATA_ITEM_STACK.equals(data)) {
            stack = getDataTracker().get(DATA_ITEM_STACK);
        }
        super.onTrackedDataSet(data);
    }

    protected ItemStack getItemRaw() {
        return this.getDataTracker().get(DATA_ITEM_STACK);
    }

    protected Item getDefaultItem() {
        return SACRED_SPIRIT;
    }

    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemstack;
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        ItemStack itemStack = this.getItemRaw();
        if (!itemStack.isEmpty()) {
            tag.put("Item", itemStack.writeNbt(new NbtCompound()));
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        ItemStack itemstack = ItemStack.fromNbt(tag.getCompound("Item"));
        this.setItem(itemstack);
    }
}
