package ca.rttv.malum.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

import java.util.List;

import static ca.rttv.malum.registry.MalumRegistry.TOTEM_POLE_BLOCK_ENTITY;

public class TotemPoleBlockEntity extends BlockEntity {
    public List<Item> list;

    public TotemPoleBlockEntity(BlockPos pos, BlockState state) {
        this(TOTEM_POLE_BLOCK_ENTITY, pos, state);
    }

    public TotemPoleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
