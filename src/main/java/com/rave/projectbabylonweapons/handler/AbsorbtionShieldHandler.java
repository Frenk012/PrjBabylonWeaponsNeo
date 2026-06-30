package com.rave.projectbabylonweapons.handler;

import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonweapons.client.PhotonWeaponEffectHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = ProjectBabylonWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class AbsorbtionShieldHandler {
    private static final float APPEAR_STEP = 0.12F;
    private static final float DISAPPEAR_STEP = 0.08F;
    private static final Map<Integer, ShieldState> ACTIVE_SHIELDS = new ConcurrentHashMap<>();

    private AbsorbtionShieldHandler() {
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) {
            ACTIVE_SHIELDS.clear();
            return;
        }

        Set<Integer> seenEntities = new HashSet<>();
        for (Entity rawEntity : level.entitiesForRendering()) {
            if (!(rawEntity instanceof LivingEntity entity) || !entity.isAlive()) {
                continue;
            }

            int entityId = entity.getId();
            seenEntities.add(entityId);
            ShieldState state = ACTIVE_SHIELDS.get(entityId);
            boolean hasAbsorption = entity.getAbsorptionAmount() > 0.0F;
            if (!hasAbsorption && state == null) {
                continue;
            }

            if (state == null) {
                state = new ShieldState();
                ACTIVE_SHIELDS.put(entityId, state);
            }

            if (hasAbsorption) {
                state.lastAbsorptionAmount = entity.getAbsorptionAmount();
                state.progress = Math.min(1.0F, state.progress + APPEAR_STEP);
            } else {
                state.progress = Math.max(0.0F, state.progress - DISAPPEAR_STEP);
            }

            if (state.progress <= 0.0F) {
                ACTIVE_SHIELDS.remove(entityId);
                continue;
            }

            PhotonWeaponEffectHelper.spawnAbsorptionShield(entity, state.progress, state.tick, state.lastAbsorptionAmount);
            state.tick++;
        }

        ACTIVE_SHIELDS.entrySet().removeIf(entry -> {
            if (seenEntities.contains(entry.getKey())) {
                return false;
            }

            Entity entity = level.getEntity(entry.getKey());
            return entity == null || !entity.isAlive();
        });
    }

    @SubscribeEvent
    public static void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        ACTIVE_SHIELDS.clear();
    }

    private static final class ShieldState {
        private float progress;
        private float lastAbsorptionAmount;
        private int tick;
    }
}
