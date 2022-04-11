package ca.rttv.malum.mixin;

import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

import static ca.rttv.malum.Malum.MODID;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin {// todo: fix this, its 2 layers of model sooo
    @Shadow @Final private static Map<String, Identifier> ARMOR_TEXTURE_CACHE;

    @Inject(method = "getArmorTexture", at = @At("HEAD"), cancellable = true)
    private void getArmorTexture(ArmorItem item, boolean legs, String overlay, CallbackInfoReturnable<Identifier> cir) {
        if (item.getMaterial().getName().equals("soul_cloak")) {
            String string = "textures/models/armor/" + item.getMaterial().getName() + ".png";
            cir.setReturnValue(ARMOR_TEXTURE_CACHE.computeIfAbsent(string, path -> new Identifier(MODID, path)));
        }
    }
}
