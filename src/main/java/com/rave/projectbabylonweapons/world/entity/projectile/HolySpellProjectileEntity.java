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

public class HolySpellProjectileEntity extends BasicSpellProjectileEntity {
    private static final RawAnimation HOLY_LOOP_ANIMATION = RawAnimation.begin().thenLoop("animation.holy_spell_projectile.idle");
    private static final int IMPACT_END_ROD_COUNT = 26;

    public HolySpellProjectileEntity(EntityType<? extends HolySpellProjectileEntity> type, Level level) {
        super(type, level);
    }

    public HolySpellProjectileEntity(Level level) {
        this(PBModEntities.HOLY_SPELL_PROJECTILE.get(), level);
    }

    public HolySpellProjectileEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(PBModEntities.HOLY_SPELL_PROJECTILE.get(), level);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "loop_controller", 0, state -> {
            state.getController().setAnimation(HOLY_LOOP_ANIMATION);
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
        Vec3 center = this.position().subtract(normalized.scale(0.3D));
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

        float angle = this.tickCount * 0.45F;
        Vec3 spiralOffset = right.scale(Math.cos(angle) * 0.1D).add(up.scale(Math.sin(angle) * 0.1D));
        Vec3 oppositeOffset = spiralOffset.scale(-1.0D);

        this.level().addParticle(ParticleTypes.END_ROD,
                center.x + spiralOffset.x,
                center.y + spiralOffset.y,
                center.z + spiralOffset.z,
                0.0D, 0.0D, 0.0D);
        this.level().addParticle(ParticleTypes.END_ROD,
                center.x + oppositeOffset.x,
                center.y + oppositeOffset.y,
                center.z + oppositeOffset.z,
                0.0D, 0.0D, 0.0D);
    }

    @Override
    protected void spawnImpactParticles(Vec3 hitPos) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        serverLevel.sendParticles(ParticleTypes.END_ROD,
                hitPos.x, hitPos.y, hitPos.z,
                IMPACT_END_ROD_COUNT,
                0.32D, 0.32D, 0.32D,
                0.02D);
    }
}
