package com.eerussianguy.barrels_2012.client;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blocks.devices.CrucibleBlock;
import org.jetbrains.annotations.Nullable;

public class CrucibleCurioRenderer extends BlockItemCurioRenderer
{
    public CrucibleCurioRenderer()
    {
        super(new BodyCurioModel<>(bake("crucible"), "body"));
    }

    @Override
    @Nullable
    public BlockState getBlock(LivingEntity entity, ItemStack stack)
    {
        return getCurio(entity, CrucibleBlock.class).map(curio -> defaultState(stack)).orElse(null);
    }
}
