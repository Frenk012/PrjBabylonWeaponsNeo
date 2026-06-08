package com.rave.projectbabylonweapons.world.entity.projectile;

import com.rave.projectbabylonweapons.init.PBModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class FireSpellProjectileEntity extends BasicSpellProjectileEntity {
    private static final RawAnimation FIRE_LOOP_ANIMATION = RawAnimation.begin().thenLoop("fire_spell_projectile.idle");
    private static final int IMPACT_FLAME_COUNT = 24;
    private static final int IMPACT_SMOKE_COUNT = 14;

    public FireSpellProjectileEntity(EntityType<? extends FireSpellProjectileEntity> type, Level level) {
        super(type, level);
    }

    public FireSpellProjectileEntity(Level level) {
        this(PBModEntities.FIRE_SPELL_PROJECTILE.get(), level);
    }

    public FireSpellProjectileEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(PBModEntities.FIRE_SPELL_PROJECTILE.get(), level);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "loop_controller", 0, state -> {
            state.getController().setAnimation(FIRE_LOOP_ANIMATION);
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
        Vec3 flameOffset = right.scale(Math.cos(angle) * 0.14D).add(up.scale(Math.sin(angle) * 0.14D));
        Vec3 smokeOffset = flameOffset.scale(-0.75D);

        this.level().addParticle(ParticleTypes.FLAME,
                center.x + flameOffset.x,
                center.y + flameOffset.y,
                center.z + flameOffset.z,
                0.0D, 0.01D, 0.0D);
        this.level().addParticle(ParticleTypes.SMOKE,
                center.x + smokeOffset.x,
                center.y + smokeOffset.y,
                center.z + smokeOffset.z,
                0.0D, 0.0D, 0.0D);
    }

    @Override
    protected void spawnImpactParticles(Vec3 hitPos) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        serverLevel.sendParticles(ParticleTypes.FLAME,
                hitPos.x, hitPos.y, hitPos.z,
                IMPACT_FLAME_COUNT,
                0.3D, 0.3D, 0.3D,
                0.06D);
        serverLevel.sendParticles(ParticleTypes.SMOKE,
                hitPos.x, hitPos.y, hitPos.z,
                IMPACT_SMOKE_COUNT,
                0.22D, 0.22D, 0.22D,
                0.03D);
    }
}
