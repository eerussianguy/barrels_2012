package com.eerussianguy.barrels_2012;

import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import com.eerussianguy.barrels_2012.common.BarrelServerConfig;

public class BarrelConfig
{
    public static final BarrelServerConfig SERVER = register(ModConfig.Type.SERVER, BarrelServerConfig::new);

    public static void init() {}

    private static <C> C register(net.minecraftforge.fml.config.ModConfig.Type type, Function<ForgeConfigSpec.Builder, C> factory)
    {
        Pair<C, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(factory);
        ModLoadingContext.get().registerConfig(type, specPair.getRight());
        return specPair.getLeft();
    }
}
