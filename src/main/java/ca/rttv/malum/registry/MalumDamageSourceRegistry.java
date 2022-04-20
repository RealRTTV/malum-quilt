package ca.rttv.malum.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class MalumDamageSourceRegistry {
    public static final DamageSource VOODOO = new DamageSource("voodoo").setUsesMagic();
    public static final DamageSource VOODOO_NO_SHATTER = new DamageSource("no_shatter_voodoo").setUsesMagic();
    public static final DamageSource FORCED_SHATTER = new DamageSource("forced_shatter").setUsesMagic().setUnblockable();

    public static DamageSource causeVoodooDamage(Entity attacker) {
        return new EntityDamageSource("voodoo", attacker).setUsesMagic();
    }
    public static DamageSource causeMagebaneDamage(Entity attacker) {
        return new EntityDamageSource("magebane", attacker).setThorns().setUsesMagic();
    }
}
