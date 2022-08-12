package com.eerussianguy.barrels_2012.client;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import com.eerussianguy.barrels_2012.Barrels2012;
import net.dries007.tfc.common.blocks.devices.PowderkegBlock;
import org.jetbrains.annotations.Nullable;

public class PowderkegCurioRenderer extends BlockItemCurioRenderer
{
    public PowderkegCurioRenderer()
    {
        super(new BodyCurioModel<>(bake("powderkeg"), "body"));
    }

    @Override
    @Nullable
    public BlockState getBlock(LivingEntity entity, ItemStack stack)
    {
        return getCurio(entity, PowderkegBlock.class).map(curio -> {
            return defaultState(stack).setValue(PowderkegBlock.SEALED, Barrels2012.isSealed(stack));
        }).orElse(null);
    }
}
