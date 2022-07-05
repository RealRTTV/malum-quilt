package ca.rttv.malum.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public interface MalumDamageSourceRegistry {
    DamageSource VOODOO            = new DamageSource("voodoo").setUsesMagic();
    DamageSource VOODOO_NO_SHATTER = new DamageSource("no_shatter_voodoo").setUsesMagic();
    DamageSource FORCED_SHATTER    = new DamageSource("forced_shatter").setUsesMagic().setUnblockable();
    public static final String SCYTHE_SWEEP_DAMAGE = "scythe_sweep";

    static DamageSource causeVoodooDamage(Entity attacker) {
        return new EntityDamageSource("voodoo", attacker).setUsesMagic();
    }

    static DamageSource causeMagebaneDamage(Entity attacker) {
        return new EntityDamageSource("magebane", attacker).setThorns().setUsesMagic();
    }
    static DamageSource scytheSweepDamage(Entity attacker) {
        return new EntityDamageSource(SCYTHE_SWEEP_DAMAGE, attacker);
    }
}
