package com.rave.projectbabylonweapons.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.rave.projectbabylonweapons.client.model.IceSpellProjectileEntityModel;
import com.rave.projectbabylonweapons.world.entity.projectile.IceSpellProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class IceSpellProjectileRenderer extends GeoEntityRenderer<IceSpellProjectileEntity> {
    public IceSpellProjectileRenderer(EntityRendererProvider.Context context) {
        super(context, new IceSpellProjectileEntityModel());
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(IceSpellProjectileEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        float renderScale = entity.getVisualScale();
        poseStack.scale(renderScale, renderScale, renderScale);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }
}
