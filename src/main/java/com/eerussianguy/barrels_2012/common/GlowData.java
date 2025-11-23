package com.eerussianguy.barrels_2012.common;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static com.eerussianguy.barrels_2012.Barrels2012.*;

public class GlowData implements INBTSerializable<CompoundTag>
{
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);
    public static final Supplier<AttachmentType<GlowData>> GLOW = ATTACHMENT_TYPES.register("glow", () -> AttachmentType.serializable(() -> new GlowData(BlockPos.ZERO, 0, false)).build());

    private BlockPos lightPos;
    private long fuelTick;
    private boolean lit;

    public GlowData(BlockPos lightPos, long fuelTick, boolean lit)
    {
        this.lightPos = lightPos;
        this.fuelTick = fuelTick;
        this.lit = lit;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider)
    {
        final CompoundTag tag = new CompoundTag();
        tag.putLong("light", lightPos.asLong());
        tag.putLong("fuel", fuelTick);
        tag.putBoolean("lit", lit);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag)
    {
        lightPos = BlockPos.of(tag.getLong("light"));
        fuelTick = tag.getLong("fuel");
        lit = tag.getBoolean("lit");
    }

    public BlockPos getLightPos()
    {
        return lightPos;
    }

    public void setLightPos(BlockPos lightPos)
    {
        this.lightPos = lightPos;
    }

    public long getFuelTick()
    {
        return fuelTick;
    }

    public void setFuelTick(long fuelTick)
    {
        this.fuelTick = fuelTick;
    }

    public boolean isLit()
    {
        return lit;
    }

    public void setLit(boolean lit)
    {
        this.lit = lit;
    }
}
