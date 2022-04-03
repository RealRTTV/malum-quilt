package ca.rttv.malum.registry;

import ca.rttv.malum.Malum;
import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static ca.rttv.malum.Malum.MODID;

public class MalumBoatTypes {
    public static TerraformBoatType runewood;
    public static void init() {
        Item juniper_boat = TerraformBoatItemHelper.registerBoatItem(new Identifier(MODID, "runewood_boat"), () -> runewood, Malum.MALUM);
        runewood = new TerraformBoatType.Builder().item(juniper_boat).build();
        Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(MODID, "runewood"), runewood);

    }
}
