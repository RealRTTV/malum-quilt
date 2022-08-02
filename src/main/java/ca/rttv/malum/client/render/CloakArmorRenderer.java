package ca.rttv.malum.client.render;

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

public class CloakArmorRenderer implements ArmorRenderer {
    private static SpiritHunterArmorModel armorModel;
    private final Identifier texture;

    public CloakArmorRenderer(Identifier texture) {
        this.texture = texture;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        final MinecraftClient client = MinecraftClient.getInstance();
        if (armorModel == null) {
            armorModel = new SpiritHunterArmorModel(client.getEntityModelLoader().getModelPart(SpiritHunterArmorModel.LAYER));
        }
        contextModel.setAttributes(armorModel);
        armorModel.setVisible(true);
        armorModel.head.visible = slot == EquipmentSlot.HEAD;
        armorModel.body.getChild("codpiece").visible = slot == EquipmentSlot.LEGS;
        armorModel.body.visible = armorModel.cape.visible = slot == EquipmentSlot.CHEST;
        armorModel.lowered_hood.visible = !armorModel.head.visible && slot == EquipmentSlot.CHEST;
        armorModel.leftArm.visible = slot == EquipmentSlot.CHEST;
        armorModel.rightArm.visible = slot == EquipmentSlot.CHEST;
        armorModel.leftLeg.visible = slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
        armorModel.rightLeg.visible = slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
        armorModel.leftLeg.getChild("left_boot").visible = slot == EquipmentSlot.FEET;
        armorModel.rightLeg.getChild("right_boot").visible = slot == EquipmentSlot.FEET;
        armorModel.leftLeg.getChild("left_leg_robe").visible = slot == EquipmentSlot.LEGS;
        armorModel.rightLeg.getChild("right_leg_robe").visible = slot == EquipmentSlot.LEGS;
        armorModel.setCapeAngles(entity, client.getTickDelta());
        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, texture);


    }
}
