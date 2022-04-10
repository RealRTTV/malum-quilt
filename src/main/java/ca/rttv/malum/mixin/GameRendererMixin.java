package ca.rttv.malum.mixin;

import ca.rttv.malum.client.init.MalumShaderRegistry;
import ca.rttv.malum.util.handler.RenderHandler;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gl.Program;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Shader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V"))
    private void malum$renderWorldLast(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {
        RenderHandler.renderLast(matrix);
    }
    @Inject(method = "loadShaders", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 53, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    private void malum$registerShaders(ResourceManager manager, CallbackInfo ci, List<Program> list, List<Pair<Shader, Consumer<Shader>>> list2) throws IOException {
        MalumShaderRegistry.init(manager);
        list2.addAll(MalumShaderRegistry.shaderList);
    }
}