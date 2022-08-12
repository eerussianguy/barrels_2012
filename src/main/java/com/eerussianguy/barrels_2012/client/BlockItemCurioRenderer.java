package com.eerussianguy.barrels_2012.client;

import java.util.Optional;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;

import com.eerussianguy.barrels_2012.Barrels2012;
import com.mojang.blaze3d.vertex.PoseStack;
import net.dries007.tfc.common.blocks.devices.BarrelBlock;
import net.dries007.tfc.common.items.BarrelBlockItem;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public abstract class BlockItemCurioRenderer implements ICurioRenderer
{
    private final BodyCurioModel<LivingEntity> model;

    public BlockItemCurioRenderer(BodyCurioModel<LivingEntity> model)
    {
        this.model = model;
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        final LivingEntity entity = slotContext.entity();
        BlockState state = getBlock(entity, stack);
        if (state != null)
        {
            poseStack.pushPose();

            model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
            model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

            ICurioRenderer.followBodyRotations(entity, model);

            model.translateToPosition(poseStack);

            ICurioRenderer.translateIfSneaking(poseStack, entity);
            ICurioRenderer.rotateIfSneaking(poseStack, entity);

            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, poseStack, buffers, light, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);

            poseStack.popPose();
        }
    }

    @Nullable
    public abstract BlockState getBlock(LivingEntity entity, ItemStack stack);
}
