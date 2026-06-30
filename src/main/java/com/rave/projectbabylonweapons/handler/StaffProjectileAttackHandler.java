package com.rave.projectbabylonweapons.handler;

import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonweapons.item.MagicProjectileStaffWeapon;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.eventlistener.AttackPhaseEndEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = ProjectBabylonWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class StaffProjectileAttackHandler {
    private static final UUID STAFF_PROJECTILE_PHASE_LISTENER = UUID.fromString("6c4702cf-64ee-4d8b-99be-b9fb1aa2d1f1");
    private static final Map<UUID, Integer> REGISTERED_PATCH_IDENTITIES = new ConcurrentHashMap<>();

    private StaffProjectileAttackHandler() {
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.player.level().isClientSide) {
            return;
        }

        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(event.player, PlayerPatch.class);
        if (playerPatch == null) {
            return;
        }

        int patchIdentity = System.identityHashCode(playerPatch.getEventListener());
        Integer previousIdentity = REGISTERED_PATCH_IDENTITIES.put(event.player.getUUID(), patchIdentity);
        if (previousIdentity == null || previousIdentity != patchIdentity) {
            playerPatch.getEventListener().addEventListener(
                    PlayerEventListener.EventType.ATTACK_PHASE_END_EVENT,
                    STAFF_PROJECTILE_PHASE_LISTENER,
                    StaffProjectileAttackHandler::onAttackPhaseEnd
            );
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        REGISTERED_PATCH_IDENTITIES.remove(event.getEntity().getUUID());

        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(event.getEntity(), PlayerPatch.class);
        if (playerPatch != null) {
            playerPatch.getEventListener().removeListener(
                    PlayerEventListener.EventType.ATTACK_PHASE_END_EVENT,
                    STAFF_PROJECTILE_PHASE_LISTENER
            );
        }
    }

    private static void onAttackPhaseEnd(AttackPhaseEndEvent event) {
        if (event.getPlayerPatch() == null || event.getPlayerPatch().getOriginal() == null) {
            return;
        }

        Player player = event.getPlayerPatch().getOriginal();
        if (player.level().isClientSide) {
            return;
        }

        if (!(player.getMainHandItem().getItem() instanceof MagicProjectileStaffWeapon weapon)) {
            return;
        }

        if (!weapon.shouldFireMagicProjectile(event.getAnimation(), event.getPhase(), event.getPhaseOrder())) {
            return;
        }

        BattleWandPassiveHooks.onAttackPhaseEnd(event.getPlayerPatch(), player.getMainHandItem());
        weapon.fireMagicProjectiles(event.getPlayerPatch(), player.getMainHandItem(), event);
    }
}
