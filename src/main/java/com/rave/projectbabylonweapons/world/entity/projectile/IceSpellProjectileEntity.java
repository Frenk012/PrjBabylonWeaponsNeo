package com.rave.projectbabylonweapons.world.entity.projectile;

import com.rave.projectbabylonweapons.init.PBModEntities;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class IceSpellProjectileEntity extends BasicSpellProjectileEntity {
    private static final RawAnimation ICE_LOOP_ANIMATION = RawAnimation.begin().thenLoop("animation.ice_spell_projectile.idle");
    private static final int IMPACT_SNOWFLAKE_COUNT = 28;
    private static final int IMPACT_SNOW_DUST_COUNT = 16;

    public IceSpellProjectileEntity(EntityType<? extends IceSpellProjectileEntity> type, Level level) {
        super(type, level);
    }

    public IceSpellProjectileEntity(Level level) {
        this(PBModEntities.ICE_SPELL_PROJECTILE.get(), level);
    }

    public IceSpellProjectileEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(PBModEntities.ICE_SPELL_PROJECTILE.get(), level);
    }

    @Override
    protected void onHit(HitResult result) {
        if (!this.level().isClientSide && result.getType() != HitResult.Type.MISS) {
            this.spawnImpactParticles(result.getLocation());
        }
        super.onHit(result);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "loop_controller", 0, state -> {
            state.getController().setAnimation(ICE_LOOP_ANIMATION);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    protected void spawnClientParticles() {
        Vec3 movement = this.getDeltaMovement();
        if (movement.lengthSqr() < 1.0E-5D) {
            return;
        }

        Vec3 normalized = movement.normalize();
        Vec3 center = this.position().subtract(normalized.scale(0.35D));
        Vec3 right = new Vec3(-normalized.z, 0.0D, normalized.x);
        if (right.lengthSqr() < 1.0E-6D) {
            right = new Vec3(1.0D, 0.0D, 0.0D);
        } else {
            right = right.normalize();
        }

        Vec3 up = normalized.cross(right);
        if (up.lengthSqr() < 1.0E-6D) {
            up = new Vec3(0.0D, 1.0D, 0.0D);
        } else {
            up = up.normalize();
        }

        float angle = this.tickCount * 0.6F;
        Vec3 spiralOffset = right.scale(Math.cos(angle) * 0.12D).add(up.scale(Math.sin(angle) * 0.12D));
        Vec3 oppositeOffset = spiralOffset.scale(-1.0D);

        this.level().addParticle(ParticleHelper.SNOWFLAKE,
                center.x + spiralOffset.x,
                center.y + spiralOffset.y,
                center.z + spiralOffset.z,
                0.0D, 0.005D, 0.0D);
        this.level().addParticle(ParticleHelper.SNOW_DUST,
                center.x + oppositeOffset.x,
                center.y + oppositeOffset.y,
                center.z + oppositeOffset.z,
                0.0D, 0.0D, 0.0D);
    }

    protected void spawnImpactParticles(Vec3 hitPos) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        serverLevel.sendParticles(ParticleHelper.SNOWFLAKE,
                hitPos.x, hitPos.y, hitPos.z,
                IMPACT_SNOWFLAKE_COUNT,
                0.35D, 0.35D, 0.35D,
                0.05D);
        serverLevel.sendParticles(ParticleHelper.SNOW_DUST,
                hitPos.x, hitPos.y, hitPos.z,
                IMPACT_SNOW_DUST_COUNT,
                0.25D, 0.25D, 0.25D,
                0.03D);
    }
}

