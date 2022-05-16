package ca.rttv.malum.client.model;

import ca.rttv.malum.util.helper.DataHelper;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class AncientSpiritHunterArmorModel extends ArmorModel {
    public static final EntityModelLayer LAYER = new EntityModelLayer(DataHelper.prefix("textures/armor/spirit_hunter"), "main");

    public AncientSpiritHunterArmorModel(ModelPart root) {
        super(root);
    }


    public static TexturedModelData getTexturedModelData() {
        ModelData data = BipedEntityModel.getModelData(Dilation.NONE, 0);
        ModelPartData root = data.getRoot();
        ModelPartData head = root.getChild(EntityModelPartNames.HEAD);

        root.addChild("body", ModelPartBuilder.create().uv(40, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, new Dilation(0.95F))
                        .uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, new Dilation(0.75F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F))
                .addChild("codpiece", ModelPartBuilder.create().uv(16, 33).cuboid(-4.0F, 9.0F, -2.0F, 8.0F, 3.0F, 4.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        root.addChild("right_arm", ModelPartBuilder.create().uv(0, 29).mirrored().cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.5F)).mirrored(false)
                .uv(0, 16).mirrored().cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.7F)).mirrored(false), ModelTransform.pivot(0, 0, 0.0F));
        root.addChild("left_arm", ModelPartBuilder.create().uv(0, 29).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.5F))
                .uv(0, 16).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.7F)), ModelTransform.pivot(0, 0, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.NONE);
        right_leg.addChild("right_leggings", ModelPartBuilder.create().uv(0, 42).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.45F)), ModelTransform.pivot(0, 0, 0.0F));
        right_leg.addChild("right_boot", ModelPartBuilder.create().uv(0, 55).cuboid(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.9F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create(), ModelTransform.NONE);
        left_leg.addChild("left_leggings", ModelPartBuilder.create().uv(0, 42).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.45F)).mirrored(false), ModelTransform.pivot(0, 0, 0));
        left_leg.addChild("left_boot", ModelPartBuilder.create().uv(0, 55).mirrored().cuboid(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.9001F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("helmet", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.5F))
                .uv(32, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.7F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 64, 64);
    }
}
