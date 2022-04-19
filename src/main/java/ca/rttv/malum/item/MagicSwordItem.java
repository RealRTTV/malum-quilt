package ca.rttv.malum.item;

import ca.rttv.malum.duck.MysticMirrorDuck;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

import static ca.rttv.malum.registry.MalumAttributeRegistry.MAGIC_DAMAGE;
import static ca.rttv.malum.registry.MalumAttributeRegistry.MAGIC_PROFICIENCY;
import static ca.rttv.malum.registry.MalumRegistry.MAGIC_DAMAGE_MODIFIER_ID;

public class MagicSwordItem extends SwordItem {
    private final Multimap<EntityAttribute, EntityAttributeModifier> magicAttributeModifiers;

    public MagicSwordItem(ToolMaterial toolMaterial, float damage, float speed, float magic, Settings settings) {
        super(toolMaterial, (int) damage, speed, settings);
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
        if (((MysticMirrorDuck) attacker).tryUseMysticMirror()) {
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
