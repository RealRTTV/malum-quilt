package ca.rttv.malum.item;

public interface ISoulContainerItem {
//    TypedActionResult<ItemStack> interactWithSoul(PlayerEntity player, Hand hand, SoulEntity soul);
//
//    default TypedActionResult<ItemStack> fetchSoul(PlayerEntity player, Hand hand) {
//        ArrayList<SoulEntity> entities = new ArrayList<>(player.world.getEntitiesByClass(SoulEntity.class, player.getBoundingBox().inflate(player.getAttributeValue(ForgeMod.REACH_DISTANCE.get())*0.4f)));
//        double biggestAngle = 0;
//        SoulEntity chosenEntity = null;
//        for (SoulEntity entity : entities) {
//            Vec3d lookDirection = player.getRotationVector();
//            Vec3d directionToEntity = entity.getPos().subtract(player.position()).normalize();
//            double angle = lookDirection.dotProduct(directionToEntity);
//            if (angle > biggestAngle && angle > 0.1f) {
//                biggestAngle = angle;
//                chosenEntity = entity;
//            }
//        }
//        if (chosenEntity != null)
//        {
//            return interactWithSoul(player, hand, chosenEntity);
//        }
//        return TypedActionResult.pass(player.getStackInHand(hand));
//    }
}
