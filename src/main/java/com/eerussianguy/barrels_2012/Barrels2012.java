package com.eerussianguy.barrels_2012;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Barrels2012.MOD_ID)
public class Barrels2012
{
    public static final String MOD_ID = "barrels_2012";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Barrels2012()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    }


}
