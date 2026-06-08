package com.rave.projectbabylonweapons.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.rave.projectbabylonweapons.client.model.ManaBubbleProjectileEntityModel;
import com.rave.projectbabylonweapons.init.PBModItems;
import com.rave.projectbabylonweapons.world.entity.projectile.ManaBubbleProjectileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ManaBubbleProjectileRenderer extends GeoEntityRenderer<ManaBubbleProjectileEntity> {
    private static final ItemStack FIRE_PROJECTILE_STACK = new ItemStack(PBModItems.FIRE_SPELL_PROJECTILE.get());

    public ManaBubbleProjectileRenderer(EntityRendererProvider.Context context) {
        super(context, new ManaBubbleProjectileEntityModel());
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(ManaBubbleProjectileEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        float scale = entity.getRenderScale();
        poseStack.scale(scale, scale, scale);
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
        float yawOffset = entity.getVisualPreset().yawOffset();
        if (yawOffset != 0.0F) {
            poseStack.mulPose(Axis.YP.rotationDegrees(yawOffset));
        }

        if (entity.getVisualPreset().usesFireItemRenderer()) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            Minecraft.getInstance().getItemRenderer().renderStatic(
                    FIRE_PROJECTILE_STACK,
                    ItemDisplayContext.NONE,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    bufferSource,
                    entity.level(),
                    entity.getId()
            );
        } else {
            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        }

        poseStack.popPose();
    }
}
