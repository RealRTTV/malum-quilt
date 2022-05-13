package ca.rttv.malum.enchantment;

import ca.rttv.malum.entity.ScytheBoomerangEntity;
import ca.rttv.malum.registry.MalumEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ReboundEnchantment extends Enchantment {
    public ReboundEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... equipmentSlots) {
        super(weight, target, equipmentSlots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public static TypedActionResult<ItemStack> onRightClickItem(PlayerEntity player, World world, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!player.isSpectator()) {
            int enchantmentLevel = EnchantmentHelper.getLevel(MalumEnchantments.REBOUND, stack);
            if (enchantmentLevel > 0) {
                if (!world.isClient) {
                    player.setStackInHand(hand, ItemStack.EMPTY);
                    double baseDamage = player.getAttributes().getValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                    float multiplier = 1.2f;
                    double damage = 1.0F + baseDamage * multiplier;

                    int slot = hand == Hand.OFF_HAND ? player.getInventory().size() - 1 : player.getInventory().selectedSlot;
                    ScytheBoomerangEntity entity = new ScytheBoomerangEntity(world);
                    entity.setPos(player.getPos().x, player.getPos().y + player.getHeight() / 2f, player.getPos().z);

                    entity.setData((float) damage, player.getUuid(), slot, stack);
                    entity.getDataTracker().set(ScytheBoomerangEntity.SCYTHE, stack);

                    entity.shootFromRotation(player, player.getPitch(), player.getYaw(), 0.0F, (float) (1.5F /* + player.getAttributeValue(MalumAttributeRegistry.SCYTHE_PROFICIENCY) * 0.125f */), 0F);
                    world.spawnEntity(entity);
                }
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                return TypedActionResult.success(stack, world.isClient);
            }
        }
        return TypedActionResult.pass(stack);
    }
}
