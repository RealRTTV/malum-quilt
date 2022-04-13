package ca.rttv.malum.item;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.registry.SpiritTypeRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class ScytheItem extends SwordItem {
    public ScytheItem(ToolMaterial material, int bonusMaterialDamage, float attackSpeed, Settings settings) {
        super(material, bonusMaterialDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker instanceof PlayerEntity) {
            SoundEvent sound;
//            if (ItemHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE)) {
//                spawnSweepParticles((PlayerEntity) attacker, ParticleRegistry.SCYTHE_CUT_ATTACK_PARTICLE.get());
//                sound = MalumSoundRegistry.SCYTHE_CUT.get();
//            } else {
                spawnSweepParticles((PlayerEntity) attacker, MalumParticleRegistry.SCYTHE_SWEEP_ATTACK_PARTICLE);
                sound = SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP;
//            }
            attacker.world.playSound(null, target.getX(), target.getY(), target.getZ(), sound, attacker.getSoundCategory(), 1, 1);
        }
        return super.postHit(stack, target, attacker);
    }
    public void spawnSweepParticles(PlayerEntity player, DefaultParticleType type) {
        double d0 = (-MathHelper.sin(player.getYaw() * ((float) Math.PI / 180F)));
        double d1 = MathHelper.cos(player.getYaw() * ((float) Math.PI / 180F));
        if (player.world instanceof ServerWorld) {
            ((ServerWorld) player.world).spawnParticles(type, player.getX() + d0, player.getBodyY(0.5D), player.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }
    }
}
