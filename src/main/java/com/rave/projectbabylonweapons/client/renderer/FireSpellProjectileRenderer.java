package com.rave.projectbabylonweapons.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.rave.projectbabylonweapons.client.model.FireSpellProjectileEntityModel;
import com.rave.projectbabylonweapons.world.entity.projectile.FireSpellProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireSpellProjectileRenderer extends GeoEntityRenderer<FireSpellProjectileEntity> {
    public FireSpellProjectileRenderer(EntityRendererProvider.Context context) {
        super(context, new FireSpellProjectileEntityModel());
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(FireSpellProjectileEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
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
