package com.eerussianguy.barrels_2012.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

import com.eerussianguy.barrels_2012.Barrels2012;

public final class PlayerGlowCapability
{
    public static final Capability<PlayerGlow> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final ResourceLocation KEY = Barrels2012.identifier("player_glow");
}
