package com.eerussianguy.barrels_2012.client;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import com.eerussianguy.barrels_2012.Barrels2012;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class ClientEvents
{
    public static void init(IEventBus bus)
    {
        bus.addListener(ClientEvents::setup);
        bus.addListener(ClientEvents::registerLayers);
    }

    private static void setup(FMLClientSetupEvent event)
    {
        TFCBlocks.WOODS.values().forEach(reg ->
            CuriosRendererRegistry.register(reg.get(Wood.BlockType.BARREL).get().asItem(), BarrelCurioRenderer::new)
        );
        CuriosRendererRegistry.register(TFCBlocks.LARGE_VESSEL.get().asItem(), LargeVesselCurioRenderer::new);
    }

    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(Barrels2012.modelLayer("barrel"), () -> BodyCurioModel.createBodyLayer(-8f, 0f, 0f));
        event.registerLayerDefinition(Barrels2012.modelLayer("vessel"), () -> BodyCurioModel.createBodyLayer(-8f, 3f, 0f));
    }
}
