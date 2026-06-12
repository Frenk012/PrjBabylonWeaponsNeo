package com.rave.projectbabylonweapons.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.rave.projectbabylonweapons.client.model.HolyMagicalSealEntityModel;
import com.rave.projectbabylonweapons.world.entity.effect.HolyMagicalSealEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.UUID;

public class HolyMagicalSealRenderer extends GeoEntityRenderer<HolyMagicalSealEntity> {
    private static final double Y_OFFSET = 0.02D;

    public HolyMagicalSealRenderer(EntityRendererProvider.Context context) {
        super(context, new HolyMagicalSealEntityModel());
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(HolyMagicalSealEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        SealRenderState renderState = this.computeRenderState(entity, partialTick);
        float renderYaw = renderState != null ? renderState.targetYaw() : Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());

        poseStack.pushPose();
        if (renderState != null) {
            poseStack.translate(renderState.offset().x, renderState.offset().y, renderState.offset().z);
        }
        poseStack.mulPose(Axis.YP.rotationDegrees(-renderYaw));
        super.render(entity, renderYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }

    private SealRenderState computeRenderState(HolyMagicalSealEntity entity, float partialTick) {
        UUID targetUuid = entity.getTargetUuid();
        if (targetUuid == null) {
            return null;
        }

        Player target = entity.level().getPlayerByUUID(targetUuid);
        if (target == null) {
            return null;
        }

        double targetX = Mth.lerp(partialTick, target.xo, target.getX());
        double targetY = Mth.lerp(partialTick, target.yo, target.getY()) + Y_OFFSET;
        double targetZ = Mth.lerp(partialTick, target.zo, target.getZ());

        double currentX = Mth.lerp(partialTick, entity.xo, entity.getX());
        double currentY = Mth.lerp(partialTick, entity.yo, entity.getY());
        double currentZ = Mth.lerp(partialTick, entity.zo, entity.getZ());

        Vec3 offset = new Vec3(targetX - currentX, targetY - currentY, targetZ - currentZ);
        float targetYaw = Mth.rotLerp(partialTick, target.yRotO, target.getYRot());
        return new SealRenderState(offset, targetYaw);
    }

    private record SealRenderState(Vec3 offset, float targetYaw) {
    }
}
