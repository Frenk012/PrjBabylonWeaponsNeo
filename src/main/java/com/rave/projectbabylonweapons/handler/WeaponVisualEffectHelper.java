package com.rave.projectbabylonweapons.handler;

import com.rave.projectbabylonweapons.network.PBNetworkManager;
import com.rave.projectbabylonweapons.network.SPPlayWeaponVisualEffect;
import net.minecraft.world.entity.Entity;

public final class WeaponVisualEffectHelper {
    public static final String DRAGON_DESCEND_CAST_START = "dragon_descend_cast_start";
    public static final String DRAGON_DESCEND_CAST_BURST = "dragon_descend_cast_burst";
    public static final String DRAGON_DESCEND_CAST_STOP = "dragon_descend_cast_stop";

    private WeaponVisualEffectHelper() {
    }

    public static void startDragonDescendCast(Entity entity) {
        play(entity, DRAGON_DESCEND_CAST_START);
    }

    public static void burstDragonDescendCast(Entity entity) {
        play(entity, DRAGON_DESCEND_CAST_BURST);
    }

    public static void stopDragonDescendCast(Entity entity) {
        play(entity, DRAGON_DESCEND_CAST_STOP);
    }

    private static void play(Entity entity, String effectId) {
        if (entity == null || entity.level().isClientSide) {
            return;
        }

        PBNetworkManager.sendToTrackingAndSelf(entity, new SPPlayWeaponVisualEffect(effectId, entity.getId()));
    }
}
