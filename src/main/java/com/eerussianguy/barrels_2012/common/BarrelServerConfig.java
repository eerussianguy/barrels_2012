package com.eerussianguy.barrels_2012.common;

import java.util.function.Function;

import net.neoforged.neoforge.common.ModConfigSpec;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class BarrelServerConfig
{
    public final ModConfigSpec.BooleanValue enablePowderkegExplosions;
    public final ModConfigSpec.BooleanValue enableLampBurningFuel;
    public final ModConfigSpec.IntValue lampBrightness;
    public final ModConfigSpec.IntValue lampUpdateInterval;

    public BarrelServerConfig(ModConfigSpec.Builder innerBuilder)
    {
        Function<String, ModConfigSpec.Builder> builder = name -> innerBuilder.translation(MOD_ID + ".config.server." + name);

        innerBuilder.push("general");

        enablePowderkegExplosions = builder.apply("enablePowderkegExplosions").comment("Enables worn powderkegs exploding when you catch on fire.").define("enablePowderkegExplosions", true);
        enableLampBurningFuel = builder.apply("enableLampBurningFuel").comment("Enables lamps burning fuel when worn. Disabling this makes them last forever.").define("enableLampBurningFuel", true);
        lampBrightness = builder.apply("lampBrightness").comment("The light value emitted by the worn lamp.").defineInRange("lampBrightness", 15, 0, 15);
        lampUpdateInterval = builder.apply("lampUpdateInterval").comment("The ticks between times the lamp tries to update its light position while worn. 20 ticks are in a second.").defineInRange("lampUpdateInterval", 15, 1, Integer.MAX_VALUE);

        innerBuilder.pop();
    }
}
