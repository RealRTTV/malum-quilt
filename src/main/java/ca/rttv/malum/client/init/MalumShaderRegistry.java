package ca.rttv.malum.client.init;

import ca.rttv.malum.util.helper.DataHelper;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class MalumShaderRegistry {
    public static final ManagedCoreShader ADDITIVE_TEXTURE = ShaderEffectManager.getInstance().manageCoreShader(DataHelper.prefix("additive_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT);
    public static final ManagedCoreShader ADDITIVE_PARTICLE = ShaderEffectManager.getInstance().manageCoreShader(DataHelper.prefix("additive_particle"), VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);

    public static final ManagedCoreShader DISTORTED_TEXTURE = ShaderEffectManager.getInstance().manageCoreShader(DataHelper.prefix("noise/distorted_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT);
    public static final ManagedCoreShader METALLIC_NOISE = ShaderEffectManager.getInstance().manageCoreShader(DataHelper.prefix("noise/metallic"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT);
    public static final ManagedCoreShader RADIAL_NOISE = ShaderEffectManager.getInstance().manageCoreShader(DataHelper.prefix("noise/radial_noise"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT);
    public static final ManagedCoreShader RADIAL_SCATTER_NOISE = ShaderEffectManager.getInstance().manageCoreShader(DataHelper.prefix("noise/radial_scatter_noise"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT);

    public static final ManagedCoreShader SCROLLING_TEXTURE = ShaderEffectManager.getInstance().manageCoreShader(DataHelper.prefix("vfx/scrolling_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT);
    public static final ManagedCoreShader TRIANGLE_TEXTURE = ShaderEffectManager.getInstance().manageCoreShader(DataHelper.prefix("vfx/triangle_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT);
    public static final ManagedCoreShader SCROLLING_TRIANGLE_TEXTURE = ShaderEffectManager.getInstance().manageCoreShader(DataHelper.prefix("vfx/scrolling_triangle_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT);


}
