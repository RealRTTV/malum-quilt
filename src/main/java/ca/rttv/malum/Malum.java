package ca.rttv.malum;

import ca.rttv.malum.item.MItems;
import net.fabricmc.api.ModInitializer;

public class Malum implements ModInitializer {
    @Override
    public void onInitialize() {
        MItems.init();
    }
}
