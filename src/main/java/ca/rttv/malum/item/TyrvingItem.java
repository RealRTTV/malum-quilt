package ca.rttv.malum.item;

import ca.rttv.malum.util.helper.SpiritHelper;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static ca.rttv.malum.registry.MalumAttributeRegistry.MAGIC_PROFICIENCY;
import static ca.rttv.malum.registry.MalumItemRegistry.NECKLACE_OF_THE_MYSTIC_MIRROR;

public class TyrvingItem extends SwordItem {
    public TyrvingItem(ToolMaterial material, float damage, float speed, Item.Settings settings) {
        super(material, (int) damage, speed, settings);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return true;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int lastDamageTaken = (int) target.lastDamageTaken;
        target.lastDamageTaken = 0;
        int spiritSum = SpiritHelper.getSpiritItemStacks(target).stream()
            .mapToInt(ItemStack::getCount)
            .reduce(0, Integer::sum);
        target.damage(DamageSource.magic(null, attacker), (float) ((spiritSum == 0 ? 3 : spiritSum) + 0.5f * attacker.getAttributeValue(MAGIC_PROFICIENCY)));
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
}
