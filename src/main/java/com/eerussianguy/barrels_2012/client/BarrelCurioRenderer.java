package com.eerussianguy.barrels_2012.client;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;


import net.dries007.tfc.common.blocks.devices.BarrelBlock;
import net.dries007.tfc.common.items.BarrelBlockItem;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

public class BarrelCurioRenderer extends BlockItemCurioRenderer
{
    public static boolean isSealed(ItemStack stack)
    {
        final CompoundTag tag = stack.getTag();
        return tag != null && tag.contains("BlockEntityTag", Tag.TAG_COMPOUND);
    }

    public BarrelCurioRenderer()
    {
        super(new BodyCurioModel<>(bake("barrel"), "body"));
    }

    @Override
    @Nullable
    public BlockState getBlock(LivingEntity entity, ItemStack stack)
    {
        return CuriosApi.getCuriosHelper().findFirstCurio(entity, st -> st.getItem() instanceof BarrelBlockItem).map(curio -> {
            return defaultState(stack).setValue(BarrelBlock.SEALED, isSealed(stack));
        }).orElse(null);
    }
}
