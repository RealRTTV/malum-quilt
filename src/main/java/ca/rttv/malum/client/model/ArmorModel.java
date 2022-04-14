package ca.rttv.malum.client.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class ArmorModel extends BipedEntityModel<LivingEntity> {
    public EquipmentSlot slot;
    public final ModelPart root, hat, body, leftArm, rightArm, leggings, leftLegging, rightLegging, leftFoot, rightFoot;

    public ArmorModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
        child = false;
        this.root = root;
        this.hat = head.getChild("helmet");
        this.body = root.getChild(EntityModelPartNames.BODY);
        this.leggings = root.getChild(EntityModelPartNames.BODY);
        this.leftArm = root.getChild(EntityModelPartNames.LEFT_ARM);;
        this.rightArm = root.getChild(EntityModelPartNames.RIGHT_ARM);
        this.leftLegging = root.getChild(EntityModelPartNames.LEFT_LEG);
        this.rightLegging = root.getChild(EntityModelPartNames.RIGHT_LEG);
        this.leftFoot = root.getChild(EntityModelPartNames.LEFT_LEG);
        this.rightFoot = root.getChild(EntityModelPartNames.RIGHT_LEG);
    }
}
