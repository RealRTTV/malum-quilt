package ca.rttv.malum.registry;

import ca.rttv.malum.util.block.entity.IAltarAccelerator;

public interface MalumAcceleratorTypeRegistry {
    IAltarAccelerator.AltarAcceleratorType RUNEWOOD_ACCELERATOR_TYPE  = registerAltarAcceleratorType("runewood",  1);
    IAltarAccelerator.AltarAcceleratorType BRILLIANT_ACCELERATOR_TYPE = registerAltarAcceleratorType("brilliant", 0);

    static IAltarAccelerator.AltarAcceleratorType registerAltarAcceleratorType(String id, int acceleration) {
        return new IAltarAccelerator.AltarAcceleratorType(acceleration, id);
    }

    static void init() {

    }
}
