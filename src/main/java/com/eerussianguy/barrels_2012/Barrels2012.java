package com.eerussianguy.barrels_2012;

import java.util.function.Predicate;
import com.eerussianguy.barrels_2012.common.GlowData;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import com.eerussianguy.barrels_2012.client.ClientEvents;
import com.eerussianguy.barrels_2012.common.ForgeEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import net.dries007.tfc.common.component.TFCComponents;

@Mod(Barrels2012.MOD_ID)
public class Barrels2012
{
    public static final String MOD_ID = "barrels_2012";

    public Barrels2012(ModContainer mod, IEventBus bus)
    {
        ForgeEvents.init();
        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            ClientEvents.init(bus);
        }
        mod.registerConfig(ModConfig.Type.SERVER, BarrelConfig.SERVER_SPEC);
        GlowData.ATTACHMENT_TYPES.register(bus);
    }

    public static ResourceLocation identifier(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(Barrels2012.MOD_ID, path);
    }

    public static ModelLayerLocation modelLayer(String name)
    {
        return new ModelLayerLocation(identifier(name), "main");
    }

    public static boolean isSealed(ItemStack stack)
    {
        return stack.has(TFCComponents.BARREL) || stack.has(TFCComponents.CONTENTS);
    }

    @Nullable
    public static SlotResult getCurio(LivingEntity entity, Predicate<ItemStack> predicate)
    {
        return CuriosApi.getCuriosInventory(entity).flatMap(inv -> inv.findFirstCurio(predicate)).orElse(null);
    }
}
