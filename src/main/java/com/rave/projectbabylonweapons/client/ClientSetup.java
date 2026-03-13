package com.rave.projectbabylonweapons.client;

import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonweapons.client.input.PBKeyMappings;
import com.rave.projectbabylonweapons.network.PBNetworkManager;
import com.rave.projectbabylonweapons.network.CPPullOwnerToTarget;
import com.rave.projectbabylonweapons.network.CPPullTargetToOwner;
import com.rave.projectbabylonweapons.skill.weapon_innate.SickleThrowSkill;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProjectBabylonWeapons.MODID, value = Dist.CLIENT)
public class ClientSetup {
    @Mod.EventBusSubscriber(modid = ProjectBabylonWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
            PBKeyMappings.register(event);
        }
    }


    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        boolean hasProjectile = SickleThrowSkill.hasActiveProjectile(mc.player.getUUID());
        if (!hasProjectile) return;


        while (PBKeyMappings.PULL_OWNER_TO_TARGET.consumeClick()) {
            PBNetworkManager.sendToServer(new CPPullOwnerToTarget());
        }


        while (PBKeyMappings.PULL_TARGET_TO_OWNER.consumeClick()) {
            PBNetworkManager.sendToServer(new CPPullTargetToOwner());
        }
    }
}
