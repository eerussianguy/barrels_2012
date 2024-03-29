package com.eerussianguy.barrels_2012.client;

import java.util.Optional;

import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import com.eerussianguy.barrels_2012.Barrels2012;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public abstract class BlockItemCurioRenderer implements ICurioRenderer
{
    public static ModelPart bake(String name)
    {
        return Minecraft.getInstance().getEntityModels().bakeLayer(Barrels2012.modelLayer(name));
    }

    public static Optional<SlotResult> getCurio(LivingEntity entity, Class<?> blockClass)
    {
        return Optional.ofNullable(Barrels2012.getCurio(entity, st -> st.getItem() instanceof BlockItem bi && blockClass.isInstance(bi.getBlock())));
    }

    public static BlockState defaultState(ItemStack item)
    {
        return ((BlockItem) item.getItem()).getBlock().defaultBlockState();
    }

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

            poseStack.pushPose();
            poseStack.translate(0.5f, 0.5f, 0.5f);
            poseStack.mulPose(rotation());
            poseStack.translate(-0.5f, -0.5f, -0.5f);
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, poseStack, buffers, isFullbright(state) ? LightTexture.FULL_BRIGHT : light, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.cutout());
            poseStack.popPose();

            poseStack.popPose();
        }
    }

    public boolean isFullbright(BlockState state)
    {
        return false;
    }

    @Nullable
    public abstract BlockState getBlock(LivingEntity entity, ItemStack stack);

    public Quaternionf rotation()
    {
        return Axis.ZP.rotationDegrees(180f);
    }
}
