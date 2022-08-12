package com.eerussianguy.barrels_2012.client;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
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
        super(new BodyCurioModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(Barrels2012.modelLayer("barrel")), "body"));
    }

    @Override
    @Nullable
    public BlockState getBlock(LivingEntity entity, ItemStack stack)
    {
        return CuriosApi.getCuriosHelper().findFirstCurio(entity, st -> st.getItem() instanceof BarrelBlockItem).map(curio -> {
            if (stack.getItem() instanceof BarrelBlockItem blockItem)
            {
                final Block block = blockItem.getBlock();
                if (block instanceof BarrelBlock)
                {
                    final CompoundTag tag = stack.getTag();
                    final boolean sealed = tag != null && tag.contains("BlockEntityTag", Tag.TAG_COMPOUND);
                    return block.defaultBlockState().setValue(BarrelBlock.SEALED, sealed);
                }
            }
            return null;
        }).orElse(null);
    }
}
