package com.rave.projectbabylonweapons.client;

import com.rave.projectbabylonmaterials.client.photon.PBMPhotonEffectHelper;
import com.rave.projectbabylonweapons.world.entity.effect.FireStormEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public final class PhotonWeaponEffectHelper {
    private PhotonWeaponEffectHelper() {
    }

    public static void startDragonDescendCast(Entity entity) {
        PBMPhotonEffectHelper.startDragonDescendCast(entity);
    }

    public static void burstDragonDescendCast(Entity entity) {
        PBMPhotonEffectHelper.burstDragonDescendCast(entity);
    }

    public static void stopDragonDescendCast(Entity entity) {
        PBMPhotonEffectHelper.stopDragonDescendCast(entity);
    }

    public static void startGlacierCast(Entity entity) {
        PBMPhotonEffectHelper.startGlacierCast(entity);
    }

    public static void stopGlacierCast(Entity entity) {
        PBMPhotonEffectHelper.stopGlacierCast(entity);
    }

    public static void startFireStormCast(Entity entity) {
        PBMPhotonEffectHelper.startFireStormCast(entity);
    }

    public static void burstFireStormCast(Entity entity) {
        PBMPhotonEffectHelper.burstFireStormCast(entity);
    }

    public static void stopFireStormCast(Entity entity) {
        PBMPhotonEffectHelper.stopFireStormCast(entity);
    }

    public static void spawnFireStorm(FireStormEntity entity) {
        PBMPhotonEffectHelper.spawnFireStorm(entity, entity.getVisualProgress(), entity.getVisualHeight(), entity.getVisualRadius(), entity.tickCount);
    }

    public static void startBlessingCast(Entity entity) {
        PBMPhotonEffectHelper.startBlessingCast(entity);
    }

    public static void burstBlessingCast(Entity entity) {
        PBMPhotonEffectHelper.burstBlessingCast(entity);
    }

    public static void stopBlessingCast(Entity entity) {
        PBMPhotonEffectHelper.stopBlessingCast(entity);
    }

    public static void spawnGlacierContactWave(Entity entity) {
        PBMPhotonEffectHelper.spawnGlacierContactWave(entity);
    }

    public static void spawnDragonDescendFlight(Entity projectile, Vec3 movement) {
        PBMPhotonEffectHelper.spawnDragonDescendFlight(projectile, movement);
    }

    public static void spawnEnderProjectileFlight(Entity projectile, Vec3 movement) {
        PBMPhotonEffectHelper.spawnEnderProjectileFlight(projectile, movement);
    }

    public static void spawnEnderProjectileImpact(Entity projectile, Vec3 hitPos) {
        PBMPhotonEffectHelper.spawnEnderProjectileImpact(projectile, hitPos);
    }

    public static void spawnHolyProjectileFlight(Entity projectile, Vec3 movement) {
        PBMPhotonEffectHelper.spawnHolyProjectileFlight(projectile, movement);
    }

    public static void spawnHolyProjectileImpact(Entity projectile, Vec3 hitPos) {
        PBMPhotonEffectHelper.spawnHolyProjectileImpact(projectile, hitPos);
    }

    public static void spawnIceProjectileFlight(Entity projectile, Vec3 movement) {
        PBMPhotonEffectHelper.spawnIceProjectileFlight(projectile, movement);
    }

    public static void spawnIceProjectileImpact(Entity projectile, Vec3 hitPos) {
        PBMPhotonEffectHelper.spawnIceProjectileImpact(projectile, hitPos);
    }

    public static void spawnFireProjectileFlight(Entity projectile, Vec3 movement) {
        PBMPhotonEffectHelper.spawnFireProjectileFlight(projectile, movement);
    }

    public static void spawnFireProjectileImpact(Entity projectile, Vec3 hitPos) {
        PBMPhotonEffectHelper.spawnFireProjectileImpact(projectile, hitPos);
    }

    public static void spawnGoldenProjectileFlight(Entity projectile, Vec3 movement) {
        PBMPhotonEffectHelper.spawnGoldenProjectileFlight(projectile, movement);
    }

    public static void spawnGoldenProjectileImpact(Entity projectile, Vec3 hitPos) {
        PBMPhotonEffectHelper.spawnGoldenProjectileImpact(projectile, hitPos);
    }

    public static void spawnDiamondProjectileFlight(Entity projectile, Vec3 movement) {
        PBMPhotonEffectHelper.spawnDiamondProjectileFlight(projectile, movement);
    }

    public static void spawnDiamondProjectileImpact(Entity projectile, Vec3 hitPos) {
        PBMPhotonEffectHelper.spawnDiamondProjectileImpact(projectile, hitPos);
    }

    public static void spawnBlessingHealPulse(Entity entity) {
        PBMPhotonEffectHelper.spawnBlessingHealPulse(entity);
    }

    public static void spawnBlessingAbsorptionPulse(Entity entity) {
        PBMPhotonEffectHelper.spawnBlessingAbsorptionPulse(entity);
    }

    public static void spawnAbsorptionShield(LivingEntity entity, float progress, int tick, float absorptionAmount) {
        PBMPhotonEffectHelper.spawnAbsorptionShield(entity, progress, tick, absorptionAmount);
    }
}
