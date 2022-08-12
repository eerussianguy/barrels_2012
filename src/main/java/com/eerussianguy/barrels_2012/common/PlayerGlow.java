package com.eerussianguy.barrels_2012.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import net.dries007.tfc.common.entities.IGlow;
import net.dries007.tfc.common.items.LampBlockItem;
import net.dries007.tfc.util.LampFuel;
import net.dries007.tfc.util.calendar.Calendars;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

public class PlayerGlow implements ICapabilitySerializable<CompoundTag>, IGlow
{
    public static void reset(Player player)
    {
        player.getCapability(PlayerGlowCapability.CAPABILITY).ifPresent(cap -> {
            cap.resetCounter();
            // noinspection deprecation
            if (player.level.isAreaLoaded(cap.lightPos, 2))
            {
                cap.tryRemoveLight();
            }
        });
    }

    private final Player player;
    private final LazyOptional<PlayerGlow> capability;

    private long lastFuelTick;
    private BlockPos lightPos;
    private boolean lit;

    public PlayerGlow(Player player)
    {
        this.player = player;
        this.lastFuelTick = -1;
        this.capability = LazyOptional.of(() -> this);
        this.lightPos = BlockPos.ZERO;
        this.lit = false;
    }

    @Override
    public void tick()
    {
        if (player.tickCount % this.getLightUpdateInterval() == 0)
        {
            final boolean placeLight = CuriosApi.getCuriosHelper().findFirstCurio(player, s -> {
                return s.getItem() instanceof LampBlockItem;
            }).map(curio -> {
                final ItemStack stack = curio.stack();
                return stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).map(cap -> {
                    FluidStack fluid = cap.getFluidInTank(0);
                    LampFuel fuel = LampFuel.get(fluid.getFluid(), ((LampBlockItem) stack.getItem()).getBlock().defaultBlockState());
                    if (!fluid.isEmpty() && fuel != null)
                    {
                        if (lastFuelTick == -1)
                        {
                            resetCounter();
                            return true;
                        }
                        final int usage = Mth.floor(getTicksSinceFuelUpdate() / (double) fuel.getBurnRate());
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
                }).orElse(false);
            }).orElse(false);
            if (placeLight)
            {
                lit = true;
                IGlow.super.tick();
            }
            else
            {
                lit = false;
                resetCounter();
                if (initialized())
                {
                    tryRemoveLight();
                }
            }
        }
    }

    private void resetCounter()
    {
        lastFuelTick = Calendars.get(player.level).getTicks();
    }

    private long getTicksSinceFuelUpdate()
    {
        return Calendars.get(player.level).getTicks() - lastFuelTick;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        return cap == PlayerGlowCapability.CAPABILITY ? capability.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT()
    {
        final CompoundTag nbt = new CompoundTag();
        saveLight(nbt);
        nbt.putLong("fuelTick", lastFuelTick);
        nbt.putBoolean("lit", lit);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        readLight(nbt);
        lastFuelTick = nbt.getLong("fuelTick");
        lit = nbt.getBoolean("lit");
    }

    @Override
    public void setLightPos(BlockPos blockPos)
    {
        lightPos = blockPos;
    }

    @Override
    public BlockPos getLightPos()
    {
        return lightPos;
    }

    @Override
    public int getLightUpdateInterval()
    {
        return 15;
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
