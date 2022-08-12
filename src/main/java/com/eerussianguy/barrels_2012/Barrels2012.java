package com.eerussianguy.barrels_2012;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import com.eerussianguy.barrels_2012.client.ClientEvents;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod(Barrels2012.MOD_ID)
public class Barrels2012
{
    public static final String MOD_ID = "barrels_2012";
    public static final String CURIOS_ID = "curios";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Barrels2012()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::onInterModComms);

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            ClientEvents.init(bus);
        }
    }

    private void onInterModComms(InterModEnqueueEvent event)
    {
        InterModComms.sendTo(CURIOS_ID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BACK.getMessageBuilder().build());
    }

    public static ModelLayerLocation modelLayer(String name)
    {
        return new ModelLayerLocation(new ResourceLocation(Barrels2012.MOD_ID, name), "main");
    }
}
