package ca.rttv.malum.util.handler;

import ca.rttv.malum.component.MalumComponents;
import ca.rttv.malum.component.SpiritLivingEntityComponent;
import ca.rttv.malum.config.CommonConfig;
import ca.rttv.malum.entity.boomerang.ScytheBoomerangEntity;
import ca.rttv.malum.item.SpiritCollectActivity;
import ca.rttv.malum.item.SpiritPouchItem;
import ca.rttv.malum.registry.MalumTags;
import ca.rttv.malum.util.helper.ItemHelper;
import ca.rttv.malum.util.helper.SpiritHelper;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class SpiritHarvestHandler {

    public static void exposeSoul(DamageSource source, float amount, LivingEntity target) {
        if (amount == 0) {
            return;
        }
        if (source.getAttacker() instanceof LivingEntity attacker) {
            ItemStack stack = attacker.getMainHandStack();
            if (source.getSource() instanceof ScytheBoomerangEntity) {
                stack = ((ScytheBoomerangEntity) source.getSource()).scythe;
            }
            if (stack.isIn(MalumTags.SOUL_HUNTER_WEAPON)) {
                MalumComponents.SPIRIT_COMPONENT.get(target).exposedSoul = 200;
            }
        }
    }

    public static void shatterSoul(DamageSource source, LivingEntity target) {
        LivingEntity attacker = null;
        if (source.getAttacker() instanceof LivingEntity directAttacker) {
            attacker = directAttacker;
        }
        if (attacker == null) {
            attacker = target.getAttacker();
        }
        if (attacker != null) {
            ItemStack stack = attacker.getMainHandStack();
            if (source.getSource() instanceof ScytheBoomerangEntity scytheBoomerang) {
                stack = scytheBoomerang.scythe;
            }
            if (!(target instanceof PlayerEntity)) {
                //  if (!source.getMsgId().equals(DamageSourceRegistry.VOODOO_NO_SHATTER.getMsgId())) {
                SpiritLivingEntityComponent component = MalumComponents.SPIRIT_COMPONENT.get(target);
                if (component.exposedSoul > 0 && !component.isSoulless() && (!CommonConfig.SOULLESS_SPAWNERS || (CommonConfig.SOULLESS_SPAWNERS && !component.isSpawnerSpawned()))) {
                    SpiritHelper.createSpiritsFromWeapon(target, attacker, stack);
                    component.setSoulless(true);
                }
               // }
            }
        }
    }

    public static void pickupSpirit(ItemStack stack, LivingEntity collector) {
        if (collector instanceof PlayerEntity playerEntity) {
            TrinketsApi.getTrinketComponent(playerEntity).orElseThrow().forEach((slot, trinket) -> {
                if (trinket.getItem() instanceof SpiritCollectActivity spiritCollectActivity) {
                    spiritCollectActivity.collect(stack, playerEntity, slot, trinket);
                }
            });
            for (DefaultedList<ItemStack> playerInventory : playerEntity.getInventory().combinedInventory) {
                for (ItemStack item : playerInventory) {
                    if (item.getItem() instanceof SpiritPouchItem) {
                        DefaultedList<ItemStack> stacks = DefaultedList.ofSize(27, ItemStack.EMPTY);
                        NbtCompound nbt = item.getOrCreateNbt();
                        if (nbt != null) {
                            Inventories.readNbt(nbt, stacks);
                        }
                        SimpleInventory inventory = new SimpleInventory(stacks.delegate.toArray(ItemStack[]::new));

                        ItemStack newStack = inventory.addStack(stack);
                        if (!newStack.isEmpty()) {
                            ItemHelper.giveItemToEntity(newStack, collector);
                        }

                        Inventories.writeNbt(nbt, inventory.stacks);

                        World world = playerEntity.world;
                        world.playSound(null, playerEntity.getX(), playerEntity.getY() + 0.5, playerEntity.getZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((world.random.nextFloat() - world.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                        return;
                    }
                }
            }
        }
        ItemHelper.giveItemToEntity(stack, collector);
    }
}