package ca.rttv.malum.mixin;

import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumSignTypeRegistry.RUNEWOOD_SIGN_TYPE;
import static ca.rttv.malum.registry.MalumSignTypeRegistry.SOULWOOD_SIGN_TYPE;

@Mixin(TexturedRenderLayers.class)
public final class TexturedRenderLayersMixin {
    @Inject(method = "createSignTextureId", at = @At("HEAD"), cancellable = true)
    private static void changeSignNamespace(SignType type, CallbackInfoReturnable<SpriteIdentifier> cir) {
        if (type == RUNEWOOD_SIGN_TYPE || type == SOULWOOD_SIGN_TYPE) {
            cir.setReturnValue(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, new Identifier(MODID, "entity/signs/" + type.getName())));
        }
    }
}
