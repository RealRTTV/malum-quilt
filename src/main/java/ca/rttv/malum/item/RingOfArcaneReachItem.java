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

import static ca.rttv.malum.registry.MalumAttributeRegistry.SPIRIT_REACH;

public class RingOfArcaneReachItem extends TrinketItem {
    public RingOfArcaneReachItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        Multimap<EntityAttribute, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, uuid);
        modifiers.put(
                SPIRIT_REACH,
                new EntityAttributeModifier(uuid, "Trinket modifier", 8.0f, EntityAttributeModifier.Operation.ADDITION)
        );
        return modifiers;
    }
}
