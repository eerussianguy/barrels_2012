package com.eerussianguy.barrels_2012.client;

import java.util.function.Supplier;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import com.eerussianguy.barrels_2012.Barrels2012;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Metal;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

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
            regCurio(reg.get(Wood.BlockType.BARREL), BarrelCurioRenderer::new)
        );
        regCurio(TFCBlocks.LARGE_VESSEL, LargeVesselCurioRenderer::new);
        TFCBlocks.GLAZED_LARGE_VESSELS.values().forEach(reg -> regCurio(reg, LargeVesselCurioRenderer::new));
        TFCBlocks.METALS.forEach((metal, map) -> {
            if (metal.hasUtilities())
            {
                regCurio(map.get(Metal.BlockType.ANVIL), AnvilCurioRenderer::new);
            }
        });
    }

    private static void regCurio(Supplier<Block> supplier, Supplier<ICurioRenderer> rendererSupplier)
    {
        CuriosRendererRegistry.register(supplier.get().asItem(), rendererSupplier);
    }

    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(Barrels2012.modelLayer("barrel"), () -> BodyCurioModel.create("body", PartPose.offset(-8f, 0f, 0f)));
        event.registerLayerDefinition(Barrels2012.modelLayer("vessel"), () -> BodyCurioModel.create("body", PartPose.offset(-8f, -5f, 0f)));
        event.registerLayerDefinition(Barrels2012.modelLayer("anvil"), () -> BodyCurioModel.create("body", PartPose.offset(-8f, -4f, 2f)));
    }
}
