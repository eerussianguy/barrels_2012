package com.eerussianguy.barrels_2012;


import com.eerussianguy.barrels_2012.common.BarrelServerConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class BarrelConfig
{
    public static final BarrelServerConfig SERVER;
    public static final ModConfigSpec SERVER_SPEC;

    static
    {
        final var server = new ModConfigSpec.Builder().configure(BarrelServerConfig::new);
        SERVER = server.getLeft();
        SERVER_SPEC = server.getRight();
    }
}
