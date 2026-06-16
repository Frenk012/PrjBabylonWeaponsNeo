package com.rave.projectbabylonweapons.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.rave.projectbabylonweapons.client.model.GoldenSpellProjectileEntityModel;
import com.rave.projectbabylonweapons.world.entity.projectile.GoldenSpellProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GoldenSpellProjectileRenderer extends GeoEntityRenderer<GoldenSpellProjectileEntity> {
    public GoldenSpellProjectileRenderer(EntityRendererProvider.Context context) {
        super(context, new GoldenSpellProjectileEntityModel());
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(GoldenSpellProjectileEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
        float renderScale = entity.getVisualScale();
        poseStack.scale(renderScale, renderScale, renderScale);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }
}
