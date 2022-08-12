package com.eerussianguy.barrels_2012.client;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import com.eerussianguy.barrels_2012.Barrels2012;
import net.dries007.tfc.common.blocks.LargeVesselBlock;
import org.jetbrains.annotations.Nullable;

public class LargeVesselCurioRenderer extends BlockItemCurioRenderer
{
    public LargeVesselCurioRenderer()
    {
        super(new BodyCurioModel<>(bake("vessel"), "body"));
    }

    @Override
    @Nullable
    public BlockState getBlock(LivingEntity entity, ItemStack stack)
    {
        return getCurio(entity, LargeVesselBlock.class).map(curio ->
            defaultState(stack).setValue(LargeVesselBlock.SEALED, Barrels2012.isSealed(stack))
        ).orElse(null);
    }
}
