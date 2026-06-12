package com.rave.projectbabylonweapons.client;

import com.rave.projectbabylonweapons.handler.WeaponVisualEffectHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public final class WeaponVisualEffectClientHelper {
    private WeaponVisualEffectClientHelper() {
    }

    public static void play(String effectId, int entityId) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return;
        }

        Entity entity = minecraft.level.getEntity(entityId);
        if (entity == null) {
            return;
        }

        if (WeaponVisualEffectHelper.DRAGON_DESCEND_CAST_START.equals(effectId)) {
            PhotonWeaponEffectHelper.startDragonDescendCast(entity);
            return;
        }

        if (WeaponVisualEffectHelper.DRAGON_DESCEND_CAST_BURST.equals(effectId)) {
            PhotonWeaponEffectHelper.burstDragonDescendCast(entity);
            return;
        }

        if (WeaponVisualEffectHelper.DRAGON_DESCEND_CAST_STOP.equals(effectId)) {
            PhotonWeaponEffectHelper.stopDragonDescendCast(entity);
        }
    }
}
