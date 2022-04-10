package ca.rttv.malum.util.helper;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public final class ItemHelper {
    public static void applyEnchantments(LivingEntity user, Entity target, ItemStack stack) {
        EnchantmentHelper.Consumer consumer = (enchantment, level) ->
                enchantment.onTargetDamaged(user, target, level);
        if (user != null) {
            EnchantmentHelper.forEachEnchantment(consumer, user.getItemsEquipped());
        }

        if (user instanceof PlayerEntity) {
            EnchantmentHelper.forEachEnchantment(consumer, stack);
        }
    }

    public static void giveItemToEntity(ItemStack item, LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            ((PlayerEntity) entity).giveItemStack(item);
        } else {
            ItemEntity itemEntity = new ItemEntity(entity.world, entity.getX(), entity.getY() + 0.5, entity.getZ(), item);
            itemEntity.setPickupDelay(40);
            itemEntity.setVelocity(itemEntity.getVelocity().multiply(0, 1, 0));
            entity.world.spawnEntity(itemEntity);
        }
    }
}
