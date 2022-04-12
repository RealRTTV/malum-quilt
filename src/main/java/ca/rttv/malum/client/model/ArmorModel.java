package ca.rttv.malum.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
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
    public static ModelPartData createHumanoidAlias(ModelData mesh) {
        ModelPartData root = mesh.getRoot();
        root.addChild("body", new ModelPartBuilder(), ModelTransform.NONE);
        root.addChild("leggings", new ModelPartBuilder(), ModelTransform.NONE);
        root.addChild("head", new ModelPartBuilder(), ModelTransform.NONE);
        root.addChild("left_legging", new ModelPartBuilder(), ModelTransform.NONE);
        root.addChild("left_foot", new ModelPartBuilder(), ModelTransform.NONE);
        root.addChild("right_legging", new ModelPartBuilder(), ModelTransform.NONE);
        root.addChild("right_foot", new ModelPartBuilder(), ModelTransform.NONE);
        root.addChild("left_arm", new ModelPartBuilder(), ModelTransform.NONE);
        root.addChild("right_arm", new ModelPartBuilder(), ModelTransform.NONE);

        return root;
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return slot == EquipmentSlot.HEAD ? ImmutableList.of(head) : ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        if (slot == EquipmentSlot.CHEST) {
            return ImmutableList.of(body, leftArm, rightArm);
        } else if (slot == EquipmentSlot.LEGS) {
            return ImmutableList.of(leftLegging, rightLegging, leggings);
        } else if (slot == EquipmentSlot.FEET) {
            return ImmutableList.of(leftFoot, rightFoot);
        } else return ImmutableList.of();
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        if (slot == EquipmentSlot.LEGS) {  //I don't know why this is needed, but it is.
            this.leggings.copyTransform(this.body);
            this.leftLegging.copyTransform(this.leftLeg);
            this.rightLegging.copyTransform(this.rightLeg);
        }
        super.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    public void copyStateFrom(BipedEntityModel<LivingEntity> model) {
        leggings.copyTransform(model.body);
        body.copyTransform(model.body);
        head.copyTransform(model.head);
        leftArm.copyTransform(model.leftArm);
        rightArm.copyTransform(model.rightArm);
        leftLegging.copyTransform(leftLeg);
        rightLegging.copyTransform(rightLeg);
        leftFoot.copyTransform(leftLeg);
        rightFoot.copyTransform(rightLeg);
    }
}
