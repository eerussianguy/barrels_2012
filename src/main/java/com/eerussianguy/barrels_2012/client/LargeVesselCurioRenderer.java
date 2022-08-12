package com.eerussianguy.barrels_2012.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import com.eerussianguy.barrels_2012.Barrels2012;
import net.dries007.tfc.common.blocks.LargeVesselBlock;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

public class LargeVesselCurioRenderer extends BlockItemCurioRenderer
{
    public LargeVesselCurioRenderer()
    {
        super(new BodyCurioModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(Barrels2012.modelLayer("vessel")), "body"));
    }

    @Override
    @Nullable
    public BlockState getBlock(LivingEntity entity, ItemStack stack)
    {
        return CuriosApi.getCuriosHelper().findFirstCurio(entity, st -> st.getItem() instanceof BlockItem bi && bi.getBlock() instanceof LargeVesselBlock).map(curio -> {
            return ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
        }).orElse(null);
    }
}
