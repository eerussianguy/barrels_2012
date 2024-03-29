package com.eerussianguy.barrels_2012;

import java.util.function.Predicate;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import com.eerussianguy.barrels_2012.client.ClientEvents;
import com.eerussianguy.barrels_2012.common.ForgeEvents;
import com.eerussianguy.barrels_2012.common.PlayerGlow;
import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

@Mod(Barrels2012.MOD_ID)
public class Barrels2012
{
    public static final String MOD_ID = "barrels_2012";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Barrels2012()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::registerCaps);

        ForgeEvents.init();
        BarrelConfig.init();

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            ClientEvents.init(bus);
        }
    }

    private void registerCaps(RegisterCapabilitiesEvent event)
    {
        event.register(PlayerGlow.class);
    }

    public static ResourceLocation identifier(String path)
    {
        return new ResourceLocation(Barrels2012.MOD_ID, path);
    }

    public static ModelLayerLocation modelLayer(String name)
    {
        return new ModelLayerLocation(identifier(name), "main");
    }

    public static boolean isSealed(ItemStack stack)
    {
        final CompoundTag tag = stack.getTag();
        return tag != null && tag.contains("BlockEntityTag", Tag.TAG_COMPOUND);
    }

    @Nullable
    public static SlotResult getCurio(LivingEntity entity, Predicate<ItemStack> predicate)
    {
        return CuriosApi.getCuriosInventory(entity).resolve().flatMap(inv -> inv.findFirstCurio(predicate)).orElse(null);
    }
}
