package com.eerussianguy.barrels_2012.client;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;


import com.eerussianguy.barrels_2012.Barrels2012;
import net.dries007.tfc.common.blocks.devices.BarrelBlock;
import net.dries007.tfc.common.items.BarrelBlockItem;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

public class BarrelCurioRenderer extends BlockItemCurioRenderer
{
    public BarrelCurioRenderer()
    {
        super(new BodyCurioModel<>(bake("barrel"), "body"));
    }

    @Override
    @Nullable
    public BlockState getBlock(LivingEntity entity, ItemStack stack)
    {
        return CuriosApi.getCuriosHelper().findFirstCurio(entity, st -> st.getItem() instanceof BarrelBlockItem).map(curio -> {
            return defaultState(stack).setValue(BarrelBlock.SEALED, Barrels2012.isSealed(stack));
        }).orElse(null);
    }
}
