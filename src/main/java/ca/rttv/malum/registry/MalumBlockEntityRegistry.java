package ca.rttv.malum.registry;

import ca.rttv.malum.block.entity.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

import static ca.rttv.malum.Malum.MODID;
import static ca.rttv.malum.registry.MalumBlockRegistry.*;

@SuppressWarnings("unused")
public interface MalumBlockEntityRegistry {
    Map<Identifier, BlockEntityType<?>> BLOCK_ENTITY_TYPES  = new LinkedHashMap<>();

          BlockEntityType<ItemStandBlockEntity> ITEM_STAND_BLOCK_ENTITY        = register("item_stand",        BlockEntityType.Builder.create(ItemStandBlockEntity::new, RUNEWOOD_ITEM_STAND, SOULWOOD_ITEM_STAND, TAINTED_ROCK_ITEM_STAND, TWISTED_ROCK_ITEM_STAND).build(null));
       BlockEntityType<ItemPedestalBlockEntity> ITEM_PEDESTAL_BLOCK_ENTITY     = register("item_pedestal",     BlockEntityType.Builder.create(ItemPedestalBlockEntity::new, RUNEWOOD_ITEM_PEDESTAL, SOULWOOD_ITEM_PEDESTAL, TAINTED_ROCK_ITEM_PEDESTAL, TWISTED_ROCK_ITEM_PEDESTAL).build(null));
        BlockEntityType<SpiritAltarBlockEntity> SPIRIT_ALTAR_BLOCK_ENTITY      = register("spirit_altar",      BlockEntityType.Builder.create(SpiritAltarBlockEntity::new, SPIRIT_ALTAR).build(null));
              BlockEntityType<EtherBlockEntity> ETHER_BLOCK_ENTITY             = register("ether",             BlockEntityType.Builder.create(EtherBlockEntity::new, ETHER, ETHER_TORCH, WALL_ETHER_TORCH, TAINTED_ETHER_BRAZIER, TWISTED_ETHER_BRAZIER, IRIDESCENT_ETHER, IRIDESCENT_ETHER_TORCH, IRIDESCENT_WALL_ETHER_TORCH, TAINTED_IRIDESCENT_ETHER_BRAZIER, TWISTED_IRIDESCENT_ETHER_BRAZIER).build(null));
          BlockEntityType<SpiritJarBlockEntity> SPIRIT_JAR_BLOCK_ENTITY        = register("spirit_jar",        BlockEntityType.Builder.create(SpiritJarBlockEntity::new, SPIRIT_JAR).build(null));
          BlockEntityType<TotemBaseBlockEntity> TOTEM_BASE_BLOCK_ENTITY        = register("totem_base",        BlockEntityType.Builder.create(TotemBaseBlockEntity::new, RUNEWOOD_TOTEM_BASE, SOULWOOD_TOTEM_BASE).build(null));
          BlockEntityType<TotemPoleBlockEntity> TOTEM_POLE_BLOCK_ENTITY        = register("totem_pole",        BlockEntityType.Builder.create(TotemPoleBlockEntity::new, RUNEWOOD_TOTEM_POLE, SOULWOOD_TOTEM_POLE).build(null));
     BlockEntityType<SpiritCrucibleBlockEntity> SPIRIT_CRUCIBLE_BLOCK_ENTITY   = register("spirit_crucible",   BlockEntityType.Builder.create(SpiritCrucibleBlockEntity::new, SPIRIT_CRUCIBLE).build(null));
             BlockEntityType<TabletBlockEntity> TABLET_BLOCK_ENTITY            = register("tablet",            BlockEntityType.Builder.create(TabletBlockEntity::new, TWISTED_TABLET).build(null));
    BlockEntityType<SpiritCatalyzerBlockEntity> SPIRIT_CATALYZER_BLOCK_ENTITY  = register("spirit_catalyzer",  BlockEntityType.Builder.create(SpiritCatalyzerBlockEntity::new, SPIRIT_CATALYZER).build(null));

    static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(new Identifier(MODID, id), type);
        return type;
    }

    static void init() {
        BLOCK_ENTITY_TYPES.forEach((id, entityType) -> Registry.register(Registries.BLOCK_ENTITY_TYPE, id, entityType));
    }
}
