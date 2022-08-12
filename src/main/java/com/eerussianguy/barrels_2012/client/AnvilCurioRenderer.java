package com.eerussianguy.barrels_2012.client;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.dries007.tfc.common.blocks.devices.AnvilBlock;
import org.jetbrains.annotations.Nullable;

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
    public Quaternion rotation()
    {
        Quaternion q = Vector3f.XP.rotationDegrees(-90f);
        q.mul(Vector3f.ZP.rotationDegrees(180f));
        return q;
    }
}
