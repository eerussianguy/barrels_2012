package com.eerussianguy.barrels_2012.common;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import com.eerussianguy.barrels_2012.BarrelConfig;
import com.eerussianguy.barrels_2012.Barrels2012;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.component.TFCComponents;
import net.dries007.tfc.common.component.item.ItemListComponent;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.SlotResult;

public class ForgeEvents
{
    public static void init()
    {
        final IEventBus bus = NeoForge.EVENT_BUS;
        bus.addListener(ForgeEvents::onPlayerLoggedIn);
        bus.addListener(ForgeEvents::onPlayerLoggedOut);
        bus.addListener(ForgeEvents::onPlayerTick);
        bus.addListener(ForgeEvents::onPlayerChangeDimension);
    }

    public static void onPlayerTick(PlayerTickEvent.Pre event)
    {
        final Player player = event.getEntity();
        final Level level = player.level();
        if (!level.isClientSide)
        {
            new PlayerGlow(player).tickGlow();
        }
        if (!level.isClientSide && BarrelConfig.SERVER.enablePowderkegExplosions.get() && level.getGameTime() % 40 == 0 && player.isOnFire())
        {
            final SlotResult curio = Barrels2012.getCurio(player, is -> is.getItem().equals(TFCBlocks.POWDERKEG.get().asItem()));
            if (curio != null)
            {
                final ItemStack stack = curio.stack();
                if (Barrels2012.isSealed(stack))
                {
                    final float size = getExplosionSize(stack);
                    curio.stack().shrink(1);
                    level.explode(null, player.getX(), player.getY(0.0625D), player.getZ(), size, Level.ExplosionInteraction.BLOCK);
                }
            }
        }
    }

    private static float getExplosionSize(ItemStack stack)
    {
        float str = 0f;
        final ItemListComponent items = stack.get(TFCComponents.CONTENTS);
        if (items != null)
        {
            for (ItemStack item : items.contents())
            {
                str += item.getCount();
            }
            str /= 12f; // the regular powderkeg scaling
        }
        return str;
    }

    public static void onPlayerLoggedOut(EntityLeaveLevelEvent event)
    {
        final Entity entity = event.getEntity();
        if (!entity.level().isClientSide && entity instanceof Player player)
        {
            PlayerGlow.reset(player, true);
        }
    }

    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        final Player player = event.getEntity();
        if (!player.level().isClientSide)
        {
            PlayerGlow.reset(player, true);
        }
    }

    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        PlayerGlow.reset(event.getEntity(), false);
    }
}
