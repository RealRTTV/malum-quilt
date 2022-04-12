package ca.rttv.malum.client.render;

import ca.rttv.malum.MalumClient;
import ca.rttv.malum.client.model.SpiritHunterArmorModel;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class MalumArmorRenderer implements ArmorRenderer {
    private static SpiritHunterArmorModel armorModel;
    private final Identifier texture;
    public MalumArmorRenderer(Identifier texture) {
        this.texture = texture;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (armorModel == null) {
            armorModel = new SpiritHunterArmorModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(SpiritHunterArmorModel.LAYER));
        }
        contextModel.setAttributes(armorModel);
        armorModel.setVisible(false);
        armorModel.head.visible = slot == EquipmentSlot.HEAD;
        armorModel.body.visible = slot == EquipmentSlot.CHEST;
        armorModel.leftArm.visible = slot == EquipmentSlot.CHEST;
        armorModel.rightArm.visible = slot == EquipmentSlot.CHEST;
        armorModel.leggings.visible = slot == EquipmentSlot.LEGS;
        armorModel.leftLeg.visible = slot == EquipmentSlot.LEGS;
        armorModel.rightLeg.visible = slot == EquipmentSlot.LEGS;
        armorModel.leftFoot.visible = slot == EquipmentSlot.FEET;
        armorModel.rightFoot.visible = slot == EquipmentSlot.FEET;
        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, texture);

    }
}
