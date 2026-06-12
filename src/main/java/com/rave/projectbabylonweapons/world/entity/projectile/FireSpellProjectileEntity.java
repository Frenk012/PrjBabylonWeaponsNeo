package com.rave.projectbabylonweapons.world.entity.projectile;

import com.rave.projectbabylonweapons.client.PhotonWeaponEffectHelper;
import com.rave.projectbabylonweapons.init.PBModEntities;
import net.minecraft.core.particles.ParticleTypes;
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
        PhotonWeaponEffectHelper.spawnFireProjectileFlight(this, this.getDeltaMovement());
    }

    @Override
    protected void onHit(HitResult result) {
        if (this.level().isClientSide && result.getType() != HitResult.Type.MISS) {
            PhotonWeaponEffectHelper.spawnFireProjectileImpact(this, result.getLocation());
        }
        super.onHit(result);
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
