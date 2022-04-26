package ca.rttv.malum.mixin;

import net.minecraft.client.gl.Program;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Shader.class)
public final class CoreShaderMixin  {

    @Redirect(method = "<init>", at = @At(value = "NEW", target = "net/minecraft/util/Identifier", ordinal = 0))
    private Identifier fixId(String arg, ResourceFactory factory, String name, VertexFormat format) {
        if (!name.contains(":")) {
            return new Identifier(arg);
        }
        Identifier split = new Identifier(name);
        return new Identifier(split.getNamespace(), "shaders/core/" + split.getPath() + ".json");
    }

    @ModifyVariable(method = "loadProgram", at = @At("STORE"), ordinal = 1)
    private static String fixPath(String path, final ResourceFactory factory, Program.Type type, String name) {
        if (!name.contains(":")) {
            return path;
        }
        Identifier split = new Identifier(name);
        return split.getNamespace() + ":shaders/core/" + split.getPath() + type.getFileExtension();
    }

}
