package ca.rttv.malum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Main.MODID;

public class SoulStainedSteelArmorModel extends ArmorModel {
    public static final EntityModelLayer LAYER = new EntityModelLayer(new Identifier(MODID, "textures/armor/soul_stained_steel"), "main");

    public SoulStainedSteelArmorModel(ModelPart root) {
        super(root);
    }


    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0);
        ModelPartData root = modelData.getRoot();
        ModelPartData head = root.getChild(EntityModelPartNames.HEAD);

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.NONE);
        right_leg.addChild("right_leggings", ModelPartBuilder.create().uv(0, 33).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.5F))
                        .uv(0, 45).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.85F)), ModelTransform.pivot(-0, 0, 0.0F));
        right_leg.addChild("right_boot", ModelPartBuilder.create().uv(16, 40).cuboid(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.5F))
                        .uv(32, 40).cuboid(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.75F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create(), ModelTransform.NONE);
        left_leg.addChild("left_leggings", ModelPartBuilder.create().uv(0, 33).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.5001F)).mirrored(false)
                        .uv(0, 45).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.8501F)).mirrored(false), ModelTransform.pivot(0, 0, 0.0F));
        left_leg.addChild("left_boot", ModelPartBuilder.create().uv(16, 40).mirrored().cuboid(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.5001F)).mirrored(false)
                        .uv(32, 40).mirrored().cuboid(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.7501F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("left_arm", ModelPartBuilder.create().uv(48, 30).cuboid(-0.75F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.75F))
                .uv(48, 17).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.5F)), ModelTransform.pivot(0, 0, 0.0F));
        root.addChild("right_arm", ModelPartBuilder.create().uv(48, 17).mirrored().cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.5F)).mirrored(false)
                .uv(48, 30).mirrored().cuboid(-3.25F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.75F)).mirrored(false), ModelTransform.pivot(0, 0, 0.0F));

        root.addChild("body", ModelPartBuilder.create().uv(24, 17).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(1.1F))
                        .uv(0, 17).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.6F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F))
                .addChild("codpiece", ModelPartBuilder.create().uv(16, 33).cuboid(-4.0F, 9.0F, -2.0F, 8.0F, 3.0F, 4.0F, new Dilation(0.501F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("helmet", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, new Dilation(0.5F))
                .uv(32, 0).cuboid(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, new Dilation(0.75F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}
