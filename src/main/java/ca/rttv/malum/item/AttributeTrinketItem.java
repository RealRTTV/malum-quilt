package ca.rttv.malum.item;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.UUID;
import java.util.function.BiConsumer;

public class AttributeTrinketItem extends TrinketItem {
    private final BiConsumer<Multimap<EntityAttribute, EntityAttributeModifier>, UUID> attributes;

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        Multimap<EntityAttribute, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, uuid);
        attributes.accept(modifiers, uuid);
        return modifiers;
    }

    public AttributeTrinketItem(Item.Settings settings, BiConsumer<Multimap<EntityAttribute, EntityAttributeModifier>, UUID> attributes) {
        super(settings);
        this.attributes = attributes;
    }
}
