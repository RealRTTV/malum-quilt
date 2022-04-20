package ca.rttv.malum.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;

import static ca.rttv.malum.registry.MalumAttributeRegistry.MAGIC_DAMAGE;
import static ca.rttv.malum.registry.MalumAttributeRegistry.MAGIC_PROFICIENCY;
import static ca.rttv.malum.registry.MalumRegistry.MAGIC_DAMAGE_MODIFIER_ID;
import static ca.rttv.malum.registry.MalumRegistry.NECKLACE_OF_THE_MYSTIC_MIRROR;

public class MagicAxeItem extends AxeItem {
    private final Multimap<EntityAttribute, EntityAttributeModifier> magicAttributeModifiers;

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    public MagicAxeItem(ToolMaterial toolMaterial, float damage, float speed, float magic, Settings settings) {
        super(toolMaterial, damage, speed, settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(
                EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", this.getAttackDamage(), EntityAttributeModifier.Operation.ADDITION)
        );
        builder.put(
                EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", speed, EntityAttributeModifier.Operation.ADDITION)
        );
        builder.put(
                MAGIC_DAMAGE,
                new EntityAttributeModifier(MAGIC_DAMAGE_MODIFIER_ID, "Weapon modifier", magic, EntityAttributeModifier.Operation.ADDITION)
        );
        magicAttributeModifiers = builder.build();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int lastDamageTaken = (int) target.lastDamageTaken;
        target.lastDamageTaken = 0;
        target.damage(DamageSource.MAGIC, (float) (attacker.getAttributeValue(MAGIC_DAMAGE) + 0.5f * attacker.getAttributeValue(MAGIC_PROFICIENCY)));
        target.lastDamageTaken += lastDamageTaken;
        if ((TrinketsApi.getTrinketComponent(attacker).orElseThrow().isEquipped(NECKLACE_OF_THE_MYSTIC_MIRROR))) {
            TrinketsApi.getTrinketComponent(attacker).orElseThrow().forEach((slot, trinket) -> {
                if (trinket.getItem() instanceof SpiritCollectActivity spiritCollectActivity) {
                    spiritCollectActivity.collect(stack, attacker, slot, trinket);
                }
            });
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.magicAttributeModifiers : super.getAttributeModifiers(slot);
    }
}
