package com.eerussianguy.barrels_2012.client;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blocks.devices.LampBlock;
import net.dries007.tfc.common.capabilities.Capabilities;

import org.jetbrains.annotations.Nullable;

public class LampCurioRenderer extends BlockItemCurioRenderer
{
    public LampCurioRenderer()
    {
        super(new BodyCurioModel<>(bake("lamp"), "body"));
    }

    @Override
    @Nullable
    public BlockState getBlock(LivingEntity entity, ItemStack stack)
    {
        return getCurio(entity, LampBlock.class).map(curio -> {
            final boolean lit = stack.getCapability(Capabilities.FLUID_ITEM).filter(cap -> !cap.getFluidInTank(0).isEmpty()).isPresent();
            return defaultState(stack).setValue(LampBlock.LIT, lit);
        }).orElse(null);
    }

    @Override
    public boolean isFullbright(BlockState state)
    {
        return state.getValue(LampBlock.LIT);
    }
}
