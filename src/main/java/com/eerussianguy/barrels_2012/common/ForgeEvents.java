package com.eerussianguy.barrels_2012.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.items.ItemStackHandler;

import com.eerussianguy.barrels_2012.Barrels2012;
import net.dries007.tfc.common.blocks.devices.PowderkegBlock;
import net.dries007.tfc.util.Helpers;
import top.theillusivec4.curios.api.CuriosApi;

public class ForgeEvents
{
    public static void init()
    {
        final IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addGenericListener(Entity.class, ForgeEvents::onEntityCaps);
        bus.addListener(ForgeEvents::onPlayerLoggedIn);
        bus.addListener(ForgeEvents::onPlayerLoggedOut);
        bus.addListener(ForgeEvents::onPlayerTick);
        bus.addListener(ForgeEvents::onPlayerChangeDimension);
    }

    public static void onEntityCaps(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof Player player)
        {
            event.addCapability(PlayerGlowCapability.KEY, new PlayerGlow(player));
        }
    }

    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        final Player player = event.player;
        final Level level = player.level;
        if (event.phase == TickEvent.Phase.START && !level.isClientSide)
        {
            player.getCapability(PlayerGlowCapability.CAPABILITY).ifPresent(PlayerGlow::tick);
        }
        if (!level.isClientSide && level.getGameTime() % 40 == 0 && player.isOnFire())
        {
            CuriosApi.getCuriosHelper().findFirstCurio(player, is -> is.getItem() instanceof BlockItem bi && bi.getBlock() instanceof PowderkegBlock).ifPresent(curio -> {
                ItemStack stack = curio.stack();
                if (Barrels2012.isSealed(stack))
                {
                    final float size = getExplosionSize(stack);
                    curio.stack().shrink(1);
                    level.explode(null, player.getX(), player.getY(0.0625D), player.getZ(), size, Explosion.BlockInteraction.BREAK);
                }
            });
        }
    }

    private static float getExplosionSize(ItemStack stack)
    {
        float str = 0f;
        CompoundTag tag = stack.getTagElement("BlockEntityTag");
        if (tag != null)
        {
            final CompoundTag inventoryTag = tag.getCompound("inventory");
            final ItemStackHandler inventory = new ItemStackHandler();

            inventory.deserializeNBT(inventoryTag.getCompound("inventory"));

            if (!Helpers.isEmpty(inventory))
            {
                for (int i = 0; i < inventory.getSlots(); i++)
                {
                    str += inventory.getStackInSlot(i).getCount();
                }
                str /= 12f; // the regular powderkeg scaling
            }
        }
        return str;
    }

    public static void onPlayerLoggedOut(EntityLeaveWorldEvent event)
    {
        final Entity entity = event.getEntity();
        if (!entity.level.isClientSide && entity instanceof Player player)
        {
            PlayerGlow.reset(player);
        }
    }

    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        final Player player = event.getPlayer();
        if (!player.level.isClientSide)
        {
            PlayerGlow.reset(player);
        }
    }

    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        final Player player = event.getPlayer();
        player.getCapability(PlayerGlowCapability.CAPABILITY).ifPresent(cap -> {
            // noinspection deprecation
            if (player.level.isAreaLoaded(cap.getLightPos(), 2))
            {
                cap.tryRemoveLight();
            }
        });
    }
}
