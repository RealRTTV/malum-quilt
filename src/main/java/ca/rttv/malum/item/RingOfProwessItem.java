package ca.rttv.malum.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class RingOfProwessItem extends TrinketItem implements SpiritCollectActivity {
    public RingOfProwessItem(Settings settings) {
        super(settings);
    }

    @Override
    public void collect(ItemStack spirit, LivingEntity livingEntity, SlotReference slot, ItemStack trinket) {
        int i = 1 + livingEntity.world.random.nextInt(1) + livingEntity.world.random.nextInt(2);

        while (i > 0) {
            int j = ExperienceOrbEntity.roundToOrbSize(i);
            i -= j;
            livingEntity.world.spawnEntity(new ExperienceOrbEntity(livingEntity.world, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), j));
        }
    }
}
