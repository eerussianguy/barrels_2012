package com.eerussianguy.barrels_2012.common;

import com.eerussianguy.barrels_2012.Barrels2012;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.eerussianguy.barrels_2012.BarrelConfig;

import net.dries007.tfc.common.capabilities.ItemCapabilities;
import net.dries007.tfc.common.entities.IGlow;
import net.dries007.tfc.common.items.LampBlockItem;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.data.LampFuel;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import top.theillusivec4.curios.api.SlotResult;

public class PlayerGlow implements IGlow
{
    public static void reset(Player player, boolean counter)
    {
        final PlayerGlow glow = new PlayerGlow(player);
        if (counter)
            glow.resetCounter();
        if (player.level().isAreaLoaded(glow.getLightPos(), 2))
        {
            glow.tryRemoveLight();
        }
    }

    private final Player player;
    private final GlowData data;

    public PlayerGlow(Player player)
    {
        this.player = player;
        this.data = player.getData(GlowData.GLOW);
    }

    @Override
    public void tickGlow()
    {
        if (player.tickCount % this.getLightUpdateInterval() == 0)
        {
            boolean placeLight = false;
            final SlotResult curio = Barrels2012.getCurio(player, st -> st.getItem() instanceof LampBlockItem);
            if (curio != null)
            {
                placeLight = tickInternal(curio);
            }
            if (placeLight)
            {
                if (!data.isLit())
                {
                    data.setLit(true);
                    markDirty();
                }
                IGlow.super.tickGlow();
            }
            else
            {
                if (data.isLit())
                {
                    data.setLit(false);
                    markDirty();
                }
                resetCounter();
                if (initialized())
                {
                    tryRemoveLight();
                }
            }
        }
    }

    private boolean tickInternal(SlotResult curio)
    {
        final ItemStack stack = curio.stack();
        final var cap = stack.getCapability(ItemCapabilities.FLUID);
        if (cap == null)
            return false;
        final FluidStack fluid = cap.getFluidInTank(0);
        final LampFuel fuel = LampFuel.get(fluid.getFluid(), ((LampBlockItem) stack.getItem()).getBlock().defaultBlockState());
        if (!fluid.isEmpty() && fuel != null)
        {
            if (data.getFuelTick() == -1 || !BarrelConfig.SERVER.enableLampBurningFuel.get())
            {
                resetCounter();
                return true;
            }
            final int usage = Mth.floor(getTicksSinceFuelUpdate() / (double) fuel.burnRate());
            if (usage >= 1)
            {
                FluidStack used = cap.drain(usage, IFluidHandler.FluidAction.EXECUTE);
                if (used.isEmpty() || used.getAmount() < usage)
                {
                    return false;
                }
                resetCounter();
            }
            return true;
        }
        return false;
    }

    private void resetCounter()
    {
        data.setFuelTick(Calendars.get(player.level()).getTicks());
        markDirty();
    }

    private long getTicksSinceFuelUpdate()
    {
        return Calendars.get(player.level()).getTicks() - data.getFuelTick();
    }

    @Override
    public void setLightPos(BlockPos blockPos)
    {
        data.setLightPos(blockPos);
        markDirty();
    }

    private void markDirty()
    {
        player.setData(GlowData.GLOW, data);
    }

    @Override
    public BlockPos getLightPos()
    {
        return data.getLightPos();
    }

    @Override
    public int getLightLevel()
    {
        return BarrelConfig.SERVER.lampBrightness.get();
    }

    @Override
    public int getLightUpdateInterval()
    {
        return BarrelConfig.SERVER.lampUpdateInterval.get();
    }

    @Override
    public int getLightUpdateDistanceSqr()
    {
        return 4;
    }

    private boolean initialized()
    {
        return !getLightPos().equals(BlockPos.ZERO);
    }

    @Override
    public Player getEntity()
    {
        return player;
    }
}
