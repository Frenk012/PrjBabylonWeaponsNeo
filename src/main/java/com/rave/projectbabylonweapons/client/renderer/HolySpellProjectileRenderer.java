package com.rave.projectbabylonweapons.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.rave.projectbabylonweapons.client.model.HolySpellProjectileEntityModel;
import com.rave.projectbabylonweapons.world.entity.projectile.HolySpellProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HolySpellProjectileRenderer extends GeoEntityRenderer<HolySpellProjectileEntity> {
    public HolySpellProjectileRenderer(EntityRendererProvider.Context context) {
        super(context, new HolySpellProjectileEntityModel());
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(HolySpellProjectileEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }
}
