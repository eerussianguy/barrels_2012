package com.eerussianguy.barrels_2012.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public class BodyCurioModel<T extends LivingEntity> extends HumanoidModel<T>
{
    public static LayerDefinition createBodyLayer(float dx, float dy, float dz)
    {
        CubeDeformation cube = new CubeDeformation(0.4f);
        MeshDefinition mesh = HumanoidModel.createMesh(cube, 0f);
        PartDefinition part = mesh.getRoot();
        part.getChild("body").addOrReplaceChild("thing", CubeListBuilder.create(), PartPose.offset(dx, dy, dz));
        return LayerDefinition.create(mesh, 16, 16);
    }

    private final ModelPart thingHolder;

    public BodyCurioModel(ModelPart root, String parent)
    {
        super(root);
        thingHolder = root.getChild(parent).getChild("thing");
    }

    public void translateToPosition(PoseStack poseStack)
    {
        thingHolder.translateAndRotate(poseStack);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha)
    {

    }
}
