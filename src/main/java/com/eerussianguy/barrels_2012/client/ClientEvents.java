package com.eerussianguy.barrels_2012.client;

import java.util.function.Supplier;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import com.eerussianguy.barrels_2012.Barrels2012;
import net.dries007.tfc.common.blocks.LargeVesselBlock;
import net.dries007.tfc.common.blocks.devices.*;
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
        for (Block block : ForgeRegistries.BLOCKS.getValues())
        {
            if (block instanceof BarrelBlock)
            {
                regCurio(block, BarrelCurioRenderer::new);
            }
            else if (block instanceof LargeVesselBlock)
            {
                regCurio(block, LargeVesselCurioRenderer::new);
            }
            else if (block instanceof LampBlock)
            {
                regCurio(block, LampCurioRenderer::new);
            }
            else if (block instanceof AnvilBlock)
            {
                regCurio(block, AnvilCurioRenderer::new);
            }
            else if (block instanceof PowderkegBlock)
            {
                regCurio(block, PowderkegCurioRenderer::new);
            }
            else if (block instanceof CrucibleBlock)
            {
                regCurio(block, CrucibleCurioRenderer::new);
            }
        }
    }

    private static void regCurio(Block block, Supplier<ICurioRenderer> rendererSupplier)
    {
        regCurio(block.asItem(), rendererSupplier);
    }

    private static void regCurio(Item item, Supplier<ICurioRenderer> rendererSupplier)
    {
        CuriosRendererRegistry.register(item, rendererSupplier);
    }

    private static void regCurio(Supplier<Block> supplier, Supplier<ICurioRenderer> rendererSupplier)
    {
        CuriosRendererRegistry.register(supplier.get().asItem(), rendererSupplier);
    }

    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        bodyLayer(event, "barrel", "body", PartPose.offset(-8f, 0f, 0f));
        bodyLayer(event, "powderkeg", "body", PartPose.offset(-8f, 0f, 2f));
        bodyLayer(event, "vessel", "body", PartPose.offset(-8f, -5f, 0f));
        bodyLayer(event, "anvil", "body", PartPose.offset(-8f, -4f, 2f));
        bodyLayer(event, "lamp", "body", PartPose.offset(-8f, -8f, -3f));
        bodyLayer(event, "crucible", "body", PartPose.offset(-8f, 0f, 2f));
    }

    private static void bodyLayer(EntityRenderersEvent.RegisterLayerDefinitions event, String name, String parent, PartPose pose)
    {
        event.registerLayerDefinition(Barrels2012.modelLayer(name), () -> BodyCurioModel.create(parent, pose));
    }

}
