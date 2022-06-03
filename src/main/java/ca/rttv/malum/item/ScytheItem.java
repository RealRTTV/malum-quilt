package ca.rttv.malum.item;

import ca.rttv.malum.client.init.MalumParticleRegistry;
import ca.rttv.malum.util.helper.SpiritHelper;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import static ca.rttv.malum.registry.MalumAttributeRegistry.MAGIC_DAMAGE;
import static ca.rttv.malum.registry.MalumAttributeRegistry.MAGIC_DAMAGE_MODIFIER_ID;

public class ScytheItem extends SwordItem {
    public ScytheItem(ToolMaterial material, float damage, float speed, float magic, Settings settings) {
        super(material, (int) damage, speed, settings);
        this.attackDamage = damage + material.getAttackDamage();
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(
                EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", this.attackDamage, EntityAttributeModifier.Operation.ADDITION)
        );
        builder.put(
                EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", speed, EntityAttributeModifier.Operation.ADDITION)
        );
        if (magic > 0.0f) {
            builder.put(
                    MAGIC_DAMAGE,
                    new EntityAttributeModifier(MAGIC_DAMAGE_MODIFIER_ID, "Weapon modifier", magic, EntityAttributeModifier.Operation.ADDITION)
            );
        }
        this.attributeModifiers = builder.build();
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return true;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            spawnSweepParticles((PlayerEntity) attacker, MalumParticleRegistry.SCYTHE_SWEEP_ATTACK_PARTICLE);
            attacker.world.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, attacker.getSoundCategory(), 1, 1);
        }
        SpiritHelper.applySpiritDamage(stack, target, attacker);
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
