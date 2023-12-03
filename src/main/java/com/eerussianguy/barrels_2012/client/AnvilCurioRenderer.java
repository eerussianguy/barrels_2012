package com.eerussianguy.barrels_2012.client;

import com.mojang.math.Axis;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blocks.devices.AnvilBlock;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class AnvilCurioRenderer extends BlockItemCurioRenderer
{
    public AnvilCurioRenderer()
    {
        super(new BodyCurioModel<>(bake("anvil"), "body"));
    }

    @Override
    @Nullable
    public BlockState getBlock(LivingEntity entity, ItemStack stack)
    {
        return getCurio(entity, AnvilBlock.class).map(curio -> defaultState(stack)).orElse(null);
    }

    @Override
    public Quaternionf rotation()
    {
        Quaternionf q = Axis.XP.rotationDegrees(-90f);
        q.mul(Axis.ZP.rotationDegrees(180f));
        return q;
    }
}
